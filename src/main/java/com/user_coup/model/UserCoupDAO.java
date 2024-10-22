package com.user_coup.model;

import java.util.List;

public interface UserCoupDAO {
	// 此介面定義對資料庫的相關存取抽象方法
	int add(UserCoupon userCoup);

	int update(UserCoupon userCoup);

	int delete(Integer no);

	UserCoupon findByPK(Integer no);

	List<UserCoupon> getAll();
}