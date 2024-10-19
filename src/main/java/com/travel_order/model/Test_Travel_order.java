package com.travel_order.model;

import java.util.List;

public class Test_Travel_order {
	
	public static void main(String[] args) {
	
		//新增資料
//		Travel_orderVO travel_orderVO = new Travel_orderVO(); 
//		
//		travel_orderVO.setId(1);
//		travel_orderVO.setMemb_id(2);
//		travel_orderVO.setShip_id(3);		
//		travel_orderVO.setCoup_id(3);
//		travel_orderVO.setTrav_orde_status("測試狀態");
//		travel_orderVO.setRoom_amount(2);
//		travel_orderVO.setTrav_orde_amount("測試總價");	
//		
//		Travel_orderDAO travel_orderDAO = new Travel_orderDAO();
//		travel_orderDAO.insert(travel_orderVO);
	
    
		//更改資料,若有欄位未改會變空值
//		Travel_orderVO travel_orderVO = new Travel_orderVO(); 
//		
//		travel_orderVO.setId(12);
//		travel_orderVO.setMemb_id(1);
//		travel_orderVO.setShip_id(3);		
//		travel_orderVO.setCoup_id(3);
//		travel_orderVO.setTrav_orde_status("專題");
//		travel_orderVO.setRoom_amount(5);
//		travel_orderVO.setTrav_orde_amount("快寫完");	
//		
//		Travel_orderDAO travel_orderDAO = new Travel_orderDAO();
//		travel_orderDAO.update(travel_orderVO);
	
		//刪除-填寫的ship_id
//		Travel_orderDAO travel_orderDAO = new Travel_orderDAO();
//		travel_orderDAO.delete(2);
	
		//查詢單筆,VO層一定要Override toString()否則會印不出來
//		Travel_orderDAO travel_orderDAO = new Travel_orderDAO();
//		System.out.println(travel_orderDAO.findByPrimaryKey(2));
		
		
	
		//查詢全部行程
		Travel_orderDAO travel_orderDAO = new Travel_orderDAO();
		List<Travel_orderVO> list = travel_orderDAO.getAll();
		for (Travel_orderVO travel_orderVO : list) {
			System.out.print(travel_orderVO.getId() + ",");
			System.out.print(travel_orderVO.getMemb_id() + ",");
			System.out.print(travel_orderVO.getShip_id() + ",");
			System.out.print(travel_orderVO.getCoup_id() + ",");
			System.out.print(travel_orderVO.getTrav_orde_status() + ",");
			System.out.print(travel_orderVO.getRoom_amount() + ",");
			System.out.print(travel_orderVO.getTrav_orde_amount());
			System.out.println();
		}

}
}
