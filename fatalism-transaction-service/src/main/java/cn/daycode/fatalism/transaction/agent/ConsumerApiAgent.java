package cn.daycode.fatalism.transaction.agent;

import cn.daycode.fatalism.api.consumer.model.BalanceDetailsDTO;
import cn.daycode.fatalism.api.consumer.model.ConsumerDTO;
import cn.daycode.fatalism.common.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "consumer-service")
public interface ConsumerApiAgent {

    @GetMapping("/consumer/l/currConsumer/{mobile}")
    RestResponse<ConsumerDTO> getCurrConsumer(@PathVariable("mobile") String mobile);

    @GetMapping("/consumer/l/balances/{userNo}")
    public RestResponse<BalanceDetailsDTO> getBalance(@PathVariable("userNo")
                                                      String userNo);
}

