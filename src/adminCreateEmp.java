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
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class adminCreateEmp {

	public JFrame frame;
	private JTextField name;
	private JTextField username;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminCreateEmp window = new adminCreateEmp();
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
	public adminCreateEmp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(null);
		
		JLabel empProLabel = new JLabel("Create Employee");
		empProLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		empProLabel.setHorizontalAlignment(SwingConstants.CENTER);
		empProLabel.setBounds(210, 45, 140, 30);
		frame.getContentPane().add(empProLabel);
		
		JLabel nameLabel = new JLabel("Full Name");
		nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
		nameLabel.setBounds(210, 85, 140, 30);
		frame.getContentPane().add(nameLabel);
		
		name = new JTextField();
		name.setBounds(210, 113, 140, 30);
		frame.getContentPane().add(name);
		name.setColumns(10);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(210, 181, 140, 30);
		frame.getContentPane().add(username);
		
		JLabel userNameLabel = new JLabel("Username");
		userNameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
		userNameLabel.setBounds(210, 153, 140, 30);
		frame.getContentPane().add(userNameLabel);
		
		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(210, 249, 140, 30);
		frame.getContentPane().add(password);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
		passwordLabel.setBounds(210, 221, 140, 30);
		frame.getContentPane().add(passwordLabel);
		
		JButton regBtn = new JButton("Register");
		regBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateRegistration()) {
	                System.out.println("Account registered");
	                addEmpInfo();
	            } else {
	                System.out.println("Invalid registration input");
	            }
			}
		});
		regBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		regBtn.setBounds(210, 306, 140, 30);
		frame.getContentPane().add(regBtn);
		
		JButton backBtn = new JButton("");
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
		backBtn.setBorder(null);
		backBtn.setBackground(new Color(255, 255, 255));
		backBtn.setIcon(new ImageIcon("C:\\Users\\bhavp\\Downloads\\icons8-back-button-35.png"));
		backBtn.setBounds(10, 10, 35, 35);
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
	public void addEmpInfo( ) {
		try {
			
			if (Database.connection == null) {
		        Database.connect();
		    }
			Connection connection = Database.connection;
			String Query = "Insert into empacc (empUserName, empPassword, empName) values (?,?,?)";
			PreparedStatement stm = connection.prepareStatement(Query);
			char[] passwordChars = password.getPassword();

			// Convert char[] to String
			String passwordString = new String(passwordChars);

			// Store the string in your object
			stm.setString(1, username.getText());
			stm.setString(2, passwordString);
			stm.setString(3, name.getText());
			
			stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private boolean validateRegistration() {
	    String enteredUserName = username.getText();
	    char[] enteredPasswordChars = password.getPassword();
	    String enteredPassword = new String(enteredPasswordChars);
	    String enteredName = name.getText();

	    if (enteredUserName.isEmpty() || enteredPassword.isEmpty() || enteredName.isEmpty()) {
	        System.out.println("Please fill in all fields");
	        return false;
	    }

	    return true;
	}

}
