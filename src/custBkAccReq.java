import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class custBkAccReq {

	public JFrame frame;
	private JTable BARtable;
	private JTextField amount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					custBkAccReq window = new custBkAccReq();
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
	public custBkAccReq() {
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 566, 194);
		frame.getContentPane().add(scrollPane);
		
		BARtable = new JTable();
		scrollPane.setViewportView(BARtable);
		populateTable();
		
		JButton backBtn = new JButton("");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Going Back");
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
		backBtn.setIcon(new ImageIcon("C:\\Users\\bhavp\\Downloads\\icons8-back-button-35.png"));
		backBtn.setBackground(new Color(255, 255, 255));
		backBtn.setBounds(10, 10, 35, 35);
		backBtn.setFocusPainted(false);
		frame.getContentPane().add(backBtn);
		
		amount = new JTextField();
		amount.setBounds(90, 310, 130, 30);
		frame.getContentPane().add(amount);
		amount.setColumns(10);
		
		JLabel amountLabel = new JLabel("Amount");
		amountLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		amountLabel.setBounds(90, 270, 130, 30);
		frame.getContentPane().add(amountLabel);
		
		JButton reqBtn = new JButton("Request Bank Account");
		reqBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Requesting account with amount = " + amount.getText());
				try {
					reqBA();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		reqBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
		reqBtn.setBounds(318, 308, 177, 30);
		frame.getContentPane().add(reqBtn);
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
	public void populateTable() {
		try {
			if (Database.connection == null) {
	            Database.connect();
	        }
			Connection connection = Database.connection;
	        userModel UM = userModel.getInstance();
			String custUserName = UM.getUserName();
			String query = "SELECT requestID as 'Request Number', amount as 'Deposited Amount', CASE WHEN aproval = true THEN 'Approved' WHEN aproval = false THEN 'False' ELSE 'Waiting' END AS 'Approval' FROM shadowbank.barequest inner join shadowbank.custacc on custacc.custID = barequest.custID where custacc.custUserName = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, custUserName);
			ResultSet result = stm.executeQuery();
			BARtable.setModel(DbUtils.resultSetToTableModel(result));
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void reqBA() throws SQLException {
		if (Database.connection == null) {
            Database.connect();
        }
		Connection connection = Database.connection;
        userModel UM = userModel.getInstance();
		String custUserName = UM.getUserName();
		String Query = "INSERT INTO shadowbank.barequest (custID, amount) SELECT custID, ? FROM shadowbank.custacc WHERE custUserName = ?";
		PreparedStatement stm = connection.prepareStatement(Query);
		stm.setInt(1, Integer.parseInt(amount.getText()));
		stm.setString(2, custUserName);
		stm.executeUpdate();
		
	}
}
