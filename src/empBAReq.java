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

public class empBAReq {

	public JFrame frame;
	private JTable BARtable;
	private JTextField request;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					empBAReq window = new empBAReq();
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
	public empBAReq() {
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
		
		BARtable = new JTable();
		scrollPane.setViewportView(BARtable);
		populateTable();
		
		request = new JTextField();
		request.setBounds(224, 283, 150, 30);
		frame.getContentPane().add(request);
		request.setColumns(10);
		
		JButton appBtn = new JButton("Approve");
		appBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleApproval(true);
			}
		});
		appBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		appBtn.setBounds(168, 323, 120, 30);
		frame.getContentPane().add(appBtn);
		
		JButton rejBtn = new JButton("Reject");
		rejBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		rejBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleApproval(false);
			}
		});
		rejBtn.setBounds(298, 323, 120, 30);
		frame.getContentPane().add(rejBtn);
		
		JLabel reqNum = new JLabel("Request Number");
		reqNum.setHorizontalAlignment(SwingConstants.CENTER);
		reqNum.setFont(new Font("SansSerif", Font.BOLD, 14));
		reqNum.setBounds(224, 253, 150, 32);
		frame.getContentPane().add(reqNum);
		
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
	        //userModel UM = userModel.getInstance();
			//String custUserName = UM.getUserName();
			String query = "SELECT requestID as 'Request Number', custName as 'Customer Name', amount as 'Deposited Amount', CASE WHEN aproval = true THEN 'Approved' WHEN aproval = false THEN 'False' ELSE 'Waiting' END AS 'Approval' FROM shadowbank.barequest inner join shadowbank.custacc on custacc.custID = barequest.custID";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			BARtable.setModel(DbUtils.resultSetToTableModel(result));
		}catch (Exception e) {System.out.println(e);
		}
	}
	private void handleApproval(boolean approve) {
        String reqIDString = request.getText();
        if (!reqIDString.isEmpty()) {
            try {
                int reqID = Integer.parseInt(reqIDString);
                if (approve) {
                    // Update the approval status to true
                    updateApprovalStatus(reqID, true);

                    // If approved, insert into bankacc
                    insertIntoBankAcc(reqID);
                } else {
                    // Update the approval status to false
                    updateApprovalStatus(reqID, false);
                }

                // Refresh the table after the update
                populateTable();
            } catch (NumberFormatException ex) {
            	System.out.println(ex);
            }
        } else {
            System.out.println("Error");
        }
    }
	private void updateApprovalStatus(int reqID, boolean approve) {
		try {
			if (Database.connection == null) {
	            Database.connect();
	        }
			Connection connection = Database.connection;
			String updateQuery = "UPDATE shadowbank.barequest SET aproval = ? WHERE requestID = ?";
			PreparedStatement updateStm = connection.prepareStatement(updateQuery);
			updateStm.setBoolean(1, approve);
			updateStm.setInt(2, reqID);
            updateStm.executeUpdate();
			
		}
		catch (Exception e) {
			System.out.print(e);
		}
	}
	private void insertIntoBankAcc(int reqID) {
		try {
			if (Database.connection == null) {
	            Database.connect();
	        }
			Connection connection = Database.connection;
			String query = "INSERT INTO shadowbank.bankacc (custID, amount) SELECT custID, amount FROM shadowbank.barequest WHERE requestID = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, reqID);
			stm.executeUpdate();
			
		}catch (Exception e) {
			System.out.print(e);
		}
	}

}
