package com.user_coup.dto;

import java.sql.Timestamp;

public class WebMembCoupDto {

	private Integer id;
	private String coupName;
	private Timestamp expiryDate;
	private double discount;

	public WebMembCoupDto() {
		super();
	}

	public WebMembCoupDto(Integer id, String coupName, Timestamp expireDate, double discount) {
		super();
		this.id = id;
		this.coupName = coupName;
		this.expiryDate = expireDate;
		this.discount = discount;
	}

	public Integer getId() {
		return id;
	}

	public String getCoupName() {
		return coupName;
	}

	public Timestamp getExpireDate() {
		return expiryDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCoupName(String coupName) {
		this.coupName = coupName;
	}

	public void setExpireDate(Timestamp expireDate) {
		this.expiryDate = expireDate;
	}

	@Override
	public String toString() {
		return "WebMembCoupDto [id=" + id + ", coupName=" + coupName + ", expireDate=" + expiryDate + "]";
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
}
