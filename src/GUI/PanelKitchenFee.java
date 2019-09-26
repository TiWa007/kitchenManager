package GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DAO.KitchenFeeDAO;
import DAO.KitchenMemberDAO;
import model.KitchenFeeMonth;
import model.KitchenFeeRecipt;
import model.KitchenMember;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

public class PanelKitchenFee extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelKitchenFee;
	private List<KitchenFeeRecipt> kitchenFeeList;
	private List<KitchenFeeMonth> monthList;
	private List<KitchenMember> memberList;
	private JTable tableKitchenFee;
//	row index HashMap
	private HashMap<Integer, Integer> roomNoMap;
	private Object[][] data;
//	column index HashMap
	private HashMap<java.util.Date, Integer> monthMap;
	private String[] columns;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelKitchenFee window = new PanelKitchenFee();
					JFrame frame = new JFrame();					
					frame.setBounds(100, 100, 719, 573);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(new CardLayout(0, 0));
					frame.getContentPane().add(window.getKitchenFeePanel());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public PanelKitchenFee() throws SQLException {
		
		panelKitchenFee = new JPanel();
		panelKitchenFee.setBounds(0, 0, 687, 496);
		panelKitchenFee.setLayout(null);
		
		JLabel lblKitchenFeeTable = new JLabel("Kitchen Fee:");
		lblKitchenFeeTable.setBounds(100, 40, 300, 50);
		panelKitchenFee.add(lblKitchenFeeTable);
		lblKitchenFeeTable.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 100, 450, 280);
		panelKitchenFee.add(scrollPane);
		
//		Data	
		kitchenFeeList = KitchenFeeDAO.getKitchenFeeReciptList();
		monthList = KitchenFeeDAO.getKitchenFeeMonth();
		memberList = KitchenMemberDAO.getKitchenMemberList();
		
		columns = getColumnData();
		data = getTableContentData();
		
		tableKitchenFee = new JTable();
		tableKitchenFee.setModel(new DefaultTableModel(data, columns
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				if (columnIndex > 1) return columnTypes[2];
				return columnTypes[columnIndex];
			}
			public boolean isCellEditable(int row, int column) {
				if (column > 1) return true;
				return false;
			}
			
		});
		tableKitchenFee.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		tableKitchenFee.setRowHeight(20);
		tableKitchenFee.setFont(new Font("Tahoma", Font.PLAIN, 15));
		TableColumnModel columnModel = tableKitchenFee.getColumnModel();
		for (int i = 0; i < tableKitchenFee.getColumnCount(); i++) {
			if (i < 1) 
				columnModel.getColumn(i).setPreferredWidth(50);
			else columnModel.getColumn(i).setPreferredWidth(100);
		}
		scrollPane.setViewportView(tableKitchenFee);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<KitchenFeeRecipt> addMonth = new ArrayList<>();
				List<KitchenFeeRecipt> deleteMonth = new ArrayList<>();
				HashMap<Integer, KitchenMember> memberMap = new HashMap<>();
				for (KitchenMember member : memberList) {
					memberMap.put(member.getRoom(), member);
				}
				HashMap<java.util.Date, KitchenFeeMonth> getMonthMap = new HashMap<>();
				for (KitchenFeeMonth month : monthList) {
					getMonthMap.put(month.getMonth(), month);
				}
				for (int i = 0; i < tableKitchenFee.getRowCount(); i++) {
					KitchenMember member = memberMap.get((int) data[i][0]);
					for (int col = 2; col < tableKitchenFee.getColumnCount(); col++) {
						Boolean checked = Boolean.valueOf((boolean) tableKitchenFee.getValueAt(i, col));
						Boolean isChecked = (Boolean) data[i][col];
						if (checked != isChecked) {
							java.util.Date date = new java.util.Date();
							try {
								date = new SimpleDateFormat("yyyy MMMM").parse(columns[col]);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							KitchenFeeMonth month = getMonthMap.get(date);
							KitchenFeeRecipt recipt = new KitchenFeeRecipt(member, month, new java.util.Date());
							if (checked) {
//								System.out.println(" Add " + recipt.toString());
								addMonth.add(recipt);
							} else {
//								System.out.println(" Delete " + recipt.toString());
								deleteMonth.add(recipt);
							}
						}
					}					
				}
				try {
					if (!addMonth.isEmpty()) {
						KitchenFeeDAO.addKitchenFeeRecipt(addMonth);
					}
					if (!deleteMonth.isEmpty()) {
						KitchenFeeDAO.deleteKitchenFeeRecipt(deleteMonth);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		btnSave.setBounds(100, 400, 100, 30);
		panelKitchenFee.add(btnSave);
		
		JButton btnChangeDateRange = new JButton("Change End Date");
		btnChangeDateRange.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnChangeDateRange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDateChooser dateChooser = new JDateChooser();
				dateChooser.setMinSelectableDate(monthList.get(monthList.size()-1).getMonth());
				int result = JOptionPane.showConfirmDialog(panelKitchenFee, dateChooser,
						"Please select new end date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
//					System.out.println(dateChooser.getDate().toString());
					java.util.Date newEndDate = dateChooser.getDate();
					try {
						KitchenFeeDAO.addMonth(newEndDate);
						monthList = KitchenFeeDAO.getKitchenFeeMonth();
						columns = getColumnData();
						data = getTableContentData();
						DefaultTableModel dm = (DefaultTableModel) tableKitchenFee.getModel();
						dm.setDataVector(data, columns);
						dm.fireTableDataChanged();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				
			}
		});
		btnChangeDateRange.setBounds(250, 400, 160, 30);
		panelKitchenFee.add(btnChangeDateRange);
		
	}
	
	public JPanel getKitchenFeePanel() {
		return panelKitchenFee;
	}
	
	private Object[][] getTableContentData() throws SQLException {
		Object[][] data = new Object[memberList.size()][monthList.size()+2];
//		row index HashMap
		roomNoMap = new HashMap<>();
		for (int i = 0; i < memberList.size(); i++) {
			data[i][0] = memberList.get(i).getRoom(); 
			data[i][1] = memberList.get(i).getName();
			roomNoMap.put(memberList.get(i).getRoom(), i);
			for (int j = 2; j <monthList.size()+2; j++) {
					data[i][j] = Boolean.FALSE;
			}
		}
		
		for (KitchenFeeRecipt recipt : kitchenFeeList) {
			int row = roomNoMap.get(recipt.getMember().getRoom());
			int col = monthMap.get(recipt.getMonth().getMonth());
			data[row][col] = Boolean.TRUE;
		}	
		return data;
	}
	
	private String[] getColumnData() throws SQLException {
//		Kitchen Fee Table data
		String[] columns = new String[monthList.size()+2];
		columns[0] = "Room";
		columns[1] = "Name";
//		column index HashMap
		monthMap = new HashMap<>();
		for (int i = 2; i < columns.length; i++) {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy MMMM");
			
			columns[i] = ft.format(monthList.get(i-2).getMonth());
			monthMap.put(monthList.get(i-2).getMonth(), i);
		}
		
		return columns;
	}
}
