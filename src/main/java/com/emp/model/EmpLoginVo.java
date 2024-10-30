package com.emp.model;

import java.io.Serializable;

public class EmpLoginVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 3221665227674402163L;

	String account;
	String password;

	public EmpLoginVo() {}

	public EmpLoginVo(String account, String password) {
		this.account = account;
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
