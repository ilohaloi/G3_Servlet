package com.order.dto;

import java.util.List;

import com.order.model.OrderListVo;
import com.prod.model.ProdVo;

public class WebOrderDto {

	private OrderListVo data;
	private List<ProdVo> prod;
	public OrderListVo getData() {
		return data;
	}
	public List<ProdVo> getProd() {
		return prod;
	}
	public void setData(OrderListVo data) {
		this.data = data;
	}
	public void setProd(List<ProdVo> prod) {
		this.prod = prod;
	}


}
