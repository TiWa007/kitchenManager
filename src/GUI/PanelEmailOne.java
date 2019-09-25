package GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;

import DAO.KitchenFeeDAO;
import DAO.KitchenMemberDAO;
import DAO.ReminderEmail;
import model.KitchenMember;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;

public class PanelEmailOne extends JPanel {

	/**
	 * 
	 */
	private final String SUBJECT = "Kitchen Fee";
	private static final long serialVersionUID = 1L;
	private List<KitchenMember> memberList;
	private KitchenMember selectedMember;
	private JPanel panelEmail;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelEmailOne window = new PanelEmailOne();
					JFrame frame = new JFrame();					
					frame.setBounds(100, 100, 719, 573);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(new CardLayout(0, 0));
					frame.getContentPane().add(window.getEmailOnePanel());
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
	public PanelEmailOne() throws SQLException {
		
//      Data
		SimpleDateFormat sf = new SimpleDateFormat("yyyy MMMM");
		String today = sf.format(new Date());
		memberList = KitchenMemberDAO.getKitchenMemberList();
		
		KitchenFeeDAO.addMonth(new Date());
		
		panelEmail = new JPanel();
        panelEmail.setLayout(null);
        
        JTextField txtEmailReceiver = new JTextField();
        txtEmailReceiver.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtEmailReceiver.setEditable(false);
        txtEmailReceiver.setBounds(250, 400, 200, 30);
        panelEmail.add(txtEmailReceiver);
        txtEmailReceiver.setColumns(10);
        
        JTextField txtSubject = new JTextField();
        txtSubject.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtSubject.setText(SUBJECT);
        txtSubject.setBounds(350, 110, 200, 25);
        panelEmail.add(txtSubject);
        txtSubject.setColumns(10);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 180, 500, 200);
        panelEmail.add(scrollPane);
        
        JTextArea txtrEmailContentInPanel = new JTextArea();
        txtrEmailContentInPanel.setFont(new Font("Monospaced", Font.PLAIN, 15));
        txtrEmailContentInPanel.setText("Email");
        scrollPane.setViewportView(txtrEmailContentInPanel);
        
        JLabel lblPleaseSelectThe = new JLabel("Please select the receiver:");
        lblPleaseSelectThe.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPleaseSelectThe.setBounds(100, 50, 200, 30);
        panelEmail.add(lblPleaseSelectThe);
        
        JLabel lblPleaseSelectThe_1 = new JLabel("Current month:");
        lblPleaseSelectThe_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPleaseSelectThe_1.setBounds(100, 80, 200, 30);
        panelEmail.add(lblPleaseSelectThe_1);
        
        JLabel lblEmailContent = new JLabel("Email content:");
        lblEmailContent.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmailContent.setBounds(100, 140, 200, 30);
        panelEmail.add(lblEmailContent);
        
        JLabel lblEmailOfReceiver = new JLabel("Email of receiver:");
        lblEmailOfReceiver.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmailOfReceiver.setBounds(100, 400, 150, 30);
        panelEmail.add(lblEmailOfReceiver);
        
        JLabel lblEmailSubject = new JLabel("Email subject:");
        lblEmailSubject.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmailSubject.setBounds(100, 110, 200, 30);
        panelEmail.add(lblEmailSubject);
        
        JTextField txtDate = new JTextField();      
        txtDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        txtDate.setText(today);
        txtDate.setEditable(false);
        txtDate.setBounds(350, 80, 150, 25);
        panelEmail.add(txtDate);
        txtDate.setColumns(120);
        
        JButton btnSend = new JButton("Send");
        btnSend.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSend.setEnabled(false);
        btnSend.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
					ReminderEmail.sendIndividualReminderEmail(selectedMember, txtrEmailContentInPanel.getText(), txtSubject.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
        btnSend.setBounds(100, 440, 100, 30);
        panelEmail.add(btnSend);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		btnSend.setEnabled(false);
        		txtrEmailContentInPanel.setText("Email");
        		txtEmailReceiver.setText("");
        	}
        });
        btnCancel.setBounds(220, 440, 100, 30);
        panelEmail.add(btnCancel);
        
        
        JComboBox<Object> comboBox_KitchenMember = new JComboBox<>();
        comboBox_KitchenMember.setFont(new Font("Tahoma", Font.PLAIN, 15));
        comboBox_KitchenMember.setModel(new DefaultComboBoxModel<Object>(getMemberRoomAndName(memberList)));
        comboBox_KitchenMember.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String select = String.valueOf(comboBox_KitchenMember.getSelectedItem()).split(" ")[0];
        		Integer roomNumber = Integer.parseInt(select);
        		selectedMember = findKitchenMember(memberList, roomNumber);
        		txtEmailReceiver.setText(selectedMember.getEmail());
        		try {
					txtrEmailContentInPanel.setText(ReminderEmail.generateEmailContent(selectedMember, new Date()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}      		
        		btnSend.setEnabled(true);
        	}
        });
        comboBox_KitchenMember.setBounds(350, 50, 150, 25);
        panelEmail.add(comboBox_KitchenMember);	
	}
	
	private String[] getMemberRoomAndName(List<KitchenMember> memberList) {
		String[] memberRoomAndName = new String[memberList.size()];
		int i = 0;
		for (KitchenMember mem : memberList) {
			memberRoomAndName[i] = Integer.toString(mem.getRoom()) + " " + mem.getName();
			i++;
		}
		return memberRoomAndName;
	}
	
	private KitchenMember findKitchenMember(List<KitchenMember> memberList, int room) {
		for (KitchenMember mem : memberList) {
			if (mem.getRoom() == room) {
				return mem;
			}
		}
		return null;
	}
	
	
	
	public JPanel getEmailOnePanel() {
		return panelEmail;
	}
}
