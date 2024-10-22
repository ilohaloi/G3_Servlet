package com.addroute.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ships_schedule")
public class ShipSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ship_id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(name = "ship_status")
    private String shipStatus;

    @Column(name = "ship_shipping_time")
    private Date shipShippingTime;

    @Column(name = "ship_shipping_dock")
    private String shipShippingDock;

    @Column(name = "ship_rooms_booked")
    private int shipRoomsBooked;

	public int getShip_id() {
		return ship_id;
	}

	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}

	public Date getShipShippingTime() {
		return shipShippingTime;
	}

	public void setShipShippingTime(Date shipShippingTime) {
		this.shipShippingTime = shipShippingTime;
	}

	public String getShipShippingDock() {
		return shipShippingDock;
	}

	public void setShipShippingDock(String shipShippingDock) {
		this.shipShippingDock = shipShippingDock;
	}

	public int getShipRoomsBooked() {
		return shipRoomsBooked;
	}

	public void setShipRoomsBooked(int shipRoomsBooked) {
		this.shipRoomsBooked = shipRoomsBooked;
	}

    
}
