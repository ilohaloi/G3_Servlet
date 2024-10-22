package com.addroute.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int route_id;

    @Column(name = "route_name")
    private String routeName;

    @Column(name = "route_price")
    private int routePrice;

    @Column(name = "route_depiction", columnDefinition = "LONGTEXT")
    private String routeDepiction;

    @Column(name = "route_days")
    private int routeDays;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<ShipSchedule> shipSchedules;

	public int getRoute_id() {
		return route_id;
	}

	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public int getRoutePrice() {
		return routePrice;
	}

	public void setRoutePrice(int routePrice) {
		this.routePrice = routePrice;
	}

	public String getRouteDepiction() {
		return routeDepiction;
	}

	public void setRouteDepiction(String routeDepiction) {
		this.routeDepiction = routeDepiction;
	}

	public int getRouteDays() {
		return routeDays;
	}

	public void setRouteDays(int routeDays) {
		this.routeDays = routeDays;
	}

	public List<ShipSchedule> getShipSchedules() {
		return shipSchedules;
	}

	public void setShipSchedules(List<ShipSchedule> shipSchedules) {
		this.shipSchedules = shipSchedules;
	}
        
}
