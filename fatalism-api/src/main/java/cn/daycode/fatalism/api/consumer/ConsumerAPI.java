package cn.daycode.fatalism.api.consumer;

import cn.daycode.fatalism.api.consumer.model.*;
import cn.daycode.fatalism.api.depository.GatewayRequest;
import cn.daycode.fatalism.common.domain.RestResponse;

public interface ConsumerAPI {

    RestResponse register(ConsumerRegisterDTO consumerRegisterDTO);

    RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest);

    RestResponse<ConsumerDTO> getCurrConsumer(String mobile);

    RestResponse<ConsumerDTO> getMyConsumer();

    RestResponse<BorrowerDTO> getBorrower(Long id);

    RestResponse<BorrowerDTO> getBorrowerMobile(Long id);

    RestResponse<BalanceDetailsDTO> getBalance(String userNo);

    RestResponse<BalanceDetailsDTO> getMyBalance();

}
