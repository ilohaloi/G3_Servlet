package com.member.model;

import java.text.ParseException; // 引入 java.text.ParseException
import java.text.SimpleDateFormat;
import java.util.List;

public class JdbcTest {
    public static void main(String[] args) {
    	
    	MemberJDBC dao = new MemberJDBC();

//        // 新增
//        MemberVO memberVO1 = new MemberVO();
//        memberVO1.setName("王小明");
//        memberVO1.setEmail("Tibame@tibame.me");
//        memberVO1.setTell("0912345678");
//        memberVO1.setAddress("新北市汐止區新台五路一段100號25樓");
//
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date date = sdf.parse("2023-01-01");
//            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//            memberVO1.setBirthday(sqlDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        memberVO1.setPassword("12345678");
//
//        dao.insert(memberVO1);
//        System.out.println("新增成功");
//
		// 修改
		MemberVO memberVO2 = new MemberVO();
		memberVO2.setName("王小美");
		memberVO2.setTell("0912345678");
		memberVO2.setAddress("新北市汐止區新台五路一段100號25樓");
		memberVO2.setPassword("12345678");
		memberVO2.setId(4);
		dao.update(memberVO2);
		
		System.out.println("修改成功");

    	// 查詢
//    	MemberVO memberVO3 = dao.findByPK(2);
//		MemberJDBC memberVO3= new MemberJDBC();
//		System.out.println(memberVO3.findByPK(2));
//
//
//		// 查詢
//    	MemberVO memberVO = new MemberVO();
//    	List<MemberVO> list = dao.getAll();
//    	    for (MemberVO member : list) {
//    	        System.out.print(member.getId() + ", ");
//    	        System.out.print(member.getName() + ", ");
//    	        System.out.print(member.getEmail() + ", ");
//    	        System.out.print(member.getTell() + ", ");
//    	        System.out.print(member.getAddress() + ", ");
//    	    	}
//    		}
		}
}

