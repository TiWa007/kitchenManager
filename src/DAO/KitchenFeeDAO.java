package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.KitchenFeeMonth;
import model.KitchenFeeRecipt;
import model.KitchenMember;

public class KitchenFeeDAO {

//	Table kitchen_fee_recipt
	private static final String KITCHEN_FEE_RECIPT_TABLE = "kitchen_fee_recipt";
	private static final String COL_FEE_ID = "fee_id";
	private static final String COL_MEMBER_ID = "member_id";
	private static final String COL_MONTH_ID1 = "month_id";
	private static final String COL_DATE = "date";
	
//	Table kitchen_fee_month
	private static final String KITCHEN_FEE_MONTH_TABLE = "kitchen_fee_month";
	private static final String COL_MONTH_ID = "month_id";
	private static final String COL_MONTH = "month";

	
//	Get KitchenFeeRecipt
	public static List<KitchenFeeRecipt> getKitchenFeeReciptList() throws SQLException {
		String sqlGetRecipt = "SELECT * FROM " + "`" + KITCHEN_FEE_RECIPT_TABLE + "`";
		List<KitchenMember> memberList = KitchenMemberDAO.getKitchenMemberList();
		HashMap<Integer, KitchenMember> memberMap = new HashMap<>();
		for (KitchenMember member : memberList) {
			memberMap.put(member.getId(), member);
		}
		List<KitchenFeeMonth> monthList = getKitchenFeeMonth();
		HashMap<Integer, KitchenFeeMonth> monthMap = new HashMap<>();
		for (KitchenFeeMonth month : monthList) {
			monthMap.put(month.getMonthId(), month);
		}
		
		Connection conn = null;
		PreparedStatement stmtRecipt = null;
		ResultSet rsRecipt = null;

		List<KitchenFeeRecipt> reciptList = null;
		try {
			conn = DBConnection.getConnection();
			stmtRecipt = conn.prepareStatement(sqlGetRecipt, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rsRecipt = stmtRecipt.executeQuery();

			reciptList = new ArrayList<>();
			rsRecipt.beforeFirst();
			while (rsRecipt.next()) {		
				KitchenFeeRecipt recipt = new KitchenFeeRecipt();
				recipt.setFee_id(rsRecipt.getInt(COL_FEE_ID));
				recipt.setMember(memberMap.get(rsRecipt.getInt(COL_MEMBER_ID)));
				recipt.setMonth(monthMap.get(rsRecipt.getInt(COL_MONTH_ID1)));
				recipt.setDate(rsRecipt.getDate(COL_DATE));
				
//				System.out.println(recipt.toString());
				reciptList.add(recipt);
			}
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (rsRecipt != null) rsRecipt.close();
			if (stmtRecipt != null) stmtRecipt.close();
			if (conn != null) conn.close();
		}
		return reciptList;
	}

//	Get MissingMonth 
	public static List<String> getMissingMonthList(KitchenMember member, Date currentMonth) throws SQLException {
		String sqlGetMonth = "SELECT max(month_id) FROM " + "`" + KITCHEN_FEE_MONTH_TABLE + "` " 
				+ "WHERE " + COL_MONTH + " <= ?";
		String sqlGetMissingMonth = "SELECT `month` FROM " + "`" + KITCHEN_FEE_MONTH_TABLE + "` "
				+ "WHERE `month_id` <= ? "
				+ "AND `month_id` NOT IN "
				+ "(SELECT `month_id` FROM " + "`" + KITCHEN_FEE_RECIPT_TABLE + "` " 
				+ "WHERE `month_id` <= ? AND `member_id` = ?) "
				+ "ORDER BY month";
		Connection conn = null;
		PreparedStatement stmtMonth = null;
		PreparedStatement stmtMissingMonth = null;
		ResultSet rsMonth = null;
		ResultSet rsMissingMonth = null;
		List<String> monthList = new ArrayList<>();
//		System.out.println(member.toString());
//		System.out.println(currentMonth.toString());
		try {
			conn = DBConnection.getConnection();
			stmtMonth = conn.prepareStatement(sqlGetMonth, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmtMonth.setDate(1, new java.sql.Date(currentMonth.getTime()));
			rsMonth = stmtMonth.executeQuery();
			rsMonth.beforeFirst();
			rsMonth.next();
			
			Integer monthId = rsMonth.getInt(1);
//			System.out.println(monthId);
			stmtMissingMonth = conn.prepareStatement(sqlGetMissingMonth, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmtMissingMonth.setInt(1, monthId);
			stmtMissingMonth.setInt(2, monthId);
			stmtMissingMonth.setInt(3, member.getId());
			rsMissingMonth = stmtMissingMonth.executeQuery();
			rsMissingMonth.beforeFirst();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy MMMM");
			while (rsMissingMonth.next()) {
				Date date = rsMissingMonth.getDate(1);
//				System.out.println(sf.format(date));
				monthList.add(sf.format(date));
			}	
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (rsMonth != null) rsMonth.close();
			if (rsMissingMonth != null) rsMissingMonth.close();
			if (stmtMonth != null) stmtMonth.close();
			if (stmtMissingMonth != null) stmtMissingMonth.close();
			if (conn != null) conn.close();
		}
		
		return monthList;
	}
	
//	Get KitchenFeeMonth
	public static List<KitchenFeeMonth> getKitchenFeeMonth() throws SQLException {
		String sqlGetMonth = "SELECT * FROM " + "`" + KITCHEN_FEE_MONTH_TABLE + "` " 
				+ "ORDER BY " + COL_MONTH + " ASC";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<KitchenFeeMonth> monthList = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlGetMonth, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery();

			monthList = new ArrayList<>();
			rs.beforeFirst();
			while (rs.next()) {		
				KitchenFeeMonth month = new KitchenFeeMonth();
				month.setMonthId(rs.getInt(COL_MONTH_ID));
				month.setMonth(rs.getDate(COL_MONTH));
				
//				System.out.println(month.toString());
				monthList.add(month);
			}
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return monthList;
	}
	
	
//	Add
	public static void addKitchenFeeRecipt(KitchenFeeRecipt kitchenFeeRecipt) throws SQLException {
		String sqlAddKitchenFeeRecipt = "INSERT INTO " + "`" + KITCHEN_FEE_RECIPT_TABLE + "` "
				+ "(`member_id`, `month_id`, `date`) "
				+ "VALUES (?, ?, ?)";

		Connection conn = null;
		PreparedStatement stmtFee = null;
		try {
			conn = DBConnection.getConnection();
			
//			add Kitchen Fee Recipt
			stmtFee = conn.prepareStatement(sqlAddKitchenFeeRecipt);
			stmtFee.setInt(1, kitchenFeeRecipt.getMember().getId());
			stmtFee.setInt(2, kitchenFeeRecipt.getMonth().getMonthId());
			stmtFee.setDate(3, new java.sql.Date(kitchenFeeRecipt.getDate().getTime()));
			if (kitchenFeeRecipt.getDate() != null) {
				stmtFee.setDate(3, new java.sql.Date(kitchenFeeRecipt.getDate().getTime()));
			} else {
				stmtFee.setDate(3, null);
			}		
			stmtFee.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmtFee != null) stmtFee.close();
			if (conn != null) conn.close();
		}
	}
	
	public static void addKitchenFeeRecipt(List<KitchenFeeRecipt> kitchenFeeReciptList) throws SQLException {
		String sqlAddKitchenFeeRecipt = "INSERT INTO " + "`" + KITCHEN_FEE_RECIPT_TABLE + "` "
				+ "(`member_id`, `month_id`, `date`) "
				+ "VALUES (?, ?, ?)";

		Connection conn = null;
		PreparedStatement stmtFee = null;
		try {
			conn = DBConnection.getConnection();
			stmtFee = conn.prepareStatement(sqlAddKitchenFeeRecipt);
			
			for (KitchenFeeRecipt kitchenFeeRecipt : kitchenFeeReciptList) {
//				add Kitchen Fee Recipt
				stmtFee.setInt(1, kitchenFeeRecipt.getMember().getId());
				stmtFee.setInt(2, kitchenFeeRecipt.getMonth().getMonthId());
				stmtFee.setDate(3, new java.sql.Date(kitchenFeeRecipt.getDate().getTime()));
				if (kitchenFeeRecipt.getDate() != null) {
					stmtFee.setDate(3, new java.sql.Date(kitchenFeeRecipt.getDate().getTime()));
				} else {
					stmtFee.setDate(3, null);
				}		
				stmtFee.executeUpdate();
//				System.out.print(res);
//				System.out.println(kitchenFeeRecipt.toString());
			}
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmtFee != null) stmtFee.close();
			if (conn != null) conn.close();
		}
	}
	
	public static void addMonth(java.util.Date date) throws SQLException {
		String sqlGetLatestMonth = "SELECT MAX(month) FROM " + KITCHEN_FEE_MONTH_TABLE ;
		String sqlAddMonth = "INSERT INTO " + KITCHEN_FEE_MONTH_TABLE + " (`month`) VALUES (?)";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtAddDate = null;
		ResultSet rs = null;
		java.util.Date maxDate = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlGetLatestMonth, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmtAddDate = conn.prepareStatement(sqlAddMonth);
			rs = stmt.executeQuery();
			rs.beforeFirst();
			if (rs.next()) {
				maxDate = rs.getDate("MAX(month)");
			}
			if (maxDate == null) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.MONTH, Calendar.JANUARY);
				cal.set(Calendar.YEAR, 2019);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				maxDate = cal.getTime();  
			}
//			System.out.println(maxDate.toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(maxDate);
			while (date.after(maxDate)) {
				cal.add(Calendar.MONTH, 1);
				stmtAddDate.setDate(1, new java.sql.Date(cal.getTimeInMillis()));
				stmtAddDate.executeUpdate();
				maxDate = cal.getTime();
			}
		}catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmtAddDate != null) stmtAddDate.close();
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
//	Delete
	public static void deleteKitchenFeeRecipt(List<KitchenFeeRecipt> kitchenFeeReciptList) throws SQLException {
		String sqlDeleteRecipt = "DELETE FROM " + "`" + KITCHEN_FEE_RECIPT_TABLE + "` "
				+ "WHERE (`member_id` = ? AND `month_id` = ?)";
		
		Connection conn = null;
		PreparedStatement stmtFee = null;
		try {
			conn = DBConnection.getConnection();
			stmtFee = conn.prepareStatement(sqlDeleteRecipt);
			
			for (KitchenFeeRecipt kitchenFeeRecipt : kitchenFeeReciptList) {
//				add Kitchen Fee Recipt
				stmtFee.setInt(1, kitchenFeeRecipt.getMember().getId());
				stmtFee.setInt(2, kitchenFeeRecipt.getMonth().getMonthId());
				stmtFee.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmtFee != null) stmtFee.close();
			if (conn != null) conn.close();
		}
	}
	
	
	public static void main(String[] args) throws ParseException, SQLException {

		//Get data from Excel and add to the database
		/*
		//		Data
		String[][] kitchenFeeList = ExcelDAO.getKitchenFeeList();
		Date date = new SimpleDateFormat("yyyy MMMM").parse(kitchenFeeList[0][kitchenFeeList[0].length-1]);  
//		System.out.println(date.toString());
		addMonth(date);
		List<KitchenFeeMonth> monthList = getKitchenFeeMonth();
		HashMap<java.util.Date, KitchenFeeMonth> monthMap = new HashMap<>();
		for (KitchenFeeMonth month : monthList) {
			monthMap.put(month.getMonth(), month);
		}
		List<KitchenFeeRecipt> kitchenFeeReciptList = new ArrayList<>();
		for (int i = 1; i < kitchenFeeList.length; i++) {
			Integer roomNo = (int) Double.parseDouble(kitchenFeeList[i][0]);
			KitchenMember member = KitchenMemberDAO.getKitchenMemberByRoom(roomNo);
			for (int j = 1; j < kitchenFeeList[0].length; j++) {
				if (kitchenFeeList[i][j] != null && kitchenFeeList[i][j].equalsIgnoreCase("X")) {
					Date date1 = new SimpleDateFormat("yyyy MMMM").parse(kitchenFeeList[0][j]);
					KitchenFeeRecipt recipt = new KitchenFeeRecipt(member, monthMap.get(date1), date1);
//					System.out.println(recipt.toString());
					kitchenFeeReciptList.add(recipt);
				}
			}
		}		
		addKitchenFeeRecipt(kitchenFeeReciptList);
		*/
		
		getKitchenFeeReciptList();
	}

}
