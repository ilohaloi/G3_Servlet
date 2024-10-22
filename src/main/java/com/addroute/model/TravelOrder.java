package com.addroute.model;

import javax.persistence.*;

@Entity
@Table(name = "travel_order")
public class TravelOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trav_orde_id;

    @Column(name = "trav_orde_status")
    private String travOrdeStatus;

    @Column(name = "room_amount")
    private int roomAmount;

    @Column(name = "trav_orde_amount")
    private String travOrdeAmount;

    @ManyToOne
    @JoinColumn(name = "memb_id", nullable = false)
    private int member;

    @ManyToOne
    @JoinColumn(name = "ship_id", nullable = false)
    private ShipSchedule shipSchedule;

    @ManyToOne
    @JoinColumn(name = "coup_id", nullable = true)
    private int coupon;

	public int getTrav_orde_id() {
		return trav_orde_id;
	}

	public void setTrav_orde_id(int trav_orde_id) {
		this.trav_orde_id = trav_orde_id;
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

	public int getMember() {
		return member;
	}

	public void setMember(int member) {
		this.member = member;
	}

	public ShipSchedule getShipSchedule() {
		return shipSchedule;
	}

	public void setShipSchedule(ShipSchedule shipSchedule) {
		this.shipSchedule = shipSchedule;
	}

	public int getCoupon() {
		return coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}
    
    
}
