package cn.daycode.fatalism.depository.service;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.stereotype.Service;

@Service
@EnableApolloConfig
public class ConfigService {
    @ApolloConfig
    private Config config;


    public String getDepositoryUrl() {
        return config.getProperty("depository.url", null);
    }

    public String getDepositoryPublicKey() {
        return config.getProperty("depository.publicKey", null);
    }


    public String getP2pPublicKey() {
        return config.getProperty("p2p.publicKey", null);
    }


    public String getP2pCode() {
        return config.getProperty("p2p.code", null);
    }


    public String getP2pPrivateKey() {
        return config.getProperty("p2p.privateKey", null);
    }
}
