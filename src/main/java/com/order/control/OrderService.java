package com.order.control;

import java.util.ArrayList;
import java.util.List;

import com.order.model.OrderDaoImpl;
import com.order.model.OrderDetailVo;
import com.order.model.OrderListVo;
import com.order.model.WebOrder;
import com.order.model.WebOrderDetail;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;

import kotlin.Pair;

public class OrderService implements JsonDeserializerInterface, JsonSerializerInterface{
	OrderDaoImpl oDaoImpl;
	public OrderService(){
		oDaoImpl = new OrderDaoImpl();
	}

	public void insertOrder(WebOrder Data) {
		List<OrderDetailVo> oDetail = new ArrayList<>();
		System.out.println(Data.getData());
		System.out.println(Data.getProd());
		Data.getProd().forEach(e->{
			OrderDetailVo od = new OrderDetailVo();
			od.setOrder(Data.getData());
			od.setProd(e);
			od.setPrice(e.getPrice()* e.getQty());
			od.setQty(e.getQty());
			oDetail.add(od);
		});
		Data.getData().setOrderDetails(oDetail);
		oDaoImpl.insert(Data.getData());
	}

	public List<OrderListVo> getOrders(){
		return oDaoImpl.getAll("order_list", OrderListVo.class);
	}

	//會員用
	public List<OrderListVo> getOrders(String membid){
		return oDaoImpl.getByMembId(Integer.valueOf(membid));
	}

	public List<OrderDetailVo> getOrderDetail(String orderid,String membid){
		return oDaoImpl.getOrderDetail(Integer.valueOf(orderid), Integer.valueOf(membid));
	}

	public WebOrderDetail getOrderDetail(String orderid){
		var order = oDaoImpl.getOrderDetail(Integer.valueOf(orderid));
		WebOrderDetail oDetail = new WebOrderDetail();
		oDetail.setOrder(order);

		List<Pair<Integer,OrderDetailVo>> prodsList = new ArrayList<Pair<Integer,OrderDetailVo>>();
		order.getOrderDetails().forEach(e->{

			e.setName(e.getProd().getName());
			prodsList.add(new Pair<Integer, OrderDetailVo>(e.getProd().getId(),e));
		});

		oDetail.setProds(prodsList);

		return oDetail;
	}
}
