import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class custApp {

	public JFrame frame;
	private static JComboBox<String> empNameCB;
	static DefaultComboBoxModel<String> empNameCBmodel = new DefaultComboBoxModel<String>();
	private static JComboBox<String> TimeCB;
	static DefaultComboBoxModel<String> TimeCBmodel = new DefaultComboBoxModel<String>();
	private static JComboBox<String> dateCB;
	static DefaultComboBoxModel<String> dateCBmodel = new DefaultComboBoxModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					custApp window = new custApp();
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
	public custApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(null);
		
		JLabel appLabel = new JLabel("Book an Appointment");
		appLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		appLabel.setHorizontalAlignment(SwingConstants.CENTER);
		appLabel.setBounds(199, 36, 173, 30);
		frame.getContentPane().add(appLabel);
		
		JLabel empName = new JLabel("Employee");
		empName.setFont(new Font("SansSerif", Font.BOLD, 14));
		empName.setBounds(214, 76, 142, 30);
		frame.getContentPane().add(empName);
		
		empNameCB = new JComboBox<String>();
		empNameCB.setBounds(214, 113, 142, 30);
		frame.getContentPane().add(empNameCB);
		populateEmpCB();
		
		TimeCB = new JComboBox<String>();
		TimeCB.setBounds(214, 180, 142, 30);
		frame.getContentPane().add(TimeCB);
		populatetimeCB();
		
		JLabel timeLabel = new JLabel("Time");
		timeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		timeLabel.setBounds(214, 147, 142, 30);
		frame.getContentPane().add(timeLabel);
		
		JButton bookBtn = new JButton("Book");
		bookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookApp();
			}
		});
		bookBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
		bookBtn.setBounds(214, 292, 142, 30);
		frame.getContentPane().add(bookBtn);
		
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
		backBtn.setIcon(new ImageIcon("C:\\Users\\bhavp\\Downloads\\icons8-back-button-35.png"));
		backBtn.setBounds(10, 10, 30, 30);
		backBtn.setFocusPainted(false);
		frame.getContentPane().add(backBtn);
		
		dateCB = new JComboBox<String>();
		dateCB.setBounds(214, 253, 142, 30);
		frame.getContentPane().add(dateCB);
		populatedateCB();
		
		JLabel dateLabel = new JLabel("Date");
		dateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		dateLabel.setBounds(214, 220, 142, 30);
		frame.getContentPane().add(dateLabel);
		
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
	public void populateEmpCB() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			String query = "SELECT * FROM shadowbank.empacc";
			empNameCBmodel = new DefaultComboBoxModel<String>();

			try (PreparedStatement stm = connection.prepareStatement(query)) {
			    ResultSet result = stm.executeQuery();

			    while (result.next()) {
			        String empName = result.getString("empName");
			        empNameCBmodel.addElement(empName);
			    }

			    empNameCB.setModel(empNameCBmodel);
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void populatetimeCB() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			String query = "SELECT * FROM shadowbank.availtime";
			TimeCBmodel = new DefaultComboBoxModel<String>();

			try (PreparedStatement stm = connection.prepareStatement(query)) {
			    ResultSet result = stm.executeQuery();

			    while (result.next()) {
			        String availTime = result.getString("time");
			        TimeCBmodel.addElement(availTime);
			    }

			    TimeCB.setModel(TimeCBmodel);
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void populatedateCB() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			String query = "SELECT * FROM shadowbank.availdate";
			dateCBmodel = new DefaultComboBoxModel<String>();

			try (PreparedStatement stm = connection.prepareStatement(query)) {
			    ResultSet result = stm.executeQuery();

			    while (result.next()) {
			        String availDate = result.getString("date");
			        dateCBmodel.addElement(availDate);
			    }

			    dateCB.setModel(dateCBmodel);
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void bookApp() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			userModel UM = userModel.getInstance();
			String custUserName = UM.getUserName();
			String selectedEmpName = (String) empNameCB.getSelectedItem();
			String selectedTimeDate = (String) dateCB.getSelectedItem() +" at "+TimeCB.getSelectedItem();
			String Query = "INSERT INTO shadowbank.custempapp (custID, empID, appTime) SELECT custacc.custID, empacc1.empID, ? FROM shadowbank.custacc INNER JOIN shadowbank.empacc empacc1 ON custacc.custUserName = ? INNER JOIN shadowbank.empacc empacc2 ON empacc2.empName = ?";
			PreparedStatement stm = connection.prepareStatement(Query);
			stm.setString(1, selectedTimeDate);
		    stm.setString(2, custUserName);
		    stm.setString(3, selectedEmpName);

		    stm.executeUpdate();
		    System.out.println("Appointment Booked");
		}catch (Exception e) {System.out.println(e);
		}
	}

}
