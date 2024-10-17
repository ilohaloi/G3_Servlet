package com.route.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;




public class RouteVO implements Serializable {
	
	private static final long serialVersionUID = 1705291059587091553L;
	
	private Integer id;
	private String name;
	private String depiction;
	private Integer days;
	private Integer price;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDepiction() {
		return depiction;
	}


	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}


	public Integer getDays() {
		return days;
	}


	public void setDays(Integer days) {
		this.days = days;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "RouteVO [id=" + id + ", name=" + name + ", depiction=" + depiction + ", days=" + days + ", price="
				+ price + "]";
	}
	
	
	
}
