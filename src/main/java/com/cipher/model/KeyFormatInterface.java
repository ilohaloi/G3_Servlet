package com.cipher.model;

import java.security.Key;
import java.security.KeyFactory;

import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public interface KeyFormatInterface {

	enum sraType {
		PRIVATE,PUNLIC
	}


	public default SecretKey getAesKeyFromBase64(String base64Key) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(base64Key); // 解码 Base64 字符串
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); // 创建 AES SecretKey
	}

	public default String getBase64FromKey(Key key) {
		byte[] KeyBytes = key.getEncoded();
		return Base64.getEncoder().encodeToString(KeyBytes);
	}
	public default Key getRsaKeyFromBase64(String base64Key, sraType sraKeytype) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(base64Key);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		switch (sraKeytype) {
		case PRIVATE:
			PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(keyBytes);
			return keyFactory.generatePrivate(privateSpec);
		case PUNLIC:
			X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(keyBytes);
			return keyFactory.generatePublic(publicSpec);

		}
		return null;

	}
}
