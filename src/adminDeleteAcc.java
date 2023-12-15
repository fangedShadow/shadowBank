import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import net.proteanit.sql.DbUtils;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class adminDeleteAcc {

	public JFrame frame;
	private JTable custTable;
	private JTable empTable;
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
					adminDeleteAcc window = new adminDeleteAcc();
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
	public adminDeleteAcc() {
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
		custLabel.setBounds(54, 35, 150, 30);
		frame.getContentPane().add(custLabel);
		
		JLabel empLabel = new JLabel("Employee");
		empLabel.setHorizontalAlignment(SwingConstants.CENTER);
		empLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		empLabel.setBounds(376, 35, 150, 30);
		frame.getContentPane().add(empLabel);
		
		JLabel custNameLabel = new JLabel("Username");
		custNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		custNameLabel.setBounds(54, 243, 150, 30);
		frame.getContentPane().add(custNameLabel);
		
		JLabel empNameLabel = new JLabel("Username");
		empNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		empNameLabel.setBounds(376, 243, 150, 30);
		frame.getContentPane().add(empNameLabel);
		
		custCB = new JComboBox<String>();
		custCB.setBounds(54, 283, 150, 30);
		frame.getContentPane().add(custCB);
		populateCustCB();
		
		empCB = new JComboBox<String>();
		empCB.setBounds(376, 283, 150, 30);
		frame.getContentPane().add(empCB);
		populateEmpCB();
		
		JButton custDelBtn = new JButton("Delete");
		custDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCustAcc();
			}
		});
		custDelBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		custDelBtn.setBounds(54, 323, 150, 30);
		frame.getContentPane().add(custDelBtn);
		
		JButton empDelBtn = new JButton("Delete");
		empDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEmpAcc();
			}
		});
		empDelBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		empDelBtn.setBounds(376, 323, 150, 30);
		frame.getContentPane().add(empDelBtn);
		
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
		backBtn.setBounds(0, 0, 35, 35);
		backBtn.setFocusPainted(false);
		frame.getContentPane().add(backBtn);
		
		JScrollPane custScrollPane = new JScrollPane();
		custScrollPane.setBounds(10, 75, 284, 170);
		frame.getContentPane().add(custScrollPane);
		
		custTable = new JTable();
		custTable.setFont(new Font("SansSerif", Font.BOLD, 12));
		custScrollPane.setViewportView(custTable);
		populateCustTable();
		
		JScrollPane empScrollPane = new JScrollPane();
		empScrollPane.setBounds(292, 75, 284, 170);
		frame.getContentPane().add(empScrollPane);
		
		empTable = new JTable();
		empTable.setFont(new Font("SansSerif", Font.BOLD, 12));
		empScrollPane.setViewportView(empTable);
		populateEmpTable();
		
		
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		java.net.URL iconURL = LandingMenu.class.getResource("icons8-bank-64.png"); // Replace with the path to your icon
        ImageIcon originalIcon = new ImageIcon(iconURL);
        
        int newWidth = 1328; // Set the desired width
        int newHeight = 1328; // Set the desired height
        Image scaledImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
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
	public void populateCustTable() {
		try {
			if (Database.connection == null) {
	            Database.connect();
	        }
	        Connection connection = Database.connection;
			String query = "SELECT custID as 'Account ID', custUserName as 'Username', custName as 'Name' FROM shadowbank.custacc;";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			custTable.setModel(DbUtils.resultSetToTableModel(result));
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void populateEmpTable() {
		try {
			if (Database.connection == null) {
	            Database.connect();
	        }
	        Connection connection = Database.connection;
			String query = "SELECT empID as 'Account ID', empUserName as 'Username', empName as 'Name' FROM shadowbank.empacc;";
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			empTable.setModel(DbUtils.resultSetToTableModel(result));
		}catch (Exception e) {System.out.println(e);
		}
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
			        String custName = result.getString("custUserName");
			        custCBmodel.addElement(custName);
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
			        String empName = result.getString("empUserName");
			        empCBmodel.addElement(empName);
			    }

			    empCB.setModel(empCBmodel);
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void deleteCustAcc() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}
			Connection connection = Database.connection;
			String selectedCustUserName = (String) custCB.getSelectedItem();
			String deleteQuery = "DELETE FROM shadowbank.custacc WHERE custUserName = ?";
		    try (PreparedStatement deleteStm = connection.prepareStatement(deleteQuery)) {
		        deleteStm.setString(1, selectedCustUserName);
		        int rowsAffected = deleteStm.executeUpdate();

		        if (rowsAffected > 0) {
		            System.out.println("Account deleted successfully.");
		            populateCustTable();
		        } else {
		            System.out.println("Account with specified accID not found.");
		        }
		    } catch (Exception e) {
			    e.printStackTrace();
			    System.out.println("Error sending message: " + e.getMessage());
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void deleteEmpAcc() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}
			Connection connection = Database.connection;
			String selectedEmpUserName = (String) empCB.getSelectedItem();
			String deleteQuery = "DELETE FROM shadowbank.empacc WHERE empUserName = ?";
		    try (PreparedStatement deleteStm = connection.prepareStatement(deleteQuery)) {
		        deleteStm.setString(1, selectedEmpUserName);
		        int rowsAffected = deleteStm.executeUpdate();

		        if (rowsAffected > 0) {
		            System.out.println("Account deleted successfully.");
		            populateEmpTable();
		        } else {
		            System.out.println("Account with specified accID not found.");
		        }
		    } catch (Exception e) {
			    e.printStackTrace();
			    System.out.println("Error sending message: " + e.getMessage());
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
}
