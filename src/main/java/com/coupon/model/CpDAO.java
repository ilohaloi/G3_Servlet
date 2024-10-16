package com.coupon.model;
import java.util.List;

//import entity.Cp;


public interface CpDAO {
	// 此介面定義對資料庫的相關存取抽象方法
	int add(Cp cp);
	int update(Cp cp);
	int delete(Integer id);
	Cp findByPK(Integer id);
	List<Cp> getAll();
}