package cn.daycode.fatalism.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransactionApp
{
    public static void main( String[] args )
    {

        SpringApplication.run(TransactionApp.class);
    }
}
