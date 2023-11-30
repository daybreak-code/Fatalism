package cn.daycode.fatalism.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:hmily-config.properties")
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DistributedTransactionConfig {

    @Autowired
    private Environment env;


}
