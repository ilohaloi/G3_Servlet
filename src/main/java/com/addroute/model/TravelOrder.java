package com.addroute.model;

import javax.persistence.*;

import com.coupon.controller.GetCoupon;
import com.user_coup.model.UserCoupon;

import java.io.Serializable;

@Entity
@Table(name = "travel_order")
public class TravelOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trav_orde_id")
    private int travOrdeId;

    @ManyToOne
    @JoinColumn(name = "ship_id", nullable = false)
    private ShipSchedule shipSchedule;

    @ManyToOne
    @JoinColumn(name = "coup_id", nullable = true)  // 如果優惠券是可選的，這裡可以允許為空
    private GetCoupon coupon;  // 這裡使用 Coupon 類，而不是 int

    @Column(name = "trav_orde_status", nullable = false)
    private String travOrdeStatus;

    @Column(name = "room_amount", nullable = false)
    private int roomAmount;

    @Column(name = "trav_orde_amount", nullable = false)
    private String travOrdeAmount;

    // Getter 和 Setter 方法
    public int getTravOrdeId() {
        return travOrdeId;
    }

    public void setTravOrdeId(int travOrdeId) {
        this.travOrdeId = travOrdeId;
    }

    public ShipSchedule getShipSchedule() {
        return shipSchedule;
    }

    public void setShipSchedule(ShipSchedule shipSchedule) {
        this.shipSchedule = shipSchedule;
    }

    public GetCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(GetCoupon coupon) {
        this.coupon = coupon;
    }

    public String getTravOrdeStatus() {
        return travOrdeStatus;
    }

    public void setTravOrdeStatus(String travOrdeStatus) {
        this.travOrdeStatus = travOrdeStatus;
    }

    public int getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(int roomAmount) {
        this.roomAmount = roomAmount;
    }

    public String getTravOrdeAmount() {
        return travOrdeAmount;
    }

    public void setTravOrdeAmount(String travOrdeAmount) {
        this.travOrdeAmount = travOrdeAmount;
    }

	public void setCustomerName(String customerName) {
		// TODO Auto-generated method stub
		
	}
}
