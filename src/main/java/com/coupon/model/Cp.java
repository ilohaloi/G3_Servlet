package com.coupon.model;

import javax.persistence.*;

@Entity
@Table(name = "coupon_type") // 確保表名與資料庫中一致
public class Cp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer coup_id;

	@Column(name = "coup_code")
	private String coup_code;

	@Column(name = "coup_description")
	private String coup_description;

	@Column(name = "coup_discount")
	private double coup_discount;

	// Constructor
	public Cp() {
		// 這裡不再生成隨機名稱
	}

	// Getters 和 Setters

	public Integer getCoup_id() {
		return coup_id;
	}

	public void setCoup_id(Integer coup_id) {
		this.coup_id = coup_id;
	}

	public String getCoup_code() {
		return coup_code;
	}

	public void setCoup_code(String coup_code) {
		this.coup_code = coup_code;
	}

	public String getCoup_description() {
		return coup_description;
	}

	public void setCoup_description(String coup_description) {
		this.coup_description = coup_description;
	}

	public double getCoup_discount() {
		return coup_discount;
	}

	public void setCoup_discount(double coup_discount) {
		this.coup_discount = coup_discount;
	}

	@Override
	public String toString() {
		return "Cp{" + "coup_id=" + coup_id + ", coup_code='" + coup_code + '\'' + ", coup_description='"
				+ coup_description + '\'' + ", coup_discount=" + coup_discount + '}';
	}

}