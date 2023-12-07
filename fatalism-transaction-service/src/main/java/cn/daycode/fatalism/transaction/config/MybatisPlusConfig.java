package cn.daycode.fatalism.transaction.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.daycode.fatalism.**.mapper")
public class MybatisPlusConfig {


}
