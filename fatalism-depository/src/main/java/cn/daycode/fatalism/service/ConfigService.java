package cn.daycode.fatalism.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ConfigService {


	@Value("${depository.publicKey}")
	private String depositoryPublicKey;

	@Value("${depository.privateKey}")
	private String depositoryPrivateKey;


	public String getP2PPublicKey(String platformNo) {
		return ""; //config.getProperty("client-info.clients[" + platformNo +  "].publicKey", null);
	}


	public String getP2PNotifyUrl(String platformNo) {
		return ""; //config.getProperty("client-info.clients[" + platformNo +  "].notifyUrl", null);
	}

}
