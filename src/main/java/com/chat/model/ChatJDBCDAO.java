package com.chat.model;

import java.util.*;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.sql.*;

public class ChatJDBCDAO implements ChatDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3307/CruiseShip?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	// 增加對話
	private static final String INSERT_STMT = "INSERT INTO conversation_content (memb_id,empo_id,conv_speaking_time,conv_content) VALUES (?, ?, ?, ?)";
	// 查詢所有聊天紀錄
	private static final String GET_ALL_STMT = "SELECT memb_id,empo_id,conv_speaking_time,conv_content FROM conversation_content order by conv_speaking_time";
	// 查詢特定會員的特定對話
	private static final String GET_ONE_CONV_ByOneMEMB_STMT = "SELECT memb_id, empo_id,conv_speaking_time,conv_content FROM conversation_content WHERE memb_id = ? AND conv_content LIKE ?";
	// 查詢特定會員的所有對話
	private static final String GET_ALL_CONV_ByOneMEMB_STMT = "SELECT memb_id,empo_id,conv_speaking_time,conv_content FROM conversation_content where memb_id = ? ";

	// =======================增=============================================
	// 增加對話
	@Override
	public void insert(ChatVO chatVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 加載 JDBC 驅動
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 準備 SQL 語句
			pstmt = con.prepareStatement(INSERT_STMT);
			// 設置參數
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
//=============================================查
	
	// 查詢特定會員的所有對話
	@Override
	public List<ChatVO> findByMembId(Integer memb_id) {

		// 定義變數
		List<ChatVO> chatList = new ArrayList<>();
		ChatVO chatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 加載JDBC
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 準備SQL
			pstmt = con.prepareStatement(GET_ALL_CONV_ByOneMEMB_STMT);
			// 設置參數
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
//*********************我是間隔*******************************************
	// 查詢特定會員的特定對話
	@Override
	public List<ChatVO> findByMembIdAndConvContent(Integer memb_id, String conv_content) {

		// 定義變數
		List<ChatVO> chatList = new ArrayList<>();
		ChatVO chatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 加載 JDBC 驅動
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 準備 SQL 語句
			pstmt = con.prepareStatement(GET_ONE_CONV_ByOneMEMB_STMT);
			// 設置參數
			pstmt.setInt(1, memb_id);
			pstmt.setString(2, "%" + conv_content + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
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
		
	//*********************我是間隔*******************************************
	// 查詢全部對話
	
	@Override
	public List<ChatVO> getAll() {
		
		// 定義變數
		List<ChatVO> chatList = new ArrayList<ChatVO>();
		ChatVO chatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 加載 JDBC 驅動
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
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

//try(Jedis jedis = pool.getResource())
//(JedisPool)getServletContext().getAttribute("redis")
