package com.order.model;


import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.prod.model.ProdVo;


@Entity
@Table(name = "order_detail")
@Access(AccessType.FIELD)
public class OrderDetailVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 6040461937122942624L;

	@EmbeddedId
	private OrderDetailId id = new OrderDetailId();


	@ManyToOne
	@MapsId("orderId")
    @JoinColumn(name = "order_id",referencedColumnName = "order_id")
	private OrderListVo order;


	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("prodId")
    @JoinColumn(name = "prod_id", referencedColumnName = "prod_id")
	private ProdVo prod;

	@Expose
	@Transient
	private String name;


	@Expose
	@Column(name = "prod_qty")
	private Integer qty;

	@Expose
	@Column(name = "prod_price")
	private Integer price;

	public OrderDetailVo() {
		super();
	}

	public OrderDetailVo(OrderListVo order, ProdVo prodId, Integer prodQty, Integer prodPrice) {
		super();
		this.order = order;
		this.prod = prodId;
		this.qty = prodQty;
		this.price = prodPrice;
	}

	public OrderListVo getOrder() {
		return order;
	}

	public ProdVo getProd() {
		return prod;
	}

	public Integer getQty() {
		return qty;
	}

	public Integer getPrice() {
		return price;
	}

	public OrderDetailId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setOrder(OrderListVo order) {
		this.order = order;
	}

	public void setProd(ProdVo prod) {
		this.prod = prod;
	}

	public void setQty(Integer prodQty) {
		this.qty = prodQty;
	}

	public void setId(OrderDetailId id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Integer prodPrice) {
		this.price = prodPrice;
	}

	@Override
	public String toString() {
		return "OrderDetailVo [orderId=" + order.getOrid() + ", prodId=" + prod.getId() + ", prodQty=" + qty + ", prodPrice="
				+ price + "]";
	}

}
