package cn.daycode.fatalism.depository.service;

import cn.daycode.fatalism.api.consumer.model.ConsumerRequest;
import cn.daycode.fatalism.api.depository.GatewayRequest;
import cn.daycode.fatalism.api.depository.model.*;
import cn.daycode.fatalism.api.transaction.model.ModifyProjectStatusDTO;
import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.depository.entity.DepositoryRecord;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DepositoryRecordService extends IService<DepositoryRecord> {

    GatewayRequest createConsumer(ConsumerRequest consumerRequest);

    Boolean modifyRequestStatus(String requestNo, Integer requestsStatus);


    DepositoryResponseDTO<DepositoryBaseResponse> createProject(ProjectDTO
                                                                        projectDTO);


    DepositoryResponseDTO<DepositoryBaseResponse>
    userAutoPreTransaction(UserAutoPreTransactionRequest userAutoPreTransactionRequest);


    DepositoryResponseDTO<DepositoryBaseResponse> confirmLoan(LoanRequest loanRequest);


    DepositoryResponseDTO<DepositoryBaseResponse>
    modifyProjectStatus(ModifyProjectStatusDTO modifyProjectStatusDTO);


    DepositoryResponseDTO<DepositoryBaseResponse> confirmRepayment(RepaymentRequest
                                                                           repaymentRequest);
}

