package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import DAO.KitchenMemberDAO;
import DAO.ReciptDAO;
import model.KitchenMember;

public class PanelKitchenMember extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<KitchenMember> memberList;
	private JPanel panelKitchenMember;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelKitchenMember window = new PanelKitchenMember();
					JFrame frame = new JFrame();					
					frame.setBounds(100, 100, 719, 573);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(new CardLayout(0, 0));
					frame.getContentPane().add(window.getKitchenMemberPanel());
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
	public PanelKitchenMember() throws SQLException {
//      Panel kitchen member
      panelKitchenMember  = new JPanel();
      
      memberList = KitchenMemberDAO.getKitchenMemberList();
      
      JScrollPane scrollPane_KitchenMem = new JScrollPane();
      scrollPane_KitchenMem.setBounds(100, 110, 450, 270);
      
      String[] columns_kitMem = {"Room", "Name", "Email"};
      int numOfRow = memberList.size();
      Object[][] data_kitMem = new Object[numOfRow][3];
      for (int i = 0; i < numOfRow; i++) {
      	KitchenMember member = memberList.get(i);
      	data_kitMem[i][0] = member.getRoom();
      	data_kitMem[i][1] = member.getName();
      	data_kitMem[i][2] = member.getEmail();
      }
      
      JTable table_kitchenMem = new JTable(data_kitMem, columns_kitMem);
      table_kitchenMem.setRowSelectionAllowed(false);
      table_kitchenMem.setEnabled(false);
      table_kitchenMem.setRowHeight(20);
      scrollPane_KitchenMem.setViewportView(table_kitchenMem);
      
      JLabel lblKitchenMembers = new JLabel("Kitchen Members:");
      lblKitchenMembers.setBounds(100, 50, 300, 50);
      lblKitchenMembers.setFont(new Font("Tahoma", Font.PLAIN, 28));
      lblKitchenMembers.setHorizontalAlignment(SwingConstants.LEFT);
      lblKitchenMembers.setBackground(new Color(255, 255, 0));
      lblKitchenMembers.setLabelFor(scrollPane_KitchenMem);
      
      for (int i = 0; i < 3; i++) {
      	  TableColumn column = table_kitchenMem.getColumnModel().getColumn(i);
          if (i == 2) {
              column.setPreferredWidth(100); //Email column is bigger
          } else {
              column.setPreferredWidth(30);
          }
      } 
      
      JTextField balance = new JTextField();
      balance.setFont(new Font("Tahoma", Font.PLAIN, 15));
      balance.setBounds(410, 400, 80, 30);
      balance.setEditable(false);
      balance.setText(Double.toString(ReciptDAO.getAccountBalance()));
      balance.setColumns(10);
      
      JLabel lblBalance = new JLabel("Account Balance:");
      lblBalance.setFont(new Font("Tahoma", Font.BOLD, 15));
      lblBalance.setBounds(250, 400, 150, 30);
      
      JLabel lblDkk = new JLabel("DKK");
      lblDkk.setFont(new Font("Tahoma", Font.PLAIN, 15));
      lblDkk.setBounds(500, 400, 50, 30);
      panelKitchenMember.setLayout(null);
      panelKitchenMember.add(lblKitchenMembers);
      panelKitchenMember.add(scrollPane_KitchenMem);
      
      JButton btnEdit = new JButton("Edit");
      btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
      btnEdit.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		
      		
      		JComboBox<Integer[]> xField = new JComboBox<>();
      		Integer[] roomList = new Integer[memberList.size()];
      		for (int i = 0; i< memberList.size(); i++) {
      			roomList[i] = memberList.get(i).getRoom();
      		}
      		xField.setModel(new DefaultComboBoxModel(roomList));
				JTextField yField = new JTextField();
				JTextField zField = new JTextField();

				JPanel emailPanel = new JPanel();
				emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
				emailPanel.add(new JLabel("Room:"));
				emailPanel.add(xField);
				emailPanel.add(new JLabel("Name:"));
				emailPanel.add(yField);
				emailPanel.add(new JLabel("Email:"));
				emailPanel.add(zField);

				xField.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int index = getMemberFromRoomNo((Integer) xField.getSelectedItem());
						if (index != -1) {
							KitchenMember mem = memberList.get(index);
							yField.setText(mem.getName());
							zField.setText(mem.getEmail());
						}
					}
				});

				int result = JOptionPane.showConfirmDialog(panelKitchenMember, emailPanel,
						"Update Kitchen Member Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					int index = getMemberFromRoomNo((Integer) xField.getSelectedItem());
					KitchenMember mem = memberList.get(index);
					mem.setName(yField.getText());
					mem.setEmail(zField.getText());
					try {
						KitchenMemberDAO.updateKitchenMember(mem);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					memberList.set(index, mem);
					table_kitchenMem.getModel().setValueAt(mem.getName(), xField.getSelectedIndex(), 1);
					table_kitchenMem.getModel().setValueAt(mem.getEmail(), xField.getSelectedIndex(), 2);
					JOptionPane.showMessageDialog(panelKitchenMember, "Saved");
				}
      	}
      });
      btnEdit.setBounds(100, 400, 100, 30);
      panelKitchenMember.add(btnEdit);
      panelKitchenMember.add(lblBalance);
      panelKitchenMember.add(balance);
      panelKitchenMember.add(lblDkk);

	}
	
	private int getMemberFromRoomNo(Integer roomNo) {
		for (int i = 0; i < memberList.size(); i++) {
			if (memberList.get(i).getRoom() == roomNo)
				return i;
		}
		return -1;
	}
	
	public JPanel getKitchenMemberPanel() {
		return panelKitchenMember;
	}
	

}
