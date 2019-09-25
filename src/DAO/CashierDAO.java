package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Cashier;
import model.KitchenMember;

public class CashierDAO {

	private static final String CASHIER_TABLE = "cashier";

	private static final String COL_ID = "cashier_id";
	private static final String COL_MEMBER_ID = "member_id";
	private static final String COL_EMAIL = "email";
	private static final String COL_PASSWORD = "password";
	private static final String COL_EMAIL_SMTP_HOST = "email_smtp_host";
	private static final String COL_MOBILE_PAY = "mobilepay";
	private static final String COL_BANKREG = "bank_reg";
	private static final String COL_BANKACCOUNT = "bank_account";
	private static final String COL_MONTHFEE = "month_fee";

//	Get
	public static Cashier getCashier() throws SQLException {
		String sqlGetCashier = "SELECT * FROM " + "`" + CASHIER_TABLE + "`";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Cashier cashier = null;
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlGetCashier, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery();
			rs.beforeFirst();
			if (rs.next()) {
				cashier = new Cashier();
				cashier.setId(rs.getInt(COL_ID));
				cashier.setEmail(rs.getString(COL_EMAIL));
				cashier.setPassword(rs.getNString(COL_PASSWORD));
				cashier.setEmailSMTPHost(rs.getString(COL_EMAIL_SMTP_HOST));
				cashier.setMobilePay(rs.getString(COL_MOBILE_PAY));
				cashier.setBankReg(rs.getString(COL_BANKREG));
				cashier.setBankAccount(rs.getString(COL_BANKACCOUNT));
				cashier.setMonthFee(rs.getDouble(COL_MONTHFEE));
				Integer memberId = rs.getInt(COL_MEMBER_ID);
				KitchenMember member = KitchenMemberDAO.getKitchenMember(memberId);
				cashier.setMember(member);
//				System.out.println(cashier.toString());
			}
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return cashier;
	}

//	Update
	public static void updateCashier(Cashier cashier) throws SQLException {
		String sqlUpdateCashier = "UPDATE " + "`" + CASHIER_TABLE + "` " + "SET `member_id` = ?, `email` = ?, "
				+ "`password` = ?, `email_smtp_host` = ?, " + "`mobilepay` = ?, `bank_reg` = ?, "
				+ "`bank_account` = ?, `month_fee` = ? " + "WHERE (`cashier_id` = ?)";

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlUpdateCashier);
			stmt.setInt(1, cashier.getMember().getId());
			stmt.setString(2, cashier.getEmail());
			stmt.setString(3, cashier.getPassword());
			stmt.setString(4, cashier.getEmailSMTPHost());
			stmt.setString(5, cashier.getMobilePay());
			stmt.setString(6, cashier.getBankReg());
			stmt.setString(7, cashier.getBankAccount());
			stmt.setDouble(8, cashier.getMonthFee());
			stmt.setInt(9, cashier.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

//	Add
	
	public static void addCashier(Cashier cashier) throws SQLException {
		String sqlUpdateMember = "INSERT INTO " + "`" + CASHIER_TABLE + "` " 
				+ "(`member_id`, `email`, "
				+ "`password`, `email_smtp_host`, " 
				+ "`mobilepay`, `bank_reg`, " 
				+ "`bank_account`, `month_fee`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sqlUpdateMember);
			stmt.setInt(1, cashier.getMember().getId());
			stmt.setString(2, cashier.getEmail());
			stmt.setString(3, cashier.getPassword());
			stmt.setString(4, cashier.getEmailSMTPHost());
			stmt.setString(5, cashier.getMobilePay());
			stmt.setString(6, cashier.getBankReg());
			stmt.setString(7, cashier.getBankAccount());
			stmt.setDouble(8, cashier.getMonthFee());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
	}

}
