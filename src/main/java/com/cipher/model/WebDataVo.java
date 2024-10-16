package com.cipher.model;

public class WebDataVo {
	String action;
	String identity;
	String data;
	String base64key;

	public WebDataVo() {}

	public WebDataVo(String action,String identity,String data, String key) {
		this.action = action;
		this.identity = identity;
		this.data = data;
		this.base64key = key;
	}

	public String getData() {
		return data;
	}

	public String getKey() {
		return base64key;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setKey(String key) {
		this.base64key = key;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
