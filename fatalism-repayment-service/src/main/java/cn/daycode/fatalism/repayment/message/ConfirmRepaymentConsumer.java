package cn.daycode.fatalism.repayment.message;

import cn.daycode.fatalism.api.depository.model.RepaymentRequest;
import cn.daycode.fatalism.repayment.entity.RepaymentPlan;
import cn.daycode.fatalism.repayment.service.RepaymentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "TP_CONFIG_REPAYMENT", consumerGroup = "CID_CONFIRM_REPAYMENT")
public class ConfirmRepaymentConsumer implements RocketMQListener<String> {

    @Autowired
    private RepaymentService repaymentService;

    @Override
    public void onMessage(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        RepaymentPlan repaymentPlan=JSONObject.parseObject(jsonObject.getString("repaymentPlan"),RepaymentPlan.class);
        RepaymentRequest repaymentRequest=JSONObject.parseObject(jsonObject.getString("repaymentRequest"), RepaymentRequest.class);

        repaymentService.invokeConfirmRepayment(repaymentPlan,repaymentRequest);
    }
}
