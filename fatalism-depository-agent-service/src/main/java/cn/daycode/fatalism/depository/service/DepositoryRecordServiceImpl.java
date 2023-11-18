package cn.daycode.fatalism.depository.service;

import cn.daycode.fatalism.api.consumer.model.ConsumerRequest;
import cn.daycode.fatalism.api.depository.GatewayRequest;
import cn.daycode.fatalism.api.depository.model.*;
import cn.daycode.fatalism.api.transaction.model.ModifyProjectStatusDTO;
import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.common.cache.Cache;
import cn.daycode.fatalism.common.domain.BusinessException;
import cn.daycode.fatalism.common.domain.PreprocessBusinessTypeCode;
import cn.daycode.fatalism.common.domain.StatusCode;
import cn.daycode.fatalism.common.util.EncryptUtil;
import cn.daycode.fatalism.common.util.RSAUtil;
import cn.daycode.fatalism.depository.common.constant.DepositoryErrorCode;
import cn.daycode.fatalism.depository.common.constant.DepositoryRequestTypeCode;
import cn.daycode.fatalism.depository.entity.DepositoryRecord;
import cn.daycode.fatalism.depository.mappper.DepositoryRecordMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DepositoryRecordServiceImpl extends ServiceImpl<DepositoryRecordMapper, DepositoryRecord> implements DepositoryRecordService {

    @Autowired
    private ConfigService configService;

    @Autowired
    private OkHttpService okHttpService;

    @Autowired
    private Cache cache;

    @Override
    public GatewayRequest createConsumer(ConsumerRequest consumerRequest) {

        saveDepositoryRecord(consumerRequest);

        String reqData=JSON.toJSONString(consumerRequest);
        String sign=RSAUtil.sign(reqData,configService.getP2pPrivateKey(),"utf-8");
        GatewayRequest gatewayRequest=new GatewayRequest();
        gatewayRequest.setServiceName("PERSONAL_REGISTER");
        gatewayRequest.setPlatformNo(configService.getP2pCode());
        gatewayRequest.setReqData(EncryptUtil.encodeURL(EncryptUtil
                .encodeUTF8StringBase64(reqData)));
        gatewayRequest.setSignature(EncryptUtil.encodeURL(sign));
        gatewayRequest.setDepositoryUrl(configService.getDepositoryUrl() + "/gateway");
        return gatewayRequest;

    }

    @Override
    public Boolean modifyRequestStatus(String requestNo, Integer requestsStatus) {
        return update(Wrappers.<DepositoryRecord>lambdaUpdate()
                .eq(DepositoryRecord::getRequestNo, requestNo)
                .set(DepositoryRecord::getRequestStatus, requestsStatus)
                .set(DepositoryRecord::getConfirmDate, LocalDateTime.now()));
    }

    @Override
    public DepositoryResponseDTO<DepositoryBaseResponse> createProject(ProjectDTO projectDTO) {
        DepositoryRecord depositoryRecord = new DepositoryRecord(
                projectDTO.getRequestNo(),
                DepositoryRequestTypeCode.CREATE.getCode(),
                "Project",
                projectDTO.getId());

        DepositoryResponseDTO<DepositoryBaseResponse> responseDTO = handleIdempotent(depositoryRecord);
        if (responseDTO != null) {
            return responseDTO;
        }
        depositoryRecord = getEntityByRequestNo(projectDTO.getRequestNo());

        ProjectRequestDataDTO projectRequestDataDTO =
                convertProjectDTOToProjectRequestDataDTO(projectDTO,
                        depositoryRecord.getRequestNo());
        String jsonString=JSON.toJSONString(projectRequestDataDTO);
        String reqData=EncryptUtil.encodeUTF8StringBase64(jsonString);


        String url=configService.getDepositoryUrl()+"/service";
        return sendHttpGet("CREATE_PROJECT",url,reqData,depositoryRecord);

    }

    @Override
    public DepositoryResponseDTO<DepositoryBaseResponse> userAutoPreTransaction(UserAutoPreTransactionRequest userAutoPreTransactionRequest) {
        DepositoryRecord depositoryRecord=new DepositoryRecord(userAutoPreTransactionRequest.getRequestNo(),userAutoPreTransactionRequest.getBizType(),"UserAutoPreTransactionRequest",userAutoPreTransactionRequest.getId());
        DepositoryResponseDTO<DepositoryBaseResponse> responseDTO=handleIdempotent(depositoryRecord);
        if(responseDTO!=null){
            return responseDTO;
        }
        depositoryRecord = getEntityByRequestNo(userAutoPreTransactionRequest.getRequestNo());

        String jsonString=JSON.toJSONString(userAutoPreTransactionRequest);
        String reqData=EncryptUtil.encodeUTF8StringBase64(jsonString);

        String url = configService.getDepositoryUrl() + "/service";
        return sendHttpGet("USER_AUTO_PRE_TRANSACTION", url, reqData,
                depositoryRecord);
    }

    @Override
    public DepositoryResponseDTO<DepositoryBaseResponse> confirmLoan(LoanRequest loanRequest) {
        DepositoryRecord depositoryRecord=new DepositoryRecord(loanRequest.getRequestNo(),DepositoryRequestTypeCode.FULL_LOAN.getCode(),"LoanRequest",loanRequest.getId());
        DepositoryResponseDTO<DepositoryBaseResponse> responseDTO=handleIdempotent(depositoryRecord);
        if(responseDTO!=null){
            return responseDTO;
        }

        depositoryRecord=getEntityByRequestNo(loanRequest.getRequestNo());

        String jsonString=JSON.toJSONString(loanRequest);
        String reqData=EncryptUtil.encodeUTF8StringBase64(jsonString);

        String url = configService.getDepositoryUrl() + "/service";
        return sendHttpGet("CONFIRM_LOAN",url,reqData,depositoryRecord);
    }

    @Override
    public DepositoryResponseDTO<DepositoryBaseResponse> modifyProjectStatus(ModifyProjectStatusDTO modifyProjectStatusDTO) {
        DepositoryRecord depositoryRecord=new DepositoryRecord(modifyProjectStatusDTO.getRequestNo(),DepositoryRequestTypeCode.MODIFY_STATUS.getCode(),"Project",modifyProjectStatusDTO.getId());

        DepositoryResponseDTO<DepositoryBaseResponse> responseDTO=handleIdempotent(depositoryRecord);

        if(responseDTO!=null){
            return responseDTO;
        }

        depositoryRecord=getEntityByRequestNo(modifyProjectStatusDTO.getRequestNo());

        String jsonString=JSON.toJSONString(modifyProjectStatusDTO);
        String reqData=EncryptUtil.encodeUTF8StringBase64(jsonString);

        String url = configService.getDepositoryUrl() + "/service";
        return sendHttpGet("MODIFY_PROJECT",url,reqData,depositoryRecord);
    }

    @Override
    public DepositoryResponseDTO<DepositoryBaseResponse> confirmRepayment(RepaymentRequest repaymentRequest) {
        DepositoryRecord depositoryRecord = null; //new DepositoryRecord(repaymentRequest.getRequestNo(), PreprocessBusinessTypeCode.REPAYMENT.getCode(),"Repayment",repaymentRequest.getId());

        DepositoryResponseDTO<DepositoryBaseResponse> responseDTO =
                handleIdempotent(depositoryRecord);
        if (responseDTO != null) {
            return responseDTO;
        }

        depositoryRecord=getEntityByRequestNo(repaymentRequest.getRequestNo());

        final String jsonString = JSON.toJSONString(repaymentRequest);
        String reqData = EncryptUtil.encodeUTF8StringBase64(jsonString);
        String url = configService.getDepositoryUrl() + "/service";
        return sendHttpGet("CONFIRM_REPAYMENT", url, reqData, depositoryRecord);

    }


    private DepositoryResponseDTO<DepositoryBaseResponse> sendHttpGet(
            String serviceName, String url, String reqData,
            DepositoryRecord depositoryRecord){
        // 银行存管系统接收的4大参数: serviceName, platformNo, reqData, signature
        // signature会在okHttp拦截器(SignatureInterceptor)中处理
        // 平台编号
        String platformNo = configService.getP2pCode();
        // redData签名
        // 发送请求, 获取结果, 如果检验签名失败, 拦截器会在结果中放入: "signature", "false"
        String responseBody = okHttpService
                .doSyncGet(url + "?serviceName=" + serviceName + "&platformNo=" +
                        platformNo + "&reqData=" + reqData);
        DepositoryResponseDTO<DepositoryBaseResponse> depositoryResponse = JSON
                .parseObject(responseBody,
                        new TypeReference<DepositoryResponseDTO<DepositoryBaseResponse>>(){});

        //封装返回的处理结果
        depositoryRecord.setResponseData(responseBody);

        // 响应后, 根据结果更新数据库( 进行签名判断 )
        // 判断签名(signature)是为 false, 如果是说明验签失败!
        if (!"false".equals(depositoryResponse.getSignature())) {
            // 成功 - 设置数据同步状态
            depositoryRecord.setRequestStatus(StatusCode.STATUS_IN.getCode());
            // 设置消息确认时间
            depositoryRecord.setConfirmDate(LocalDateTime.now());
            // 更新数据库
            updateById(depositoryRecord);
        } else {
            // 失败 - 设置数据同步状态
            depositoryRecord.setRequestStatus(StatusCode.STATUS_FAIL.getCode());
            // 设置消息确认时间
            depositoryRecord.setConfirmDate(LocalDateTime.now());
            // 更新数据库
            updateById(depositoryRecord);
            // 抛业务异常
            throw new BusinessException(DepositoryErrorCode.E_160101);
        }
        return depositoryResponse;
    }

    private ProjectRequestDataDTO convertProjectDTOToProjectRequestDataDTO(
            ProjectDTO projectDTO, String requestNo) {
        if(projectDTO==null){
            return null;
        }
        ProjectRequestDataDTO requestDataDTO = new ProjectRequestDataDTO();
        BeanUtils.copyProperties(projectDTO,requestDataDTO);
        requestDataDTO.setRequestNo(requestNo);
        return requestDataDTO;
    }
    /**
     * 保存交易记录
     */
    private DepositoryRecord saveDepositoryRecord(String requestNo,
                                                  String requestType,
                                                  String objectType,
                                                  Long objectId) {
        DepositoryRecord depositoryRecord = new DepositoryRecord();
        // 设置请求流水号
        depositoryRecord.setRequestNo(requestNo);
        // 设置请求类型
        depositoryRecord.setRequestType(requestType);
        // 设置关联业务实体类型
        depositoryRecord.setObjectType(objectType);
        // 设置关联业务实体标识
        depositoryRecord.setObjectId(objectId);
        // 设置请求时间
        depositoryRecord.setCreateDate(LocalDateTime.now());
        // 设置数据同步状态
        depositoryRecord.setRequestStatus(StatusCode.STATUS_OUT.getCode());
        // 保存数据
        save(depositoryRecord);
        return depositoryRecord;
    }

    /**
     * 保存交易记录
     */
    private DepositoryRecord saveDepositoryRecord(DepositoryRecord depositoryRecord) {

        // 设置请求时间
        depositoryRecord.setCreateDate(LocalDateTime.now());
        // 设置数据同步状态
        depositoryRecord.setRequestStatus(StatusCode.STATUS_OUT.getCode());
        // 保存数据
        save(depositoryRecord);
        return depositoryRecord;
    }

    private void saveDepositoryRecord(ConsumerRequest consumerRequest){
        DepositoryRecord depositoryRecord=new DepositoryRecord();
        depositoryRecord.setRequestNo(consumerRequest.getRequestNo());
        depositoryRecord.setRequestType(DepositoryRequestTypeCode.CONSUMER_CREATE.getCode());
        depositoryRecord.setObjectType("Consumer");
        depositoryRecord.setObjectId(consumerRequest.getId());
        depositoryRecord.setCreateDate(LocalDateTime.now());
        depositoryRecord.setRequestStatus(StatusCode.STATUS_OUT.getCode());
        save(depositoryRecord);
    }

    /**
     * 实现幂等性
     * @param depositoryRecord
     * @return
     */
    private DepositoryResponseDTO<DepositoryBaseResponse> handleIdempotent(DepositoryRecord depositoryRecord) {
        // 根据requestNo进行查询
        String requestNo=depositoryRecord.getRequestNo();
        DepositoryRecordDTO depositoryRecordDTO=getByRequestNo(requestNo);

        //1. 交易记录不存在,保存交易记录
        if(null==depositoryRecordDTO){
            saveDepositoryRecord(depositoryRecord);
            return null;
        }

        //2. 重复点击，重复请求，利用redis的原子性，争夺执行权
        if (StatusCode.STATUS_OUT.getCode() ==
                depositoryRecordDTO.getRequestStatus()) {
            //如果requestNo不存在则返回1,如果已经存在,则会返回（requestNo已存在个数+1）
            Long count = cache.incrBy(requestNo, 1L);
            if (count == 1) {
                cache.expire(requestNo, 10); //设置requestNo有效期5秒
                return null;
            }
            // 若count大于1，说明已有线程在执行该操作，直接返回“正在处理”
            if (count > 1) {
                throw new BusinessException(DepositoryErrorCode.E_160103);
            }
        }

        //3. 交易记录已经存在，并且状态是“已同步”
        return JSON.parseObject(depositoryRecordDTO.getResponseData(),
                new TypeReference<DepositoryResponseDTO<DepositoryBaseResponse>>(){
                });
    }

    private DepositoryRecordDTO getByRequestNo(String requestNo) {
        DepositoryRecord depositoryRecord = getEntityByRequestNo(requestNo);
        if (depositoryRecord == null) {
            return null;
        }
        DepositoryRecordDTO depositoryRecordDTO = new DepositoryRecordDTO();
        BeanUtils.copyProperties(depositoryRecord, depositoryRecordDTO);
        return depositoryRecordDTO;
    }

    private DepositoryRecord getEntityByRequestNo(String requestNo) {
        return getOne(new QueryWrapper<DepositoryRecord>().lambda()
                .eq(DepositoryRecord::getRequestNo, requestNo));
    }

}
