package cn.daycode.fatalism.repayment.job;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZKRegistryCenterConfig {

    @Value("${p2p.zookeeper.connString}")
    private String ZOOKEEPER_CONNECTION_STRING ;

    @Value("${p2p.job.namespace}")
    private String JOB_NAMESPACE;

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter setUpRegistryCenter(){
        ZookeeperConfiguration zookeeperConfiguration = new
                ZookeeperConfiguration(ZOOKEEPER_CONNECTION_STRING, JOB_NAMESPACE);
        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        return zookeeperRegistryCenter;
    }
}
