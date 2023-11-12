package cn.daycode.fatalism;

import cn.daycode.fatalism.entity.balance.BalanceDetails;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("cn.daycode.fatalism.mapper")
public class DepositoryApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(DepositoryApp.class);
    }

    @Bean
    RocketMQTemplate getMQ(){
        return new RocketMQTemplate();
    }

}
