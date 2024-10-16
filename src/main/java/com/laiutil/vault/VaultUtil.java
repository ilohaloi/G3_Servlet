package com.laiutil.vault;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;

public class VaultUtil {

	private static Vault vault = ValueInit();

	public static Vault getVault() {
		return vault;
	}

	private static Vault ValueInit() {
		if(System.getenv("VAULT_ADDR")==null)
			return null;

		try {


			VaultConfig config = new VaultConfig().address(System.getenv("VAULT_ADDR")).openTimeout(5)
					.token(System.getenv("vToken_k")).build();
			Vault vault = new Vault(config);


			if (vault.seal().sealStatus().getSealed()) {
				vault.seal().unseal(System.getenv("VAULT_SEAL"));
			}

			System.out.println("vault 建立成功");
			return vault;
		} catch (VaultException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}
}
