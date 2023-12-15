import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;


import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class CustWithDep {

	public JFrame frame;
	private JTextField Amount;
	private JComboBox<String> accCB;
	static DefaultComboBoxModel<String> accCBModel = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustWithDep window = new CustWithDep();
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
	public CustWithDep() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(null);
		
		JButton depBtn = new JButton("Deposit");
		depBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				depositMoney();
			}
		});
		depBtn.setHorizontalAlignment(SwingConstants.CENTER);
		depBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		depBtn.setBounds(171, 229, 100, 30);
		frame.getContentPane().add(depBtn);
		
		JButton withBtn = new JButton("Withdraw");
		withBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				withdrawMoney();
			}
		});
		withBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		withBtn.setHorizontalAlignment(SwingConstants.CENTER);
		withBtn.setBounds(296, 229, 100, 30);
		frame.getContentPane().add(withBtn);
		
		JLabel accLabel = new JLabel("Account");
		accLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		accLabel.setBounds(227, 76, 125, 30);
		frame.getContentPane().add(accLabel);
		
		accCB = new JComboBox<String>();
		accCB.setBounds(227, 109, 125, 30);
		frame.getContentPane().add(accCB);
		populateCB();
		
		Amount = new JTextField();
		Amount.setColumns(10);
		Amount.setBounds(227, 177, 125, 30);
		frame.getContentPane().add(Amount);
		
		JLabel amountLabel = new JLabel("Amount");
		amountLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		amountLabel.setBounds(227, 149, 125, 30);
		frame.getContentPane().add(amountLabel);
		
		JButton backBtn = new JButton("");
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
	public void populateCB() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			userModel UM = userModel.getInstance();
			String custUserName = UM.getUserName();
			String query = "SELECT accID, amount FROM shadowbank.bankacc " +
			               "INNER JOIN shadowbank.custacc ON custacc.custID = bankacc.custID " +
			               "WHERE custacc.custUserName = ?";
			accCBModel = new DefaultComboBoxModel<String>();

			try (PreparedStatement stm = connection.prepareStatement(query)) {
			    stm.setString(1, custUserName);
			    ResultSet result = stm.executeQuery();

			    while (result.next()) {
			        String accID = String.valueOf(result.getInt("accID"));
			        accCBModel.addElement(accID);
			    }

			    accCB.setModel(accCBModel);
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void withdrawMoney() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			userModel UM = userModel.getInstance();
			String custUserName = UM.getUserName();
			String selectedAccID = (String) accCB.getSelectedItem();

			// Assuming you have the value to subtract in a variable named 'subtractValue'
	        int withdrawValue = Integer.parseInt(Amount.getText()); // Replace this with the actual value you want to subtract

			// Query to retrieve the current amount
			String selectQuery = "SELECT amount FROM shadowbank.bankacc " +
			                     "INNER JOIN shadowbank.custacc ON custacc.custID = bankacc.custID " +
			                     "WHERE custacc.custUserName = ? AND bankacc.accID = ?";

			// Query to update the amount
			String updateQuery = "UPDATE shadowbank.bankacc SET amount = ? " +
			                     "WHERE custID IN (SELECT custID FROM shadowbank.custacc WHERE custUserName = ?) " +
			                     "AND accID = ?";

			try (PreparedStatement selectStm = connection.prepareStatement(selectQuery)) {
			    selectStm.setString(1, custUserName);
			    selectStm.setString(2, selectedAccID);

			    ResultSet result = selectStm.executeQuery();

			    if (result.next()) {
			        // Get the current amount
			        int currentAmount = result.getInt("amount");
			        // Subtract the value
			        int newAmount = currentAmount - withdrawValue;

			        // Update the new amount in the database
			        try (PreparedStatement updateStm = connection.prepareStatement(updateQuery)) {
			            updateStm.setInt(1, newAmount);
			            updateStm.setString(2, custUserName);
			            updateStm.setString(3, selectedAccID);

			            int rowsAffected = updateStm.executeUpdate();

			            if (rowsAffected > 0) {
			                System.out.println("Amount updated successfully.");
			            } else {
			                System.out.println("No rows updated. Check your WHERE conditions.");
			            }
			        } catch (Exception e) {
			            e.printStackTrace(); // Handle the exception according to your needs
			        }
			    } else {
			        System.out.println("No rows found for the specified conditions.");
			    }
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	public void depositMoney() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			userModel UM = userModel.getInstance();
			String custUserName = UM.getUserName();
			String selectedAccID = (String) accCB.getSelectedItem();

			// Assuming you have the value to subtract in a variable named 'subtractValue'
	        int depositValue = Integer.parseInt(Amount.getText()); // Replace this with the actual value you want to subtract

			// Query to retrieve the current amount
			String selectQuery = "SELECT amount FROM shadowbank.bankacc " +
			                     "INNER JOIN shadowbank.custacc ON custacc.custID = bankacc.custID " +
			                     "WHERE custacc.custUserName = ? AND bankacc.accID = ?";

			// Query to update the amount
			String updateQuery = "UPDATE shadowbank.bankacc SET amount = ? " +
			                     "WHERE custID IN (SELECT custID FROM shadowbank.custacc WHERE custUserName = ?) " +
			                     "AND accID = ?";

			try (PreparedStatement selectStm = connection.prepareStatement(selectQuery)) {
			    selectStm.setString(1, custUserName);
			    selectStm.setString(2, selectedAccID);

			    ResultSet result = selectStm.executeQuery();

			    if (result.next()) {
			        // Get the current amount
			        int currentAmount = result.getInt("amount");
			        // Subtract the value
			        int newAmount = currentAmount + depositValue;

			        // Update the new amount in the database
			        try (PreparedStatement updateStm = connection.prepareStatement(updateQuery)) {
			            updateStm.setInt(1, newAmount);
			            updateStm.setString(2, custUserName);
			            updateStm.setString(3, selectedAccID);

			            int rowsAffected = updateStm.executeUpdate();

			            if (rowsAffected > 0) {
			                System.out.println("Amount updated successfully.");
			            } else {
			                System.out.println("No rows updated. Check your WHERE conditions.");
			            }
			        } catch (Exception e) {
			            e.printStackTrace(); // Handle the exception according to your needs
			        }
			    } else {
			        System.out.println("No rows found for the specified conditions.");
			    }
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
