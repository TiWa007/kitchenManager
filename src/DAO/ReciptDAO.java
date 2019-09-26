package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.KitchenMember;
import model.Receipt;

public class ReciptDAO {

//	Table recipt
	private static final String RECIPT_TABLE = "recipt";	
	private static final String COL_DATE = "date";
	private static final String COL_AMOUNT = "amount";
	private static final String COL_ID = "recipt_id";
	private static final String COL_MEMBER_ID = "member_id";
	private static final String COL_COMMENT = "comment";
	
//	Get Account balance
	public static double getAccountBalance() throws SQLException {
		String sqlGetBalance = "SELECT sum(" + COL_AMOUNT + ") FROM " + RECIPT_TABLE;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		double balance = 0;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlGetBalance, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery();
			rs.beforeFirst();
			rs.next();
			balance = rs.getDouble(1);
			
			DecimalFormat df = new DecimalFormat("#.##"); 
			
			balance = Double.parseDouble(df.format(balance));
//			System.out.print(balance);
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return balance;
	}
	
//	Get Recipt List
	public static List<Receipt> getReciptList() throws SQLException {
		String sqlGetMember = "SELECT * FROM " + "`" + RECIPT_TABLE + "` " + "ORDER BY " + COL_DATE + " DESC";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Receipt> reciptList = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlGetMember, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery();
			reciptList = getReciptFromResultSet(rs);
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return reciptList;
	}
	
	private static List<Receipt> getReciptFromResultSet(ResultSet rs) throws SQLException {
		List<Receipt> reciptList = new ArrayList<>();
		rs.beforeFirst();
		while (rs.next()) {		
			Receipt recipt = new Receipt();
			recipt.setId(rs.getInt(COL_ID));
			recipt.setDate(rs.getDate(COL_DATE));
			recipt.setAmount(rs.getDouble(COL_AMOUNT));
			recipt.setComment(rs.getString(COL_COMMENT));
			Integer memberId = rs.getInt(COL_MEMBER_ID);
			KitchenMember member = KitchenMemberDAO.getKitchenMember(memberId);
			recipt.setMember(member);
//			System.out.println(recipt.toString());
			reciptList.add(recipt);
		}
		return reciptList;
	}
	
//	Add
	public static void addRecipt(List<Receipt> reciptList) throws SQLException {
		String sqlAddRecipt = "INSERT INTO " + "`" + RECIPT_TABLE + "` "
				+ "(`member_id`, `date`, `amount`, `comment`) "
				+ "VALUES (?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlAddRecipt);
			for (Receipt recipt : reciptList) {
				stmt.setInt(1, recipt.getMember().getId());
				if (recipt.getDate() != null) {
					stmt.setDate(2, new java.sql.Date(recipt.getDate().getTime()));
				} else {
					stmt.setDate(2, null);
				}
				stmt.setDouble(3, recipt.getAmount());
				stmt.setString(4, recipt.getComment());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
	public static void addRecipt(Receipt recipt) throws SQLException {
		String sqlAddRecipt = "INSERT INTO " + "`" + RECIPT_TABLE + "` "
				+ "(`member_id`, `date`, `amount`, `comment`) "
				+ "VALUES (?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlAddRecipt);
			stmt.setInt(1, recipt.getMember().getId());
			if (recipt.getDate() != null) {
				stmt.setDate(2, new java.sql.Date(recipt.getDate().getTime()));
			} else {
				stmt.setDate(2, null);
			}
			stmt.setDouble(3, recipt.getAmount());
			stmt.setString(4, recipt.getComment());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
// Update
	public static void updateRecipt(List<Receipt> reciptList) throws SQLException {
		String sqlUpdateRecipt = "UPDATE " + "`" + RECIPT_TABLE + "` "
				+ "SET `member_id` = ?, `date` = ?, `amount` = ?, `comment` = ? "
				+ "WHERE (" + COL_ID + " = ?)";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlUpdateRecipt);
			for (Receipt recipt : reciptList) {
				stmt.setInt(1, recipt.getMember().getId());
				if (recipt.getDate() != null) {
					stmt.setDate(2, new java.sql.Date(recipt.getDate().getTime()));
				} else {
					stmt.setDate(2, null);
				}
				stmt.setDouble(3, recipt.getAmount());
				stmt.setString(4, recipt.getComment());
				stmt.setInt(5, recipt.getId());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
//	Delete
	public static void deleteRecipt(List<Receipt> reciptList) throws SQLException {
		String sqlDeleteRecipt = "DELETE FROM " + "`" + RECIPT_TABLE + "` "
				+ "WHERE (" + COL_ID + " = ?)";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlDeleteRecipt);
			for (Receipt recipt : reciptList) {
				stmt.setInt(1, recipt.getId());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
	}

}
