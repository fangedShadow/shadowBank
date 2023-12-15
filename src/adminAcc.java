import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.UIManager;

public class adminAcc {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminAcc window = new adminAcc();
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
	public adminAcc() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
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
		
		JButton empBtn = new JButton("Create Employee");
		empBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		empBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToACE();
			}
			private void goToACE() {
				// TODO Auto-generated method stub
				frame.dispose();
				adminCreateEmp ACE = new adminCreateEmp();
				ACE.initialize();
				ACE.frame.setVisible(true);
			}
		});
		empBtn.setBounds(88, 110, 165, 40);
		empBtn.setFocusPainted(false);
		frame.getContentPane().add(empBtn);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToAdminLogin();
			}
			private void goToAdminLogin() {
				// TODO Auto-generated method stub
				frame.dispose();
				adminLogin AL = new adminLogin();
				AL.initialize();
				AL.frame.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnNewButton.setBounds(247, 308, 80, 30);
		frame.getContentPane().add(btnNewButton);
		
		JButton deleteBtn = new JButton("Delete Accounts");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToADA();
			}
			private void goToADA() {
				// TODO Auto-generated method stub
				frame.dispose();
				adminDeleteAcc ADA = new adminDeleteAcc();
				ADA.initialize();
				ADA.frame.setVisible(true);
			}
		});
		deleteBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		deleteBtn.setFocusPainted(false);
		deleteBtn.setBounds(88, 185, 165, 40);
		frame.getContentPane().add(deleteBtn);
		
		JButton bankBtn = new JButton("Delete Bank Account");
		bankBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToADBA();
			}
			private void goToADBA() {
				// TODO Auto-generated method stub
				frame.dispose();
				adminDeleteBA ADBA = new adminDeleteBA();
				ADBA.initialize();
				ADBA.frame.setVisible(true);
			}
		});
		bankBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		bankBtn.setFocusPainted(false);
		bankBtn.setBounds(319, 185, 165, 40);
		frame.getContentPane().add(bankBtn);
		
		JButton ProfileBtn = new JButton("Change Profile");
		ProfileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToACP();
			}
			private void goToACP() {
				// TODO Auto-generated method stub
				frame.dispose();
				adminChangeProfile ACP = new adminChangeProfile();
				ACP.initialize();
				ACP.frame.setVisible(true);
			}
		});
		ProfileBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		ProfileBtn.setFocusPainted(false);
		ProfileBtn.setBounds(319, 110, 165, 40);
		frame.getContentPane().add(ProfileBtn);

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
}
