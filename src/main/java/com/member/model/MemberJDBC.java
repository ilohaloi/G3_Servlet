package com.member.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class MemberJDBC implements MemberDAO{
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3307/CruiseShip?serverTimezone=Asia/Taipei";
		String userid = "root";
		String passwd = "123456";

		private static final String INSERT_STMT =
			"INSERT INTO member_data (memb_name,memb_email,memb_tell,memb_address,memb_birthday,memb_password) VALUES (?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT =
			"SELECT * FROM member_data order by memb_id";
		private static final String GET_ONE_STMT =
			"SELECT memb_id,memb_name,memb_email,memb_tell,memb_address,memb_birthday,memb_password FROM member_data where memb_id = ?";
		private static final String UPDATE =
			"UPDATE member_data set memb_name=?, memb_tell=?, memb_address=?, memb_password=? where memb_id = ?";

		@Override
		public void insert(MemberVO memberVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, memberVO.getName());
				pstmt.setString(2, memberVO.getEmail());
				pstmt.setString(3, memberVO.getTell());
				pstmt.setString(4, memberVO.getAddress());
				pstmt.setDate(5, memberVO.getBirthday());
				pstmt.setString(6, memberVO.getPassword());

				pstmt.executeUpdate();

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

		@Override
		public void update(MemberVO memberVO) {

		    Connection con = null;
		    PreparedStatement pstmt = null;

		    try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, memberVO.getName());
				pstmt.setString(2, memberVO.getTell());
				pstmt.setString(3, memberVO.getAddress());
				pstmt.setString(4, memberVO.getPassword());
				pstmt.setInt(5, memberVO.getId());
				pstmt.executeUpdate();


		    } catch (ClassNotFoundException e) {
		        throw new RuntimeException("Couldn't load database driver. " + e.getMessage(), e);
		    } catch (SQLException se) {
		        throw new RuntimeException("A database error occurred. " + se.getMessage(), se);
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

		@Override
		public MemberVO findByPK(Integer id) {

			MemberVO memberVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, id);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					memberVO = new MemberVO();
					memberVO.setId(rs.getInt("memb_id"));
					memberVO.setName(rs.getString("memb_name"));
					memberVO.setEmail(rs.getString("memb_email"));
					memberVO.setTell(rs.getString("memb_tell"));
					memberVO.setAddress(rs.getString("memb_address"));
					memberVO.setBirthday(rs.getDate("memb_birthday"));
					memberVO.setPassword(rs.getString("memb_password"));
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
			return memberVO;
		}

		@Override
		public List<MemberVO> getAll() {
			List<MemberVO> list = new ArrayList<MemberVO>();
			MemberVO memberVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {

					memberVO = new MemberVO();
					memberVO.setId(rs.getInt("memb_id"));
					memberVO.setName(rs.getString("memb_name"));
					memberVO.setEmail(rs.getString("memb_email"));
					memberVO.setTell(rs.getString("memb_tell"));
					memberVO.setAddress(rs.getString("memb_address"));
					memberVO.setBirthday(rs.getDate("memb_birthday"));
					memberVO.setPassword(rs.getString("memb_password"));
					list.add(memberVO); // Store the row in the list
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
			return list;
		}

//		public static void main(String[] args) {
//
//			MemberJDBC dao = new MemberJDBC();
//
//			// 新增
//			MemberVO memberVO1 = new MemberVO();
//			memberVO1.setName("王小明");
//			memberVO1.setEmail("Tibame@tibame.me");
//			memberVO1.setTell("0912345678");
//			memberVO1.setAddress("新北市汐止區新台五路一段100號25樓");
//			memberVO1.setBirthday(java.sql.Date.valueOf("2005-01-01"));
//			memberVO1.setPassword("12345678");
//			dao.insert(memberVO1);
////
//			// 修改
//			MemberVO memberVO2 = new MemberVO();
//			memberVO1.setName("王小明");
//			memberVO1.setTell("0912345678");
//			memberVO1.setAddress("新北市汐止區新台五路一段100號25樓");
//			memberVO1.setBirthday(java.sql.Date.valueOf("2005-01-01"));
//			memberVO1.setPassword("12345678");
//			dao.insert(memberVO2);
//
//			// 查詢
//			MemberVO memberVO3 = dao.findByPK(1);
//			System.out.print(memberVO3.getId() + ",");
//			System.out.print(memberVO3.getName() + ",");
//			System.out.print(memberVO3.getEmail() + ",");
//			System.out.print(memberVO3.getTell() + ",");
//			System.out.print(memberVO3.getAddress() + ",");
//			System.out.print(((Date) memberVO3).getDate() + ",");
//			System.out.println(memberVO3.getPassword());
//			System.out.println("---------------------");
//
//			// 查詢
//			List<MemberVO> list = dao.getAll();
//			for (MemberVO aMember : list) {
//				System.out.print(aMember.getId() + ",");
//				System.out.print(aMember.getName() + ",");
//				System.out.print(aMember.getEmail() + ",");
//				System.out.print(aMember.getTell() + ",");
//				System.out.print(aMember.getAddress() + ",");
//				System.out.print(((Date) aMember).getDate() + ",");
//				System.out.println(aMember.getPassword());
//				System.out.println();
//			}
//		}
//	}
}

