package cn.daycode.fatalism.service;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.stereotype.Service;


@Service
@EnableApolloConfig
public class ConfigService {

	@ApolloConfig
	private Config config;


	public String getDepositoryPublicKey() {
		return config.getProperty("depository.publicKey", null);
	}


	public String getDepositoryPrivateKey() {
		return config.getProperty("depository.privateKey", null);
	}


	public String getP2PPublicKey(String platformNo) {
		return config.getProperty("client-info.clients[" + platformNo +  "].publicKey", null);
	}


	public String getP2PNotifyUrl(String platformNo) {
		return config.getProperty("client-info.clients[" + platformNo +  "].notifyUrl", null);
	}

}
