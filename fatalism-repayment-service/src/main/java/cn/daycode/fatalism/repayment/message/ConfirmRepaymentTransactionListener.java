package cn.daycode.fatalism.repayment.message;

import cn.daycode.fatalism.api.depository.model.RepaymentRequest;
import cn.daycode.fatalism.repayment.entity.RepaymentPlan;
import cn.daycode.fatalism.repayment.mapper.PlanMapper;
import cn.daycode.fatalism.repayment.service.RepaymentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
//@RocketMQTransactionListener(txProducerGroup = "PID_CONFIRM_REPAYMENT")
public class ConfirmRepaymentTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private PlanMapper planMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        JSONObject jsonObject=JSON.parseObject(new String((byte[])message.getPayload()));
        RepaymentPlan repaymentPlan=JSONObject.parseObject(jsonObject.getString("repaymentPlan"),RepaymentPlan.class);
        RepaymentRequest repaymentRequest=JSONObject.parseObject(jsonObject.getString("repaymentRequest"),RepaymentRequest.class);

        Boolean result = repaymentService.confirmRepayment(repaymentPlan,repaymentRequest);

        if(result){
            return RocketMQLocalTransactionState.COMMIT;
        }else{
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        JSONObject jsonObject=JSON.parseObject(new String((byte[])message.getPayload()));
        RepaymentPlan repaymentPlan=JSONObject.parseObject(jsonObject.getString("repaymentPlan"),RepaymentPlan.class);

        RepaymentPlan newRepaymentPlan=planMapper.selectById(repaymentPlan.getId());

        if(newRepaymentPlan!=null && newRepaymentPlan.getRepaymentStatus().equals("1")){
            return RocketMQLocalTransactionState.COMMIT;
        }else{
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
