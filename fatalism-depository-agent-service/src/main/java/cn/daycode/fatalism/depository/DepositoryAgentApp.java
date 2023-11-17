package cn.daycode.fatalism.depository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.daycode.fatalism.depository")
public class DepositoryAgentApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(DepositoryAgentApp.class);
    }
}
