import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class custProfile {

	public JFrame frame;
	private JTextField name;
	private JTextField address;
	private String uName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					custProfile window = new custProfile();
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
	public custProfile() {
		initialize();
	}
	
	public void setuName(String Uname) {
		uName = Uname;
	}
	public String getuName() {
		return uName;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(null);
		
		JLabel profileLabel = new JLabel("Edit Profile");
		profileLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		profileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		profileLabel.setBounds(231, 70, 125, 30);
		frame.getContentPane().add(profileLabel);
		
		JLabel fullName = new JLabel("Full Name");
		fullName.setFont(new Font("SansSerif", Font.BOLD, 14));
		fullName.setBounds(214, 127, 142, 30);
		frame.getContentPane().add(fullName);
		
		name = new JTextField();
		name.setBounds(214, 167, 142, 30);
		frame.getContentPane().add(name);
		name.setColumns(10);
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(214, 247, 142, 30);
		frame.getContentPane().add(address);
		
		JLabel addabel = new JLabel("Address");
		addabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		addabel.setBounds(214, 207, 142, 30);
		frame.getContentPane().add(addabel);
		
		JButton changeBtn = new JButton("Change");
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userModel UM = userModel.getInstance();
				String usernameToUpdate = UM.getUserName();
				System.out.println(usernameToUpdate);
				

		        // Get the new name and address from the text fields
		        String newName = name.getText();
		        String newAddress = address.getText();

		        // Check if both name and address are not empty
		        if (!newName.isEmpty() && !newAddress.isEmpty()) {
		            // Update the user's profile in the database
		            if (updateUserProfile(usernameToUpdate, newName, newAddress)) {
		                System.out.println("Customer Account Updated!!");
		                // Optionally, you can clear the text fields after a successful update
		                name.setText("");
		                address.setText("");
		            } else {
		                System.out.println("Failed to update customer account");
		            }
		        } else {
		            System.out.println("Name and address cannot be empty");
		        }			
		     }
		});
		changeBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		changeBtn.setBounds(214, 292, 142, 30);
		frame.getContentPane().add(changeBtn);
		
		JButton backBtn = new JButton("");
		backBtn.setBackground(new Color(255, 255, 255));
		backBtn.setBorder(null);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustBankAcc();
			}
			private void goToCustBankAcc() {
				// TODO Auto-generated method stub
				frame.dispose();
				custBankAcc CBA = new custBankAcc();
				CBA.initialize();
				CBA.frame.setVisible(true);
			}
		});
		backBtn.setIcon(new ImageIcon("C:\\Users\\bhavp\\Downloads\\icons8-back-button-35.png"));
		backBtn.setBounds(10, 10, 30, 30);
		backBtn.setFocusPainted(false);
		frame.getContentPane().add(backBtn);
		
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
	private boolean updateUserProfile(String username, String newName, String newAddress) {
	    try {
	        if (Database.connection == null) {
	            Database.connect();
	        }
	        Connection connection = Database.connection;

	        // Use a PreparedStatement to prevent SQL injection
	        String query = "UPDATE custacc SET custName = ?, custAddress = ? WHERE custUserName = ?";
	        PreparedStatement stm = connection.prepareStatement(query);
	        stm.setString(1, newName);
	        stm.setString(2, newAddress);
	        stm.setString(3, username);

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
