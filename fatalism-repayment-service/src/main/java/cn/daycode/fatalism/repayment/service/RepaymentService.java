package cn.daycode.fatalism.repayment.service;

import cn.daycode.fatalism.api.depository.model.RepaymentRequest;
import cn.daycode.fatalism.api.repayment.model.ProjectWithTendersDTO;
import cn.daycode.fatalism.repayment.entity.RepaymentDetail;
import cn.daycode.fatalism.repayment.entity.RepaymentPlan;

import java.util.List;

public interface RepaymentService {

    String startRepayment(ProjectWithTendersDTO projectWithTendersDTO);

    List<RepaymentPlan> selectDueRepayment(String date);

    List<RepaymentPlan> selectDueRepayment(String date, int shardingCount, int shardingItem);

    RepaymentDetail saveRepaymentDetail(RepaymentPlan repaymentPlan);

    void executeRepayment(String date, int shardingCount, int shardingItem);

    Boolean preRepayment(RepaymentPlan repaymentPlan, String preRequestNo);

    Boolean confirmRepayment(RepaymentPlan repaymentPlan, RepaymentRequest repaymentRequest);

    void invokeConfirmRepayment(RepaymentPlan repaymentPlan, RepaymentRequest repaymentRequest);

    void sendRepaymentNotify(String date);

}
