package com.member.model;

import java.sql.Date;
import java.util.List;

import com.member.model.MemberVO;

public interface MemberDAO {
	// 此介面定義對資料庫的相關存取抽象方法
	void insert(MemberVO memberVO);
	void update(MemberVO memberVO);
	MemberVO findByPK(Integer id);
	List<MemberVO> getAll();
	public boolean isEmailExists(String email);
	MemberVO findByEmail(String email);

}