package cn.daycode.fatalism.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.dromara.hmily", "cn.daycode.fatalism.account"})
public class AccountApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AccountApp.class);
    }
}
