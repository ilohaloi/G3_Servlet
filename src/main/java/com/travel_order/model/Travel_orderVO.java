package com.travel_order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


public class Travel_orderVO {

	private int id;
	private int memb_id;
	private int ship_id;
	private int coup_id;
	private String trav_orde_status;
	private int room_amount;
	private String trav_orde_amount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMemb_id() {
		return memb_id;
	}
	public void setMemb_id(int memb_id) {
		this.memb_id = memb_id;
	}
	public int getShip_id() {
		return ship_id;
	}
	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}
	public int getCoup_id() {
		return coup_id;
	}
	public void setCoup_id(int coup_id) {
		this.coup_id = coup_id;
	}
	public String getTrav_orde_status() {
		return trav_orde_status;
	}
	public void setTrav_orde_status(String trav_orde_status) {
		this.trav_orde_status = trav_orde_status;
	}
	public int getRoom_amount() {
		return room_amount;
	}
	public void setRoom_amount(int room_amount) {
		this.room_amount = room_amount;
	}
	public String getTrav_orde_amount() {
		return trav_orde_amount;
	}
	public void setTrav_orde_amount(String trav_orde_amount) {
		this.trav_orde_amount = trav_orde_amount;
	}
	@Override
	public String toString() {
		return "Travel_orderVO [id=" + id + ", memb_id=" + memb_id + ", ship_id=" + ship_id + ", coup_id=" + coup_id
				+ ", trav_orde_status=" + trav_orde_status + ", room_amount=" + room_amount + ", trav_orde_amount="
				+ trav_orde_amount + "]";
	}
	
	
	
}