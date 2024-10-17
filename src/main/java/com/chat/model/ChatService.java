package com.chat.model;

import java.util.List;


public class ChatService {

	private ChatDAO_interface dao;

	public ChatService() {
		dao = new ChatDAO();
	}

	public ChatVO addChat(Integer memb_id, Integer empo_id, java.sql.Timestamp conv_speaking_time,
			String conv_content) {

		ChatVO chatVO = new ChatVO();

		chatVO.setMembId(memb_id);
		chatVO.setEmpoId(empo_id);
		chatVO.setConvSpeakingTime(conv_speaking_time);
		chatVO.setConvContent(conv_content);
		dao.insert(chatVO);

		return chatVO;
	}

	public List<ChatVO> findByMembId(Integer memb_id){
		return dao.findByMembId(memb_id);
	}
	
	public List<ChatVO> findByMembIdAndConvContent(Integer memb_id, String conv_content){
		return dao.findByMembIdAndConvContent(memb_id, conv_content);
	}

	public List<ChatVO> getAll() {
		return dao.getAll();
	}
}
