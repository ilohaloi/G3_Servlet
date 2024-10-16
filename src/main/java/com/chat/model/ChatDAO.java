package com.chat.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.chat.model.ChatVO;

public class ChatDAO implements ChatDAO_interface {

	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestCruise");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// �W�[���
	private static final String INSERT_STMT = "INSERT INTO conversation_content (memb_id,empo_id,conv_speaking_time,conv_content) VALUES (?, ?, ?, ?)";
	// �d�ߩҦ���Ѭ���
	private static final String GET_ALL_STMT = "SELECT memb_id,empo_id,conv_speaking_time,conv_content FROM conversation_content order by conv_speaking_time";
	// �d�߯S�w�|�����S�w���
	private static final String GET_ONE_CONV_ByOneMEMB_STMT = "SELECT memb_id, empo_id,conv_speaking_time,conv_content FROM conversation_content WHERE memb_id = ? AND conv_content = ?";
	// �d�߯S�w�|�����Ҧ����
	private static final String GET_ALL_CONV_ByOneMEMB_STMT = "SELECT memb_id,empo_id,con_speaking_time,conv_content FROM conversation_content where memb_id = ? ";

	
	
	//*****************************�ڬO�Ŧ�
	// ���J�@���s�� ChatVO
	@Override
	public void insert(ChatVO chatVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, chatVO.getMembId());
			pstmt.setInt(2, chatVO.getEmpoId());
			pstmt.setTimestamp(3, chatVO.getConvSpeakingTime());
			pstmt.setString(4, chatVO.getConvContent());

			pstmt.executeUpdate("set auto_increment_offset=10;");
			pstmt.executeUpdate("set auto_increment_increment=10;");
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {

			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	
//*****************************************�ڬO�Ŧ�
	
	// �d�߯S�w�|�� (memb_id) ���Ҧ���ܰO��
	@Override
	public List<ChatVO> findByMembId(Integer memb_id) {
		
		List<ChatVO> chatList = new ArrayList<>();
		ChatVO chatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_CONV_ByOneMEMB_STMT);
			pstmt.setInt(1, memb_id);
			rs = pstmt.executeQuery();
		
			while(rs.next()){
				
				chatVO = new ChatVO();
				chatVO.setMembId(rs.getInt("memb_id"));
				chatVO.setEmpoId(rs.getInt("empo_id"));
				chatVO.setConvSpeakingTime(rs.getTimestamp("conv_speaking_time"));
				chatVO.setConvContent(rs.getString("conv_content"));
				chatList.add(chatVO);	
			}
				
		} catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return chatList;
	}
	
	
//*****************************�ڬO�Ŧ�
	// �d�߯S�w�|�����S�w���
	@Override
	public List<ChatVO> findByMembIdAndConvContent(Integer memb_id, String conv_content) {
		
		List<ChatVO> chatList = new ArrayList<>();
		ChatVO chatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_CONV_ByOneMEMB_STMT);

			pstmt.setInt(1, memb_id);
			pstmt.setString(2, "%" + conv_content + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO �]�٬� Domain objects
				chatVO = new ChatVO();
				chatVO.setEmpoId(rs.getInt("emp_id"));
				chatVO.setMembId(rs.getInt("memb_id"));
				chatVO.setConvContent(rs.getString("conv_content"));
				chatVO.setConvSpeakingTime(rs.getTimestamp("conv_speaking_time"));
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return chatList;
	}
	
	//*****************************************�ڬO�Ŧ�
	// �d�ߩҦ� ChatVO
	@Override
	public List<ChatVO> getAll() {
		
		List<ChatVO> chatList = new ArrayList<ChatVO>();
		ChatVO chatVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				chatVO = new ChatVO();
				chatVO.setEmpoId(rs.getInt("empo_id"));
				chatVO.setMembId(rs.getInt("memb_id"));
				chatVO.setConvContent(rs.getString("conv_content"));
				chatVO.setConvSpeakingTime(rs.getTimestamp("conv_speaking_time"));
				
				chatList.add(chatVO); // Store the row in the list
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return chatList;
	}
}


// �D��k�A����CRUD�ާ@
