package com.oder.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.order.control.OrderService;
import com.order.model.OrderDaoImpl;
import com.order.model.OrderDetailVo;
import com.order.model.OrderListVo;

public class TestOrder {
	static OrderListVo order;
	static OrderDaoImpl oDaoImpl;
	static OrderService oService;
	static final String tableName = "order_list";

	@BeforeAll
	public static void init() {
		oDaoImpl = new OrderDaoImpl();
		oService = new OrderService();
	}

//	@Test
	public void testInsert() {
		// 创建订单
	    OrderListVo order = new OrderListVo();
	    order.setMembId(1);
	    order.setStatus("未付款");
	    order.setPayment("信用卡");
	    order.setName("kiki");
	    order.setTell("0123456789");
	    order.setAddr("新北");
	    order.setAmount(10000);

	    List<OrderDetailVo> orderDetailVo = new ArrayList<>();
	    for (int i = 1; i <= 2; i++) { // 从1开始，确保prodId有效
	        OrderDetailVo od = new OrderDetailVo();
	        od.setOrder(order); // 关联订单
	        od.setPrice(999);
	        od.setQty(999);
	        orderDetailVo.add(od);
	    }
	    order.setOrderDetails(orderDetailVo); // 关联订单明细
	    oDaoImpl.insert(order);
	}

//	@Test
	public void testUpdate() {
		oDaoImpl.update(1, order);
	}

//	@Test
	public void testGetByID() {
	 var ode =  oDaoImpl.getByPk(1, OrderListVo.class);
	 System.out.println(ode.getOrderDetails());
	}

//	@Test
	public void testGetOrders() {
	 var o = oService.getOrders();
	 System.out.println(o);
	}

//	@Test
	public void tetsGeto() {
		var o = oService.getOrderDetail("21", "1");
		System.out.println(o);
	}

	@Test
	public void tetsGetOrderDetail() {
		var o = oService.getOrderDetail("22");
		System.out.println(o);
	}


}
