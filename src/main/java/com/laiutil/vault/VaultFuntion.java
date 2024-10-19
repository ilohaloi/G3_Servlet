package com.laiutil.vault;

import java.util.HashMap;
import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

import kotlin.Pair;

public class VaultFuntion {

	private Vault vault;
	private String path;

	public VaultFuntion(Vault vault, String path) {
		this.vault = vault;
		this.path = path;
	}

	public Map<String, String> getAllData() {
		LogicalResponse readResponse = null;
		try {
			readResponse = vault.logical().read(path);
		} catch (VaultException e) {
			e.printStackTrace();
		}
		return readResponse.getData();
	}
	public Pair<String, String> getUserData(String name) {
		var data = getAllData();
		Pair<String, String> userPair = null;
		for (Map.Entry<String, String> entry : data.entrySet()) {
			if(entry.getKey().equals(name))
			{
				userPair = new Pair<String, String>(entry.getKey(),entry.getValue());
				break;
			}
		}
		return userPair;
	}

	/**
	 * 更新新增共用同一個API
	 *
	 * @throws VaultException
	 *
	 */

	public void update(Pair<String, String> data) throws VaultException {
		Map<String, String> existingData = getAllData();
		existingData.put(data.getFirst(), data.getSecond());
		vault.logical().write(path, new HashMap<String, Object>(existingData));
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
