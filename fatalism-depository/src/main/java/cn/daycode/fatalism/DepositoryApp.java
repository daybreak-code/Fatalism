package cn.daycode.fatalism;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.daycode.fatalism.mapper")
public class DepositoryApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(DepositoryApp.class);
    }
}
