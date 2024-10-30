package com.user_coup.model;

import java.sql.Timestamp;

import javax.persistence.*;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "user_coupon") // 確保表名與資料庫中一致
public class UserCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer coup_no;

	// 1對多
	@Column(name = "memb_id")
	private Integer memb_id;

	// 1 1
	@Column(name = "coup_id")
	private Integer coup_id;

	@SerializedName("coup_issue_date") // 對應 JSON 中的 issue_date
	@Column(name = "coup_issue_date")
	private Timestamp coup_issue_date; // 使用 Timestamp

	@SerializedName("coup_expiry_date") // 對應 JSON 中的 expiry_date
	@Column(name = "coup_expiry_date")
	private Timestamp coup_expiry_date; // 使用 Timestamp

	@Column(name = "coup_is_used")
	private Integer coup_is_used;

	// Constructor
	public UserCoupon() {
		// 這裡不再生成隨機名稱
	}

	// Getters 和 Setters
	public Integer getCoup_no() {
		return coup_no;
	}

	public void setCoup_no(Integer coup_no) {
		this.coup_no = coup_no;
	}

	public Integer getMemb_id() {
		return memb_id;
	}

	public void setMemb_id(Integer memb_id) {
		this.memb_id = memb_id;
	}

	public Integer getCoup_id() {
		return coup_id;
	}

	public void setCoup_id(Integer coup_id) {
		this.coup_id = coup_id;
	}

	public Timestamp getCoup_issue_date() {
		return coup_issue_date;
	}

	public void setCoup_issue_date(Timestamp coup_issue_date) {
		this.coup_issue_date = coup_issue_date;
	}

	public Timestamp getCoup_expiry_date() {
		return coup_expiry_date;
	}

	public void setCoup_expiry_date(Timestamp coup_expiry_date) {
		this.coup_expiry_date = coup_expiry_date;
	}

	public Integer getCoup_is_used() {
		return coup_is_used;
	}

	public void setCoup_is_used(Integer coup_is_used) {
		this.coup_is_used = coup_is_used;
	}

	@Override
	public String toString() {
		return "Cp{" + "coup_no=" + coup_no + ", memb_id='" + memb_id + '\'' + ", coup_id='" + coup_id + '\''
				+ ", coup_issueDate=" + coup_issue_date + ", coup_expiryDate=" + coup_expiry_date + '}';
	}

}
