package cn.daycode.fatalism.repayment.mapper;

import cn.daycode.fatalism.repayment.entity.RepaymentPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PlanMapper extends BaseMapper<RepaymentPlan> {

    @Select("SELECT * FROM repayment_plan WHERE DATE_FORMAT(SHOULD_REPAYMENT_DATE,'%Y-%m-%d') = #{date} AND REPAYMENT_STATUS = '0'")
    List<RepaymentPlan> selectDueRepayment(String date);

    @Select("SELECT * FROM repayment_plan WHERE DATE_FORMAT(SHOULD_REPAYMENT_DATE,'%Y-%m-%d') = #{date} AND REPAYMENT_STATUS = '0' AND MOD(NUMBER_OF_PERIODS,#{shardingCount})=#{shardingItem}")
    List<RepaymentPlan> selectDueRepaymentList(@Param("date") String date, @Param("shardingCount") int count, @Param("shardingItem")int item);

}
