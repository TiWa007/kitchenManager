package DAO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Cashier;
import model.KitchenFeeMonth;
import model.KitchenFeeRecipt;
import model.KitchenMember;
import model.Recipt;

public class ExportExcelDAO {
	private static final String MEMBER_SHEET = "KitchenMember";
	private static final String RECIPT_SHEET = "Recipt";
	private static final String CASHIER_SHEET = "Cashier";
	private static final String KITCHEN_FEE_SHEET = "KitchenFee";
//	private static final String FILE_PATH = System.getProperty("user.dir") + "\\Data\\A0_Economic_Example.xlsx";

	public static void writeToExcel(String filePath) {
		FileOutputStream fileOut = null;
		try {
			Workbook workbook = new XSSFWorkbook();
			
			writeKitchenMember(workbook);
			writeCashier(workbook);
			writeKitchenFee(workbook);
			writeRecipt(workbook);
			
			fileOut = new FileOutputStream(filePath);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private static void writeKitchenMember(Workbook workbook) throws SQLException {
	
		Sheet sheet = workbook.createSheet(MEMBER_SHEET);

//			First row, create header
		Row firstRow = sheet.createRow(0);
		String[] header = { "Room", "Name", "Email" };
		for (int i = 0; i < header.length; i++) {
			Cell cell = firstRow.createCell(i);
			cell.setCellValue(header[i]);
		}

//			Kitchen Member rows
		List<KitchenMember> memberList = KitchenMemberDAO.getKitchenMemberList();
		for (int i = 0; i < memberList.size(); i++) {
			Row row = sheet.createRow(i + 1);
//				Room
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(memberList.get(i).getRoom());
//				Name	
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(memberList.get(i).getName());
//				Email
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(memberList.get(i).getEmail());

		}			
	}
	
	private static void writeRecipt(Workbook workbook) throws SQLException {
		Sheet sheet = workbook.createSheet(RECIPT_SHEET);

//		First row, Account balance
		Row firstRow = sheet.createRow(0);
		Cell c0 = firstRow.createCell(0);
		c0.setCellValue("Balance");	
		Cell c1 = firstRow.createCell(1);
		c1.setCellValue(ReciptDAO.getAccountBalance());
		
		
//		Second row, create Header
		Row secRow = sheet.createRow(1);
		String[] header = { "Date", "Amount", "Room", "Name", "Comment" };
		for (int i = 0; i < header.length; i++) {
			Cell cell = secRow .createCell(i);
			cell.setCellValue(header[i]);
		}

//		Kitchen Member rows
		List<Recipt> reciptList = ReciptDAO.getReciptList();
		for (int i = 0; i < reciptList.size(); i++) {
			Row row = sheet.createRow(i + 2);
//			Date
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(reciptList.get(i).getDate().toString());
//			Amount
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(reciptList.get(i).getAmount());
//			Room
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(reciptList.get(i).getMember().getRoom());
//			Name
			Cell cell3 = row.createCell(3);
			cell3.setCellValue(reciptList.get(i).getMember().getName());
//			Room
			Cell cell4 = row.createCell(4);
			cell4.setCellValue(reciptList.get(i).getComment());
		}
	}
	
	private static void writeCashier(Workbook workbook) throws SQLException {
		Sheet sheet = workbook.createSheet(CASHIER_SHEET);

		String[] header = { "Room", "Email", "Password", "EmailSMTPHost", "MobilePay", "Bank Reg", "Bank Account",
				"Month Fee" };

		Cashier cashier = CashierDAO.getCashier();

		for (int i = 0; i < header.length; i++) {
			Row row = sheet.createRow(i);
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(header[i]);
			row.createCell(1);
		}
	
//		Room
		sheet.getRow(0).getCell(1).setCellValue(cashier.getMember().getRoom());
//		Email
		sheet.getRow(1).getCell(1).setCellValue(cashier.getEmail());
//		Password
		sheet.getRow(2).getCell(1).setCellValue(cashier.getPassword());
//		EmailSMTPHost
		sheet.getRow(3).getCell(1).setCellValue(cashier.getEmailSMTPHost());
//		MobilePay
		sheet.getRow(4).getCell(1).setCellValue(cashier.getMobilePay());
//		Bank Reg
		sheet.getRow(5).getCell(1).setCellValue(cashier.getBankReg());
//		Bank Account
		sheet.getRow(6).getCell(1).setCellValue(cashier.getBankAccount());
//		Month Fee
		sheet.getRow(7).getCell(1).setCellValue(cashier.getMonthFee());
	
	}
	
	private static void writeKitchenFee(Workbook workbook) throws SQLException {
		Sheet sheet = workbook.createSheet(KITCHEN_FEE_SHEET);
		List<KitchenFeeMonth> monthList = KitchenFeeDAO.getKitchenFeeMonth();
		List<KitchenFeeRecipt> kitchenFeeList = KitchenFeeDAO.getKitchenFeeReciptList();
		List<KitchenMember> memberList = KitchenMemberDAO.getKitchenMemberList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy MMMM");
		
//		first row
		HashMap<Integer, Integer> monthMap = new HashMap<>(); // key: month; value col No
		Row firstRow = sheet.createRow(0);
		for (int i = 0; i < monthList.size(); i++) {
			Cell cell = firstRow.createCell(i+1);
			cell.setCellValue(sf.format(monthList.get(i).getMonth()));
			monthMap.put(monthList.get(i).getMonthId(), i+1);
		}
//		first column
		HashMap<Integer, Integer> memberMap = new HashMap<>();  // key: Room No; value row No
		firstRow.createCell(0).setCellValue("Room");
		for (int i = 0; i < memberList.size(); i++) {
			Row row = sheet.createRow(i+1);
			Cell cell = row.createCell(0);
			cell.setCellValue(memberList.get(i).getRoom());
			memberMap.put(memberList.get(i).getRoom(), i+1);
		}
		
		for (KitchenFeeRecipt kitchenFee : kitchenFeeList) {
			int row = memberMap.get(kitchenFee.getMember().getRoom());
			int col = monthMap.get(kitchenFee.getMonth().getMonthId());
			sheet.getRow(row).createCell(col).setCellValue("X");
		}
		
	}
	
	public static void main(String[] args) throws SQLException {
//		List<KitchenMember> memberList = KitchenMemberDAO.getKitchenMemberList();
//		writeKitchenMember();
//		writeToExcel(FILE_PATH);
	}

}
