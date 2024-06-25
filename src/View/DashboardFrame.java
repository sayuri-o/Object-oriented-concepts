package View;

//Import necessary Java libraries
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Create a class named DashboardFrame that extends JFrame
public class DashboardFrame extends JFrame {

	// Initialize images for icons
	private Image img_dashboard = new ImageIcon(DashboardFrame.class.getResource("/View/res/home.png")).getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
	private Image img_student = new ImageIcon(DashboardFrame.class.getResource("/View/res/student.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image img_teacher = new ImageIcon(DashboardFrame.class.getResource("/View/res/teacher.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image img_subject = new ImageIcon(DashboardFrame.class.getResource("/View/res/subject.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image img_class = new ImageIcon(DashboardFrame.class.getResource("/View/res/class.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image img_search = new ImageIcon(DashboardFrame.class.getResource("/View/res/search.png")).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
	private Image img_signout = new ImageIcon(DashboardFrame.class.getResource("/View/res/SignOut.png")).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);

	private static final long serialVersionUID = 1L;

	// Create panels for students, teachers, courses, and classes
	private StudentsPanel studentsPanel;
	private TeachersPanel teachersPanel;
	private SubjectsPanel subjectsPanel;
	private ClassesPanel classesPanel;

	private JPanel contentPane;
	private JTextField SearchTextBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DashboardFrame frame = new DashboardFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame (Constructor)
	 */
	public DashboardFrame() throws ClassNotFoundException, SQLException {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 255), 4, true));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Initialize panels and components
		studentsPanel = new StudentsPanel();
		teachersPanel = new TeachersPanel();
		subjectsPanel = new SubjectsPanel();
		classesPanel = new ClassesPanel();
		
		// Hide them at the beginning
		studentsPanel.setVisible(false);
		teachersPanel.setVisible(false);
		subjectsPanel.setVisible(false);
		classesPanel.setVisible(false);

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

		// Create the main panel and add various sub panels to it
		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		panel.setBounds(10, 10, 264, 748);
		contentPane.add(panel);
		panel.setLayout(null);

		//Make Close Button
		JLabel closeX = new JLabel("X");
		closeX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this application?", "Confirmation", JOptionPane.YES_NO_OPTION)==0)
				{
					DashboardFrame.this.dispose();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				closeX.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				closeX.setForeground(Color.BLACK);
			}
		});
		closeX.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		closeX.setForeground(new Color(0, 0, 0));
		closeX.setHorizontalAlignment(SwingConstants.CENTER);
		closeX.setBounds(994, 0, 30, 30);
		contentPane.add(closeX);

		JLabel DashboardName = new JLabel("DASHBOARD");
		DashboardName.setHorizontalAlignment(SwingConstants.CENTER);
		DashboardName.setForeground(new Color(255, 255, 255));
		DashboardName.setFont(new Font("Arial Black", Font.BOLD, 24));
		DashboardName.setBounds(41, 121, 181, 50);
		panel.add(DashboardName);

		JLabel DashboardIco = new JLabel("");
		DashboardIco.setBounds(80, 49, 100, 74);
		panel.add(DashboardIco);
		DashboardIco.setIcon(new ImageIcon(img_dashboard));


		//Define methods for search and panel visibility
		JPanel SearchPanel = new JPanel();
		SearchPanel.setBackground(new Color(211, 211, 211));
		SearchPanel.setForeground(new Color(192, 192, 192));
		SearchPanel.setBounds(10, 181, 244, 50);
		panel.add(SearchPanel);
		SearchPanel.setLayout(null);

		// Add a beveled border with a raised effect to the "Search" panel
		SearchPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		SearchTextBox = new JTextField();
		SearchTextBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(SearchTextBox.getText().equals("Search")) {
					SearchTextBox.setText("");
				}
				else {
					SearchTextBox.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(SearchTextBox.getText().equals(""))
					SearchTextBox.setText("Search");
			}
		});
		SearchTextBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		SearchTextBox.setText("Search");
		SearchTextBox.setBounds(10, 10, 187, 30);
		SearchPanel.add(SearchTextBox);
		SearchTextBox.setColumns(10);
		SearchTextBox.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					performSearch(SearchTextBox.getText());
				}
			}
		});

		JPanel SearchButtonPanel = new JPanel();
		SearchButtonPanel.setBounds(204, 10, 30, 30);
		SearchPanel.add(SearchButtonPanel);
		SearchButtonPanel.setLayout(null);

		JLabel SearchIco = new JLabel("");
		SearchIco.setBounds(0, 0, 30, 30);
		SearchIco.setHorizontalAlignment(SwingConstants.LEFT);
		SearchButtonPanel.add(SearchIco);
		SearchIco.setIcon(new ImageIcon(img_search));
		SearchIco.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				performSearch(SearchTextBox.getText());
			}
		});

		//clicking panel names
		JPanel StudentsPanel = new JPanel();
		StudentsPanel.addMouseListener(new PanelButtonMouseAdapter(StudentsPanel) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(studentsPanel);
			}
		});
		StudentsPanel.setBackground(new Color(30, 144, 255));
		StudentsPanel.setForeground(new Color(192, 192, 192));
		StudentsPanel.setBounds(0, 254, 264, 78);
		panel.add(StudentsPanel);
		StudentsPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("STUDENTS");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 22));
		lblNewLabel.setBounds(91, 22, 163, 29);
		StudentsPanel.add(lblNewLabel);

		JLabel StudentsIco = new JLabel("");
		StudentsIco.setBounds(22, 10, 50, 50);
		StudentsPanel.add(StudentsIco);
		StudentsIco.setIcon(new ImageIcon(img_student));


		JPanel TeachersPanel = new JPanel();
		TeachersPanel.addMouseListener(new PanelButtonMouseAdapter(TeachersPanel) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(teachersPanel);
			}
		});
		TeachersPanel.setBackground(new Color(30, 144, 255));
		TeachersPanel.setForeground(new Color(192, 192, 192));
		TeachersPanel.setBounds(0, 332, 264, 78);
		panel.add(TeachersPanel);
		TeachersPanel.setLayout(null);

		JLabel lblTeachers = new JLabel("TEACHERS");
		lblTeachers.setHorizontalAlignment(SwingConstants.LEFT);
		lblTeachers.setForeground(new Color(255, 255, 255));
		lblTeachers.setFont(new Font("Arial", Font.BOLD, 22));
		lblTeachers.setBounds(91, 22, 163, 29);
		TeachersPanel.add(lblTeachers);

		JLabel TeachersIco = new JLabel("");
		TeachersIco.setBounds(22, 10, 50, 50);
		TeachersPanel.add(TeachersIco);
		TeachersIco.setIcon(new ImageIcon(img_teacher));

		JPanel SubjectsPanel = new JPanel();
		SubjectsPanel.addMouseListener(new PanelButtonMouseAdapter(SubjectsPanel) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(subjectsPanel);
			}
		});
		SubjectsPanel.setBackground(new Color(30, 144, 255));
		SubjectsPanel.setForeground(new Color(192, 192, 192));
		SubjectsPanel.setBounds(0, 410, 264, 78);
		panel.add(SubjectsPanel);
		SubjectsPanel.setLayout(null);

		JLabel lblSubjects = new JLabel("SUBJECTS");
		lblSubjects.setHorizontalAlignment(SwingConstants.LEFT);
		lblSubjects.setForeground(new Color(255, 255, 255));
		lblSubjects.setFont(new Font("Arial", Font.BOLD, 22));
		lblSubjects.setBounds(91, 22, 163, 29);
		SubjectsPanel.add(lblSubjects);

		JLabel SubjectsIco = new JLabel("");
		SubjectsIco.setBounds(22, 10, 50, 50);
		SubjectsPanel.add(SubjectsIco);
		SubjectsIco.setIcon(new ImageIcon(img_subject));

		JPanel ClassesPanel = new JPanel();
		ClassesPanel.addMouseListener(new PanelButtonMouseAdapter(ClassesPanel) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(classesPanel);
			}
		});
		ClassesPanel.setBackground(new Color(30, 144, 255));
		ClassesPanel.setForeground(new Color(192, 192, 192));
		ClassesPanel.setBounds(0, 488, 264, 78);
		panel.add(ClassesPanel);
		ClassesPanel.setLayout(null);

		JLabel lblClasses = new JLabel("CLASSES");
		lblClasses.setHorizontalAlignment(SwingConstants.LEFT);
		lblClasses.setForeground(new Color(255, 255, 255));
		lblClasses.setFont(new Font("Arial", Font.BOLD, 22));
		lblClasses.setBounds(91, 22, 163, 29);
		ClassesPanel.add(lblClasses);

		JLabel ClassesIco = new JLabel("");
		ClassesIco.setBounds(22, 10, 50, 50);
		ClassesPanel.add(ClassesIco);
		ClassesIco.setIcon(new ImageIcon(img_class));



		JPanel SignOutPanel = new JPanel();
		SignOutPanel.addMouseListener(new PanelButtonMouseAdapter(SignOutPanel) {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?")==0) {
					LoginFrame loginFrame = new LoginFrame();
					loginFrame.setVisible(true);
					DashboardFrame.this.dispose();
				}
			}
		});

		SignOutPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SignOutPanel.setBackground(new Color(105, 105, 105));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				SignOutPanel.setBackground(new Color(128, 128, 128));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				SignOutPanel.setBackground(new Color(211, 211, 211));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				SignOutPanel.setBackground(new Color(105, 105, 105));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				SignOutPanel.setBackground(new Color(211, 211, 211));
			}
		});

		SignOutPanel.setBackground(new Color(211, 211, 211));
		SignOutPanel.setForeground(new Color(192, 192, 192));
		SignOutPanel.setBounds(54, 635, 154, 39);
		panel.add(SignOutPanel);
		SignOutPanel.setLayout(null);

		// Add a beveled border with a raised effect to the "Sign Out" panel
		SignOutPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		JLabel lblSignOut = new JLabel("Sign Out");
		lblSignOut.setHorizontalAlignment(SwingConstants.LEFT);
		lblSignOut.setForeground(new Color(255, 0, 0));
		lblSignOut.setFont(new Font("Arial", Font.BOLD, 22));
		lblSignOut.setBounds(48, 5, 98, 29);
		SignOutPanel.add(lblSignOut);

		JLabel SignOutIco = new JLabel("");
		SignOutIco.setBounds(10, 4, 30, 30);
		SignOutPanel.add(SignOutIco);
		SignOutIco.setIcon(new ImageIcon(img_signout));

		JPanel MainPanel = new JPanel();
		MainPanel.setBounds(279, 26, 735, 716);
		contentPane.add(MainPanel);
		MainPanel.setLayout(null);

		MainPanel.add(studentsPanel);
		MainPanel.add(teachersPanel);
		MainPanel.add(subjectsPanel);
		MainPanel.add(classesPanel);

	}

	// Method to handle search functionality
	private void performSearch(String searchText) {
		if (searchText.equalsIgnoreCase("students")) {
			showPanel(studentsPanel);
		} else if (searchText.equalsIgnoreCase("teachers")) {
			showPanel(teachersPanel);
		} else if (searchText.equalsIgnoreCase("courses")) {
			showPanel(subjectsPanel);
		} else if (searchText.equalsIgnoreCase("classes")) {
			showPanel(classesPanel);
		} else {
			//JOptionPane.showMessageDialog(this, "No matching panel found for the search: " + searchText);
		}
	}

	// Method to show a specific panel
	private void showPanel(JPanel panelToShow) {
		studentsPanel.setVisible(false);
		teachersPanel.setVisible(false);
		subjectsPanel.setVisible(false);
		classesPanel.setVisible(false);

		panelToShow.setVisible(true);
	}

	// Method to handle panel clicks
	public void menuClicked(JPanel panel) {
		studentsPanel.setVisible(false);
		teachersPanel.setVisible(false);
		subjectsPanel.setVisible(false);
		classesPanel.setVisible(false);

		panel.setVisible(true);

	}

	// Custom mouse adapter for panel buttons
	private class PanelButtonMouseAdapter extends MouseAdapter{

		JPanel panel;
		public PanelButtonMouseAdapter(JPanel panel) {
			this.panel = panel;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			panel.setBackground(new Color(135,205,250));			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			panel.setBackground(new Color(30,144,255));

		}

		@Override
		public void mousePressed(MouseEvent e) {
			panel.setBackground(new Color(60,179,113));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			panel.setBackground(new Color(135,205,250));
		}
	}
}
