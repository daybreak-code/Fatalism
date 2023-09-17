package cn.daycode.fatalism.transaction.service;

import cn.daycode.fatalism.api.account.model.LoginUser;
import cn.daycode.fatalism.api.consumer.model.BalanceDetailsDTO;
import cn.daycode.fatalism.api.consumer.model.ConsumerDTO;
import cn.daycode.fatalism.api.depository.model.LoanDetailRequest;
import cn.daycode.fatalism.api.depository.model.LoanRequest;
import cn.daycode.fatalism.api.depository.model.UserAutoPreTransactionRequest;
import cn.daycode.fatalism.api.repayment.model.ProjectWithTendersDTO;
import cn.daycode.fatalism.api.transaction.model.*;
import cn.daycode.fatalism.common.domain.*;
import cn.daycode.fatalism.common.util.CodeNoUtil;
import cn.daycode.fatalism.common.util.CommonUtil;
import cn.daycode.fatalism.transaction.agent.ConsumerApiAgent;
import cn.daycode.fatalism.transaction.agent.ContentSearchApiAgent;
import cn.daycode.fatalism.transaction.agent.DepositoryAgentApiAgent;
import cn.daycode.fatalism.transaction.common.constant.TradingCode;
import cn.daycode.fatalism.transaction.common.constant.TransactionErrorCode;
import cn.daycode.fatalism.transaction.common.utils.IncomeCalcUtil;
import cn.daycode.fatalism.transaction.common.utils.SecurityUtil;
import cn.daycode.fatalism.transaction.entity.Project;
import cn.daycode.fatalism.transaction.entity.Tender;
import cn.daycode.fatalism.transaction.mapper.ProjectMapper;
import cn.daycode.fatalism.transaction.mapper.TenderMapper;
import cn.daycode.fatalism.transaction.message.P2pTransactionProducer;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private ConsumerApiAgent consumerApiAgent;

    @Autowired
    private DepositoryAgentApiAgent depositoryAgentApiAgent;

    @Autowired
    private ContentSearchApiAgent contentSearchApiAgent;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TenderMapper tenderMapper;

    @Autowired
    private P2pTransactionProducer p2pTransactionProducer;


    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        RestResponse<ConsumerDTO> restResponse = consumerApiAgent.getCurrConsumer(SecurityUtil.getUser().getMobile());
        projectDTO.setConsumerId(restResponse.getResult().getId());
        projectDTO.setUserNo(restResponse.getResult().getUserNo());
        projectDTO.setProjectNo(CodeNoUtil.getNo(CodePrefixCode.CODE_PROJECT_PREFIX));
        projectDTO.setProjectStatus(ProjectCode.COLLECTING.name());
        projectDTO.setStatus(StatusCode.STATUS_OUT.getCode());
        projectDTO.setCreateDate(LocalDateTime.now());
        projectDTO.setRepaymentWay(RepaymentWayCode.FIXED_REPAYMENT.name());
        projectDTO.setType("NEW");
        Project project=convertProjectDTOToEntity(projectDTO);
        project.setBorrowerAnnualRate(configService.getBorrowerAnnualRate());
        project.setAnnualRate(configService.getAnnualRate());
        project.setCommissionAnnualRate(configService.getCommissionAnnualRate());
        project.setIsAssignment(0);

        String sex = Integer.parseInt(restResponse.getResult().getIdNumber()
                .substring(16, 17)) % 2 == 0 ? "女士" : "先生";

        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Project::getConsumerId,
                restResponse.getResult().getId());
        project.setName(restResponse.getResult().getFullName() + sex
                + "第" + (count(queryWrapper) + 1) + "次借款");
        save(project);

        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        return projectDTO;
    }

    @Override
    public PageVO<ProjectDTO> queryProjectsByQueryDTO(ProjectQueryDTO projectQueryDTO, String order, Integer pageNo, Integer pageSize, String sortBy) {

        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();

        if (StringUtils.isNotBlank(projectQueryDTO.getType())) {
            queryWrapper.lambda().eq(Project::getType, projectQueryDTO.getType());
        }
        if (null != projectQueryDTO.getStartAnnualRate()) {
            queryWrapper.lambda().ge(Project::getAnnualRate,
                    projectQueryDTO.getStartAnnualRate());
        } if (null != projectQueryDTO.getEndAnnualRate()) {
            queryWrapper.lambda().le(Project::getAnnualRate,
                    projectQueryDTO.getStartAnnualRate());
        }
        if (null != projectQueryDTO.getStartPeriod()) {
            queryWrapper.lambda().ge(Project::getPeriod,
                    projectQueryDTO.getStartPeriod());
        } if (null != projectQueryDTO.getEndPeriod()) {
            queryWrapper.lambda().le(Project::getPeriod,
                    projectQueryDTO.getEndPeriod());
        }
        if (StringUtils.isNotBlank(projectQueryDTO.getProjectStatus())) {
            queryWrapper.lambda().eq(Project::getProjectStatus,
                    projectQueryDTO.getProjectStatus());
        }

        Page<Project> page = new Page<>(pageNo, pageSize);

        if(StringUtils.isNotBlank(order)&&StringUtils.isNotBlank(sortBy)){
            if(order.toLowerCase().equals("asc")){
                queryWrapper.orderByAsc(sortBy);
            }else if(order.toLowerCase().equals("desc")){
                queryWrapper.orderByDesc(sortBy);
            }
        }else{
            queryWrapper.lambda().orderByDesc(Project::getCreateDate);
        }

        IPage<Project> iPage=page(page,queryWrapper);

        List<ProjectDTO> projectDTOList=convertProjectEntityListToDTOList(iPage.getRecords());
        return  new PageVO<>(projectDTOList,iPage.getTotal(),pageNo, pageSize);

    }

    @Override
    public String projectsApprovalStatus(Long id, String approveStatus) {
        Project project= getById(id);
        ProjectDTO projectDTO=convertProjectEntityToDTO(project);
        if(StringUtils.isBlank(project.getRequestNO())){
            projectDTO.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
            update(Wrappers.<Project>lambdaUpdate().set(Project::getRequestNO,
                    projectDTO.getRequestNo()).eq(Project::getId,id));
        }

        RestResponse<String> restResponse=depositoryAgentApiAgent.createProject(projectDTO);

        if(DepositoryReturnCode.RETURN_CODE_00000.getCode()
                .equals(restResponse.getResult())){
            update(Wrappers.<Project>lambdaUpdate().set(Project::getStatus,Integer.parseInt(approveStatus)).eq(Project::getId,id));
            return "success";
        }

        throw  new BusinessException(TransactionErrorCode.E_150113);
    }

    private List<ProjectDTO> convertProjectEntityListToDTOList(java.util.List<Project>
                                                                       projectList) {
        if (projectList == null) {
            return null;
        }
        List<ProjectDTO> dtoList = new ArrayList<>();
        projectList.forEach(project -> {
            ProjectDTO projectDTO = new ProjectDTO();
            BeanUtils.copyProperties(project, projectDTO);
            dtoList.add(projectDTO);
        });
        return dtoList;
    }

    private Project convertProjectDTOToEntity(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return null;
        }
        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);
        return project;
    }

    private ProjectDTO convertProjectEntityToDTO(Project project) {
        if (project == null) {
            return null;
        }
        ProjectDTO projectDTO = new ProjectDTO();
        BeanUtils.copyProperties(project, projectDTO);
        return projectDTO;
    }

    @Override
    public PageVO<ProjectDTO> queryProjects(ProjectQueryDTO projectQueryDTO,
                                            String order, Integer pageNo, Integer pageSize, String sortBy) {
        RestResponse<PageVO<ProjectDTO>> esResponse = contentSearchApiAgent.queryProjectIndex(projectQueryDTO, pageNo, pageSize, sortBy, order);
        if (!esResponse.isSuccessful()) {
            throw new BusinessException(CommonErrorCode.UNKOWN);
        }
        return esResponse.getResult();
    }

    @Override
    public List<ProjectDTO> queryProjectsIds(String ids) {
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        List<Long> list=new ArrayList<>();
        Arrays.asList(ids.split(",")).forEach(str->{
            list.add(Long.parseLong(str));
        });
        queryWrapper.lambda().in(Project::getId,list); // .... where  id  in  (1,2,3,4,5)
        List<Project> projects=list(queryWrapper);
        List<ProjectDTO> dtos=new ArrayList<>();
        for(Project project:projects){
            ProjectDTO projectDTO=convertProjectEntityToDTO(project);
            projectDTO.setRemainingAmount(getProjectRemainingAmount(project));
            projectDTO.setTenderCount(tenderMapper.selectCount(Wrappers.<Tender>lambdaQuery().eq(Tender::getProjectId, project.getId())));
            dtos.add(projectDTO);
        }
        return dtos;

    }

    @Override
    public List<TenderOverviewDTO> queryTendersByProjectId(Long id) {
        List<Tender> tenderList = tenderMapper.selectList(Wrappers.<Tender>lambdaQuery().eq(Tender::getProjectId,id));
        List<TenderOverviewDTO> tenderOverviewDTOS=new ArrayList<>();
        tenderList.forEach(tender -> {
            TenderOverviewDTO tenderOverviewDTO=new TenderOverviewDTO();
            BeanUtils.copyProperties(tender,tenderOverviewDTO);
            tenderOverviewDTO.setConsumerUsername(CommonUtil.hiddenMobile(tenderOverviewDTO.getConsumerUsername()));
            tenderOverviewDTOS.add(tenderOverviewDTO);
        });
        return tenderOverviewDTOS;
    }

    @Override
    public TenderDTO createTender(ProjectInvestDTO projectInvestDTO) {
        BigDecimal amount=new BigDecimal(projectInvestDTO.getAmount());
        BigDecimal miniInvestmentAmount=configService.getMiniInvestmentAmount();
        if(amount.compareTo(miniInvestmentAmount)<0){
            throw  new BusinessException(TransactionErrorCode.E_150109);
        }
        LoginUser user= SecurityUtil.getUser();
        RestResponse<ConsumerDTO> restResponse=consumerApiAgent.getCurrConsumer(user.getMobile());
        RestResponse<BalanceDetailsDTO> balanceDetailsDTORestResponse=consumerApiAgent.getBalance(restResponse.getResult().getUserNo());
        BigDecimal myBalance=balanceDetailsDTORestResponse.getResult().getBalance();
        if(myBalance.compareTo(amount)<0){
            throw  new BusinessException(TransactionErrorCode.E_150112);
        }

        Project project = getById(projectInvestDTO.getId());
        if(project.getProjectStatus().equalsIgnoreCase(ProjectCode.FULLY.name())){
            throw  new BusinessException(TransactionErrorCode.E_150114);
        }

        BigDecimal remainingAmount = getProjectRemainingAmount(project);
        if(amount.compareTo(remainingAmount)<1){
            BigDecimal subtract=remainingAmount.subtract(amount);
            int result=subtract.compareTo(configService.getMiniInvestmentAmount());
            if(result<0){
                if(subtract.compareTo(new BigDecimal("0.0"))!=0){
                    throw new BusinessException(TransactionErrorCode.E_150111);
                }
            }


            final Tender tender = new Tender();
            tender.setAmount(amount);
            tender.setConsumerId(restResponse.getResult().getId());
            tender.setConsumerUserName(restResponse.getResult().getUserName());
            tender.setUserNo(restResponse.getResult().getUserNo());
            tender.setProjectId(projectInvestDTO.getId());
            tender.setProjectNo(project.getProjectNo());
            tender.setTenderStatus(TradingCode.FROZEN.getCode());
            tender.setCreateDate(LocalDateTime.now());
            tender.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
            tender.setStatus(0);
            tender.setProjectName(project.getName());
            tender.setProjectPeriod(project.getPeriod());
            tender.setProjectAnnualRate(project.getAnnualRate());
            tenderMapper.insert(tender);

            UserAutoPreTransactionRequest userAutoPreTransactionRequest = new
                    UserAutoPreTransactionRequest();
            userAutoPreTransactionRequest.setAmount(amount);
            userAutoPreTransactionRequest.setBizType(PreprocessBusinessTypeCode.TENDER.name());
            userAutoPreTransactionRequest.setProjectNo(project.getProjectNo());
            userAutoPreTransactionRequest.setRequestNo(tender.getRequestNo());
            userAutoPreTransactionRequest.setUserNo(restResponse.getResult().getUserNo());
            userAutoPreTransactionRequest.setId(tender.getId());
            RestResponse<String> response = depositoryAgentApiAgent.userAutoPreTransaction(userAutoPreTransactionRequest);

            if(response.getResult().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode())) {
                tender.setStatus(1);
                tenderMapper.updateById(tender);
                BigDecimal remainAmont=getProjectRemainingAmount(project);
                if(remainAmont.compareTo(new BigDecimal(0))==0){
                    project.setProjectStatus(ProjectCode.FULLY.name());
                    updateById(project);
                }

                TenderDTO tenderDTO=convertTenderEntityToDTO(tender);
                project.setRepaymentWay(RepaymentWayCode.FIXED_REPAYMENT.name());
                tenderDTO.setProject(convertProjectEntityToDTO(project));

                final Double ceil = Math.ceil(project.getPeriod() / 30.0);
                Integer month = ceil.intValue();
                tenderDTO.setExpectedIncome(IncomeCalcUtil.getIncomeTotalInterest(new BigDecimal(projectInvestDTO.getAmount()),configService.getAnnualRate(),month));
                return tenderDTO;
            }
            else{
                throw  new BusinessException(TransactionErrorCode.E_150113);
            }

        }else{
            throw new BusinessException(TransactionErrorCode.E_150110);
        }

    }

    @Override
    public String loansApprovalStatus(Long id, String approveStatus, String commission) {

        Project project=getById(id);
        QueryWrapper<Tender> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(Tender::getProjectId,id);
        List<Tender> tenderList=tenderMapper.selectList(queryWrapper);
        LoanRequest loanRequest=generateLoanRequest(project,tenderList,commission);

        RestResponse<String> restResponse=depositoryAgentApiAgent.confirmLoan(loanRequest);
        if(restResponse.getResult().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode())){
            updateTenderStatusAlreadyLoan(tenderList);

            ModifyProjectStatusDTO modifyProjectStatusDTO=new ModifyProjectStatusDTO();
            modifyProjectStatusDTO.setId(project.getId());
            modifyProjectStatusDTO.setProjectStatus(ProjectCode.REPAYING.name());
            modifyProjectStatusDTO.setRequestNo(loanRequest.getRequestNo());
            modifyProjectStatusDTO.setProjectNo(project.getProjectNo());

            RestResponse<String> modifyProjectStatus = depositoryAgentApiAgent.modifyProjectStatus(modifyProjectStatusDTO);
            if(modifyProjectStatus.getResult().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode())){

                ProjectWithTendersDTO projectWithTendersDTO=new ProjectWithTendersDTO();
                projectWithTendersDTO.setProject(convertProjectEntityToDTO(project));
                projectWithTendersDTO.setTenders(convertTenderEntityListToDTOList(tenderList));
                projectWithTendersDTO.setCommissionInvestorAnnualRate(configService.getCommissionInvestorAnnualRate());
                projectWithTendersDTO.setCommissionBorrowerAnnualRate(configService.getBorrowerAnnualRate());

                p2pTransactionProducer.updateProjectStatusAndStartRepayment(project,projectWithTendersDTO);

                return "审核成功";

            }else{
                throw  new BusinessException(TransactionErrorCode.E_150113);
            }

        }else{
            throw  new BusinessException(TransactionErrorCode.E_150113);
        }



    }

    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public Boolean updateProjectStatusAndStartRepayment(Project project) {
        project.setProjectStatus(ProjectCode.REPAYING.name());
        return updateById(project);
    }


    private void updateTenderStatusAlreadyLoan(List<Tender> tenderList){
        tenderList.forEach(tender -> {
            tender.setTenderStatus(TradingCode.LOAN.getCode());
            tenderMapper.updateById(tender);
        });
    }


    public LoanRequest generateLoanRequest(Project project, List<Tender> tenderList, String commission){
        LoanRequest loanRequest=new LoanRequest();

        loanRequest.setId(project.getId());

        if(StringUtils.isNotBlank(commission)){
            loanRequest.setCommission(new BigDecimal(commission));
        }

        loanRequest.setProjectNo(project.getProjectNo());

        loanRequest.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));

        List<LoanDetailRequest> details=new ArrayList<>();
        tenderList.forEach(tender -> {
            LoanDetailRequest loanDetailRequest=new LoanDetailRequest();
            loanDetailRequest.setAmount(tender.getAmount());
            loanDetailRequest.setPreRequestNo(tender.getRequestNo());
            details.add(loanDetailRequest);
        });

        loanRequest.setDetails(details);

        return loanRequest;

    }

    private List<TenderDTO> convertTenderEntityListToDTOList(List<Tender> records) {
        if (records == null) {
            return null;
        }
        List<TenderDTO> dtoList = new ArrayList<>();
        records.forEach(tender -> {
            TenderDTO tenderDTO = new TenderDTO();
            BeanUtils.copyProperties(tender, tenderDTO);
            dtoList.add(tenderDTO);
        });
        return dtoList;
    }

    private TenderDTO convertTenderEntityToDTO(Tender tender) {
        if (tender == null) {
            return null;
        }
        TenderDTO tenderDTO = new TenderDTO();
        BeanUtils.copyProperties(tender, tenderDTO);
        return tenderDTO;
    }


    private BigDecimal getProjectRemainingAmount(Project project) {
        List<BigDecimal> decimalList =
                tenderMapper.selectAmountInvestedByProjectId(project.getId());
        BigDecimal amountInvested = new BigDecimal("0.0");
        for (BigDecimal d : decimalList) {
            amountInvested = amountInvested.add(d);
        }
        return project.getAmount().subtract(amountInvested);
    }

}
