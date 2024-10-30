package com.cipher.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 加解密功能
 */
public interface CipherInterface {

	public final String RSA = "RSA";
	public final String AES = "AES";

	// @ return Base64format
	public default String encrypt(String originalData, Key key, String keyType) {

		Cipher cipher;
		byte[] encryptedData = null;
		try {
			cipher = Cipher.getInstance(keyType);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedData = cipher.doFinal(originalData.getBytes());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return Base64.getEncoder().encodeToString(encryptedData);
	}

	// @ return Decrypt data
	public default String decrypt(String encryptedData, Key key, String keyType) throws UnsupportedEncodingException{
		Cipher cipher = null;
		byte[] decryptedBytes = null;
		try {
			// AES RSA/ECB/PKCS1Padding

			switch (keyType) {
			case RSA:
				cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				break;
			case AES:
				cipher = Cipher.getInstance("AES");

			}
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return new String(decryptedBytes,"UTF-8");
	}
}
