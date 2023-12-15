import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class customerRegister {

	public JFrame frame;
	private JTextField userName;
	private JPasswordField password;
	private JButton regBtn;
	private JTextField name;
	private JLabel nameLabel;
	private JLabel addLabel;
	private JTextField address;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					customerRegister window = new customerRegister();
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
	public customerRegister() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(null);
		
		
		java.net.URL iconURL = LandingMenu.class.getResource("icons8-bank-64.png"); // Replace with the path to your icon
        ImageIcon originalIcon = new ImageIcon(iconURL);
        
        int newWidth = 1328; // Set the desired width
        int newHeight = 1328; // Set the desired height
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the scaled icon for the JFrame
        frame.setIconImage(scaledIcon.getImage());
		
		userName = new JTextField();
		userName.setBounds(208, 55, 150, 30);
		frame.getContentPane().add(userName);
		userName.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(208, 125, 150, 30);
		frame.getContentPane().add(password);
		
		JLabel UserNLabel = new JLabel("Username");
		UserNLabel.setHorizontalAlignment(SwingConstants.CENTER);
		UserNLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		UserNLabel.setBounds(208, 29, 150, 20);
		frame.getContentPane().add(UserNLabel);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		passLabel.setBounds(208, 95, 150, 20);
		frame.getContentPane().add(passLabel);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Going to Login");
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
		loginBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		loginBtn.setBounds(208, 337, 150, 30);
		frame.getContentPane().add(loginBtn);
		
		regBtn = new JButton("Register");
		regBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateRegistration()) {
	                System.out.println("Account registered");
	                addCustInfo();
	            } else {
	                System.out.println("Invalid registration input");
	            }
			}
			
		});
		regBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		regBtn.setBounds(208, 297, 150, 30);
		frame.getContentPane().add(regBtn);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(208, 191, 150, 30);
		frame.getContentPane().add(name);
		
		nameLabel = new JLabel("Full Name");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		nameLabel.setBounds(208, 165, 150, 20);
		frame.getContentPane().add(nameLabel);
		
		addLabel = new JLabel("Address");
		addLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		addLabel.setBounds(208, 231, 150, 20);
		frame.getContentPane().add(addLabel);
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(208, 257, 150, 30);
		frame.getContentPane().add(address);
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
	public void addCustInfo( ) {
		try {
			
			if (Database.connection == null) {
		        Database.connect();
		    }
			Connection connection = Database.connection;
			String Query = "Insert into custacc (custUserName, custPassword, custName, custAddress) values (?,?,?,?)";
			PreparedStatement stm = connection.prepareStatement(Query);
			char[] passwordChars = password.getPassword();

			// Convert char[] to String
			String passwordString = new String(passwordChars);

			// Store the string in your object
			stm.setString(1, userName.getText());
			stm.setString(2, passwordString);
			stm.setString(3, name.getText());
			stm.setString(4, address.getText());
			
			stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private boolean validateRegistration() {
	    String enteredUserName = userName.getText();
	    char[] enteredPasswordChars = password.getPassword();
	    String enteredPassword = new String(enteredPasswordChars);
	    String enteredName = name.getText();
	    String enteredAddress = address.getText();

	    if (enteredUserName.isEmpty() || enteredPassword.isEmpty() || enteredName.isEmpty() || enteredAddress.isEmpty()) {
	        System.out.println("Please fill in all fields");
	        return false;
	    }

	    return true;
	}
	
}
