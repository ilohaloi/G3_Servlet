package com.user_coup.model;

import java.sql.Timestamp;
import javax.persistence.*;
import com.coupon.model.Cp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "user_coupon") // 確保表名與資料庫中一致
public class UserCoupon {
	
	@Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coup_no;

	@Expose
    @Column(name = "memb_id")
    private Integer memb_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coup_id", referencedColumnName = "coup_id", nullable = false)
    private Cp cp; // 關聯到 Cp 類的外鍵屬性
    
    @Expose
    @SerializedName("coup_issue_date")
    @Column(name = "coup_issue_date")
    private Timestamp coup_issue_date;
    
    @Expose
    @SerializedName("coup_expiry_date")
    @Column(name = "coup_expiry_date")
    private Timestamp coup_expiry_date;
    
    @Expose
    @Column(name = "coup_is_used")
    private Integer coup_is_used;
    
    @Expose
    @Transient
    private Integer coup_id;
    
    // Constructor
    public UserCoupon() {}

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

    public Cp getCp() {
        return cp;
    }

    public void setCp(Cp cp) {
        this.cp = cp;
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
        return "UserCoupon{" +
                "coup_no=" + coup_no +
                ", memb_id=" + memb_id +
                ", coup_id=" + (cp != null ? cp.getCoup_id() : null) +
                ", coup_issue_date=" + coup_issue_date +
                ", coup_expiry_date=" + coup_expiry_date +
                '}';
    }

	public Integer getCoup_id() {
		return coup_id;
	}

	public void setCoup_id(Integer coup_id) {
		this.coup_id = coup_id;
	}
}
