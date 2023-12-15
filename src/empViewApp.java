import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class empViewApp {

	public JFrame frame;
	private JTable AVtable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					empViewApp window = new empViewApp();
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
	public empViewApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 66, 566, 259);
		frame.getContentPane().add(scrollPane);
		
		AVtable = new JTable();
		scrollPane.setViewportView(AVtable);
		populateTable();
		
		JButton backBtn = new JButton("");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEmpAcc();
			}
			private void goToEmpAcc() {
				// TODO Auto-generated method stub
				frame.dispose();
				empAcc EA = new empAcc();
				EA.initialize();
				EA.frame.setVisible(true);
			}
		});
		backBtn.setBorder(null);
		backBtn.setIcon(new ImageIcon("C:\\Users\\bhavp\\Downloads\\icons8-back-button-35.png"));
		backBtn.setBackground(new Color(255, 255, 255));
		backBtn.setBounds(10, 10, 35, 35);
		backBtn.setFocusPainted(false);
		frame.getContentPane().add(backBtn);
		
		JLabel appLabel = new JLabel("All Appointment");
		appLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		appLabel.setHorizontalAlignment(SwingConstants.CENTER);
		appLabel.setBounds(217, 26, 160, 30);
		frame.getContentPane().add(appLabel);
		
		java.net.URL iconURL = LandingMenu.class.getResource("icons8-bank-64.png"); // Replace with the path to your icon
        ImageIcon originalIcon = new ImageIcon(iconURL);
        
        int newWidth = 1328; // Set the desired width
        int newHeight = 1328; // Set the desired height
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the scaled icon for the JFrame
        frame.setIconImage(scaledIcon.getImage());
		
		frame.setBounds(150, 150, 600, 400);
		frame.setMinimumSize(new Dimension(600, 400));
		frame.setMaximumSize(new Dimension(600, 400));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			String empUserName = UM.getUserName();
			String query = "SELECT appID as 'Appointment Number', custacc.custName as 'Customer Name', appTime as 'Appointment Time' FROM shadowbank.custempapp inner join shadowbank.custacc on custacc.custID = custempapp.custID inner join shadowbank.empacc on empacc.empID = custempapp.empID where empacc.empUserName = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, empUserName);
			ResultSet result = stm.executeQuery();
			AVtable.setModel(DbUtils.resultSetToTableModel(result));
		}catch (Exception e) {System.out.println(e);
		}
	}

}
