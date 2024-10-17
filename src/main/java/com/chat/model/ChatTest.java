package com.chat.model;

import java.util.List;

import com.chat.model.ChatVO;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class ChatTest {

	public static void main(String[] args) {

		// 新增資料
		//成功
//		ChatVO chatVO = new ChatVO();
//		chatVO.setMembId(1);// 表格自增主鍵無法指定ID
//		chatVO.setEmpoId(1);
//		chatVO.setConvContent("哈囉你好");
//
//		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//		chatVO.setConvSpeakingTime(currentTimestamp);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定義格式
//		String formattedDate = sdf.format(chatVO.getConvSpeakingTime()); // 轉換 TimeStamp 為字串
//		
//		ChatJDBCDAO chatjdbcdao = new ChatJDBCDAO();
//		chatjdbcdao.insert(chatVO);
//				
//		System.out.print("hello");
//	}
//}



	//查詢單筆,VO層一定要Override toString()否則會印不出來
		// 查詢特定會員的所有對話(成功)

//		ChatJDBCDAO chatjdbcdao = new ChatJDBCDAO();
//		
//		ChatVO chatVO = new ChatVO();
//		List<ChatVO> list = chatjdbcdao.findByMembId(1);
//		
//		System.out.print(list);
		
		
		
		//======不需要
//		System.out.print(chatVO.getMembId() + ",");
//		System.out.print(chatVO.getEmpoId() + ",");
//		System.out.print(chatVO.getConvSpeakingTime() + ",");
//		System.out.print(chatVO.getConvContent() + ",");
//		System.out.println("---------------------");
//		System.out.print("hello");
//		}
//	}

//****************************我是間隔***********************************
	//  查詢特定會員的特定對話(成功)

//	ChatJDBCDAO chatjdbcdao = new ChatJDBCDAO();
//	ChatVO chatVO = new ChatVO();
//	List<ChatVO> list = chatjdbcdao.findByMembIdAndConvContent(1, "你好");
//	System.out.println(list);  
//	}
//}
//****************************我是間隔***********************************
	// 查詢全部對話(成功)

	ChatJDBCDAO chatjdbcdao = new ChatJDBCDAO();
	ChatVO chatVO = new ChatVO();
	List<ChatVO> list = chatjdbcdao.getAll();
	for (ChatVO chat : list) {
		System.out.print(chat.getMembId() + ",");
		System.out.print(chat.getEmpoId() + ",");
		System.out.print(chat.getConvSpeakingTime() + ",");
		System.out.print(chat.getConvContent() + ",");
		
		System.out.println();
		
	}
	}
}
	