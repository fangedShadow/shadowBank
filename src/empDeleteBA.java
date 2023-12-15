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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class empDeleteBA {

	public JFrame frame;
	private JTable dbatable;
	private JTextField accNum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					empDeleteBA window = new empDeleteBA();
					window.setLookAndFeel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public empDeleteBA() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		
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
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 566, 184);
		frame.getContentPane().add(scrollPane);
		
		dbatable = new JTable();
		dbatable.setFont(new Font("SansSerif", Font.BOLD, 12));
		scrollPane.setViewportView(dbatable);
		populateTable();
		
		accNum = new JTextField();
		accNum.setBounds(224, 283, 150, 30);
		frame.getContentPane().add(accNum);
		accNum.setColumns(10);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String accIDToDelete = accNum.getText();

		        // Check if accIDToDelete is not empty before attempting to delete
		        if (!accIDToDelete.isEmpty()) {
		            try {
		                // Delete account with the specified accID
		                deleteAccount(accIDToDelete);

		                // Refresh the table after deletion
		                populateTable();
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                // Handle the exception according to your needs
		            }
		        } else {
		            // accID is empty, show an error message or take appropriate action
		            System.out.println("Please enter an account number to delete.");
		        }
		    }
		});
		deleteBtn.setBounds(224, 323, 150, 30);
		frame.getContentPane().add(deleteBtn);
		
		JLabel accNumber = new JLabel("Account Number");
		accNumber.setHorizontalAlignment(SwingConstants.CENTER);
		accNumber.setFont(new Font("SansSerif", Font.BOLD, 14));
		accNumber.setBounds(224, 253, 150, 32);
		frame.getContentPane().add(accNumber);
		
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
		
		JLabel deleteLabel = new JLabel("Delete Bank Account");
		deleteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		deleteLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		deleteLabel.setBounds(214, 7, 160, 30);
		frame.getContentPane().add(deleteLabel);
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
			String query = "select accID AS 'Account Number', amount AS 'Account Balance',custacc.custName AS 'Owner' from shadowbank.bankacc inner join shadowbank.custacc on custacc.custID = bankacc.custID order by custacc.custName";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			dbatable.setModel(DbUtils.resultSetToTableModel(result));
		}catch (Exception e) {System.out.println(e);
		}
	}
	private void deleteAccount(String accID) throws Exception {
	    // Execute a DELETE SQL statement
	    String deleteQuery = "DELETE FROM shadowbank.bankacc WHERE accID = ?";
	    try (PreparedStatement deleteStm = Database.connection.prepareStatement(deleteQuery)) {
	        deleteStm.setString(1, accID);
	        int rowsAffected = deleteStm.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Account deleted successfully.");
	        } else {
	            System.out.println("Account with specified accID not found.");
	        }
	    }
	}

}
//goToAdminA();
//}
//private void goToAdminA() {
//	// TODO Auto-generated method stub
//	frame.dispose();
//	adminAcc AA = new adminAcc();
//	AA.initialize();
//	AA.frame.setVisible(true);
//}