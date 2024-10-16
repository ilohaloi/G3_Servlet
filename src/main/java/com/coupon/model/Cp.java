package com.coupon.model;

import java.sql.Timestamp;

import javax.persistence.*;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "coupon_type") // 確保表名與資料庫中一致
public class Cp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coup_id;

    @Column(name = "coup_name", nullable = true) // 允許 NULL
    private String coup_name;

    @Column(name = "coup_description")
    private String coup_description;

    @Column(name = "coup_discount")
    private double coup_discount;

    @SerializedName("coup_issue_date") // 對應 JSON 中的 issue_date
    @Column(name = "coup_issue_date")
    private Timestamp coup_issue_date; // 使用 Timestamp

    @SerializedName("coup_expiry_date") // 對應 JSON 中的 expiry_date
    @Column(name = "coup_expiry_date")
    private Timestamp coup_expiry_date; // 使用 Timestamp

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

    public String getCoup_name() {
        return coup_name;
    }

    public void setCoup_name(String coup_name) {
        this.coup_name = coup_name;
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

    @Override
    public String toString() {
        return "Cp{" + "coup_id=" + coup_id + ", coup_name='" + coup_name + '\'' + ", coup_description='"
                + coup_description + '\'' + ", coup_discount=" + coup_discount + ", coup_issueDate=" + coup_issue_date
                + ", coup_expiryDate=" + coup_expiry_date + '}';
    }
}
