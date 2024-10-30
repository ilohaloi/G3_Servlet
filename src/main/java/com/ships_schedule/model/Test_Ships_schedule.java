package com.ships_schedule.model;

import java.sql.Date;
import java.util.List;
import com.route.model.RouteDAO;
import com.route.model.RouteVO;


public class Test_Ships_schedule {

	public static void main(String[] args) {
		
		//新增資料
//		Ships_scheduleVO ships_scheduleVO = new Ships_scheduleVO(); 
//		Date data = new Date(System.currentTimeMillis());//獲取當前時間 		
//		ships_scheduleVO.setShip_id(1);
//		ships_scheduleVO.setRoute_id(1);
//		ships_scheduleVO.setStatus("行駛中");//船舶狀態		
//		ships_scheduleVO.setShipping_time(data);//出航時間
//		ships_scheduleVO.setShipping_dock("台中港");//碼頭
//		ships_scheduleVO.setRooms_booked(5);//預訂房間數	
//		
//		Ships_scheduleDAO ships_scheduleDAO = new Ships_scheduleDAO();
//		ships_scheduleDAO.insert(ships_scheduleVO);
		
        
		//更改資料,若有欄位未改會變空值
		Ships_scheduleVO ships_scheduleVO = new Ships_scheduleVO();
		Date data = new Date(System.currentTimeMillis());//獲取當前時間 		
		ships_scheduleVO.setShip_id(5);
		ships_scheduleVO.setRoute_id(2);
		ships_scheduleVO.setStatus("停駛");//船舶狀態		
		ships_scheduleVO.setShipping_time(data);
		ships_scheduleVO.setShipping_dock("高雄港");//碼頭		
		ships_scheduleVO.setRooms_booked(8);//預訂房間數	
	
		Ships_scheduleDAO ships_scheduleDAO = new Ships_scheduleDAO();
		ships_scheduleDAO.update(ships_scheduleVO);
		
		//刪除-填寫的ship_id
//		Ships_scheduleDAO ships_scheduleDAO = new Ships_scheduleDAO();
//		ships_scheduleDAO.delete(4);
		
		//查詢單筆,VO層一定要Override toString()否則會印不出來
//		Ships_scheduleDAO ships_scheduleDAO = new Ships_scheduleDAO();
//		System.out.println(ships_scheduleDAO.findByPrimaryKey(2));
		
		//查詢全部行程,10/12測試正常
//		Ships_scheduleDAO ships_scheduleDAO = new Ships_scheduleDAO();
//		List<Ships_scheduleVO> list = ships_scheduleDAO.getAll();
//		for (Ships_scheduleVO ships_scheduleVO : list) {
//			System.out.print(ships_scheduleVO.getShip_id() + ",");
//			System.out.print(ships_scheduleVO.getRoute_id() + ",");
//			System.out.print(ships_scheduleVO.getStatus() + ",");
//			System.out.print(ships_scheduleVO.getShipping_dock() + ",");
//			System.out.print(ships_scheduleVO.getShipping_time() + ",");
//			System.out.print(ships_scheduleVO.getRooms_booked());
//			System.out.println();
//		}
	
	}

}