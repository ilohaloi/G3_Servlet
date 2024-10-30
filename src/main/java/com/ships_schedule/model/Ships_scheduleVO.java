package com.ships_schedule.model;
import java.sql.Date;
import java.util.List;



public class Ships_scheduleVO {

	

	private int ship_id;  	     //船隻編號
	private int route_id; 	     //航線名稱
	private	String status;	     //航班狀態
	private Date shipping_time;  //出港時間
	private	String shipping_dock;//駁船地點
	private int rooms_type;		 //房型
	private int rooms_booked;	 //已定房間總數
	
	
	public int getRooms_type() {
		return rooms_type;
	}
	public void setRooms_type(int rooms_type) {
		this.rooms_type = rooms_type;
	}
	
	public int getShip_id() {
		return ship_id;
	}
	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getShipping_time() {
		return shipping_time;
	}
	public void setShipping_time(Date shipping_time) {
		this.shipping_time = shipping_time;
	}
	public String getShipping_dock() {
		return shipping_dock;
	}
	public void setShipping_dock(String shipping_dock) {
		this.shipping_dock = shipping_dock;
	}
	public int getRooms_booked() {
		return rooms_booked;
	}
	public void setRooms_booked(int rooms_booked) {
		this.rooms_booked = rooms_booked;
	}
	@Override
	public String toString() {
		return "Ships_scheduleVO [ship_id=" + ship_id + ", route_id=" + route_id + ", status=" + status
				+ ", shipping_time=" + shipping_time + ", shipping_dock=" + shipping_dock + ", rooms_type=" + rooms_type
				+ ", rooms_booked=" + rooms_booked + "]";
	}

	
	
	
}
