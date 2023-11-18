package cn.daycode.fatalism.message;

import cn.daycode.fatalism.domain.PersonalRegisterResponse;
import cn.daycode.fatalism.domain.RechargeResponse;
import cn.daycode.fatalism.domain.WithdrawResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GatewayMessageProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void personalRegister(String appCode, PersonalRegisterResponse response) {
        Message<?> message = MessageBuilder.withPayload(JSON.toJSONString(response))
                .setHeader(MessageConst.PROPERTY_KEYS, appCode).build();
        rocketMQTemplate.convertAndSend("TP_GATEWAY_NOTIFY:PERSONAL_REGISTER", message);
    }

    public void recharge(String appCode, RechargeResponse response) {
        Message<?> message = MessageBuilder.withPayload(JSON.toJSONString(response))
                .setHeader(MessageConst.PROPERTY_KEYS, appCode).build();
        rocketMQTemplate.convertAndSend("TP_GATEWAY_NOTIFY:RECHARGE", message);
    }

    public void withdraw(String appCode, WithdrawResponse response) {
        Message<?> message = MessageBuilder.withPayload(JSON.toJSONString(response))
                .setHeader(MessageConst.PROPERTY_KEYS, appCode).build();
        rocketMQTemplate.convertAndSend("TP_GATEWAY_NOTIFY:WITHDRAW", message);
    }
}
