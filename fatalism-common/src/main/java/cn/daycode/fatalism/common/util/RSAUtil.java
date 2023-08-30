package cn.daycode.fatalism.common.util;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	final static Base64.Decoder decoder = Base64.getDecoder();
	final static Base64.Encoder encoder = Base64.getEncoder();


	public static String sign(String content, String privateKey, String input_charset) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decoder.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(input_charset));

			byte[] signed = signature.sign();

			return encoder.encodeToString(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	public static boolean verify(String content, String sign, String public_key, String input_charset) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = decoder.decode(public_key);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(input_charset));

			boolean bverify = signature.verify(decoder.decode(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}


	public static String decrypt(String content, String private_key, String input_charset) throws Exception {
		PrivateKey prikey = getPrivateKey(private_key);

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, prikey);

		InputStream ins = new ByteArrayInputStream(decoder.decode(content));
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		byte[] buf = new byte[128];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}

			writer.write(cipher.doFinal(block));
		}

		return new String(writer.toByteArray(), input_charset);
	}



	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;

		keyBytes = decoder.decode(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;
	}


	public static KeyPair getKeyPair() throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(512);
		KeyPair keyPair = keyGen.generateKeyPair();
		return keyPair;
	}


}