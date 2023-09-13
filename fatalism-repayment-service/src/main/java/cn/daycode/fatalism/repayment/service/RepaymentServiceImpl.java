package cn.daycode.fatalism.repayment.service;

import cn.daycode.fatalism.api.consumer.model.BorrowerDTO;
import cn.daycode.fatalism.api.depository.model.RepaymentDetailRequest;
import cn.daycode.fatalism.api.depository.model.RepaymentRequest;
import cn.daycode.fatalism.api.depository.model.UserAutoPreTransactionRequest;
import cn.daycode.fatalism.api.repayment.model.ProjectWithTendersDTO;
import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.api.transaction.model.TenderDTO;
import cn.daycode.fatalism.common.domain.*;
import cn.daycode.fatalism.common.util.CodeNoUtil;
import cn.daycode.fatalism.common.util.DateUtil;
import cn.daycode.fatalism.repayment.agent.ConsumerApiAgent;
import cn.daycode.fatalism.repayment.agent.DepositoryApiAgent;
import cn.daycode.fatalism.repayment.entity.ReceivableDetail;
import cn.daycode.fatalism.repayment.entity.ReceivablePlan;
import cn.daycode.fatalism.repayment.entity.RepaymentDetail;
import cn.daycode.fatalism.repayment.entity.RepaymentPlan;
import cn.daycode.fatalism.repayment.mapper.PlanMapper;
import cn.daycode.fatalism.repayment.mapper.ReceivableDetailMapper;
import cn.daycode.fatalism.repayment.mapper.ReceivablePlanMapper;
import cn.daycode.fatalism.repayment.mapper.RepaymentDetailMapper;
import cn.daycode.fatalism.repayment.message.RepaymentProducer;
import cn.daycode.fatalism.repayment.model.EqualInterestRepayment;
import cn.daycode.fatalism.repayment.sms.SmsService;
import cn.daycode.fatalism.repayment.util.RepaymentUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RepaymentServiceImpl implements RepaymentService{

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private ReceivablePlanMapper receivablePlanMapper;

    @Autowired
    private RepaymentDetailMapper repaymentDetailMapper;

    @Autowired
    private DepositoryApiAgent depositoryAgentApiAgent;

    @Autowired
    private ReceivableDetailMapper receivableDetailMapper;

    @Autowired
    private RepaymentProducer repaymentProducer;

    @Autowired
    private ConsumerApiAgent consumerApiAgent;

    @Autowired
    private SmsService smsService;


    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String startRepayment(ProjectWithTendersDTO projectWithTendersDTO) {


        ProjectDTO projectDTO=projectWithTendersDTO.getProject();

        List<TenderDTO> tenders = projectWithTendersDTO.getTenders();

        Double ceil = Math.ceil(projectDTO.getPeriod()/30.0);
        Integer month = ceil.intValue();

        String repaymentWay = projectDTO.getRepaymentWay();

        if(repaymentWay.equals(RepaymentWayCode.FIXED_REPAYMENT)){
            EqualInterestRepayment fixedRepayment = RepaymentUtil.fixedRepayment(projectDTO.getAmount(),projectDTO.getBorrowAnnualRate(),month,projectDTO.getCommissionAnnualRate());

            List<RepaymentPlan> planList = saveRepaymentPlan(projectDTO,fixedRepayment);


            tenders.forEach(tenderDTO -> {
                EqualInterestRepayment receipt = RepaymentUtil.fixedRepayment(tenderDTO.getAmount(),tenderDTO.getProjectAnnualRate(),month,projectWithTendersDTO.getCommissionInvestorAnnualRate());

                planList.forEach(plan -> {
                    saveRreceivablePlan(plan,tenderDTO,receipt);
                });

            });

        }else{
            return "-1";
        }

        return DepositoryReturnCode.RETURN_CODE_00000.getCode();
    }

    @Override
    public List<RepaymentPlan> selectDueRepayment(String date,int shardingCount,int shardingItem) {
        return planMapper.selectDueRepaymentList(date,shardingCount,shardingItem);
    }

    @Override
    public List<RepaymentPlan> selectDueRepayment(String date) {
        return planMapper.selectDueRepayment(date);
    }

    @Override
    public RepaymentDetail saveRepaymentDetail(RepaymentPlan repaymentPlan) {
        //1. 进行查询
        RepaymentDetail repaymentDetail=repaymentDetailMapper.selectOne(Wrappers.<RepaymentDetail>lambdaQuery().eq(RepaymentDetail::getRepaymentPlanId,repaymentPlan.getId()));

        //2. 查不到数据才进行保存
        if(repaymentDetail == null){
            repaymentDetail = new RepaymentDetail();
            // 还款计划项标识
            repaymentDetail.setRepaymentPlanId(repaymentPlan.getId());
            // 实还本息
            repaymentDetail.setAmount(repaymentPlan.getAmount());
            // 实际还款时间
            repaymentDetail.setRepaymentDate(LocalDateTime.now());
            // 请求流水号
            repaymentDetail.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
            // 未同步
            repaymentDetail.setStatus(StatusCode.STATUS_OUT.getCode());
            // 保存数据
            repaymentDetailMapper.insert(repaymentDetail);
        }

        return repaymentDetail;
    }

    @Override
    public void executeRepayment(String date,int shardingCount,int shardingItem) {
        //查询到期的还款计划
        List<RepaymentPlan> repaymentPlanList=selectDueRepayment(date,shardingCount,shardingItem);

        //生成还款明细
        repaymentPlanList.forEach(repaymentPlan -> {
            RepaymentDetail repaymentDetail=saveRepaymentDetail(repaymentPlan);
            System.out.println("当前分片："+shardingItem+"\n"+repaymentPlan);
            //还款预处理
            Boolean proRepaymentResult = preRepayment(repaymentPlan,repaymentDetail.getRequestNo());

            if(proRepaymentResult){
                //未完待续
                System.out.println("还款预处理成功");
                String preRequestNo=repaymentDetail.getRequestNo();
                RepaymentRequest repaymentRequest=generateRepaymentRequest(repaymentPlan,preRequestNo);
                repaymentProducer.confirmRepayment(repaymentPlan,repaymentRequest);
            }
        });


    }

    /**
     * 构造还款信息请求数据
     * @param repaymentPlan
     * @param preRequestNo
     * @return
     */
    private RepaymentRequest generateRepaymentRequest(RepaymentPlan repaymentPlan,
                                                      String preRequestNo) {
        //根据还款计划id,查询应收计划
        List<ReceivablePlan> receivablePlanList = receivablePlanMapper.selectList(Wrappers.<ReceivablePlan>lambdaQuery().eq(ReceivablePlan::getRepaymentId,repaymentPlan.getId()));

        RepaymentRequest repaymentRequest=new RepaymentRequest();
        // 还款总额
        repaymentRequest.setAmount(repaymentPlan.getAmount());
        // 业务实体id
        repaymentRequest.setId(repaymentPlan.getId());
        // 向借款人收取的佣金
        repaymentRequest.setCommission(repaymentPlan.getCommission());
        // 标的编码
        repaymentRequest.setProjectNo(repaymentPlan.getProjectNo());
        // 请求流水号
        repaymentRequest.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
        // 预处理业务流水号
        repaymentRequest.setPreRequestNo(preRequestNo);

        List<RepaymentDetailRequest> detailRequests =new ArrayList<>();
        receivablePlanList.forEach(receivablePlan -> {
            RepaymentDetailRequest detailRequest=new RepaymentDetailRequest();
            // 投资人用户编码
            detailRequest.setUserNo(receivablePlan.getUserNo());
            // 向投资人收取的佣金
            detailRequest.setCommission(receivablePlan.getCommission());
            // 投资人应得本金
            detailRequest.setAmount(receivablePlan.getPrincipal());
            // 投资人应得利息
            detailRequest.setInterest(receivablePlan.getInterest());
            //添加到集合
            detailRequests.add(detailRequest);
        });

        repaymentRequest.setDetails(detailRequests);

        return repaymentRequest;
    }

    @Override
    public Boolean preRepayment(RepaymentPlan repaymentPlan, String preRequestNo) {
        //1. 构造请求数据
        UserAutoPreTransactionRequest userAutoPreTransactionRequest=generateUserAutoPreTransactionRequest(repaymentPlan,preRequestNo);

        //2. 请求存管代理服务
        RestResponse<String> restResponse=depositoryAgentApiAgent.userAutoPreTransaction(userAutoPreTransactionRequest);

        //3. 返回结果
        return restResponse.getResult().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode());
    }

    @Override
    @Transactional
    public Boolean confirmRepayment(RepaymentPlan repaymentPlan, RepaymentRequest repaymentRequest) {
        String preRequestNo=repaymentRequest.getPreRequestNo();
        repaymentDetailMapper.update(null,Wrappers.<RepaymentDetail>lambdaUpdate().set(RepaymentDetail::getStatus,StatusCode.STATUS_IN.getCode()).eq(RepaymentDetail::getRequestNo,preRequestNo));


        List<ReceivablePlan> rereceivablePlanList = receivablePlanMapper.selectList(Wrappers.<ReceivablePlan>lambdaQuery().eq(ReceivablePlan::getRepaymentId,repaymentPlan.getId()));
        rereceivablePlanList.forEach(receivablePlan -> {
            receivablePlan.setReceivableStatus(1);
            receivablePlanMapper.updateById(receivablePlan);


            ReceivableDetail receivableDetail = new ReceivableDetail();
            receivableDetail.setReceivableId(receivablePlan.getId());
            receivableDetail.setAmount(receivablePlan.getAmount());
            receivableDetail.setReceivableDate(DateUtil.now());
            receivableDetailMapper.insert(receivableDetail);
        });

        repaymentPlan.setRepaymentStatus("1");
        int rows = planMapper.updateById(repaymentPlan);
        return rows>0;
    }

    @Override
    public void invokeConfirmRepayment(RepaymentPlan repaymentPlan, RepaymentRequest repaymentRequest) {
        RestResponse<String> repaymentResponse = depositoryAgentApiAgent.confirmRepayment(repaymentRequest);
        if(!repaymentResponse.getResult().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode())){
            throw  new RuntimeException("Repayment Failure");
        }
    }

    @Override
    public void sendRepaymentNotify(String date) {
        List<RepaymentPlan> repaymentPlanList = selectDueRepayment(date);

        repaymentPlanList.forEach(repaymentPlan -> {
            RestResponse<BorrowerDTO> consumerReponse = consumerApiAgent.getBorrowerMobile(repaymentPlan.getConsumerId());

            String mobile = consumerReponse.getResult().getMobile();

            smsService.sendRepaymentNotify(mobile,date,repaymentPlan.getAmount());
        });
    }


    private UserAutoPreTransactionRequest  generateUserAutoPreTransactionRequest(RepaymentPlan repaymentPlan,
                                                                                 String preRequestNo) {
        UserAutoPreTransactionRequest userAutoPreTransactionRequest = new
                UserAutoPreTransactionRequest();
        userAutoPreTransactionRequest.setAmount(repaymentPlan.getAmount());
        userAutoPreTransactionRequest.setBizType(PreprocessBusinessTypeCode.REPAYMENT.name());
        userAutoPreTransactionRequest.setProjectNo(repaymentPlan.getProjectNo());
        userAutoPreTransactionRequest.setRequestNo(preRequestNo);
        userAutoPreTransactionRequest.setUserNo(repaymentPlan.getUserNo());
        userAutoPreTransactionRequest.setId(repaymentPlan.getId());
        return userAutoPreTransactionRequest;
    }


    public List<RepaymentPlan> saveRepaymentPlan(ProjectDTO projectDTO,
                                                 EqualInterestRepayment
                                                         fixedRepayment) {
        List<RepaymentPlan> repaymentPlanList = new ArrayList<>();
        final Map<Integer, BigDecimal> interestMap =
                fixedRepayment.getInterestMap();
        final Map<Integer, BigDecimal> commissionMap =
                fixedRepayment.getCommissionMap();
        fixedRepayment.getPrincipalMap().forEach((k, v) -> {
            final RepaymentPlan repaymentPlan = new RepaymentPlan();
            repaymentPlan.setProjectId(projectDTO.getId());
            repaymentPlan.setConsumerId(projectDTO.getConsumerId());
            repaymentPlan.setUserNo(projectDTO.getUserNo());
            repaymentPlan.setProjectNo(projectDTO.getProjectNo());
            repaymentPlan.setNumberOfPeriods(k);
            repaymentPlan.setInterest(interestMap.get(k));
            repaymentPlan.setPrincipal(v);
            repaymentPlan.setAmount(repaymentPlan.getPrincipal()
                    .add(repaymentPlan.getInterest()));
            repaymentPlan.setShouldRepaymentDate(DateUtil.localDateTimeAddMonth(DateUtil.now(), k));
            repaymentPlan.setRepaymentStatus("0");
            repaymentPlan.setCreateDate(DateUtil.now());
            repaymentPlan.setCommission(commissionMap.get(k));
            planMapper.insert(repaymentPlan);
            repaymentPlanList.add(repaymentPlan);
        });
        return repaymentPlanList;
    }


    private void saveRreceivablePlan(RepaymentPlan repaymentPlan,
                                     TenderDTO tender,
                                     EqualInterestRepayment receipt) {
        final Map<Integer, BigDecimal> principalMap = receipt.getPrincipalMap();
        final Map<Integer, BigDecimal> interestMap = receipt.getInterestMap();
        final Map<Integer, BigDecimal> commissionMap = receipt.getCommissionMap();
        ReceivablePlan receivablePlan = new ReceivablePlan();
        receivablePlan.setTenderId(tender.getId());
        receivablePlan.setNumberOfPeriods(repaymentPlan.getNumberOfPeriods());
        receivablePlan.setConsumerId(tender.getConsumerId());
        receivablePlan.setUserNo(tender.getUserNo());
        receivablePlan.setRepaymentId(repaymentPlan.getId());
        receivablePlan.setInterest(interestMap.get(repaymentPlan
                .getNumberOfPeriods()));
        receivablePlan.setPrincipal(principalMap.get(repaymentPlan
                .getNumberOfPeriods()));
        receivablePlan.setAmount(receivablePlan.getInterest()
                .add(receivablePlan.getPrincipal()));
        receivablePlan.setShouldReceivableDate(repaymentPlan
                .getShouldRepaymentDate());
        receivablePlan.setReceivableStatus(0);
        receivablePlan.setCreateDate(DateUtil.now());
        receivablePlan.setCommission(commissionMap
                .get(repaymentPlan.getNumberOfPeriods()));
        receivablePlanMapper.insert(receivablePlan);
    }
}
