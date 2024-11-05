package com.ships_schedule.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Ships_scheduleJDBCDAO implements Ships_scheduleDAO_interface{

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3307/CruiseShip?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO ships_schedule (route_id,ship_status,ship_shipping_time,ship_shipping_dock,ship_rooms_type,ship_rooms_booked)VALUES (?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM ships_schedule order by ship_id";
	private static final String GET_ONE_STMT = "SELECT ship_id,route_id,ship_status,ship_shipping_time,ship_shipping_dock,ships_rooms_type,ship_rooms_booked FROM ships_schedule where ship_id = ?";
	private static final String DELETE = "DELETE FROM ships_schedule where ship_id = ?";
	private static final String UPDATE = "UPDATE ships_schedule set route_id=?, ship_status=?, ship_shipping_time=?, ship_shipping_dock=?,ships_rooms_type=?, ship_rooms_booked=? where ship_id = ?";

	@Override
	public void insert(Ships_scheduleVO ships_scheduleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, ships_scheduleVO.getRoute_id());
			pstmt.setString(2, ships_scheduleVO.getStatus());
			pstmt.setDate(3, ships_scheduleVO.getShipping_time());// 上面要import java.sql.Date;
			pstmt.setString(4, ships_scheduleVO.getShipping_dock());
			pstmt.setInt(5, ships_scheduleVO.getRooms_booked());

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
	public void update(Ships_scheduleVO ships_scheduleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, ships_scheduleVO.getRoute_id());
			pstmt.setString(2, ships_scheduleVO.getStatus());
			pstmt.setDate(3, ships_scheduleVO.getShipping_time());// 上面要import java.sql.Date;
			pstmt.setString(4, ships_scheduleVO.getShipping_dock());
			pstmt.setInt(5, ships_scheduleVO.getRooms_booked());
			pstmt.setInt(6, ships_scheduleVO.getShip_id());//注意PK是放在最後!!!

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
	public void delete(Integer ship_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1,ship_id);

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
	public Ships_scheduleVO findByPrimaryKey(Integer ship_id) {

		Ships_scheduleVO ships_scheduleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, ship_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				ships_scheduleVO = new Ships_scheduleVO();

				ships_scheduleVO.setShip_id(rs.getInt("ship_id"));
				ships_scheduleVO.setRoute_id(rs.getInt("route_id"));
				ships_scheduleVO.setStatus(rs.getString("ship_status"));
				ships_scheduleVO.setShipping_time(rs.getDate("ship_shipping_time"));
				ships_scheduleVO.setShipping_dock(rs.getString("ship_shipping_dock"));
				ships_scheduleVO.setRooms_booked(rs.getInt("ship_rooms_booked"));

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
		return ships_scheduleVO;
	}

	@Override
	public List<Ships_scheduleVO> getAll() {
		List<Ships_scheduleVO> list = new ArrayList<Ships_scheduleVO>();
		Ships_scheduleVO ships_scheduleVO = null;

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

				ships_scheduleVO = new Ships_scheduleVO();
				ships_scheduleVO.setShip_id(rs.getInt("ship_id"));
				ships_scheduleVO.setRoute_id(rs.getInt("route_id"));
				ships_scheduleVO.setStatus(rs.getString("ship_status"));
				ships_scheduleVO.setShipping_time(rs.getDate("ship_shipping_time"));
				ships_scheduleVO.setShipping_dock(rs.getString("ship_shipping_dock"));

				ships_scheduleVO.setRooms_booked(rs.getInt("ship_rooms_booked"));
				list.add(ships_scheduleVO); // Store the row in the list
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

	@Override
	public List<Ships_scheduleVO> search(String columnName, String vaule) {
		// TODO Auto-generated method stub
		return null;
	}
}
