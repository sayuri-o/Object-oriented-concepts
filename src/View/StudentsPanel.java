package View;

//Import necessary Java libraries
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Util.dbUtil;
import Controller.IStudent;
import Controller.StudentImpl;
import Model.Student;

//Define the StudentsPanel class, which extends JPanel
public class StudentsPanel extends JPanel {

	// Load image and scale it
	private Image img_student2 = new ImageIcon(LoginFrame.class.getResource("/View/res/student2.png")).getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);

	private static final long serialVersionUID = 1L;

	private JTextField sFnameTxt;
	private JTextField sLnameTxt;
	private JTextField searchtxt;
	private JTextField ContactNoTxt;
	private JTextField ClassTxt;

	private JTable StudentsTable;

	PreparedStatement pst;
	ResultSet rs;

	private Connection con;


	// Method to load data into the table
	public void table_load(){

		IStudent studentImpl = new StudentImpl();
		ArrayList<Student> studentList = studentImpl.getAllStudent();

		// Convert the ArrayList to a DefaultTableModel
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Student ID");
		model.addColumn("First Name");
		model.addColumn("Last Name");
		model.addColumn("Date of Birth");
		model.addColumn("Gender");
		model.addColumn("Contact No");
		model.addColumn("Class Name");

		for (Student student : studentList) {
			model.addRow(new Object[] {
					student.getStudent_ID(),
					student.getFirst_Name(),
					student.getLast_Name(),
					student.getDate_of_Birth(),
					student.getGender(),
					student.getContact_No(),
					student.getClass_Name()
			});
		}

		StudentsTable.setModel(model);

	}

	/*
	 * Create the panel.   (Constructor for the StudentsPanel)
	 */
	public StudentsPanel() throws ClassNotFoundException, SQLException {

		setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
		setBounds(0,0,735,716);
		setLayout(null);
		setVisible(true);

		StudentsTable = new JTable();

		try {
			con = dbUtil.getDBConnection(); // Initialize the database connection

			table_load();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: Unable to connect to the database.");
		}

		JScrollPane scrollPane = new JScrollPane(StudentsTable);
		scrollPane.setBounds(28, 433, 677, 254);
		add(scrollPane);

		//load the table
		table_load();

		// Create and configure a label for the title
		JLabel STUDENTSpanel = new JLabel("STUDENTS");
		STUDENTSpanel.setHorizontalAlignment(SwingConstants.CENTER);
		STUDENTSpanel.setFont(new Font("Tahoma", Font.BOLD, 32));
		STUDENTSpanel.setBounds(270, 10, 185, 74);
		add(STUDENTSpanel);

		// Create a panel for CRUD operations -----------------------------------------------------------------------------------------------------------

		JPanel CrudOpPanel = new JPanel();
		CrudOpPanel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		CrudOpPanel.setBounds(28, 82, 362, 299);
		add(CrudOpPanel);
		CrudOpPanel.setLayout(null);

		JLabel FirstNameLabel = new JLabel("First Name");
		FirstNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		FirstNameLabel.setBounds(33, 25, 85, 30);
		CrudOpPanel.add(FirstNameLabel);

		JLabel LastNameLabel = new JLabel("Last Name");
		LastNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		LastNameLabel.setBounds(33, 63, 85, 30);
		CrudOpPanel.add(LastNameLabel);

		JLabel DoBLabel = new JLabel("Date of Birth");
		DoBLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		DoBLabel.setBounds(33, 103, 85, 30);
		CrudOpPanel.add(DoBLabel);

		JLabel GenderLabel = new JLabel("Gender");
		GenderLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		GenderLabel.setBounds(33, 141, 85, 30);
		CrudOpPanel.add(GenderLabel);

		JLabel NumLabel = new JLabel("Contact No.");
		NumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		NumLabel.setBounds(33, 175, 85, 30);
		CrudOpPanel.add(NumLabel);

		JLabel ClassLabel = new JLabel("Class");
		ClassLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		ClassLabel.setBounds(33, 207, 85, 30);
		CrudOpPanel.add(ClassLabel);

		JLabel cNameValidLabel = new JLabel("*Grade between 1-13 with class name A-H");
		cNameValidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cNameValidLabel.setFont(new Font("Tahoma", Font.PLAIN, 8));
		cNameValidLabel.setBounds(151, 234, 173, 13);
		CrudOpPanel.add(cNameValidLabel);

		sFnameTxt = new JTextField();
		sFnameTxt.setColumns(10);
		sFnameTxt.setBounds(128, 32, 196, 19);
		CrudOpPanel.add(sFnameTxt);

		sLnameTxt = new JTextField();
		sLnameTxt.setColumns(10);
		sLnameTxt.setBounds(128, 70, 196, 19);
		CrudOpPanel.add(sLnameTxt);

		JDateChooser DoBChoose = new JDateChooser();
		DoBChoose.setBounds(128, 109, 196, 19);
		CrudOpPanel.add(DoBChoose);

		JRadioButton MaleChoose = new JRadioButton("Male");
		MaleChoose.setFont(new Font("Arial", Font.PLAIN, 14));
		MaleChoose.setBounds(125, 146, 60, 21);
		CrudOpPanel.add(MaleChoose);

		JRadioButton FemaleChoose = new JRadioButton("Female");
		FemaleChoose.setFont(new Font("Arial", Font.PLAIN, 14));
		FemaleChoose.setBounds(188, 146, 77, 21);
		CrudOpPanel.add(FemaleChoose);

		JRadioButton OtherChoose = new JRadioButton("Other");
		OtherChoose.setFont(new Font("Arial", Font.PLAIN, 14));
		OtherChoose.setBounds(268, 146, 77, 21);
		CrudOpPanel.add(OtherChoose);

		ButtonGroup genderButtonGroup = new ButtonGroup();
		genderButtonGroup.add(MaleChoose);
		genderButtonGroup.add(FemaleChoose);
		genderButtonGroup.add(OtherChoose);

		ContactNoTxt = new JTextField();
		ContactNoTxt.setColumns(10);
		ContactNoTxt.setBounds(128, 182, 196, 19);
		CrudOpPanel.add(ContactNoTxt);

		ClassTxt = new JTextField();
		ClassTxt.setColumns(10);
		ClassTxt.setBounds(128, 214, 196, 19);
		CrudOpPanel.add(ClassTxt);


		//INSERT Operation-----------------------------------------------------------------------------------------

		JButton btnInsert = new JButton("INSERT");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Create a single instance of Student Model.
				Student s1 = new Student();

				String cNo, fName, lName, Gender, DoB, ClassN;

				fName = sFnameTxt.getText();
				lName = sLnameTxt.getText();
				cNo = ContactNoTxt.getText();
				ClassN = ClassTxt.getText();

				if (MaleChoose.isSelected()) {
					Gender = "Male";
				} else if (FemaleChoose.isSelected()) {
					Gender = "Female";
				} else if (OtherChoose.isSelected()) {
					Gender = "Other";
				}else {
					Gender = "N/A";    // Handle the case where no gender is selected.
				}

				// Check if the date is not null before formatting
				if (DoBChoose.getDate() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					DoB = sdf.format(DoBChoose.getDate());
				} else {
					JOptionPane.showMessageDialog(null, "Enter all fields");
					return;
				}

				// Check if any of the fields are empty
				if (fName.isEmpty() || lName.isEmpty() || cNo.isEmpty() || ClassN.isEmpty() || Gender.isEmpty() || DoB.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter all fields");
					return;
				}

				// Validate the class name
				if (!isValidClassName(ClassN)) {
					JOptionPane.showMessageDialog(null, "Enter a valid Class Name (e.g., 1A, 2H, 11C, 13E)");
					return;
				}

				// Validate the contact number
				if (!isContactNumberValid(cNo)) {
					return; // Exit the method if the contact number is invalid
				}

				//set the retrieved data into the s1 object
				s1.setFirst_Name(fName);
				s1.setLast_Name(lName);
				s1.setDate_of_Birth(DoB);
				s1.setGender(Gender);
				s1.setContact_No(cNo);
				s1.setClass_Name(ClassN);

				try {
					pst = con.prepareStatement("insert into students(First_Name,Last_Name,Date_of_Birth,Gender,Contact_No,Class_Name)values(?,?,?,?,?,?)");

					pst.setString(1, s1.getFirst_Name());
					pst.setString(2, s1.getLast_Name());
					pst.setString(3, s1.getDate_of_Birth());
					pst.setString(4, s1.getGender());
					pst.setString(5, s1.getContact_No());
					pst.setString(6, s1.getClass_Name());

					int rowsInserted = pst.executeUpdate();

					if (rowsInserted > 0) {
						JOptionPane.showMessageDialog(null, "Record Added Successfully!");

						table_load();

						sFnameTxt.setText("");
						sLnameTxt.setText("");
						ContactNoTxt.setText("");
						ClassTxt.setText("");

						// Reset radio buttons and date chooser as needed	
						genderButtonGroup.clearSelection();
						DoBChoose.setDate(null);

						sFnameTxt.requestFocus();

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
		btnInsert.setBounds(24, 256, 77, 21);
		CrudOpPanel.add(btnInsert);

		//UPDATE Operation-----------------------------------------------------------------------------------------

		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Create a single instance of Student Model.
				Student s1 = new Student();

				String sID, cNo, fName, lName, Gender, ClassN, formattedDate;

				sID = searchtxt.getText();
				fName = sFnameTxt.getText();
				lName = sLnameTxt.getText();
				cNo = ContactNoTxt.getText();
				ClassN = ClassTxt.getText();

				if (MaleChoose.isSelected()) {
					Gender = "Male";
				} else if (FemaleChoose.isSelected()) {
					Gender = "Female";
				} else if (OtherChoose.isSelected()) {
					Gender = "Other";
				} else {
					Gender = "N/A";
				}

				// Check if the date is not null before formatting
				if (DoBChoose.getDate() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					formattedDate = sdf.format(DoBChoose.getDate());
				} else {
					JOptionPane.showMessageDialog(null, "Please enter a valid Student ID to perform an update.");
					return;
				}

				// Check if any of the fields are empty
				if (sID.isEmpty() || fName.isEmpty() || lName.isEmpty() || cNo.isEmpty() || ClassN.isEmpty() || Gender.isEmpty() || formattedDate.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a valid Student ID to perform an update.");
					return;
				}

				// Validate the class name
				if (!isValidClassName(ClassN)) {
					JOptionPane.showMessageDialog(null, "Enter a valid Class Name (e.g., 1A, 2H, 11C, 13E)");
					return;
				}

				// Validate the contact number
				if (!isContactNumberValid(cNo)) {
					return; // Exit the method if the contact number is invalid
				}

				//set the retrieved data into the s1 object
				s1.setFirst_Name(fName);
				s1.setLast_Name(lName);
				s1.setDate_of_Birth(formattedDate);
				s1.setGender(Gender);
				s1.setContact_No(cNo);
				s1.setClass_Name(ClassN);
				s1.setStudent_ID(sID);

				try {
					pst = con.prepareStatement("update students set First_Name = ?, Last_Name = ?, Date_of_Birth = ?, Gender = ?, Contact_No = ?, Class_Name = ? where Student_ID = ?");

					pst.setString(1, s1.getFirst_Name());
					pst.setString(2, s1.getLast_Name());
					pst.setString(3, s1.getDate_of_Birth());
					pst.setString(4, s1.getGender());
					pst.setString(5, s1.getContact_No());
					pst.setString(6, s1.getClass_Name());
					pst.setString(7, s1.getStudent_ID());

					int rowsUpdated = pst.executeUpdate();

					if (rowsUpdated > 0) {
						JOptionPane.showMessageDialog(null, "Record Updated Successfully!");

						table_load();

						sFnameTxt.setText("");
						sLnameTxt.setText("");
						ContactNoTxt.setText("");
						ClassTxt.setText("");

						// Reset radio buttons and date chooser as needed	
						genderButtonGroup.clearSelection();
						DoBChoose.setDate(null);

						searchtxt.setText(""); // Clear the search text field

						sFnameTxt.requestFocus();

					} else {
						JOptionPane.showMessageDialog(null, "No records were updated.");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}

			}
		});


		btnUpdate.setFont(new Font("Arial", Font.BOLD, 10));
		btnUpdate.setBounds(104, 256, 77, 21);
		CrudOpPanel.add(btnUpdate);

		//DELETE Operation-----------------------------------------------------------------------------------------


		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String sID;

				sID = searchtxt.getText();

				// Check if any of the fields are empty
				if (sID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a valid Student ID to delete.");
				} 
				else {

					int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected record?", "Confirmation", JOptionPane.YES_NO_OPTION);

					if (choice == JOptionPane.YES_OPTION) {
						try {
							pst = con.prepareStatement("delete from students where Student_ID = ?");

							pst.setString(1, sID);

							pst.executeUpdate();

							JOptionPane.showMessageDialog(null, "Record Deleted Successfully!");

							table_load();

							sFnameTxt.setText("");
							sLnameTxt.setText("");
							ContactNoTxt.setText("");
							ClassTxt.setText("");

							// Reset radio buttons and date chooser as needed	
							genderButtonGroup.clearSelection();
							DoBChoose.setDate(null);

							sFnameTxt.requestFocus();

						}
						catch (SQLException e1){

							e1.printStackTrace();

						}

					}

				}
			}
		});
		btnDelete.setFont(new Font("Arial", Font.BOLD, 10));
		btnDelete.setBounds(184, 256, 77, 21);
		CrudOpPanel.add(btnDelete);

		//Clear-----------------------------------------------------------------------------------------

		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				sFnameTxt.setText("");
				sLnameTxt.setText("");
				ContactNoTxt.setText("");
				ClassTxt.setText("");

				// Reset radio buttons and date chooser as needed	
				genderButtonGroup.clearSelection();
				DoBChoose.setDate(null);

				searchtxt.setText("");

				sFnameTxt.requestFocus();

			}
		});
		btnClear.setFont(new Font("Arial", Font.BOLD, 10));
		btnClear.setBounds(264, 256, 77, 21);
		CrudOpPanel.add(btnClear);

		// Create a panel for search -------------------------------------------------------------------

		JPanel SearchPanel = new JPanel();
		SearchPanel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SearchPanel.setBounds(405, 82, 300, 58);
		add(SearchPanel);
		SearchPanel.setLayout(null);

		JLabel lblSearchByStudent = new JLabel("Search by Student ID");
		lblSearchByStudent.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSearchByStudent.setBounds(10, 15, 144, 30);
		SearchPanel.add(lblSearchByStudent);

		//SEARCH Operation-----------------------------------------------------------------------------------------

		searchtxt = new JTextField();
		searchtxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {

					String sID = searchtxt.getText();

					pst = con.prepareStatement("select First_Name,Last_Name,Date_of_Birth,Gender,Contact_No,Class_Name from students where Student_ID = ?");

					pst.setString(1, sID);

					ResultSet rs = pst.executeQuery();

					if(rs.next()==true) {
						String fName = rs.getString(1);
						String lName = rs.getString(2);
						String doB = rs.getString(3);
						String gen = rs.getString(4);
						String cno = rs.getString(5);
						String cls = rs.getString(6);

						sFnameTxt.setText(fName);
						sLnameTxt.setText(lName);
						ContactNoTxt.setText(cno);
						ClassTxt.setText(cls);

						if (gen.equals("Male")) {
							MaleChoose.setSelected(true);
						} else if (gen.equals("Female")) {
							FemaleChoose.setSelected(true);
						} else if (gen.equals("Other")) {
							OtherChoose.setSelected(true);
						}
						DoBChoose.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(doB));

					}
					else {

						sFnameTxt.setText("");
						sLnameTxt.setText("");
						ContactNoTxt.setText("");
						ClassTxt.setText("");

						// Reset radio buttons and date chooser as needed	
						genderButtonGroup.clearSelection();
						DoBChoose.setDate(null);
					}
				}

				catch (SQLException e1){

					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());

				}
				catch (ParseException e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error parsing Date of Birth: " + e2.getMessage());
				}
			}
		});

		searchtxt.setColumns(10);
		searchtxt.setBounds(154, 21, 134, 19);
		SearchPanel.add(searchtxt);

		//Read Data when clicked on a row --------------------------------------------------------------------

		StudentsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = StudentsTable.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel model = (DefaultTableModel) StudentsTable.getModel();
					String sID = model.getValueAt(selectedRow, 0).toString();
					String firstName = model.getValueAt(selectedRow, 1).toString();
					String lastName = model.getValueAt(selectedRow, 2).toString();
					String dateOfBirth = model.getValueAt(selectedRow, 3).toString();
					String gender = model.getValueAt(selectedRow, 4).toString();
					String contactNo = model.getValueAt(selectedRow, 5).toString();
					String className = model.getValueAt(selectedRow, 6).toString();

					// Set the retrieved data into the text fields
					searchtxt.setText(sID);
					sFnameTxt.setText(firstName);
					sLnameTxt.setText(lastName);
					ContactNoTxt.setText(contactNo);
					ClassTxt.setText(className);

					if (gender.equals("Male")) {
						MaleChoose.setSelected(true);
					} else if (gender.equals("Female")) {
						FemaleChoose.setSelected(true);
					} else if (gender.equals("Other")) {
						OtherChoose.setSelected(true);
					}
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date dob = sdf.parse(dateOfBirth);
						DoBChoose.setDate(dob);
					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				}
			}
		});


		//Set student image
		JLabel StudentIco = new JLabel("");
		StudentIco.setHorizontalAlignment(SwingConstants.CENTER);
		StudentIco.setBounds(410, 150, 295, 225);
		add(StudentIco);
		StudentIco.setIcon(new ImageIcon(img_student2));

		//Give a title to the table separately
		JLabel lblTableTopic = new JLabel("-- Students Database --");
		lblTableTopic.setFont(new Font("Arial", Font.ITALIC, 17));
		lblTableTopic.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableTopic.setBounds(258, 388, 197, 38);
		add(lblTableTopic);

	}

	//Method to validate the contact number
	private boolean isContactNumberValid(String contactNumber) {
		if (contactNumber.length() != 10 || !contactNumber.startsWith("0")) {
			JOptionPane.showMessageDialog(null, "Invalid Contact Number");
			return false;
		}
		return true;
	}

	//Method to validate class name
	private boolean isValidClassName(String className) {
		// It should be a number between 1-13 followed by a letter between A-H
		String regex = "^(1[0-3]|[1-9])?[A-H]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(className);

		return matcher.matches();
	}
}


