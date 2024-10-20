package com.memb.test;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.member.model.MemberJDBC;
import com.member.model.MemberVO;

public class TestMembCrud {
	public static MemberJDBC memDao;
	public static MemberVO memberVO1;
	@BeforeAll
	public static void init() {
		memDao = new MemberJDBC();
		memberVO1 = new MemberVO();
		memberVO1.setName("王小明");
		memberVO1.setEmail("Tibame@tibame.me");
		memberVO1.setTell("0912345678");
		memberVO1.setAddress("新北市汐止區新台五路一段100號25樓");
		memberVO1.setBirthday(java.sql.Date.valueOf("2005-01-01"));
		memberVO1.setPassword("12345678");
	}

	@Test
	public void testInsert() {
		memDao.insert(memberVO1);
	}

	//OK
//	@Test
	public void testUpdate() {

		memberVO1.setPassword("987654321");
		memberVO1.setId(1);
		memDao.update(memberVO1);
	}

	//OK
//	@Test
	public void testGetAll() {
		List<MemberVO> list = memDao.getAll();
		for (MemberVO aMember : list) {
			System.out.print(aMember.getId() + ",");
			System.out.print(aMember.getName() + ",");
			System.out.print(aMember.getEmail() + ",");
			System.out.print(aMember.getTell() + ",");
			System.out.print(aMember.getAddress() + ",");
			System.out.print(aMember.getBirthday());
			System.out.println(aMember.getPassword());
			System.out.println("---------------------");
		}
	}

	//OK
//	@Test
	public void testGetByPk() {
		var memb =  memDao.findByPK(1);

		System.out.print(memb.getId() + ",");
		System.out.print(memb.getName() + ",");
		System.out.print(memb.getEmail() + ",");
		System.out.print(memb.getTell() + ",");
		System.out.print(memb.getAddress() + ",");
		System.out.print(memb.getBirthday() + ",");
		System.out.println(memb.getPassword());
		System.out.println("---------------------");
	}
}
