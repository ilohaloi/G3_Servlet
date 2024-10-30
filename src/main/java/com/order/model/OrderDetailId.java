package com.order.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class OrderDetailId implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7750319270969706236L;

	@Column(name = "order_id")
	private Integer orderId;

	@Column(name = "prod_id")
    private Integer prodId;

    public OrderDetailId() {
		super();
	}

	public OrderDetailId(Integer orderId, Integer prodId) {
		super();
		this.orderId = orderId;
		this.prodId = prodId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public Integer getProdId() {
		return prodId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null || getClass() != obj.getClass())
	        return false;
	    OrderDetailId other = (OrderDetailId) obj;
	    return Objects.equals(orderId, other.orderId) && Objects.equals(prodId, other.prodId);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(orderId, prodId);
	}


}