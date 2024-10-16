package com.emp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import kotlin.Pair;

@Entity
@Table(name = "employee_data")
public class EmpVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -773832405979157576L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empo_id")
	Integer id;

	@Column(name = "empo_name")
	String name;

	@Column(name = "empo_account")
	String account;

	@Column(name = "empo_password", columnDefinition = "text")
	String password;

	@Column(name = "empo_public_Key",columnDefinition = "text")
	String publicKey;

	public EmpVo() {}

	public EmpVo(String name, String account, String password, String publicKey) {
		this.name = name;
		this.account = account;
		this.password = password;
		this.publicKey = publicKey;
	}

	public EmpVo(Pair<String, String> emp) {
		this.account = emp.getFirst();
		this.password = emp.getSecond();
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAccount() {
		return account;
	}
	public String getPassword() {
		return password;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
