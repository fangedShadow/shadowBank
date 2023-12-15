import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class adminChangeProfile {

	public JFrame frame;
	private JTextField custUN;
	private JTextField empUN;
	private JTextField empName;
	private JTextField custName;
	private JTextField custAdd;
	private JPasswordField custPass;
	private JPasswordField empPass;
	private static JComboBox<String> custCB;
	static DefaultComboBoxModel<String> custCBmodel = new DefaultComboBoxModel<String>();
	private static JComboBox<String> empCB;
	static DefaultComboBoxModel<String> empCBmodel = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminChangeProfile window = new adminChangeProfile();
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
	public adminChangeProfile() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(null);
		
		JLabel custLabel = new JLabel("Customer");
		custLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		custLabel.setHorizontalAlignment(SwingConstants.CENTER);
		custLabel.setBounds(74, 10, 150, 30);
		frame.getContentPane().add(custLabel);
		
		JLabel empLabel = new JLabel("Employee");
		empLabel.setHorizontalAlignment(SwingConstants.CENTER);
		empLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		empLabel.setBounds(396, 10, 150, 30);
		frame.getContentPane().add(empLabel);
		
		JLabel custIDLabel = new JLabel("Customer ID");
		custIDLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		custIDLabel.setBounds(74, 50, 150, 30);
		frame.getContentPane().add(custIDLabel);
		
		JLabel empIDLabel = new JLabel("Employee ID");
		empIDLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		empIDLabel.setBounds(396, 50, 150, 30);
		frame.getContentPane().add(empIDLabel);
		
		custCB = new JComboBox<String>();
		custCB.setBounds(74, 78, 150, 30);
		frame.getContentPane().add(custCB);
		populateCustCB();
		
		empCB = new JComboBox<String>();
		empCB.setBounds(396, 78, 150, 30);
		frame.getContentPane().add(empCB);
		populateEmpCB();
		
		JButton custChangeBtn = new JButton("Change");
		custChangeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//		        // Get the new name and address from the text fields
				int selectedCustID = Integer.parseInt((String) custCB.getSelectedItem());
				String username = custUN.getText();
				char[] password = custPass.getPassword();
		        String newName = custName.getText();
		        String newAddress = custAdd.getText();

		        // Check if both name and address are not empty
		        if (!newName.isEmpty() && !newAddress.isEmpty()) {
		            // Update the user's profile in the database
		            if (updateCustProfile(selectedCustID, username, password, newName, newAddress)) {
		                System.out.println("Customer Account Updated!!");
		                // Optionally, you can clear the text fields after a successful update
		                custUN.setText("");
                		custPass.setText("");
                		custName.setText("");
		                custAdd.setText("");
		            } else {
		                System.out.println("Failed to update customer account");
		            }
		        } else {
		            System.out.println("Name and address cannot be empty");
		        }			
			}
		});
		custChangeBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		custChangeBtn.setBounds(74, 353, 150, 30);
		frame.getContentPane().add(custChangeBtn);
		
		JButton empChangeBtn = new JButton("Change");
		empChangeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get the new name and address from the text fields
				int selectedempID = Integer.parseInt((String) empCB.getSelectedItem());
				String username = empUN.getText();
				char[] password = empPass.getPassword();
		        String newName = empName.getText();

		        // Check if both name and address are not empty
		        if (!newName.isEmpty() && !username.isEmpty() && !(password.length == 0)) {
		            // Update the user's profile in the database
		            if (updateEmpProfile(selectedempID, username, password, newName)) {
		                System.out.println("Customer Account Updated!!");
		                // Optionally, you can clear the text fields after a successful update
		                empUN.setText("");
                		empPass.setText("");
                		empName.setText("");
		            } else {
		                System.out.println("Failed to update customer account");
		            }
		        } else {
		            System.out.println("Name and address cannot be empty");
		        }	
			}
		});
		empChangeBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		empChangeBtn.setBounds(396, 353, 150, 30);
		frame.getContentPane().add(empChangeBtn);
		
		JButton backBtn = new JButton("");
		backBtn.setBorder(null);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToAdminA();
			}
			private void goToAdminA() {
				// TODO Auto-generated method stub
				frame.dispose();
				adminAcc AA = new adminAcc();
				AA.initialize();
				AA.frame.setVisible(true);
			}
		});
		backBtn.setIcon(new ImageIcon("C:\\Users\\bhavp\\Downloads\\icons8-back-button-35.png"));
		backBtn.setBackground(new Color(255, 255, 255));
		backBtn.setBounds(10, 10, 35, 35);
		backBtn.setFocusPainted(false);
		frame.getContentPane().add(backBtn);
		
		JLabel custUserNameLabel = new JLabel("Username");
		custUserNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		custUserNameLabel.setBounds(74, 107, 150, 30);
		frame.getContentPane().add(custUserNameLabel);
		
		custUN = new JTextField();
		custUN.setBounds(74, 136, 150, 30);
		frame.getContentPane().add(custUN);
		custUN.setColumns(10);
		
		JLabel empUserNameLabel = new JLabel("Username");
		empUserNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		empUserNameLabel.setBounds(396, 118, 150, 30);
		frame.getContentPane().add(empUserNameLabel);
		
		empUN = new JTextField();
		empUN.setColumns(10);
		empUN.setBounds(396, 147, 150, 30);
		frame.getContentPane().add(empUN);
		
		JLabel empNameLabel = new JLabel("Full Name");
		empNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		empNameLabel.setBounds(396, 181, 150, 30);
		frame.getContentPane().add(empNameLabel);
		
		empName = new JTextField();
		empName.setColumns(10);
		empName.setBounds(396, 210, 150, 30);
		frame.getContentPane().add(empName);
		
		custName = new JTextField();
		custName.setColumns(10);
		custName.setBounds(74, 197, 150, 30);
		frame.getContentPane().add(custName);
		
		JLabel custNameLabel = new JLabel("Full Name");
		custNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		custNameLabel.setBounds(74, 168, 150, 30);
		frame.getContentPane().add(custNameLabel);
		
		custAdd = new JTextField();
		custAdd.setColumns(10);
		custAdd.setBounds(74, 257, 150, 30);
		frame.getContentPane().add(custAdd);
		
		JLabel custAddLabel = new JLabel("Address");
		custAddLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		custAddLabel.setBounds(74, 228, 150, 30);
		frame.getContentPane().add(custAddLabel);
		
		custPass = new JPasswordField();
		custPass.setColumns(10);
		custPass.setBounds(74, 313, 150, 30);
		frame.getContentPane().add(custPass);
		
		JLabel custPassLabel = new JLabel("Password");
		custPassLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		custPassLabel.setBounds(74, 284, 150, 30);
		frame.getContentPane().add(custPassLabel);
		
		empPass = new JPasswordField();
		empPass.setColumns(10);
		empPass.setBounds(396, 286, 150, 30);
		frame.getContentPane().add(empPass);
		
		JLabel empPassLabel = new JLabel("Password");
		empPassLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		empPassLabel.setBounds(396, 257, 150, 30);
		frame.getContentPane().add(empPassLabel);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		java.net.URL iconURL = LandingMenu.class.getResource("icons8-bank-64.png"); // Replace with the path to your icon
        ImageIcon originalIcon = new ImageIcon(iconURL);
        
        int newWidth = 1328; // Set the desired width
        int newHeight = 1328; // Set the desired height
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        frame.setIconImage(scaledIcon.getImage()); 
        
		frame.setBounds(150, 150, 600, 450);
		frame.setMinimumSize(new Dimension(600, 450));
		frame.setMaximumSize(new Dimension(600, 450));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
	public void populateCustCB() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			String query = "SELECT * FROM shadowbank.custacc";
			custCBmodel = new DefaultComboBoxModel<String>();

			try (PreparedStatement stm = connection.prepareStatement(query)) {
			    ResultSet result = stm.executeQuery();

			    while (result.next()) {
			        String custID = result.getString("custID");
			        custCBmodel.addElement(custID);
			    }

			    custCB.setModel(custCBmodel);
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void populateEmpCB() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			String query = "SELECT * FROM empacc";
			empCBmodel = new DefaultComboBoxModel<String>();

			try (PreparedStatement stm = connection.prepareStatement(query)) {
			    ResultSet result = stm.executeQuery();

			    while (result.next()) {
			        String empID = result.getString("empID");
			        empCBmodel.addElement(empID);
			    }

			    empCB.setModel(empCBmodel);
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
	private boolean updateCustProfile(int custID ,String username, char[] password, String newName, String newAddress) {
	    try {
	        if (Database.connection == null) {
	            Database.connect();
	        }
	        Connection connection = Database.connection;
	        String nPassword = new String(password);
	        // Use a PreparedStatement to prevent SQL injection
	        String query = "UPDATE custacc SET custUserName = ?, custPassword = ?, custName = ?, custAddress = ? WHERE custID = ?";
	        PreparedStatement stm = connection.prepareStatement(query);
	        stm.setString(1, username);
	        stm.setString(2, nPassword);
	        stm.setString(3, newName);
	        stm.setString(4, newAddress);
	        stm.setInt(5, custID);

	        // Execute the update
	        int rowsAffected = stm.executeUpdate();

	        // Close the PreparedStatement (optional)
	        stm.close();

	        // Return true if at least one row was updated
	        return rowsAffected > 0;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	    }
	}
	private boolean updateEmpProfile(int empID ,String username, char[] password, String newName) {
	    try {
	        if (Database.connection == null) {
	            Database.connect();
	        }
	        Connection connection = Database.connection;
	        String nPassword = new String(password);
	        // Use a PreparedStatement to prevent SQL injection
	        String query = "UPDATE empacc SET empUserName = ?, empPassword = ?, empName = ? WHERE empID = ?";
	        PreparedStatement stm = connection.prepareStatement(query);
	        stm.setString(1, username);
	        stm.setString(2, nPassword);
	        stm.setString(3, newName);
	        stm.setInt(4, empID);

	        // Execute the update
	        int rowsAffected = stm.executeUpdate();

	        // Close the PreparedStatement (optional)
	        stm.close();

	        // Return true if at least one row was updated
	        return rowsAffected > 0;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	    }
	}
}
