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

public class empViewCust {

	public JFrame frame;
	private JTable custtable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					empViewCust window = new empViewCust();
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
	public empViewCust() {
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
		
		custtable = new JTable();
		custtable.setFont(new Font("SansSerif", Font.BOLD, 12));
		scrollPane.setViewportView(custtable);
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
		
		JLabel custLabel = new JLabel("All Customers");
		custLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		custLabel.setHorizontalAlignment(SwingConstants.CENTER);
		custLabel.setBounds(217, 26, 160, 30);
		frame.getContentPane().add(custLabel);
		
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
			String query = "select bankacc.custID AS 'Customer ID', custacc.custName AS 'Customer Name', COUNT(accID) AS 'Number of Accounts', SUM(amount) AS 'Total Amount' from shadowbank.bankacc inner join shadowbank.custacc on custacc.custID = bankacc.custID group by custacc.custID";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			custtable.setModel(DbUtils.resultSetToTableModel(result));
		}catch (Exception e) {System.out.println(e);
		}
	}

}
