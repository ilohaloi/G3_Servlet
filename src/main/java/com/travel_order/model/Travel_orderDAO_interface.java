package com.travel_order.model;

import java.util.List;

import com.ships_schedule.model.Ships_scheduleVO;

public interface Travel_orderDAO_interface {
	public void insert(Travel_orderVO travel_orderVO);
    public void update(Travel_orderVO travel_orderVO);
    public void delete(Integer travel_orderVO);
    public List<Travel_orderVO> search(String columnName,String vaule);
    public Travel_orderVO findByPrimaryKey(Integer travel_orderVO);
    public List<Travel_orderVO> getAll();
}
