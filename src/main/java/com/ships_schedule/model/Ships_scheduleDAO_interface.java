package com.ships_schedule.model;

import java.util.List;

import com.route.model.RouteVO;

public interface Ships_scheduleDAO_interface {
	public void insert(Ships_scheduleVO ships_scheduleVO);
    public void update(Ships_scheduleVO ships_scheduleVO);
    public void delete(Integer ships_scheduleVO);
    public Ships_scheduleVO findByPrimaryKey(Integer ships_scheduleVO);
    public List<Ships_scheduleVO> search(String columnName,String vaule);
    public List<Ships_scheduleVO> searchRoom(int route_id,int ship_rooms_booked);
    public List<Ships_scheduleVO> getAll();
}
