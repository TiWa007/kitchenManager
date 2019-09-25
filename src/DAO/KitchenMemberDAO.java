package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.KitchenMember;

public class KitchenMemberDAO {
	
	private static final String MEMBER_TABLE = "member";
	private static final String COL_NAME = "name";
	private static final String COL_ROOM = "room_number";
	private static final String COL_ID = "member_id";
	private static final String COL_EMAIL = "email";
	
//	Kitchen Member
	public static KitchenMember getKitchenMember(Integer memberId) throws SQLException {
		String sqlGetMember = "SELECT * FROM " + "`" + MEMBER_TABLE + "` " + "WHERE (" + COL_ID + " = ?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		KitchenMember member = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlGetMember, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, memberId);
			rs = stmt.executeQuery();
			List<KitchenMember> memberList = getKitchenMemberFromResultSet(rs);
			if (!memberList.isEmpty())
				member = memberList.get(0);
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return member;
	}
	
	public static KitchenMember getKitchenMemberByRoom(Integer roomNo) throws SQLException {
		String sqlGetMember = "SELECT * FROM " + "`" + MEMBER_TABLE + "` " + "WHERE (" + COL_ROOM +" = ?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		KitchenMember member = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlGetMember, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, roomNo);
			rs = stmt.executeQuery();
			List<KitchenMember> memberList = getKitchenMemberFromResultSet(rs);
			if (!memberList.isEmpty())
				member = memberList.get(0);
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return member;
	}
//	Get Member List
	public static List<KitchenMember> getKitchenMemberList() throws SQLException {
		String sqlGetMember = "SELECT * FROM " + "`" + MEMBER_TABLE + "`" + " ORDER BY " + COL_ROOM;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<KitchenMember> memberList = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlGetMember, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery();
			memberList = getKitchenMemberFromResultSet(rs);
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return memberList;
	}
	
	private static List<KitchenMember> getKitchenMemberFromResultSet(ResultSet rs) throws SQLException {
		List<KitchenMember> memberList = new ArrayList<>();
		rs.beforeFirst();
		while (rs.next()) {		
			Integer id = rs.getInt(COL_ID);
			Integer room = rs.getInt(COL_ROOM);
			String name = rs.getString(COL_NAME);
			String email = rs.getString(COL_EMAIL);
			KitchenMember member = new KitchenMember(id, name, email, room);
//			System.out.println(member.toString());
			memberList.add(member);
		}
		return memberList;
	}
	
//	Update
	public static void updateKitchenMember(KitchenMember member) throws SQLException {
		String sqlUpdateMember = "UPDATE " + "`" + MEMBER_TABLE + "` "
				+ "SET " + COL_ROOM + " = ?, " + COL_NAME + " = ?, " + COL_EMAIL + " = ? "
				+ "WHERE (" + COL_ID + " = ?)";

		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlUpdateMember);
			stmt.setInt(1, member.getRoom());
			stmt.setString(2, member.getName());
			stmt.setString(3, member.getEmail());
			stmt.setInt(4, member.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
//	Post
	public static void addKitchenMember(KitchenMember member) throws SQLException {
		String sqlUpdateMember = "INSERT INTO " + "`" + MEMBER_TABLE + "` "
				+ "(" + COL_ROOM + ", " + COL_NAME + ", " + COL_EMAIL + ") "
				+ "VALUES (?, ?, ?)";

		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlUpdateMember);
			stmt.setInt(1, member.getRoom());
			stmt.setString(2, member.getName());
			stmt.setString(3, member.getEmail());
			stmt.executeUpdate();
			

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
	public static void addKitchenMember(List<KitchenMember> memberList) throws SQLException {
		String sqlUpdateMember = "INSERT INTO " + "`" + MEMBER_TABLE + "` "
				+ "(" + COL_ROOM + ", " + COL_NAME + ", " + COL_EMAIL + ") "
				+ "VALUES (?, ?, ?)";

		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlUpdateMember);
			for (KitchenMember member : memberList) {
				stmt.setInt(1, member.getRoom());
				stmt.setString(2, member.getName());
				stmt.setString(3, member.getEmail());
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
	public static void deleteKitchenMember(KitchenMember member) throws SQLException {	
		String sqlUpdateMember = "DELETE FROM " + "`" + MEMBER_TABLE + "` "
				+ "WHERE (" + COL_ID + " = ?)";

		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlUpdateMember);
			stmt.setInt(1, member.getId());
			stmt.executeUpdate();
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
