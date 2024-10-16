package com.cipher.model;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;



import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public interface KeyGenerateInterface{

	public final int AES_KEY_SIZE_128= 128;
	public final int AES_KEY_SIZE_192= 192;
	public final int AES_KEY_SIZE_256= 256;
	public final int AES_KEY = 24;

	public default KeyPair getRsakey() {
		KeyPairGenerator keyPairGen;
		KeyPair keyPair = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(2048);
			keyPair =  keyPairGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyPair;
	}
	public default SecretKey getAesKey() {
		KeyGenerator keyGen = null;
		SecretKey key = null;
		try {
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			key = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return key;
	}
}
