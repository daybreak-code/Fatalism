package cn.daycode.fatalism.api.consumer;

import cn.daycode.fatalism.api.consumer.model.ConsumerRegisterDTO;
import cn.daycode.fatalism.api.consumer.model.ConsumerRequest;
import cn.daycode.fatalism.common.domain.RestResponse;

public interface ConsumerAPI {

    RestResponse register(ConsumerRegisterDTO consumerRegisterDTO);

    //RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest);
}
