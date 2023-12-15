import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Color;

public class EmpLogin {

	public JFrame frame;
	private JTextField userName;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmpLogin window = new EmpLogin();
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
	public EmpLogin() {
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
        frame.getContentPane().setLayout(null);
		
        userName = new JTextField();
		userName.setBounds(208, 104, 150, 30);
		frame.getContentPane().add(userName);
		userName.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(208, 174, 150, 30);
		frame.getContentPane().add(password);
		
		JLabel UserNLabel = new JLabel("Username");
		UserNLabel.setHorizontalAlignment(SwingConstants.CENTER);
		UserNLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		UserNLabel.setBounds(208, 74, 150, 20);
		frame.getContentPane().add(UserNLabel);
		
		JLabel passLabel = new JLabel("Password");
		passLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		passLabel.setBounds(208, 144, 150, 20);
		frame.getContentPane().add(passLabel);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Login");
				String enteredUserName = userName.getText();
				userModel UM = userModel.getInstance();
				UM.setUserName(enteredUserName);
				System.out.println(UM.getUserName());
		        char[] enteredPasswordChars = password.getPassword();
		        String enteredPassword = new String(enteredPasswordChars);
		        if (confirmCustinfo(enteredUserName, enteredPassword)) {
		            System.out.println("Login successful");
		            goToEmpBankAcc();
		        } else {
		            System.out.println("Invalid username or password");
		        }
			}
			private void goToEmpBankAcc() {
				// TODO Auto-generated method stub
				System.out.println("login in");
				frame.dispose();
				empAcc EA = new empAcc();
				EA.initialize();
				EA.frame.setVisible(true);
			}
		});
		loginBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		loginBtn.setBounds(208, 237, 150, 43);
		frame.getContentPane().add(loginBtn);
		
		JButton backBtn = new JButton("");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Going Back");
				goToMainMenu();
			}
			private void goToMainMenu() {
				// TODO Auto-generated method stub
				frame.dispose();
				LandingMenu LM = new LandingMenu();
				LM.initialize();
				LM.frame.setVisible(true);
			}
		});
		backBtn.setBackground(new Color(255, 255, 255));
		backBtn.setIcon(new ImageIcon("C:\\Users\\bhavp\\Downloads\\icons8-back-button-35.png"));
		backBtn.setBounds(10, 10, 35, 35);
		backBtn.setFocusPainted(false);
		backBtn.setBorder(null);
		frame.getContentPane().add(backBtn);
        
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
	public boolean confirmCustinfo(String enteredUserName, String enteredPassword) {
		try {
			
			if (Database.connection == null) {
		        Database.connect();
		    }
			Connection connection = Database.connection;
			String query = "SELECT empUserName, empPassword FROM empacc WHERE empUserName = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, enteredUserName);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
            	 String storedPassword = resultSet.getString("empPassword");
                 if (enteredPassword.equals(storedPassword)) {
                     // Update uName after a successful login
                     return true;
                 }
            }

            // No matching record found
            return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
