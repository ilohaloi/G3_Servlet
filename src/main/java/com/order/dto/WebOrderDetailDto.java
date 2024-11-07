package com.order.dto;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.order.model.OrderDetailVo;
import com.order.model.OrderListVo;

import kotlin.Pair;

public class WebOrderDetailDto {

	@Expose
	List<Pair<Integer,OrderDetailVo>> prods;

	@Expose
	OrderListVo order;

	public WebOrderDetailDto() {}

	public WebOrderDetailDto(List<Pair<Integer,OrderDetailVo>> prods, OrderListVo order) {
		super();
		this.prods = prods;
		this.order = order;
	}
	public List<Pair<Integer,OrderDetailVo>> getProds() {
		return prods;
	}
	public OrderListVo getOrder() {
		return order;
	}
	public void setProds(List<Pair<Integer,OrderDetailVo>> prods) {
		this.prods = prods;
	}
	public void setOrder(OrderListVo order) {
		this.order = order;
	}
}
