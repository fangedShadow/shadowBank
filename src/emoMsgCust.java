import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class emoMsgCust {

	public JFrame frame;
	private static JComboBox<String> custNameCB;
	static DefaultComboBoxModel<String> custNameCBmodel = new DefaultComboBoxModel<String>();
	public JTextArea descText;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					emoMsgCust window = new emoMsgCust();
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
	public emoMsgCust() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
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
		btnNewButton.setBorder(null);
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\bhavp\\Downloads\\icons8-back-button-35.png"));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setBounds(10, 10, 35, 35);
		frame.getContentPane().add(btnNewButton);
		
		JLabel msgLabel = new JLabel("Message Customer");
		msgLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		msgLabel.setBounds(215, 26, 125, 25);
		frame.getContentPane().add(msgLabel);
		
		JLabel custLabel = new JLabel("Customer Name");
		custLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		custLabel.setBounds(215, 80, 125, 25);
		frame.getContentPane().add(custLabel);
		
		custNameCB = new JComboBox<String>();
		custNameCB.setBounds(215, 105, 125, 25);
		frame.getContentPane().add(custNameCB);
		populateCustCB();
		
		JLabel desLabel = new JLabel("Description");
		desLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		desLabel.setBounds(215, 140, 125, 25);
		frame.getContentPane().add(desLabel);
		
		descText = new JTextArea();
		descText.setBorder(new LineBorder(new Color(0, 0, 0)));
		descText.setBackground(new Color(255, 255, 255));
		descText.setBounds(215, 175, 125, 125);
		frame.getContentPane().add(descText);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		sendBtn.setHorizontalAlignment(SwingConstants.CENTER);
		sendBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		sendBtn.setBounds(215, 310, 125, 25);
		frame.getContentPane().add(sendBtn);
		
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
	public void populateCustCB() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			String query = "SELECT * FROM shadowbank.custacc";
			custNameCBmodel = new DefaultComboBoxModel<String>();

			try (PreparedStatement stm = connection.prepareStatement(query)) {
			    ResultSet result = stm.executeQuery();

			    while (result.next()) {
			        String custName = result.getString("custName");
			        custNameCBmodel.addElement(custName);
			    }

			    custNameCB.setModel(custNameCBmodel);
			} catch (Exception e) {
			    e.printStackTrace(); // Handle the exception according to your needs
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
	public void sendMessage() {
		try {
			if (Database.connection == null) {
			    Database.connect();
			}

			Connection connection = Database.connection;
			userModel UM = userModel.getInstance();
			String empUserName = UM.getUserName();
			String selectedCustName = (String) custNameCB.getSelectedItem();
			String message = descText.getText();
			String Query = "INSERT INTO shadowbank.commsg (custID, empID, message) SELECT custacc.custID, empacc1.empID, ? FROM shadowbank.custacc INNER JOIN shadowbank.empacc empacc1 ON custacc.custName = ? WHERE empacc1.empUserName = ?";
			try (PreparedStatement stm = connection.prepareStatement(Query)) {
			    stm.setString(1, message);
			    stm.setString(2, selectedCustName);
			    stm.setString(3, empUserName);
			    System.out.println(empUserName);
			    stm.executeUpdate();
			    System.out.println("Message Sent");
			} catch (Exception e) {
			    e.printStackTrace();
			    System.out.println("Error sending message: " + e.getMessage());
			}
		}catch (Exception e) {System.out.println(e);
		}
	}
}
