import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Dimension;

public class LandingMenu {

	public JFrame frame;
	public JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LandingMenu window = new LandingMenu();
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
	public LandingMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		
		panel = new JPanel();
		panel.setForeground(new Color(0, 0, 0));
		panel.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		java.net.URL iconURL = LandingMenu.class.getResource("icons8-bank-64.png"); // Replace with the path to your icon
        ImageIcon originalIcon = new ImageIcon(iconURL);
        
        int newWidth = 1328; // Set the desired width
        int newHeight = 1328; // Set the desired height
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Set the scaled icon for the JFrame
        frame.setIconImage(scaledIcon.getImage());

		
		JButton custButton = new JButton("Customer");
		custButton.setBounds(214, 88, 156, 40);
		custButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		custButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		custButton.setFocusPainted(false);
		custButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Going to Customer page");
				goToCustLogin();
			}

			private void goToCustLogin() {
				// TODO Auto-generated method stub
				frame.dispose();
				customerLogin Cust = new customerLogin();
				Cust.initialize();
				Cust.frame.setVisible(true);
			}
		});
		panel.setLayout(null);
		panel.add(custButton);
		
		JButton adminButtom = new JButton("Admin");
		adminButtom.setBounds(214, 210, 156, 40);
		adminButtom.setFocusPainted(false);
		adminButtom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Going to Admin page");
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
		adminButtom.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel.add(adminButtom);
		
		JButton empButton = new JButton("Employee");
		empButton.setBounds(214, 150, 156, 40);
		empButton.setFocusPainted(false);
		empButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Going to Employee page");
				goToEmpLogin();
			}
			private void goToEmpLogin() {
				// TODO Auto-generated method stub
				frame.dispose();
				EmpLogin EL = new EmpLogin();
				EL.initialize();
				EL.frame.setVisible(true);
			}
		});
		empButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel.add(empButton);
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
