import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.UIManager;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Color;

public class custBankAcc {

	public JFrame frame;
	private JButton newAccBtn;
	private JButton wdBtn;
	private JButton appBtn;
	private JButton msgBtn;
	private JButton logoutBtn;
	private JScrollPane scrollPane;
	private JTable custBAtable;
	private JButton profileBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					custBankAcc window = new custBankAcc();
					window.frame.setVisible(true);
					window.setLookAndFeel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public custBankAcc() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(150, 150, 600, 400);
		frame.setMinimumSize(new Dimension(600, 400));
		frame.setMaximumSize(new Dimension(600, 400));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		java.net.URL iconURL = LandingMenu.class.getResource("icons8-bank-64.png"); // Replace with the path to your icon
        ImageIcon originalIcon = new ImageIcon(iconURL);
        
        int newWidth = 1328; // Set the desired width
        int newHeight = 1328; // Set the desired height
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the scaled icon for the JFrame
        frame.setIconImage(scaledIcon.getImage());
		
		newAccBtn = new JButton("New Account");
		newAccBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sending New Bank Account Request");
				goToBAreq();
			}
			private void goToBAreq() {
				// TODO Auto-generated method stub
				frame.dispose();
				custBkAccReq CBAR = new custBkAccReq();
				CBAR.initialize();
				CBAR.frame.setVisible(true);
			}
		});
		newAccBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		newAccBtn.setBounds(20, 318, 110, 30);
		frame.getContentPane().add(newAccBtn);
		
		wdBtn = new JButton("Withdraw/Deposit");
		wdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustWD();
			}
			private void goToCustWD() {
				// TODO Auto-generated method stub
				frame.dispose();
				CustWithDep CWD = new CustWithDep();
				CWD.initialize();
				CWD.frame.setVisible(true);
			}
		});
		wdBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		wdBtn.setBounds(152, 318, 139, 30);
		frame.getContentPane().add(wdBtn);
		
		appBtn = new JButton("Appointment");
		appBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustApp();
			}
			private void goToCustApp() {
				// TODO Auto-generated method stub
				frame.dispose();
				custApp CA = new custApp();
				CA.initialize();
				CA.frame.setVisible(true);
			}
		});
		appBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		appBtn.setBounds(312, 318, 110, 30);
		frame.getContentPane().add(appBtn);
		
		msgBtn = new JButton("Messages");
		msgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustMsg();
			}
			private void goToCustMsg() {
				// TODO Auto-generated method stub
				frame.dispose();
				custMsg CM = new custMsg();
				CM.initialize();
				CM.frame.setVisible(true);
			}
		});
		msgBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		msgBtn.setBounds(442, 318, 110, 30);
		frame.getContentPane().add(msgBtn);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustLogin();
			}
			private void goToCustLogin() {
				// TODO Auto-generated method stub
				frame.dispose();
				customerLogin CL = new customerLogin();
				CL.initialize();
				CL.frame.setVisible(true);
			}
		});
		logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		logoutBtn.setBounds(459, 6, 93, 21);
		frame.getContentPane().add(logoutBtn);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 566, 274);
		frame.getContentPane().add(scrollPane);
		
		custBAtable = new JTable();
		custBAtable.setBackground(new Color(255, 255, 255));
		custBAtable.setFont(new Font("SansSerif", Font.BOLD, 12));
		scrollPane.setViewportView(custBAtable);
		populateTable();
		
		profileBtn = new JButton("Edit Profile");
		profileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustProfile();
			}
			private void goToCustProfile() {
				// TODO Auto-generated method stub
				frame.dispose();
				custProfile CP = new custProfile();
				CP.initialize();
				CP.frame.setVisible(true);
			}
		});
		profileBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		profileBtn.setBounds(20, 7, 110, 21);
		profileBtn.setFocusPainted(false);
		frame.getContentPane().add(profileBtn);
	}
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
	public void populateTable() {
		try {
			if (Database.connection == null) {
	            Database.connect();
	        }
	        Connection connection = Database.connection;
	        userModel UM = userModel.getInstance();
			String custUserName = UM.getUserName();
			String query = "SELECT accID as 'Account Number', amount as 'Account Balance' FROM shadowbank.bankacc inner join shadowbank.custacc on custacc.custID = bankacc.custID where custacc.custUserName = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, custUserName);
			ResultSet result = stm.executeQuery();
			custBAtable.setModel(DbUtils.resultSetToTableModel(result));
		}catch (Exception e) {System.out.println(e);
		}
	}
}
