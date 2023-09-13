package cn.daycode.fatalism.repayment.message;

import cn.daycode.fatalism.api.repayment.model.ProjectWithTendersDTO;
import cn.daycode.fatalism.repayment.service.RepaymentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "TP_START_REPAYMENT", consumerGroup = "CID_START_REPAYMENT")
public class StartRepaymentMessageConsumer implements RocketMQListener<String> {

    @Autowired
    private RepaymentService repaymentService;

    @Override
    public void onMessage(String msg) {
        System.out.println("Consume Message：" + msg);
        final JSONObject jsonObject = JSON.parseObject(msg);
        ProjectWithTendersDTO projectWithTendersDTO =
                JSONObject.parseObject(jsonObject.getString("projectWithTendersDTO"), ProjectWithTendersDTO.class);

        repaymentService.startRepayment(projectWithTendersDTO);
    }
}
