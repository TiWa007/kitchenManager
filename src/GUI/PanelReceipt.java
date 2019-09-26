package GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.toedter.calendar.JDateChooser;

import model.KitchenMember;
import model.Receipt;
import DAO.KitchenMemberDAO;
import DAO.ReciptDAO;

import java.sql.SQLException;

public class PanelReceipt extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] columns_Account = { "Date", "Room", "Name", "Amount", "Comments" };
	private JPanel panelRecipt;
	private JTable table_Recipt;
	private JTextField txtBalance;
	private List<Receipt> reciptList;
	private List<KitchenMember> memberList;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelReceipt window = new PanelReceipt();
					JFrame frame = new JFrame();					
					frame.setBounds(100, 100, 700, 600);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(new CardLayout(0, 0));
					frame.getContentPane().add(window.getReciptPanel());
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
	public PanelReceipt() throws SQLException {
		
		reciptList = ReciptDAO.getReciptList();
		memberList = KitchenMemberDAO.getKitchenMemberList();
		
		panelRecipt = new JPanel();
		panelRecipt.setLayout(null);
		
		JScrollPane scrollPane_Account = new JScrollPane();
		scrollPane_Account.setBounds(100, 90, 500, 280);
		panelRecipt.add(scrollPane_Account);
		
		DefaultTableModel tm = new DefaultTableModel(getAccountTable(), columns_Account);
		table_Recipt = new JTable(tm);
		table_Recipt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table_Recipt.setRowHeight(20);
		
		scrollPane_Account.setViewportView(table_Recipt);
		
		JLabel lblAccountDetail = new JLabel("Receipt:");
		lblAccountDetail.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblAccountDetail.setBounds(100, 30, 300, 50);
		panelRecipt.add(lblAccountDetail);
		
		JLabel lblAccountBalance = new JLabel("Account Balance:");
		lblAccountBalance.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAccountBalance.setBounds(100, 392, 150, 30);
		panelRecipt.add(lblAccountBalance);
		
		txtBalance = new JTextField();
		txtBalance.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtBalance.setEditable(false);
		Double balance = ReciptDAO.getAccountBalance();
		txtBalance.setText(balance.toString());
		txtBalance.setBounds(250, 392, 86, 30);
		panelRecipt.add(txtBalance);
		txtBalance.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
//				Daily payment
				
				JPanel reciptPanel = new JPanel();
				
				reciptPanel.setLayout(new BoxLayout(reciptPanel, BoxLayout.Y_AXIS));
				
				JLabel lblDate = new JLabel("Date:");
				lblDate.setBounds(65, 35, 46, 14);
				reciptPanel.add(lblDate);
				
				JDateChooser dateChooser = new JDateChooser(new java.util.Date());
				dateChooser.setBounds(144, 29, 91, 20);
				reciptPanel.add(dateChooser);
				
				JLabel lblRoom = new JLabel("Room:");
				lblRoom.setBounds(65, 76, 46, 14);
				reciptPanel.add(lblRoom);
				
				JComboBox<Integer> comboBox = new JComboBox<>();
				Integer[] roomList = new Integer[memberList.size()];
		  		for (int i = 0; i< memberList.size(); i++) {
		  			roomList[i] = memberList.get(i).getRoom();
		  		}
		  		comboBox.setModel(new DefaultComboBoxModel<Integer>(roomList));
				comboBox.setMaximumRowCount(12);
				comboBox.setBounds(144, 73, 86, 20);
				reciptPanel.add(comboBox);
				
				JLabel lblName = new JLabel("Name:");
				lblName.setBounds(65, 120, 46, 14);
				reciptPanel.add(lblName);
				
				JTextField txtName = new JTextField();
				txtName.setText("name");
				txtName.setEditable(false);
				txtName.setBounds(144, 117, 86, 20);
				reciptPanel.add(txtName);
				txtName.setColumns(10);
				
				JComboBox<String> comboBox_1 = new JComboBox<>();
				String[] signList = {"-", "+"};
				comboBox_1.setModel(new DefaultComboBoxModel<String>(signList));
				comboBox.setSelectedIndex(0);
				comboBox_1.setBounds(144, 148, 38, 20);
				reciptPanel.add(comboBox_1);
				
				JLabel lblAmount = new JLabel("Amount");
				lblAmount.setBounds(65, 151, 46, 14);
				reciptPanel.add(lblAmount);
				
				JTextField textField_amount = new JTextField();
				textField_amount.setBounds(192, 148, 68, 20);
				reciptPanel.add(textField_amount);
				textField_amount.setColumns(10);	
				
				JTextField txtDkk = new JTextField();
				txtDkk.setEditable(false);
				txtDkk.setText("DKK");
				txtDkk.setBounds(270, 148, 38, 20);
				reciptPanel.add(txtDkk);
				txtDkk.setColumns(10);
				
				JLabel lblComment = new JLabel("Comment:");
				lblComment.setBounds(65, 191, 69, 14);
				reciptPanel.add(lblComment);
				
				JTextField textField_comment = new JTextField("");
				textField_comment.setBounds(144, 188, 151, 20);
				reciptPanel.add(textField_comment);
				textField_comment.setColumns(10);
				
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Integer roomNo = Integer.parseInt(comboBox.getSelectedItem().toString());
						KitchenMember member = getMember(roomNo);
						txtName.setText(member.getName());
					}
				});
				
				int result = JOptionPane.showConfirmDialog(panelRecipt, reciptPanel,
						"Recipt Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					Receipt recipt = new Receipt();
					double amount = Double.parseDouble(textField_amount.getText());
					if (comboBox_1.getSelectedItem().equals("-")) {
						amount = -amount;
					}
					recipt.setAmount(amount);
					recipt.setComment(textField_comment.getText());
					recipt.setDate(dateChooser.getDate());
					recipt.setMember(getMember(Integer.parseInt(comboBox.getSelectedItem().toString())));	
					try {
						ReciptDAO.addRecipt(recipt);
						updateAccountPanel();
						updateAccountBalance();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
				}
			}
		});
		btnAdd.setBounds(100, 433, 100, 30);
		panelRecipt.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy MMMM");
				String[] choices = new String[reciptList.size()];
				HashMap<String, Integer> reciptMap = new HashMap<>();
				for (int i = 0; i < reciptList.size(); i++) {
					Receipt recipt = reciptList.get(i);
					choices[i] = format.format(recipt.getDate()) + ", " + recipt.getMember().getRoom() + " " + recipt.getMember().getName() 
							+ ", " + recipt.getAmount() + ", " + recipt.getComment();
					reciptMap.put(choices[i], i);
				}
				
			    String input = (String) JOptionPane.showInputDialog(panelRecipt, "Select Recipt",
			        "Delete Recipt", JOptionPane.QUESTION_MESSAGE, null, // Use default icon		                                                                         
			        choices, // Array of choices
			        choices[1]); // Initial choice

			    if (input != null) {
			    	int selectedChoices = reciptMap.get(input);
			    	List<Receipt> deleteList = new ArrayList<>();
			    	deleteList.add(reciptList.get(selectedChoices));
				    try {
						ReciptDAO.deleteRecipt(deleteList);
						updateAccountPanel();
						updateAccountBalance();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
			    }   
			}
		});
		btnDelete.setBounds(320, 433, 100, 30);
		panelRecipt.add(btnDelete);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] choices = new String[reciptList.size()];
				HashMap<String, Integer> reciptMap = new HashMap<>();
				for (int i = 0; i < reciptList.size(); i++) {
					Receipt recipt = reciptList.get(i);
					choices[i] = recipt.getDate().toString() + ", " + recipt.getMember().getRoom() + " " + recipt.getMember().getName() 
							+ ", " + recipt.getAmount() + ", " + recipt.getComment();
					reciptMap.put(choices[i], i);
				}
				
			    String input = (String) JOptionPane.showInputDialog(panelRecipt, "Select Recipt",
			        "Edit Recipt", JOptionPane.QUESTION_MESSAGE, null, // Use default icon		                                                                         
			        choices, // Array of choices
			        choices[1]); // Initial choice

			    if (input != null) {
			    	int selectedChoices = reciptMap.get(input);
			    	Receipt selectedRecipt = reciptList.get(selectedChoices);
			    	
			    	JPanel reciptPanel = new JPanel();
					
					reciptPanel.setLayout(new BoxLayout(reciptPanel, BoxLayout.Y_AXIS));
					
					JLabel lblDate = new JLabel("Date:");
					lblDate.setBounds(65, 35, 46, 14);
					reciptPanel.add(lblDate);
					
					JDateChooser dateChooser = new JDateChooser(selectedRecipt.getDate());
					dateChooser.setBounds(144, 29, 91, 20);
					reciptPanel.add(dateChooser);
					
					JLabel lblRoom = new JLabel("Room:");
					lblRoom.setBounds(65, 76, 46, 14);
					reciptPanel.add(lblRoom);
					
					JComboBox<Integer> comboBox = new JComboBox<>();
					Integer[] roomList = new Integer[memberList.size()];
			  		for (int i = 0; i< memberList.size(); i++) {
			  			roomList[i] = memberList.get(i).getRoom();
			  		}
			  		comboBox.setModel(new DefaultComboBoxModel<Integer>(roomList));
//					comboBox.setMaximumRowCount(12);
					comboBox.setSelectedItem(selectedRecipt.getMember().getRoom());
					comboBox.setBounds(144, 73, 86, 20);
					reciptPanel.add(comboBox);
					
					JLabel lblName = new JLabel("Name:");
					lblName.setBounds(65, 120, 46, 14);
					reciptPanel.add(lblName);
					
					JTextField txtName = new JTextField();
					txtName.setText(selectedRecipt.getMember().getName());
					txtName.setEditable(false);
					txtName.setBounds(144, 117, 86, 20);
					reciptPanel.add(txtName);
					txtName.setColumns(10);
					
					JComboBox<String> comboBox_1 = new JComboBox<>();
					String[] signList = {"-", "+"};
					comboBox_1.setModel(new DefaultComboBoxModel<String>(signList));
					if (selectedRecipt.getAmount() < 0) {
						comboBox_1.setSelectedItem("-");
					} else {
						comboBox_1.setSelectedItem("+");
					}
					comboBox_1.setBounds(144, 148, 38, 20);
					reciptPanel.add(comboBox_1);
					
					JLabel lblAmount = new JLabel("Amount");
					lblAmount.setBounds(65, 151, 46, 14);
					reciptPanel.add(lblAmount);
					
					JTextField textField_amount = new JTextField(Double.toString(Math.abs(selectedRecipt.getAmount())));
					textField_amount.setBounds(192, 148, 68, 20);
					reciptPanel.add(textField_amount);
					textField_amount.setColumns(10);	
					
					JTextField txtDkk = new JTextField();
					txtDkk.setEditable(false);
					txtDkk.setText("DKK");
					txtDkk.setBounds(270, 148, 38, 20);
					reciptPanel.add(txtDkk);
					txtDkk.setColumns(10);
					
					JLabel lblComment = new JLabel("Comment:");
					lblComment.setBounds(65, 191, 69, 14);
					reciptPanel.add(lblComment);
					
					JTextField textField_comment = new JTextField(selectedRecipt.getComment());
					textField_comment.setBounds(144, 188, 151, 20);
					reciptPanel.add(textField_comment);
					textField_comment.setColumns(10);
					
					comboBox.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Integer roomNo = Integer.parseInt(comboBox.getSelectedItem().toString());
							KitchenMember member = getMember(roomNo);
							txtName.setText(member.getName());
						}
					});
					
					int result = JOptionPane.showConfirmDialog(panelRecipt, reciptPanel,
							"Recipt Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						boolean isChanged = false;
						double amount = Double.parseDouble(textField_amount.getText());
						if (comboBox_1.getSelectedItem().equals("-")) {
							amount = -amount;
						}
						if (amount != selectedRecipt.getAmount()) {
							selectedRecipt.setAmount(amount);
							isChanged = true;
						}
						if (!textField_comment.getText().equals(selectedRecipt.getComment())) {
							selectedRecipt.setComment(textField_comment.getText());
							isChanged = true;
						}
						if (dateChooser.getDate().compareTo(selectedRecipt.getDate()) != 0) {
							selectedRecipt.setDate(dateChooser.getDate());
							isChanged = true;
						}
						Integer selectedRoom = Integer.parseInt(comboBox.getSelectedItem().toString());
						if (selectedRecipt.getMember().getRoom() != selectedRoom) {
							selectedRecipt.setMember(getMember(selectedRoom));
							isChanged = true;
						}	
						
						if (isChanged) {
							List<Receipt> editList = new ArrayList<>();
					    	editList.add(selectedRecipt);
						    try {
								ReciptDAO.updateRecipt(editList);
								updateAccountPanel();
								updateAccountBalance();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}								
						}
						
					}	
			    } 
			}
		
		});
		btnEdit.setBounds(210, 433, 100, 30);
		panelRecipt.add(btnEdit);
		
		JLabel lblDkk = new JLabel("DKK");
		lblDkk.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDkk.setBounds(350, 392, 50, 30);
		panelRecipt.add(lblDkk);
	}
	
	private KitchenMember getMember(Integer roomNo) {
		for (KitchenMember member : memberList) {
			if (member.getRoom() == roomNo) {
				return member;
			}
		}
		return null;
	}
	
	private Object[][] getAccountTable() throws SQLException {
		
		reciptList = ReciptDAO.getReciptList();
		int numOfRow = reciptList.size();
		Object[][] data_Account = new Object[numOfRow][5];
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < numOfRow; i++) {
			Receipt recipt = reciptList.get(i);
			KitchenMember mem = recipt.getMember();
			data_Account[i][0] = format.format(recipt.getDate());
			data_Account[i][1] = mem.getRoom();
			data_Account[i][2] = mem.getName();
			data_Account[i][3] = recipt.getAmount();
			data_Account[i][4] = recipt.getComment();
		}
		
		return data_Account;
	}

	private void updateAccountBalance() throws SQLException {
		Double balance = ReciptDAO.getAccountBalance();
		txtBalance.setText(balance.toString());
	}
	
	private void updateAccountPanel() throws SQLException {
		DefaultTableModel dm = (DefaultTableModel) table_Recipt.getModel();
		dm.setDataVector(getAccountTable(), columns_Account);
		dm.fireTableDataChanged();
		txtBalance.setText(Double.toString(ReciptDAO.getAccountBalance()));
	}

	public JPanel getReciptPanel() {
		return panelRecipt;
	}
}
