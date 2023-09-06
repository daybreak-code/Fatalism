package cn.daycode.fatalism.consumer.agent;

import cn.daycode.fatalism.api.consumer.model.ConsumerRequest;
import cn.daycode.fatalism.api.depository.GatewayRequest;
import cn.daycode.fatalism.common.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "depository-agent-service")
public interface DepositoryAgentApiAgent {

    @PostMapping("/depository-agent/l/consumers")
    RestResponse<GatewayRequest> createConsumer(@RequestBody ConsumerRequest consumerRequest);
}
