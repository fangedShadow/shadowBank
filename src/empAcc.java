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

public class empAcc {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					empAcc window = new empAcc();
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
	public empAcc() {
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
		
		JButton custBtn = new JButton("Customers");
		custBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		custBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEmpVC();
			}
			private void goToEmpVC() {
				// TODO Auto-generated method stub
				frame.dispose();
				empViewCust EVC = new empViewCust();
				EVC.initialize();
				EVC.frame.setVisible(true);
			}
		});
		custBtn.setBounds(128, 75, 125, 40);
		custBtn.setFocusPainted(false);
		frame.getContentPane().add(custBtn);
		
		JButton accReqBtn = new JButton("Account Request");
		accReqBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEmpBAReq();
			}
			private void goToEmpBAReq() {
				// TODO Auto-generated method stub
				frame.dispose();
				empBAReq EBAR = new empBAReq();
				EBAR.initialize();
				EBAR.frame.setVisible(true);
			}
		});
		accReqBtn.setFont(new Font("SansSerif", Font.BOLD, 10));
		accReqBtn.setBounds(327, 75, 125, 40);
		frame.getContentPane().add(accReqBtn);
		
		JButton bankAccBtn = new JButton("Bank Accounts");
		bankAccBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEmpVBA();
			}
			private void goToEmpVBA() {
				// TODO Auto-generated method stub
				frame.dispose();
				empViewBA EVBA = new empViewBA();
				EVBA.initialize();
				EVBA.frame.setVisible(true);
			}
		});
		bankAccBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		bankAccBtn.setBounds(128, 157, 125, 40);
		frame.getContentPane().add(bankAccBtn);
		
		JButton appBtn = new JButton("Appointments");
		appBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEmpVA();
			}
			private void goToEmpVA() {
				// TODO Auto-generated method stub
				frame.dispose();
				empViewApp EVA = new empViewApp();
				EVA.initialize();
				EVA.frame.setVisible(true);
			}
		});
		appBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		appBtn.setBounds(327, 157, 125, 40);
		frame.getContentPane().add(appBtn);
		
		JButton delAccBtn = new JButton("Delete Account");
		delAccBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEmpDBA();
			}
			private void goToEmpDBA() {
				// TODO Auto-generated method stub
				frame.dispose();
				empDeleteBA EDBA = new empDeleteBA();
				EDBA.initialize();
				EDBA.frame.setVisible(true);
			}
		});
		delAccBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		delAccBtn.setBounds(128, 241, 125, 40);
		frame.getContentPane().add(delAccBtn);
		
		JButton msgBtn = new JButton("Message");
		msgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToEmpMC();
			}
			private void goToEmpMC() {
				// TODO Auto-generated method stub
				frame.dispose();
				emoMsgCust EMC = new emoMsgCust();
				EMC.initialize();
				EMC.frame.setVisible(true);
			}
		});
		msgBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		msgBtn.setBounds(327, 240, 125, 40);
		frame.getContentPane().add(msgBtn);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnNewButton.setBounds(247, 308, 80, 30);
		frame.getContentPane().add(btnNewButton);

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
