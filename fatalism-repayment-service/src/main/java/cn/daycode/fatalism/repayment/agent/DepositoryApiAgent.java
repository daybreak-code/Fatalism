package cn.daycode.fatalism.repayment.agent;

import cn.daycode.fatalism.api.depository.model.RepaymentRequest;
import cn.daycode.fatalism.api.depository.model.UserAutoPreTransactionRequest;
import cn.daycode.fatalism.common.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "depository-agent-service")
public interface DepositoryApiAgent {

    @PostMapping("/depository-agent/l/user-auto-pre-transaction")
    RestResponse<String> userAutoPreTransaction(
            UserAutoPreTransactionRequest userAutoPreTransactionRequest);

    @PostMapping("/depository-agent/l/confirm-repayment")
    RestResponse<String> confirmRepayment(RepaymentRequest repaymentRequest);
}
