package cn.daycode.fatalism;

import cn.daycode.fatalism.entity.balance.BalanceDetails;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.daycode.fatalism.mapper")
public class DepositoryApp
{
    public static void main( String[] args )
    {
        Class<BalanceDetails> balanceDetailsClass = BalanceDetails.class;
        SpringApplication.run(DepositoryApp.class);
    }
}
