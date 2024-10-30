package com.route.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import kotlin.Pair;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RouteDAO implements RouteDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3307/CruiseShip?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";
	
	

	private static final String INSERT_STMT = "INSERT INTO route (route_name,route_depiction,route_days,route_price,route_image)VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM route order by route_id";
	private static final String GET_ONE_STMT = "SELECT route_id,route_name,route_depiction,route_days,route_price,route_image FROM route where route_id = ?";
	private static final String DELETE = "DELETE FROM route where route_id = ?";
	private static final String UPDATE = "UPDATE route set route_name=?, route_depiction=?, route_days=?, route_price=?, route_image=? where route_id = ?";
	
	
	
	@Override
	public void insert(RouteVO routeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			System.out.println("連線成功");
					
					pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, routeVO.getName());
			pstmt.setString(2, routeVO.getDepiction());
			pstmt.setInt(3, routeVO.getDays());
			pstmt.setInt(4, routeVO.getPrice());
			pstmt.setString(5, routeVO.getImage());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException | ClassNotFoundException se) {
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
			int i = pstmt.executeUpdate();
			System.out.print("更新" + i +"筆資料");
			// Handle any driver errors
		} catch (SQLException | ClassNotFoundException se) {
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

	@Override
	public void delete(Integer id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException | ClassNotFoundException se) {
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
				System.out.print("查詢成功");
			}

			// Handle any driver errors
		} catch (SQLException | ClassNotFoundException se) {
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
				System.out.print("查詢成功");
			}
			return list;
			// Handle any driver errors
		} catch (SQLException | ClassNotFoundException se) {
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

}
