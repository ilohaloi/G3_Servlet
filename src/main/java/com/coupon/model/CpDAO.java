package com.coupon.model;

import java.sql.Timestamp;
import java.util.List;

public interface CpDAO {
	// 此介面定義對資料庫的相關存取抽象方法
	int add(Cp cp);

	int update(Cp cp);

	int delete(Integer id);

	Cp findByPK(Integer id);

	List<Cp> getAll();

	List<Cp> findCouponsToBeIssued(Timestamp today);

	// 新增方法：根據 coup_id 查找 UserCoupon
	List<Cp> findByCoupId(Integer coupId);
}