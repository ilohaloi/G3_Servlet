package com.chat.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.chat.model.ChatDAO;
import com.chat.model.ChatDAO_interface;
import com.chat.model.ChatVO;

import com.emp.model.EmpInterface;
import com.emp.model.EmpVo;
import com.emp.control.EmpService;
import com.emp.model.EmpDaoImpl;
import com.member.control.GetAllMemberServlet;
import com.member.model.MemberDAO;
import com.member.model.MemberJDBC;
import com.member.model.MemberVO;
import com.outherutil.HibernateUtil;



public class ChatService {

	private ChatDAO_interface dao;
	private MemberJDBC mjdbc;
	private EmpInterface empInter; 
	

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
	
	//******************************調用員工資料**************************************
    public List<EmpVo> getAllEmployees() {
        EmpService eService = new EmpService();
        
    	List<EmpVo> employees = null;
		try {
			employees = eService.getAllEmpData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (employees.isEmpty()) {
            throw new RuntimeException("No employees found.");
        }
        return employees;
    }
   //******************************調用會員資料**************************************
    public List<MemberVO> getAllMembers() {
    	//new東西
    	MemberJDBC mDao= new MemberJDBC();
        List<MemberVO> members = null;
		try {
			members = mDao.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (members.isEmpty()) {
            throw new RuntimeException("No members found.");
        }
        return members;
    }
}
	
	

