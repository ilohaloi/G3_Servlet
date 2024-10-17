package com.route.model;

import java.util.*;


import java.sql.*;

public class RouteJDBCDAO implements RouteDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3307/CruiseShip?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO route (name,depiction,days,price) VALUES (?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT id,name,depiction,days,price FROM route order by id";
		private static final String GET_ONE_STMT = 
			"SELECT id,name,depiction,days,price FROM route where id = ?";
		private static final String DELETE = 
			"DELETE FROM route where id = ?";
		private static final String UPDATE = 
			"UPDATE route set name=?, depiction=?, days=?, price=? where id = ?";

	@Override
	public void insert(RouteVO routeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, routeVO.getName());
			pstmt.setString(2, routeVO.getDepiction());
			pstmt.setInt(3, routeVO.getDays());
			pstmt.setInt(4, routeVO.getPrice());

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
	public void update(RouteVO routeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, routeVO.getId());
			pstmt.setString(2, routeVO.getName());
			pstmt.setString(3, routeVO.getDepiction());
			pstmt.setInt(4, routeVO.getDays());
			pstmt.setInt(5, routeVO.getPrice());

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
	public void delete(Integer id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1,id);

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
	public RouteVO findByPrimaryKey(Integer id) {

		RouteVO routeVO = null;
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
				// empVo 也稱為 Domain objects
				routeVO = new RouteVO();
				routeVO.setId(rs.getInt("id"));
				routeVO.setName(rs.getString("name"));
				routeVO.setDepiction(rs.getString("depiction"));
				routeVO.setPrice(rs.getInt("price"));
				routeVO.setDays(rs.getInt("days"));
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
		return routeVO;
	}

	@Override
	public List<RouteVO> getAll() {
		List<RouteVO> list = new ArrayList<RouteVO>();
		RouteVO routeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				routeVO = new RouteVO();
				routeVO.setId(rs.getInt("id"));
				routeVO.setName(rs.getString("name"));
				routeVO.setDepiction(rs.getString("setDepiction"));
				routeVO.setPrice(rs.getInt("price"));
				routeVO.setDays(rs.getInt("days"));
				list.add(routeVO); // Store the row in the list
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

	public static void main(String[] args) {

		RouteJDBCDAO routeJDBCDAO = new RouteJDBCDAO();

		// 新增
		RouteVO routeVO = new RouteVO();
		routeVO.setId(1);
		routeVO.setName("日本");
		routeVO.setDepiction("沖繩");
		routeVO.setDays(5);
		routeVO.setPrice(10000);
		routeJDBCDAO.insert(routeVO);

		// 修改
		RouteVO routeVO1 = new RouteVO();
		routeVO1.setId(1);
		routeVO1.setName("日本");
		routeVO1.setDepiction("沖繩");
		routeVO1.setDays(5);
		routeVO1.setPrice(10000);
		
		routeJDBCDAO.update(routeVO);
		// 刪除
		routeJDBCDAO.delete(2);

		// 查詢
		RouteVO routeVO2 = routeJDBCDAO.findByPrimaryKey(2);
		System.out.print(routeVO2.getId() + ",");
		System.out.print(routeVO2.getName() + ",");
		System.out.print(routeVO2.getDepiction() + ",");
		System.out.print(routeVO2.getPrice() + ",");
		System.out.print(routeVO2.getDays() + ",");
		
		System.out.println("---------------------");

		// 查詢
		List<RouteVO> list = routeJDBCDAO.getAll();
		for (RouteVO aRoute : list) {
			System.out.print(aRoute.getId() + ",");
			System.out.print(aRoute.getName() + ",");
			System.out.print(aRoute.getDepiction() + ",");
			System.out.print(aRoute.getPrice() + ",");
			System.out.print(aRoute.getDays() + ",\t");
			
			System.out.println();
		}
	}
}
