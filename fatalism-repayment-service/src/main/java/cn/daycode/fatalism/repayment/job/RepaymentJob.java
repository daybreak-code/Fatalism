package cn.daycode.fatalism.repayment.job;

import cn.daycode.fatalism.repayment.service.RepaymentService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RepaymentJob implements SimpleJob {

    @Autowired
    private RepaymentService repaymentService;

    @Override
    public void execute(ShardingContext shardingContext) {
        repaymentService.executeRepayment(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE), shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
        repaymentService.sendRepaymentNotify(LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
