package com.chat.model;

import java.util.*;
import java.sql.*;

public class ChatJDBCDAO implements ChatDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3307/CruiseShip?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	// �W�[���
	private static final String INSERT_STMT = "INSERT INTO conversation_content (memb_id,empo_id,conv_speaking_time,conv_content) VALUES (?, ?, ?, ?)";
	// �d�ߩҦ���Ѭ���
	private static final String GET_ALL_STMT = "SELECT memb_id,empo_id,conv_speaking_time,conv_content FROM conversation_content order by conv_speaking_time";
	// �d�߯S�w�|�����S�w���
	private static final String GET_ONE_CONV_ByOneMEMB_STMT = "SELECT memb_id, empo_id,conv_speaking_time,conv_content FROM conversation_content WHERE memb_id = ? AND conv_content LIKE ?";
	// �d�߯S�w�|�����Ҧ����
	private static final String GET_ALL_CONV_ByOneMEMB_STMT = "SELECT memb_id,empo_id,conv_speaking_time,conv_content FROM conversation_content where memb_id = ? ";

	// =======================�W=============================================
	// �W�[���
	@Override
	public void insert(ChatVO chatVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �[�� JDBC �X��
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// �ǳ� SQL �y�y
			pstmt = con.prepareStatement(INSERT_STMT);
			// �]�m�Ѽ�
			pstmt.setInt(1, chatVO.getMembId());
			pstmt.setInt(2, chatVO.getEmpoId());
			pstmt.setTimestamp(3, chatVO.getConvSpeakingTime());
			pstmt.setString(4, chatVO.getConvContent());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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

	}
//=============================================�d
	
	// �d�߯S�w�|�����Ҧ����
	@Override
	public List<ChatVO> findByMembId(Integer memb_id) {

		// �w�q�ܼ�
		List<ChatVO> chatList = new ArrayList<>();
		ChatVO chatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// �[��JDBC
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// �ǳ�SQL
			pstmt = con.prepareStatement(GET_ALL_CONV_ByOneMEMB_STMT);
			// �]�m�Ѽ�
			pstmt.setInt(1, memb_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				chatVO = new ChatVO();
				chatVO.setMembId(rs.getInt("memb_id"));
				chatVO.setEmpoId(rs.getInt("empo_id"));
				chatVO.setConvSpeakingTime(rs.getTimestamp("conv_speaking_time"));
				chatVO.setConvContent(rs.getString("conv_content"));
				chatList.add(chatVO);
			}
			return chatList;
			// handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

		
	}
//*********************�ڬO���j*******************************************
	// �d�߯S�w�|�����S�w���
	@Override
	public List<ChatVO> findByMembIdAndConvContent(Integer memb_id, String conv_content) {

		// �w�q�ܼ�
		List<ChatVO> chatList = new ArrayList<>();
		ChatVO chatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// �[�� JDBC �X��
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// �ǳ� SQL �y�y
			pstmt = con.prepareStatement(GET_ONE_CONV_ByOneMEMB_STMT);
			// �]�m�Ѽ�
			pstmt.setInt(1, memb_id);
			pstmt.setString(2, "%" + conv_content + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				chatVO = new ChatVO();
				chatVO.setMembId(rs.getInt("memb_id"));
				chatVO.setEmpoId(rs.getInt("empo_id"));
				chatVO.setConvSpeakingTime(rs.getTimestamp("conv_speaking_time"));
				chatVO.setConvContent(rs.getString("conv_content"));
				chatList.add(chatVO);
			}
			return chatList;
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	}
		
	//*********************�ڬO���j*******************************************
	// �d�ߥ������
	
	@Override
	public List<ChatVO> getAll() {
		
		// �w�q�ܼ�
		List<ChatVO> chatList = new ArrayList<ChatVO>();
		ChatVO chatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// �[�� JDBC �X��
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				chatVO = new ChatVO();
				chatVO.setMembId(rs.getInt("memb_id"));
				chatVO.setEmpoId(rs.getInt("empo_id"));
				chatVO.setConvSpeakingTime(rs.getTimestamp("conv_speaking_time"));
				chatVO.setConvContent(rs.getString("conv_content"));

				chatList.add(chatVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
}
