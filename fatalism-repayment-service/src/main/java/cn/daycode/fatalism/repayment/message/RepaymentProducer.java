package cn.daycode.fatalism.repayment.message;

import cn.daycode.fatalism.api.depository.model.RepaymentRequest;
import cn.daycode.fatalism.repayment.entity.RepaymentPlan;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

public class RepaymentProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void confirmRepayment(RepaymentPlan repaymentPlan, RepaymentRequest repaymentRequest) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("repaymentPlan",repaymentPlan);
        jsonObject.put("repaymentRequest",repaymentRequest);

        Message<String> msg = MessageBuilder.withPayload(jsonObject.toJSONString()).build();

//        rocketMQTemplate.sendMessageInTransaction("PID_CONFIRM_REPAYMENT",
//                "TP_CONFIRM_REPAYMENT", msg, null);
    }
}
