package GUI;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import DAO.ExportExcelDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.CardLayout;

import java.sql.SQLException;
import java.awt.Font;


public class KitchenManagerGUI {

	
	private JFrame frame;
	private JMenu mnKitchenMember;
	private JPanel panelCashier;
	private JPanel panelRecipt;
	private JPanel panelKitchenFee;
	private JPanel panelEmailOne;
	private JPanel panelEmailMultiple;
	private JPanel panelKitchenMember;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KitchenManagerGUI window = new KitchenManagerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public KitchenManagerGUI() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		
//		Frame
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
//      Panel kitchen member
        PanelKitchenMember panelMemberTemp = new PanelKitchenMember();
        panelKitchenMember  = panelMemberTemp.getKitchenMemberPanel();
        frame.getContentPane().add(panelKitchenMember);
        frame.setVisible(true);                  
              
//      Panel Cashier
		panelCashier = new JPanel();              

//    	Panel Recipt
		panelRecipt = new JPanel();	
	
//		Panel Kitchen Fee
		panelKitchenFee = new JPanel();		
		
//		Panel Email One
		panelEmailOne = new JPanel();			

//		Panel Email Multiple
		panelEmailMultiple = new JPanel();
	      
//      Menu
      mnKitchenMember = new JMenu("KitchenMember");
      mnKitchenMember.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      menuBar.add(mnKitchenMember);
      
      JMenuItem mntmOverview = new JMenuItem("Overview");
      mntmOverview.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      mntmOverview.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if (!panelKitchenMember.isShowing()) {
				try {
					PanelKitchenMember panelMemberTemp = new PanelKitchenMember();
					panelKitchenMember  = panelMemberTemp.getKitchenMemberPanel();
					switchPanels(panelKitchenMember);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}    	        
      		}
      	}
      });
      mnKitchenMember.add(mntmOverview);
      
      JMenuItem mntmExit = new JMenuItem("Exit");
      mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      mntmExit.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		System.exit(JFrame.EXIT_ON_CLOSE);
      	}
      });
      
      JMenuItem mntmCashier = new JMenuItem("Cashier");
      mntmCashier.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      mntmCashier.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if (!panelCashier.isShowing()) {
				try {
					PanelCashier panelCashierTemp = new PanelCashier();
					panelCashier = panelCashierTemp.getCashierPanel();
					switchPanels(panelCashier);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
      		}

      	}
      });
      mnKitchenMember.add(mntmCashier);
      
      JMenuItem mntmExportToExcel = new JMenuItem("Export To Excel");
      mntmExportToExcel.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		JFileChooser fileChooser = new JFileChooser();
      		fileChooser.setDialogTitle("Specify a file to save");   
      		FileNameExtensionFilter filter = new FileNameExtensionFilter(".xlsx", "xlsx");
      		fileChooser.setFileFilter(filter); 
      		
      		int userSelection = fileChooser.showSaveDialog(frame);
      		 
      		if (userSelection == JFileChooser.APPROVE_OPTION) {
      		    File fileToSave = fileChooser.getSelectedFile();    
				if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
					fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
				}
				ExportExcelDAO.writeToExcel(fileToSave.getAbsolutePath());
				JOptionPane.showMessageDialog(frame, "Saved" );
      		}
      	}
      });
      mntmExportToExcel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      mnKitchenMember.add(mntmExportToExcel);
      mnKitchenMember.add(mntmExit);
      
      JMenu mnEmail = new JMenu("Email");
      mnEmail.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      menuBar.add(mnEmail);
      
      JMenuItem mntmSendEmailTo = new JMenuItem("Send Email To One Member");
      mntmSendEmailTo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      mntmSendEmailTo.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		if (!panelEmailOne.isShowing()) {
				try {
					PanelEmailOne panelEmailOneTemp = new PanelEmailOne();
					panelEmailOne = panelEmailOneTemp.getEmailOnePanel();
	      			switchPanels(panelEmailOne);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
      		}
      	}
      });
      mnEmail.add(mntmSendEmailTo);
      
      JMenuItem mntmSendEmailTo_1 = new JMenuItem("Send Email To Multiple Member");
      mntmSendEmailTo_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      mntmSendEmailTo_1.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {	
      		if (!panelEmailMultiple.isShowing()) {
				try {
					PanelEmailMultiple panelEmailMultipleTemp = new PanelEmailMultiple();
					panelEmailMultiple = panelEmailMultipleTemp.getEmailMultiplePanel();
	      			switchPanels(panelEmailMultiple);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
      			
      		}
      	}
      });
      mnEmail.add(mntmSendEmailTo_1);
      
      JMenu mnAccountmanager = new JMenu("Receipt Manager");
      mnAccountmanager.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      menuBar.add(mnAccountmanager);
      
      JMenuItem mntmPayment = new JMenuItem("Receipt");
      mntmPayment.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      mntmPayment.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if (!panelRecipt.isShowing()) {
				try {
					PanelReceipt panelReciptTemp = new PanelReceipt();
					panelRecipt = panelReciptTemp.getReciptPanel();
					switchPanels(panelRecipt);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}     			
      		}
      	}
      });
      mnAccountmanager.add(mntmPayment);
      
      JMenuItem mntmKitchenFee = new JMenuItem("Kitchen Fee");
      mntmKitchenFee.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      mntmKitchenFee.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		if (!panelKitchenFee.isShowing()) {
				try {
					PanelKitchenFee panelKitchenFeeTemp = new PanelKitchenFee();
					JPanel panelKitchenFee = panelKitchenFeeTemp.getKitchenFeePanel();
	      			switchPanels(panelKitchenFee);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
      			
      		}
      	}
      });
      mnAccountmanager.add(mntmKitchenFee);    
	}
	
	private void switchPanels(JPanel panel) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		frame.getContentPane().repaint();
		frame.getContentPane().revalidate();
		
	}	
}
