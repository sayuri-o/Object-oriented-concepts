package View;

//Import necessary Java libraries
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import Util.dbUtil;

//Create a class named LoginFrame that extends JFrame
public class LoginFrame extends JFrame {

	// Images for the background, user icon, password icon, and login key
	private Image img_bg = new ImageIcon(LoginFrame.class.getResource("/View/res/LoginFrameBackground.jpg")).getImage().getScaledInstance(900,600,Image.SCALE_SMOOTH);
	private Image img_user = new ImageIcon(LoginFrame.class.getResource("/View/res/UserIco.png")).getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
	private Image img_pwd = new ImageIcon(LoginFrame.class.getResource("/View/res/Password.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image img_login = new ImageIcon(LoginFrame.class.getResource("/View/res/LoginKey.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField username;
	private JPasswordField Password;
	private JLabel loginMsg = new JLabel("");

	PreparedStatement pst;
	ResultSet rs;

	private Connection con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 111, 164));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 160), 4, true));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel OfficeUseOnlyLabel = new JLabel("*For Office Use Only");
		OfficeUseOnlyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		OfficeUseOnlyLabel.setForeground(Color.WHITE);
		OfficeUseOnlyLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		OfficeUseOnlyLabel.setBounds(0, 560, 161, 30);
		contentPane.add(OfficeUseOnlyLabel);

		// Labels for the application name and school name
		JLabel Name2 = new JLabel("Information Management System");
		Name2.setHorizontalAlignment(SwingConstants.CENTER);
		Name2.setFont(new Font("Arial", Font.BOLD, 18));
		Name2.setForeground(new Color(255, 255, 255));
		Name2.setBounds(45, 114, 303, 30);
		contentPane.add(Name2);

		JLabel Name = new JLabel("ABC School \r\n");
		Name.setHorizontalAlignment(SwingConstants.CENTER);
		Name.setFont(new Font("Lucida Bright", Font.BOLD, 30));
		Name.setForeground(new Color(255, 255, 255));
		Name.setBounds(107, 44, 187, 80);
		contentPane.add(Name);

		// Panel for the username input
		JPanel panelUsername = new JPanel();
		panelUsername.setBackground(new Color(255, 255, 255));
		panelUsername.setBounds(70, 227, 250, 45);
		contentPane.add(panelUsername);
		panelUsername.setLayout(null);

		// Create a dummy panel to prevent automatic focus on the text field
		JPanel dummyPanel = new JPanel();
		dummyPanel.setBounds(0, 0, 1, 1); // Make it invisible
		contentPane.add(dummyPanel);

		// Set the focus to the dummy panel to prevent text field focus
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				dummyPanel.requestFocusInWindow();
			}
		});

		// Text field for entering the username
		username = new JTextField();
		username.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(username.getText().equals("Username")) {
					username.setText("");
				}
				else {
					username.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(username.getText().equals(""))
					username.setText("Username");
			}
		});
		username.setBorder(null);
		username.setFont(new Font("Arial", Font.BOLD, 13));
		username.setText("Username");
		username.setBounds(10, 10, 190, 25);
		panelUsername.add(username);
		username.setColumns(10);

		// User icon
		JLabel userIco = new JLabel("");
		userIco.setBounds(200, 2, 40, 40);
		panelUsername.add(userIco);
		userIco.setIcon(new ImageIcon(img_user));

		// Panel for the password input
		JPanel panelPassword = new JPanel();
		panelPassword.setLayout(null);
		panelPassword.setBackground(Color.WHITE);
		panelPassword.setBounds(70, 282, 250, 45);
		contentPane.add(panelPassword);

		// Password field for entering the password
		Password = new JPasswordField();
		Password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {

				String password = new String(Password.getPassword());

				if(password.equals("Password")) {
					Password.setEchoChar('●');
					Password.setText("");
				}
				else {
					Password.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {

				String password = new String(Password.getPassword());

				if(password.equals(""))
					Password.setText("Password");
				Password.setEchoChar((char)0);

			}
		});
		Password.setBorder(null);
		Password.setEchoChar((char)0);
		Password.setFont(new Font("Arial", Font.BOLD, 13));
		Password.setText("Password");
		Password.setBounds(10, 10, 190, 25);
		panelPassword.add(Password);

		// Password icon
		JLabel passwordIco = new JLabel("New label");
		passwordIco.setBounds(196, 2, 40, 40);
		panelPassword.add(passwordIco);
		passwordIco.setIcon(new ImageIcon(img_pwd));

		// Panel for the login button
		JPanel panelLogin = new JPanel();
		panelLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try{
					con = dbUtil.getDBConnection(); // Initialize the database connection

					String password = new String(Password.getPassword());

					pst = con.prepareStatement("SELECT * FROM admin_logins WHERE username = ? AND password = ?");
					pst.setString(1, username.getText());
					pst.setString(2, password);

					rs = pst.executeQuery();
					if (rs.next()) {
						// If the login is successful, open the DashboardFrame and close this one
						DashboardFrame dFrame = new DashboardFrame();
						dFrame.setVisible(true);
						dispose();
					}

					else if(username.getText().equals("") || username.getText().equals("Username") 
							|| password.equals("Password")) {
						loginMsg.setText("The fields cannot be empty!");
					}

					else
						loginMsg.setText("Invalid Username or Password!");
					con.close();

				}catch(SQLException | ClassNotFoundException e1){
					e1.printStackTrace();
				}

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panelLogin.setBackground(new Color(0, 80, 80));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panelLogin.setBackground(new Color(0, 139, 139));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				panelLogin.setBackground(new Color(0, 50, 50));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				panelLogin.setBackground(new Color(0, 80, 80));
			}
		});
		panelLogin.setBackground(new Color(0, 139, 139));
		panelLogin.setBounds(70, 370, 250, 50);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);

		// Add a beveled border with custom highlight and shadow colors
		Color highlightColor = new Color(0, 100, 100);
		Color shadowColor = new Color(0, 50, 50);
		panelLogin.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, highlightColor, shadowColor));

		// Login icon
		JLabel loginIco = new JLabel("");
		loginIco.setBounds(63, 0, 50, 50);
		panelLogin.add(loginIco);
		loginIco.setIcon(new ImageIcon(img_login));

		// Label for the "LOGIN" text
		JLabel login = new JLabel("LOGIN");
		login.setBounds(93, 10, 115, 30);
		panelLogin.add(login);
		login.setForeground(new Color(230, 230, 250));
		login.setHorizontalAlignment(SwingConstants.CENTER);
		login.setFont(new Font("Arial", Font.BOLD, 15));

		// Close button to exit the application
		JLabel closeX = new JLabel("X");
		closeX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this application?", "Confirmation", JOptionPane.YES_NO_OPTION)==0)
				{
					LoginFrame.this.dispose();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				closeX.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				closeX.setForeground(Color.WHITE);
			}
		});
		closeX.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		closeX.setForeground(new Color(255, 255, 255));
		closeX.setHorizontalAlignment(SwingConstants.CENTER);
		closeX.setBounds(760, 10, 30, 30);
		contentPane.add(closeX);

		// Label for login messages
		loginMsg.setForeground(new Color(222, 184, 135));
		loginMsg.setFont(new Font("Arial", Font.PLAIN, 12));
		loginMsg.setBounds(70, 337, 250, 23);
		contentPane.add(loginMsg);

		// Background image
		JLabel BG = new JLabel("");
		BG.setBounds(10, 10, 780, 580);
		contentPane.add(BG);
		BG.setIcon(new ImageIcon(img_bg));

		// Set the frame to be undecorated and centered on the screen
		setUndecorated(true);
		setLocationRelativeTo(null);
	}
}
