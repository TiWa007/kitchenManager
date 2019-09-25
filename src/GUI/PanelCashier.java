package GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DAO.CashierDAO;
import DAO.KitchenMemberDAO;
import model.Cashier;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class PanelCashier extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cashier cashier;
	private JPanel panelCashier;
//	private JTextField textField_Room;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelCashier window = new PanelCashier();
					JFrame frame = new JFrame();					
					frame.setBounds(100, 100, 700, 600);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(new CardLayout(0, 0));
					frame.getContentPane().add(window.getCashierPanel());
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
	public PanelCashier() throws SQLException {
//      JPanel Cashier
      	cashier = CashierDAO.getCashier();
      	 	
		panelCashier = new JPanel();
		panelCashier.setLayout(null);

		JLabel lblCashierInformation = new JLabel("Cashier Information");
		lblCashierInformation.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblCashierInformation.setBounds(100, 30, 300, 50);
		panelCashier.add(lblCashierInformation);
		
		JLabel lblRoom = new JLabel("Room:");
		lblRoom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRoom.setBounds(100, 100, 200, 30);
		panelCashier.add(lblRoom);
		
		JTextField textField_Room = new JTextField();
		textField_Room.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_Room.setText(cashier.getMember().getRoom().toString());
		textField_Room.setBounds(300, 100, 200, 25);
		panelCashier.add(textField_Room);
		textField_Room.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(100, 130, 200, 30);
		panelCashier.add(lblEmail);

		JTextField txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtEmail.setText(cashier.getEmail());
		txtEmail.setBounds(300, 130, 200, 25);
		panelCashier.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(100, 160, 200, 30);
		panelCashier.add(lblPassword);

		JTextField txtPw = new JTextField();
		txtPw.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPw.setText(cashier.getPassword());
		txtPw.setBounds(300, 160, 200, 25);
		panelCashier.add(txtPw);
		txtPw.setColumns(10);

		JLabel lblEmailSmtpHost = new JLabel("Email SMTP Host");
		lblEmailSmtpHost.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmailSmtpHost.setBounds(100, 190, 200, 30);
		panelCashier.add(lblEmailSmtpHost);

		JTextField txtSmtp = new JTextField();
		txtSmtp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSmtp.setText(cashier.getEmailSMTPHost());
		txtSmtp.setBounds(300, 190, 200, 25);
		panelCashier.add(txtSmtp);
		txtSmtp.setColumns(10);

		JLabel lblMobilePay = new JLabel("Mobile Pay:");
		lblMobilePay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMobilePay.setBounds(100, 220, 200, 30);
		panelCashier.add(lblMobilePay);

		JTextField txtMobile = new JTextField();
		txtMobile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtMobile.setText(cashier.getMobilePay().toString());
		txtMobile.setBounds(300, 220, 200, 25);
		panelCashier.add(txtMobile);
		txtMobile.setColumns(10);

		JLabel lblBankAccount = new JLabel("Bank Reg/Account No:");
		lblBankAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBankAccount.setBounds(100, 250, 200, 30);
		panelCashier.add(lblBankAccount);

		JTextField txtReg = new JTextField();
		txtReg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtReg.setText(cashier.getBankReg());
		txtReg.setBounds(300, 250, 50, 25);
		panelCashier.add(txtReg);
		txtReg.setColumns(10);

		JTextField txtAccount = new JTextField();
		txtAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAccount.setText(cashier.getBankAccount());
		txtAccount.setBounds(360, 250, 140, 25);
		panelCashier.add(txtAccount);
		txtAccount.setColumns(10);

		JLabel lblMonthFee = new JLabel("Month Fee:");
		lblMonthFee.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMonthFee.setBounds(100, 280, 200, 30);
		panelCashier.add(lblMonthFee);

		JTextField txtFee = new JTextField();
		txtFee.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtFee.setText(cashier.getMonthFee().toString());
		txtFee.setBounds(300, 280, 140, 25);
		panelCashier.add(txtFee);
		txtFee.setColumns(10);

		JTextField txtDkk = new JTextField();
		txtDkk.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtDkk.setEditable(false);
		txtDkk.setText("DKK");
		txtDkk.setBounds(450, 280, 50, 25);
		panelCashier.add(txtDkk);
		txtDkk.setColumns(10);

		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isChanged = false;
				Integer roomNo = Integer.parseInt(textField_Room.getText());
//				System.out.println(roomNo);
				if (cashier.getMember().getRoom() != roomNo) {
					try {
						cashier.setMember(KitchenMemberDAO.getKitchenMemberByRoom(roomNo));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					isChanged = true;
				}
				if (!cashier.getEmail().equals(txtEmail.getText())) {
					cashier.setEmail(txtEmail.getText());
					isChanged = true;
				}
				if (!cashier.getEmailSMTPHost().equals(txtSmtp.getText())) {
					cashier.setEmailSMTPHost(txtSmtp.getText());
					isChanged = true;
				}
				if (!cashier.getPassword().equals(txtPw.getText())) {
					cashier.setPassword(txtPw.getText());
					isChanged = true;
				}
				if (!cashier.getMobilePay().equals(txtMobile.getText())) {
					cashier.setMobilePay(txtMobile.getText());
					isChanged = true;
				}
				if (!cashier.getBankAccount().equals(txtAccount.getText())) {
					cashier.setBankAccount(txtAccount.getText());
					isChanged = true;
				}
				if (!cashier.getBankReg().equals(txtReg.getText())) {
					cashier.setBankReg(txtReg.getText());
					isChanged = true;
				}
				if (cashier.getMonthFee() != Double.parseDouble(txtFee.getText())) {
					cashier.setMonthFee(Double.parseDouble(txtFee.getText()));
					isChanged = true;
				}
				if (isChanged) {
//					System.out.println(cashier.toString());
					try {
						CashierDAO.updateCashier(cashier);
						JOptionPane.showMessageDialog(panelCashier, "Saved");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}	
			}
		});
		btnSaveChanges.setBounds(150, 350, 160, 30);
		panelCashier.add(btnSaveChanges);
	}
	
	public JPanel getCashierPanel() {
		return panelCashier;
	}
}
