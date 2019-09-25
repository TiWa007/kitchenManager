package GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import DAO.KitchenFeeDAO;
import DAO.KitchenMemberDAO;
import DAO.ReminderEmail;
import model.KitchenMember;
import java.awt.Font;

public class PanelEmailMultiple extends JPanel {

	/**
	 * 
	 */
	private final String SUBJECT = "Kitchen Fee";
	private static final long serialVersionUID = 1L;
	private JPanel panelEmailMultiple;
	private List<KitchenMember> memberList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelEmailMultiple window = new PanelEmailMultiple();
					JFrame frame = new JFrame();
					frame.setBounds(100, 100, 700, 600);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(new CardLayout(0, 0));
					frame.getContentPane().add(window.getEmailMultiplePanel());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the panel.
	 * 
	 * @throws SQLException
	 */
	public PanelEmailMultiple() throws SQLException {
//      Data
		memberList = KitchenMemberDAO.getKitchenMemberList();

		KitchenFeeDAO.addMonth(new Date());
		
//      Multiple email
		panelEmailMultiple = new JPanel();

		panelEmailMultiple.setLayout(null);

		JRadioButton[] rdbtnKitchenMmeberList = new JRadioButton[memberList.size()];
		for (int i = 0; i < memberList.size(); i++) {
			rdbtnKitchenMmeberList[i] = new JRadioButton(
					memberList.get(i).getRoom() + " " + memberList.get(i).getName());
			rdbtnKitchenMmeberList[i].setBounds(100, 90 + (i) * 30, 120, 25);
			rdbtnKitchenMmeberList[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
			panelEmailMultiple.add(rdbtnKitchenMmeberList[i]);
		}

		JLabel lblPleaseSelectMembers = new JLabel("Please select members:");
		lblPleaseSelectMembers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPleaseSelectMembers.setBounds(100, 50, 200, 30);
		panelEmailMultiple.add(lblPleaseSelectMembers);

		JButton btnGenerateEmails = new JButton("Generate emails");
		btnGenerateEmails.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnGenerateEmails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JRadioButton selectedMem : rdbtnKitchenMmeberList) {
					if (selectedMem.isSelected()) {
						String roomNo = selectedMem.getText().split(" ")[0];
						KitchenMember mem = memberList.get(Integer.parseInt(roomNo) - 1);

						JTextField xField = new JTextField();
						JTextField yField = new JTextField();
						JTextArea zField = new JTextArea();

						JPanel emailPanel = new JPanel();
						emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
						emailPanel.add(new JLabel("Email receiver:"));
						xField.setText(mem.getEmail());
						emailPanel.add(xField);
						emailPanel.add(new JLabel("Email subject:"));
						yField.setText(SUBJECT);
						emailPanel.add(yField);
						emailPanel.add(new JLabel("Email content:"));
						try {
							zField.setText(ReminderEmail.generateEmailContent(mem, new Date()));
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						emailPanel.add(zField);

						int result = JOptionPane.showConfirmDialog(panelEmailMultiple, emailPanel,
								"Please Check the Information", JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
							try {
								ReminderEmail.sendIndividualReminderEmail(mem, zField.getText(), yField.getText());
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}

				}
			}
		});
		btnGenerateEmails.setBounds(400, 320, 200, 30);
		panelEmailMultiple.add(btnGenerateEmails);

		JButton btnSelectAll = new JButton("Select All");
		btnSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JRadioButton member : rdbtnKitchenMmeberList) {
					if (!member.isSelected()) {
						member.setSelected(true);
					}
				}
			}
		});
		btnSelectAll.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSelectAll.setBounds(400, 250, 200, 30);
		panelEmailMultiple.add(btnSelectAll);
	}

	public JPanel getEmailMultiplePanel() {
		return panelEmailMultiple;
	}
}
