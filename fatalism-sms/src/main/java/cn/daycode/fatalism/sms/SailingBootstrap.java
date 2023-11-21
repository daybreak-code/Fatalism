package cn.daycode.fatalism.sms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.util.comparator.InvertibleComparator;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SailingBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SailingBootstrap.class, args);
    }

}