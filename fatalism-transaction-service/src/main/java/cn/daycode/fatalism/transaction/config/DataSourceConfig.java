package cn.daycode.fatalism.transaction.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "cn.daycode.fatalism.transaction.mapper", sqlSessionFactoryRef = "mysqlSessionFactory")
public class DataSourceConfig {

    @Bean(name = "MySQLDataSource1")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }


    @Bean(name = "mysqlSessionFactory")
    @Primary
    public SqlSessionFactory mysqlSessionFactory(
            @Qualifier("MySQLDataSource1") DataSource datasource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean ();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:cn/daycode/fatalism/depository/mappper/*.xml"));
        return bean.getObject();
    }


    @Bean("MySQLSqlSessionTemplate1")
    @Primary
    public SqlSessionTemplate test1SqlSessionTemplate(
            @Qualifier("MySQLSqlSessionFactory1") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    @Bean
    public PlatformTransactionManager transactionManager1(@Qualifier("MySQLDataSource1")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
