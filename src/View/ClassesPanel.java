package View;

//Import necessary Java libraries

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import Controller.ClassImpl;
import Controller.IClass;
import Model.ClassM;
import Util.dbUtil;

//Define the TeachersPanel class, which extends JPanel
public class ClassesPanel extends JPanel {

	// Load image and scale it
	private Image img_class2 = new ImageIcon(LoginFrame.class.getResource("/View/res/class2.png")).getImage().getScaledInstance(220,220,Image.SCALE_SMOOTH);

	private static final long serialVersionUID = 1L;
	private JTextField ClassNameTxt;
	private JTextField NoSTxt;
	private JTextField CRoomNoTxt;
	private JTextField searchTxt;
	private JTextField ClassTeacherTxt;

	private JTable ClassesTable;

	PreparedStatement pst;
	ResultSet rs;

	private Connection con;


	// Method to load data into the table
	public void table_load(){

		IClass classImpl = new ClassImpl();
		ArrayList<ClassM> classList = classImpl.getAllClass();

		// Convert the ArrayList to a DefaultTableModel
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Class ID");
		model.addColumn("Class Name");
		model.addColumn("No of Students");
		model.addColumn("Class Room No");
		model.addColumn("Class Medium");
		model.addColumn("Class Teacher");

		for (ClassM class1 : classList) {
			model.addRow(new Object[] {
					class1.getClass_ID(),
					class1.getClass_Name(),
					class1.getNo_Students(),
					class1.getC_Room_No(),
					class1.getC_Medium(),
					class1.getC_Teacher(),
			});
		}

		ClassesTable.setModel(model);

	}


	/**
	 * Create the panel.	(Constructor for the ClassesPanel)
	 **/
	public ClassesPanel() throws ClassNotFoundException, SQLException {

		setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
		setBounds(0,0,735,716);
		setLayout(null);
		setVisible(true);

		ClassesTable = new JTable();

		try {
			con = dbUtil.getDBConnection(); // Initialize the database connection

			table_load();

		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: Unable to connect to the database.");
		}


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 454, 677, 225);
		add(scrollPane);

		ClassesTable = new JTable();
		scrollPane.setViewportView(ClassesTable);

		//load the table
		table_load();

		// Set column widths manually (for Teacher Name)
		ClassesTable.getColumnModel().getColumn(5).setPreferredWidth(100);

		// Create and configure a label for the title
		JLabel lblClasses = new JLabel("CLASSES");
		lblClasses.setHorizontalAlignment(SwingConstants.CENTER);
		lblClasses.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblClasses.setBounds(270, 10, 185, 74);
		add(lblClasses);

		// Create the panel for CRUD operations -----------------------------------------------------------------------------------------------------------

		//Registration Panel

		JPanel CrudOpPanel = new JPanel();
		CrudOpPanel.setLayout(null);
		CrudOpPanel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		CrudOpPanel.setBounds(28, 82, 362, 299);
		add(CrudOpPanel);

		JLabel ClassNameLabel = new JLabel("Class Name");
		ClassNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		ClassNameLabel.setBounds(33, 25, 85, 30);
		CrudOpPanel.add(ClassNameLabel);

		JLabel cNameValidLabel = new JLabel("*Grade between 1-13 with class name A-H");
		cNameValidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cNameValidLabel.setFont(new Font("Tahoma", Font.PLAIN, 8));
		cNameValidLabel.setBounds(151, 51, 173, 13);
		CrudOpPanel.add(cNameValidLabel);

		JLabel NoStudentsLabel = new JLabel("No. of Students");
		NoStudentsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		NoStudentsLabel.setBounds(33, 63, 99, 30);
		CrudOpPanel.add(NoStudentsLabel);

		JLabel MaximumStudentLabel = new JLabel("*Maximum 50 Students");
		MaximumStudentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		MaximumStudentLabel.setFont(new Font("Tahoma", Font.PLAIN, 8));
		MaximumStudentLabel.setBounds(239, 89, 85, 13);
		CrudOpPanel.add(MaximumStudentLabel);

		JLabel RoomNumLabel = new JLabel("Class Room No.");
		RoomNumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		RoomNumLabel.setBounds(33, 103, 114, 30);
		CrudOpPanel.add(RoomNumLabel);

		JLabel ClassMediumLabel = new JLabel("Class Medium");
		ClassMediumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		ClassMediumLabel.setBounds(33, 144, 99, 30);
		CrudOpPanel.add(ClassMediumLabel);

		JLabel ClassTeacherLabel = new JLabel("Class Teacher");
		ClassTeacherLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		ClassTeacherLabel.setBounds(33, 184, 99, 30);
		CrudOpPanel.add(ClassTeacherLabel);

		ClassNameTxt = new JTextField();
		ClassNameTxt.setColumns(10);
		ClassNameTxt.setBounds(141, 32, 183, 19);
		CrudOpPanel.add(ClassNameTxt);

		NoSTxt = new JTextField();
		NoSTxt.setColumns(10);
		NoSTxt.setBounds(142, 70, 182, 19);
		CrudOpPanel.add(NoSTxt);

		CRoomNoTxt = new JTextField();
		CRoomNoTxt.setColumns(10);
		CRoomNoTxt.setBounds(141, 110, 183, 19);
		CrudOpPanel.add(CRoomNoTxt);

		JComboBox<String> ClassMediumTxtChoose = new JComboBox<String>();
		ClassMediumTxtChoose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ClassMediumTxtChoose.setModel(new DefaultComboBoxModel<String>(new String[] {"Sinhala", "English", "Tamil"}));
		ClassMediumTxtChoose.setBounds(141, 148, 183, 22);
		CrudOpPanel.add(ClassMediumTxtChoose);

		ClassTeacherTxt = new JTextField();
		ClassTeacherTxt.setColumns(10);
		ClassTeacherTxt.setBounds(141, 191, 183, 19);
		CrudOpPanel.add(ClassTeacherTxt);

		//INSERT Operation-----------------------------------------------------------------------------------------

		JButton btnInsert = new JButton("INSERT");

		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Create a single instance (object) of Student Model.
				ClassM c1 = new ClassM();

				String cName, nofStudent, cRNum, cMedium, cTeacher;

				cName = ClassNameTxt.getText();
				nofStudent = NoSTxt.getText();
				cRNum = CRoomNoTxt.getText();
				cMedium = (String)ClassMediumTxtChoose.getSelectedItem();
				cTeacher = ClassTeacherTxt.getText();

				// Check if any of the fields are empty
				if (cName.isEmpty() || nofStudent.isEmpty() || cRNum.isEmpty() || cTeacher.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter all fields");
					return;
				}

				// Validate the class name
				if (!isValidClassName(cName)) {
					JOptionPane.showMessageDialog(null, "Enter a valid Class Name (e.g., 1A, 2H, 11C, 13E)");
					return;
				}

				// Validate if the number of students is a valid integer
				if (!isNumeric(nofStudent)) {
					JOptionPane.showMessageDialog(null, "Enter a Valid Number for 'No. of Students'");
					return;
				}

				// Parse the number of students as an integer
				int numStudents = Integer.parseInt(nofStudent);
				// Check if the number of students exceeds the maximum capacity (50)
				if (!isStudentCapacityValid(numStudents)) {
					return;
				}

				//set the retrieved data into the s1 object
				c1.setClass_Name(cName);
				c1.setNo_Students(nofStudent);
				c1.setC_Room_No(cRNum);
				c1.setC_Medium(cMedium);
				c1.setC_Teacher(cTeacher);

				try {
					pst = con.prepareStatement("insert into classes (Class_Name, No_of_Students, Class_Room_No, Class_Medium, Class_Teacher)values(?,?,?,?,?)");

					pst.setString(1, c1.getClass_Name());
					pst.setString(2, c1.getNo_Students());
					pst.setString(3, c1.getC_Room_No());
					pst.setString(4, c1.getC_Medium());
					pst.setString(5, c1.getC_Teacher());

					int rowsInserted = pst.executeUpdate();

					if (rowsInserted > 0) {
						JOptionPane.showMessageDialog(null, "Record Added Successfully!");

						table_load();

						ClassNameTxt.setText("");
						NoSTxt.setText("");
						CRoomNoTxt.setText("");

						ClassTeacherTxt.setText("");

						ClassNameTxt.requestFocus();

					} else {
						JOptionPane.showMessageDialog(null, "Failed to add the record.");
					}

					table_load();

				}
				catch (SQLException e1){

					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());

				}


			}
		});

		btnInsert.setFont(new Font("Arial", Font.BOLD, 10));
		btnInsert.setBounds(104, 228, 77, 21);
		CrudOpPanel.add(btnInsert);

		//UPDATE Operation-----------------------------------------------------------------------------------------

		JButton btnUpdate = new JButton("UPDATE");

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Create a single instance of Student Model.
				ClassM c1 = new ClassM();

				String cID, cName, nofStudent, cRNum, cMedium, cTeacher;

				cID = searchTxt.getText();
				cName = ClassNameTxt.getText();
				nofStudent = NoSTxt.getText();
				cRNum = CRoomNoTxt.getText();
				cMedium = (String) ClassMediumTxtChoose.getSelectedItem();
				cTeacher = ClassTeacherTxt.getText();

				// Check if any of the fields are empty
				if (cID.isEmpty() || cName.isEmpty() || nofStudent.isEmpty() || cRNum.isEmpty() || cMedium.isEmpty() || cTeacher.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a valid Class ID to perform an update.");
					return;
				}

				// Validate the class name
				if (!isValidClassName(cName)) {
					JOptionPane.showMessageDialog(null, "Enter a valid Class Name (e.g., 1A, 2H, 11C, 13E)");
					return;
				}

				// Validate if the number of students is a valid integer
				if (!isNumeric(nofStudent)) {
					JOptionPane.showMessageDialog(null, "Enter a Valid Number for 'No. of Students'");
					return;
				}

				// Parse the number of students as an integer
				int numStudents = Integer.parseInt(nofStudent);
				// Check if the number of students exceeds the maximum capacity (50)
				if (!isStudentCapacityValid(numStudents)) {
					return;
				}

				//set the retrieved data into the s1 object
				c1.setClass_Name(cName);
				c1.setNo_Students(nofStudent);
				c1.setC_Room_No(cRNum);
				c1.setC_Medium(cMedium);
				c1.setC_Teacher(cTeacher);
				c1.setClass_ID(cID);

				try {
					pst = con.prepareStatement("update classes set Class_Name = ?, No_of_Students = ?, Class_Room_No = ?, Class_Medium = ?, Class_Teacher = ? where Class_ID = ?");

					pst.setString(1, c1.getClass_Name());
					pst.setString(2, c1.getNo_Students());
					pst.setString(3, c1.getC_Room_No());
					pst.setString(4, c1.getC_Medium());
					pst.setString(5, c1.getC_Teacher());
					pst.setString(6, c1.getClass_ID());

					int rowsUpdated = pst.executeUpdate();

					if (rowsUpdated > 0) {
						JOptionPane.showMessageDialog(null, "Record Updated Successfully!");

						table_load();

						ClassNameTxt.setText("");
						NoSTxt.setText("");
						CRoomNoTxt.setText("");

						ClassTeacherTxt.setText("");
						searchTxt.setText("");

						ClassNameTxt.requestFocus();

					} else {
						JOptionPane.showMessageDialog(null, "Failed to update the record.");
					}

					table_load();

				}
				catch (SQLException e1){

					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());

				}
			}
		});

		btnUpdate.setFont(new Font("Arial", Font.BOLD, 10));
		btnUpdate.setBounds(184, 228, 77, 21);
		CrudOpPanel.add(btnUpdate);

		//DELETE Operation-----------------------------------------------------------------------------------------

		JButton btnDelete = new JButton("DELETE");

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String cID;

				cID = searchTxt.getText();

				// Check if any of the fields are empty
				if (cID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a valid Class ID to delete.");
				} 
				else {

					int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected record?", "Confirmation", JOptionPane.YES_NO_OPTION);

					if (choice == JOptionPane.YES_OPTION) {
						try {
							pst = con.prepareStatement("delete from classes where Class_ID = ?");

							pst.setString(1, cID);

							pst.executeUpdate();

							JOptionPane.showMessageDialog(null, "Record Deleted Successfully!");

							table_load();

							ClassNameTxt.setText("");
							NoSTxt.setText("");
							CRoomNoTxt.setText("");

							ClassTeacherTxt.setText("");
							searchTxt.setText("");

							ClassNameTxt.requestFocus();

						}
						catch (SQLException e1){

							e1.printStackTrace();

						}

					}

				}

			}
		});

		btnDelete.setFont(new Font("Arial", Font.BOLD, 10));
		btnDelete.setBounds(104, 260, 77, 21);
		CrudOpPanel.add(btnDelete);

		//Clear-----------------------------------------------------------------------------------------

		JButton btnClear = new JButton("CLEAR");

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ClassNameTxt.setText("");
				NoSTxt.setText("");
				CRoomNoTxt.setText("");

				ClassTeacherTxt.setText("");
				searchTxt.setText(""); // Clear the search text field

				ClassNameTxt.requestFocus();

			}
		});

		btnClear.setFont(new Font("Arial", Font.BOLD, 10));
		btnClear.setBounds(184, 260, 77, 21);
		CrudOpPanel.add(btnClear);

		//Search Panel

		JPanel SearchPanel = new JPanel();
		SearchPanel.setLayout(null);
		SearchPanel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SearchPanel.setBounds(405, 82, 300, 58);
		add(SearchPanel);

		JLabel lblSearchByClass = new JLabel("Search by Class ID");
		lblSearchByClass.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSearchByClass.setBounds(21, 15, 134, 30);
		SearchPanel.add(lblSearchByClass);

		//SEARCH Operation-----------------------------------------------------------------------------------------

		searchTxt = new JTextField();

		searchTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {

					String cID = searchTxt.getText();

					pst = con.prepareStatement("select Class_Name, No_of_Students, Class_Room_No, Class_Medium, Class_Teacher from classes where Class_ID = ?");

					pst.setString(1, cID);

					ResultSet rs = pst.executeQuery();

					if(rs.next()==true) {
						String cName = rs.getString(1);
						String noS = rs.getString(2);
						String rNo = rs.getString(3);
						String cMedium = rs.getString(4);
						String cTeacher = rs.getString(5);

						ClassNameTxt.setText(cName);
						NoSTxt.setText(noS);
						CRoomNoTxt.setText(rNo);
						ClassMediumTxtChoose.setSelectedItem(cMedium);
						ClassTeacherTxt.setText(cTeacher);

					}
					else {

						ClassNameTxt.setText("");
						NoSTxt.setText("");
						CRoomNoTxt.setText("");

						ClassTeacherTxt.setText("");

						ClassNameTxt.requestFocus();
					}
				}

				catch (SQLException e1){

					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());

				}
			}
		});

		searchTxt.setColumns(10);
		searchTxt.setBounds(154, 21, 134, 19);
		SearchPanel.add(searchTxt);

		//Read Data when clicked on a row --------------------------------------------------------------------

		ClassesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = ClassesTable.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel model = (DefaultTableModel) ClassesTable.getModel();
					String cID = model.getValueAt(selectedRow, 0).toString();
					String cName = model.getValueAt(selectedRow, 1).toString();
					String noStudents = model.getValueAt(selectedRow, 2).toString();
					String noRoom = model.getValueAt(selectedRow, 3).toString();
					String cMedium = model.getValueAt(selectedRow, 4).toString();
					String cTeacher = model.getValueAt(selectedRow, 5).toString();

					// Set the retrieved data into the text fields
					searchTxt.setText(cID);
					ClassNameTxt.setText(cName);
					NoSTxt.setText(noStudents);
					CRoomNoTxt.setText(noRoom);
					ClassMediumTxtChoose.setSelectedItem(cMedium);
					ClassTeacherTxt.setText(cTeacher);

				}
			}
		});

		//Set class image
		JLabel ClassIco = new JLabel("");
		ClassIco.setHorizontalAlignment(SwingConstants.CENTER);
		ClassIco.setBounds(405, 150, 295, 225);
		add(ClassIco);
		setVisible(true);
		ClassIco.setIcon(new ImageIcon(img_class2));

		//Give a title to the table separately
		JLabel lblClassesDatabase = new JLabel("-- Classes Database --");
		lblClassesDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblClassesDatabase.setFont(new Font("Arial", Font.ITALIC, 17));
		lblClassesDatabase.setBounds(258, 409, 197, 38);
		add(lblClassesDatabase);

	}

	//Method to validate class name

	private boolean isValidClassName(String className) {
		// It should be a number between 1-13 followed by a letter between A-H
		String regex = "^(1[0-3]|[1-9])?[A-H]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(className);

		return matcher.matches();
	}

	//Method to validate maximum number of students

	private boolean isStudentCapacityValid(int numStudents) {
		if (numStudents > 50) {
			JOptionPane.showMessageDialog(null, "Maximum Student Capacity is 50");
			return false;
		}
		return true;
	}

	//Method to validate number of students is an integer

	public boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
