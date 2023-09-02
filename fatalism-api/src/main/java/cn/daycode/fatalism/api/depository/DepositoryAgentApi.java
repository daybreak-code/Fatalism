package cn.daycode.fatalism.api.depository;

import cn.daycode.fatalism.api.consumer.model.ConsumerRequest;
import cn.daycode.fatalism.api.depository.model.LoanRequest;
import cn.daycode.fatalism.api.depository.model.RepaymentRequest;
import cn.daycode.fatalism.api.depository.model.UserAutoPreTransactionRequest;
import cn.daycode.fatalism.api.transaction.model.ModifyProjectStatusDTO;
import cn.daycode.fatalism.api.transaction.model.ProjectDTO;
import cn.daycode.fatalism.common.domain.RestResponse;

public interface DepositoryAgentApi {

    RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest);

    RestResponse<String> createProject(ProjectDTO projectDTO);

    RestResponse<String> userAutoPreTransaction(UserAutoPreTransactionRequest userAutoPreTransactionRequest);

    RestResponse<String> confirmLoan(LoanRequest loanRequest);

    RestResponse<String> modifyProjectStatus(ModifyProjectStatusDTO modifyProjectStatusDTO);

    RestResponse<String> confirmRepayment(RepaymentRequest repaymentRequest);
}
