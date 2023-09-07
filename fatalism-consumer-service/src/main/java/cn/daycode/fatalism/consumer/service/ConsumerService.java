package cn.daycode.fatalism.consumer.service;

import cn.daycode.fatalism.api.consumer.model.BorrowerDTO;
import cn.daycode.fatalism.api.consumer.model.ConsumerDTO;
import cn.daycode.fatalism.api.consumer.model.ConsumerRegisterDTO;
import cn.daycode.fatalism.api.consumer.model.ConsumerRequest;
import cn.daycode.fatalism.api.depository.GatewayRequest;
import cn.daycode.fatalism.api.depository.model.DepositoryConsumerResponse;
import cn.daycode.fatalism.common.domain.RestResponse;
import cn.daycode.fatalism.consumer.entity.Consumer;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ConsumerService extends IService<Consumer> {

    Integer checkMobile(String mobile);

    void register(ConsumerRegisterDTO consumerRegisterDTO);

    RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest);

    Boolean modifyResult(DepositoryConsumerResponse response);

    ConsumerDTO getByMobile(String mobile);

    BorrowerDTO getBorrower(Long id);
}
