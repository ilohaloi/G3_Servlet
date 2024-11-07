package com.order.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ListModel;

import com.order.dto.WebOrderDto;
import com.order.dto.WebOrderDetailDto;
import com.order.model.OrderDaoImpl;
import com.order.model.OrderDetailVo;
import com.order.model.OrderElementStringDefine;
import com.order.model.OrderListVo;
import com.outherutil.json.JsonDeserializerInterface;
import com.outherutil.json.JsonSerializerInterface;
import com.outherutil.redis.RedisInterface;

import kotlin.Pair;
import redis.clients.jedis.JedisPool;

public class OrderService implements JsonDeserializerInterface, JsonSerializerInterface, OrderElementStringDefine {
	OrderDaoImpl oDaoImpl;

	public final String REDIS_FIND_ALL = "order:*";
	public final String REDIS_ORDER_FOLDER_NAME = "order:";
	public final String REDIS_DETAIL_NAME = "orderDetail:";

	public OrderService() {
		oDaoImpl = new OrderDaoImpl();
	}

	public void insert(WebOrderDto Data) {
		List<OrderDetailVo> oDetail = new ArrayList<>();
		Data.getProd().forEach(e -> {
			OrderDetailVo od = new OrderDetailVo();
			od.setOrder(Data.getData());
			od.setProd(e);
			od.setPrice(e.getPrice() * e.getQty());
			od.setQty(e.getQty());
			oDetail.add(od);
		});
		Data.getData().setOrderDetails(oDetail);
		oDaoImpl.insert(Data.getData());
	}

	public void insert(JedisPool pool, List<OrderListVo> orders) {
		List<Pair<String, Map<String, String>>> data = new ArrayList<Pair<String, Map<String, String>>>();

		orders.forEach(o -> {
			Map<String, String> element = Map.of(memberId, String.valueOf(o.getMembId()), orderTime,
					String.valueOf(o.getTime()), orderStauts, o.getStatus(), orderPayment, o.getPayment(), custName,
					o.getName(), custEmail, o.getEmail(), custTell, o.getTell(), delAddr, o.getAddr(), orderAmount,
					String.valueOf(o.getAmount()));
			data.add(new Pair<>(String.valueOf(o.getOrid()), element));
		});
		oDaoImpl.redisInsert(pool, REDIS_ORDER_FOLDER_NAME, data, 1 * 60);

		List<Pair<String, Map<String, String>>> orderDetails = new ArrayList<Pair<String, Map<String, String>>>();
		orders.forEach(o -> {
			var ods = o.getOrderDetails();
			Map<String, String> od = new TreeMap<String, String>();
			for (var p : ods) {

				String id = String.valueOf(p.getProd().getId());
				od.put(prodId + id, id);
				od.put(prodName + id, p.getProd().getName());
				od.put(prodPrice +  id, String.valueOf(p.getPrice()));
			}
			orderDetails.add(new Pair<>(String.valueOf(o.getOrid()), od));
		});

		oDaoImpl.redisInsert(pool, "orderDetail:", orderDetails, RedisInterface.NO_TTL);
	}

	public List<OrderListVo> getOrders(JedisPool pool) {
		var orders = oDaoImpl.redisGetAllByKey(pool, REDIS_FIND_ALL);
		if (orders == null || orders.isEmpty()) {
			orders = oDaoImpl.getAll("order_list", OrderListVo.class);

			insert(pool, orders);
			System.out.println("from sql");
		}
		orders.sort((o1, o2) -> Integer.compare(o1.getOrid(), o2.getOrid()));
		return orders;
	}

	// 會員用
	public List<OrderListVo> getOrders(String membid) {
		return oDaoImpl.getByMembId(Integer.valueOf(membid));
	}

	public List<OrderListVo> getMultipleQuery(List<String>query,List<String>vaule){

		Map<String,String> quertMap = new HashMap<String, String>();

		for (int i = 0; i < query.size(); i++) {
			quertMap.put(query.get(i),vaule.get(i));
		}
		return oDaoImpl.getMultipleQuery(quertMap);
	}

	public List<OrderDetailVo> getOrderDetail(String orderid, String membid) {
		return oDaoImpl.getOrderDetail(Integer.valueOf(orderid), Integer.valueOf(membid));
	}

	public WebOrderDetailDto getOrderDetail(String orderid) {
		var order = oDaoImpl.getOrderDetail(Integer.valueOf(orderid));
		WebOrderDetailDto oDetail = new WebOrderDetailDto();
		oDetail.setOrder(order);

		List<Pair<Integer, OrderDetailVo>> prodsList = new ArrayList<Pair<Integer, OrderDetailVo>>();
		order.getOrderDetails().forEach(e -> {

			e.setName(e.getProd().getName());
			prodsList.add(new Pair<Integer, OrderDetailVo>(e.getProd().getId(), e));
		});

		oDetail.setProds(prodsList);

		return oDetail;
	}
}
