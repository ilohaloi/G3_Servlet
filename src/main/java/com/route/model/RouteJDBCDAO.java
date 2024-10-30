package com.route.model;

import java.util.*;


import java.sql.*;

public class RouteJDBCDAO implements RouteDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3307/CruiseShip?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO route (route_name,route_depiction,route_days,route_price,route_image)VALUES (?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM route order by route_id";
		private static final String GET_ONE_STMT = 
			"SELECT route_id,route_name,route_depiction,route_days,route_price,route_image FROM route where route_id = ?";
		private static final String DELETE = 
			"DELETE FROM route where route_id = ?";
		private static final String UPDATE = 
			"UPDATE route set route_name=?, route_depiction=?, route_days=?, route_price=?, route_image=? where route_id = ?";

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
			pstmt.setString(5, routeVO.getImage());

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

			pstmt.setString(1, routeVO.getName());
			pstmt.setString(2, routeVO.getDepiction());
			pstmt.setInt(3, routeVO.getDays());
			pstmt.setInt(4, routeVO.getPrice());
			pstmt.setString(5, routeVO.getImage());
			pstmt.setInt(6, routeVO.getId());//注意PK是放在最後!!!

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
				routeVO.setId(rs.getInt("route_id"));
				routeVO.setName(rs.getString("route_name"));
				routeVO.setDepiction(rs.getString("route_depiction"));
				routeVO.setPrice(rs.getInt("route_price"));
				routeVO.setDays(rs.getInt("route_days"));
				routeVO.setImage(rs.getString("route_image"));
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
				routeVO.setId(rs.getInt("route_id"));
				routeVO.setName(rs.getString("route_name"));
				routeVO.setDepiction(rs.getString("route_depiction"));
				routeVO.setPrice(rs.getInt("route_price"));
				routeVO.setDays(rs.getInt("route_days"));
				routeVO.setImage(rs.getString("route_image"));
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
		routeVO.setImage("圖片");
		routeJDBCDAO.insert(routeVO);

		// 修改
		RouteVO routeVO1 = new RouteVO();
		routeVO1.setId(1);
		routeVO1.setName("日本");
		routeVO1.setDepiction("沖繩");
		routeVO1.setDays(5);
		routeVO1.setPrice(10000);
		routeVO1.setImage("圖片2");
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
		System.out.print(routeVO2.getImage() + ",");
		System.out.println("---------------------");

		// 查詢
		List<RouteVO> list = routeJDBCDAO.getAll();
		for (RouteVO aRoute : list) {
			System.out.print(aRoute.getId() + ",");
			System.out.print(aRoute.getName() + ",");
			System.out.print(aRoute.getDepiction() + ",");
			System.out.print(aRoute.getPrice() + ",");
			System.out.print(aRoute.getDays() + ",");
			System.out.print(aRoute.getImage() + ",\t");
			System.out.println();
		}
	}
}
