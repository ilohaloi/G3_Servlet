package com.ships_schedule.model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



//控制中心,增刪改查的方法
public class Ships_scheduleDAO implements Ships_scheduleDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3307/CruiseShip?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO ships_schedule (route_id,ship_status,ship_shipping_time,ship_shipping_dock,ship_rooms_booked)VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM ships_schedule order by ship_id";
	private static final String GET_ONE_STMT = "SELECT ship_id,route_id,ship_status,ship_shipping_time,ship_shipping_dock,ship_rooms_booked FROM ships_schedule where ship_id = ?";
	private static final String DELETE = "DELETE FROM ships_schedule where ship_id = ?";
	private static final String UPDATE = "UPDATE ships_schedule set route_id=?, ship_status=?, ship_shipping_time=?, ship_shipping_dock=?, ship_rooms_booked=? where ship_id = ?";
	private static final String SEARCH_STMT = "SELECT * FROM ships_schedule where";

	private static final String SEARCH_ROOMS = "SELECT * from ships_schedule where ship_rooms_booked > ? and route_id = ?";


	@Override
	public List<Ships_scheduleVO> searchRoom(int ship_rooms_booked,int route_id) {

		Ships_scheduleVO ships_scheduleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String srString = SEARCH_ROOMS;
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);


				srString = "SELECT * from ships_schedule where ship_rooms_booked > ? and route_id = ?";
				pstmt = con.prepareStatement(srString);
				pstmt.setInt(1, Integer.valueOf(ship_rooms_booked));
				pstmt.setInt(2, Integer.valueOf(route_id));



			pstmt.execute();
			rs = pstmt.getResultSet();
			List<Ships_scheduleVO> schedulelist = new ArrayList<Ships_scheduleVO>();
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				ships_scheduleVO = new Ships_scheduleVO();
				ships_scheduleVO.setRooms_booked(rs.getInt("ship_rooms_booked"));
				ships_scheduleVO.setRoute_id(rs.getInt("route_id"));
				schedulelist.add(ships_scheduleVO);
				System.out.print("查詢成功");
			}
			return schedulelist;


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

	@Override
	public List<Ships_scheduleVO> search(String columnName,String value) {

		Ships_scheduleVO ships_scheduleVO = null;


		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String searchStatementString = SEARCH_STMT;
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			switch (columnName) {
			case "ship_id":
				searchStatementString += " ship_id = ?";
				pstmt = con.prepareStatement(searchStatementString);
				pstmt.setInt(1, Integer.valueOf(value));
				break;
			case "route_id":
				searchStatementString += " route_id = ?";
				pstmt = con.prepareStatement(searchStatementString);
				pstmt.setInt(1, Integer.valueOf(value));
				break;
			case "status":
				searchStatementString += " ship_status Like ?";
				pstmt = con.prepareStatement(searchStatementString);
				pstmt.setString(1, "%" + value + "%");
				break;
			case "shipping_time":
			    searchStatementString += " ship_shipping_time = ?";
			    pstmt = con.prepareStatement(searchStatementString);
			    pstmt.setDate(1, java.sql.Date.valueOf(value));
			    break;
			case "shipping_dock":
				searchStatementString+= " ship_shipping_dock Like ?";
				pstmt = con.prepareStatement(searchStatementString);
				pstmt.setString(1, "%" + value + "%");
				break;
			case "rooms_booked":
				searchStatementString+= " ship_rooms_booked = ?";
				pstmt = con.prepareStatement(searchStatementString);
				pstmt.setInt(1, Integer.valueOf(value));
				break;
			default:

				pstmt = con.prepareStatement("SELECT * FROM ships_schedule");
				break;

			}

			pstmt.execute();
			rs = pstmt.getResultSet();
			List<Ships_scheduleVO> schedulelist = new ArrayList<Ships_scheduleVO>();
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				ships_scheduleVO = new Ships_scheduleVO();
				ships_scheduleVO.setShip_id(rs.getInt("ship_id"));
				ships_scheduleVO.setRoute_id(rs.getInt("route_id"));
				ships_scheduleVO.setStatus(rs.getString("ship_status"));
				ships_scheduleVO.setShipping_time(rs.getDate("ship_shipping_time"));
				ships_scheduleVO.setShipping_dock(rs.getString("ship_shipping_dock"));
				ships_scheduleVO.setRooms_booked(rs.getInt("ship_rooms_booked"));
				schedulelist.add(ships_scheduleVO);
				System.out.print("查詢成功");
			}
			return schedulelist;


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
	@Override
	public void insert(Ships_scheduleVO ships_scheduleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);// 載入驅動
			con = DriverManager.getConnection(url, userid, passwd);// 建立連線
			System.out.println("連線成功");

			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, ships_scheduleVO.getRoute_id());
			pstmt.setString(2, ships_scheduleVO.getStatus());
			pstmt.setDate(3, ships_scheduleVO.getShipping_time()); // 上面要import java.sql.Date;
			pstmt.setString(4, ships_scheduleVO.getShipping_dock());
			pstmt.setInt(5, ships_scheduleVO.getRooms_booked());

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
	public void delete(Integer ship_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ship_id);

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
				// get("要打的跟SQL欄位同名")
				ships_scheduleVO = new Ships_scheduleVO();
				ships_scheduleVO.setShip_id(rs.getInt("ship_id"));
				ships_scheduleVO.setRoute_id(rs.getInt("route_id"));
				ships_scheduleVO.setStatus(rs.getString("ship_status"));
				ships_scheduleVO.setShipping_time(rs.getDate("ship_shipping_time"));
				ships_scheduleVO.setShipping_dock(rs.getString("ship_shipping_dock"));
				ships_scheduleVO.setRooms_booked(rs.getInt("ship_rooms_booked"));
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
				// get("要打的跟SQL欄位同名")
				ships_scheduleVO = new Ships_scheduleVO();
				ships_scheduleVO.setShip_id(rs.getInt("ship_id"));
				ships_scheduleVO.setRoute_id(rs.getInt("route_id"));
				ships_scheduleVO.setStatus(rs.getString("ship_status"));
				ships_scheduleVO.setShipping_time(rs.getDate("ship_shipping_time"));
				ships_scheduleVO.setShipping_dock(rs.getString("ship_shipping_dock"));
				ships_scheduleVO.setRooms_booked(rs.getInt("ship_rooms_booked"));
				list.add(ships_scheduleVO); // Store the row in the list
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
