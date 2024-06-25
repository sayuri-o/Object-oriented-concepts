package View;

//Import necessary Java libraries
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
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
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import com.toedter.calendar.JDateChooser;

import Controller.TeacherImpl;
import Controller.ITeacher;
import Model.Teacher;
import Util.dbUtil;

//Define the TeachersPanel class, which extends JPanel
public class TeachersPanel extends JPanel {

	// Load image and scale it
	private Image img_teacher2 = new ImageIcon(LoginFrame.class.getResource("/View/res/teacher2.png")).getImage().getScaledInstance(220,220,Image.SCALE_SMOOTH);

	private static final long serialVersionUID = 1L;
	private JTextField tFnameTxt;
	private JTextField tLnameTxt;
	private JTextField ContactNoTxt;
	private JTextField searchTxt;
	private JTextField SubjectTxt;
	private JTextField ClassTxt;

	private JTable TeachersTable;

	PreparedStatement pst;
	ResultSet rs;

	private Connection con;


	// Method to load data into the table
	public void table_load(){

		ITeacher teacherImpl = new TeacherImpl();
		ArrayList<Teacher> teacherList = teacherImpl.getAllTeacher();

		// Convert the ArrayList to a DefaultTableModel
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Teacher ID");
		model.addColumn("First Name");
		model.addColumn("Last Name");
		model.addColumn("Date of Birth");
		model.addColumn("Gender");
		model.addColumn("Contact No");
		model.addColumn("Subject Name");
		model.addColumn("Class Name");

		for (Teacher teacher : teacherList) {
			model.addRow(new Object[] {
					teacher.getTeacher_ID(),
					teacher.getFirst_Name(),
					teacher.getLast_Name(),
					teacher.getDate_of_Birth(),
					teacher.getGender(),
					teacher.getContact_No(),
					teacher.getSubject(),
					teacher.getClass_Name()
			});
		}

		TeachersTable.setModel(model);

	}

	/*
	 * Create the panel.   (Constructor for the TeachersPanel)
	 */
	public TeachersPanel() throws ClassNotFoundException, SQLException {

		setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
		setBounds(0,0,735,716);
		setLayout(null);
		setVisible(true);

		TeachersTable = new JTable();

		try {
			con = dbUtil.getDBConnection(); // Initialize the database connection

			table_load();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: Unable to connect to the database.");
		}

		TeachersTable = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 462, 677, 225);
		add(scrollPane);

		TeachersTable = new JTable();
		scrollPane.setViewportView(TeachersTable);

		//load the table
		table_load();

		// Create and configure a label for the title
		JLabel lblTeachers = new JLabel("TEACHERS");
		lblTeachers.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeachers.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTeachers.setBounds(270, 10, 185, 74);
		add(lblTeachers);

		// Create a panel for CRUD operations -----------------------------------------------------------------------------------------------------------

		JPanel CrudOpPanel = new JPanel();
		CrudOpPanel.setLayout(null);
		CrudOpPanel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		CrudOpPanel.setBounds(28, 82, 362, 325);
		add(CrudOpPanel);

		JLabel FirstNameLabel = new JLabel("First Name");
		FirstNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		FirstNameLabel.setBounds(33, 25, 85, 30);
		CrudOpPanel.add(FirstNameLabel);

		JLabel LastNameLabel = new JLabel("Last Name");
		LastNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		LastNameLabel.setBounds(33, 61, 85, 30);
		CrudOpPanel.add(LastNameLabel);

		JLabel DoBLabel = new JLabel("Date of Birth");
		DoBLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		DoBLabel.setBounds(33, 99, 85, 30);
		CrudOpPanel.add(DoBLabel);

		JLabel GenderLabel = new JLabel("Gender");
		GenderLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		GenderLabel.setBounds(33, 137, 85, 30);
		CrudOpPanel.add(GenderLabel);

		JLabel NumTxt = new JLabel("Contact No.");
		NumTxt.setFont(new Font("Arial", Font.PLAIN, 14));
		NumTxt.setBounds(33, 175, 85, 30);
		CrudOpPanel.add(NumTxt);

		//----------------------------------------------------------------------------------------------

		JLabel lblSubject = new JLabel("Subject");
		lblSubject.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSubject.setBounds(33, 207, 85, 30);
		CrudOpPanel.add(lblSubject);

		JLabel lblClass = new JLabel("Class");
		lblClass.setFont(new Font("Arial", Font.PLAIN, 14));
		lblClass.setBounds(33, 238, 85, 30);
		CrudOpPanel.add(lblClass);

		tFnameTxt = new JTextField();
		tFnameTxt.setColumns(10);
		tFnameTxt.setBounds(128, 32, 196, 19);
		CrudOpPanel.add(tFnameTxt);

		tLnameTxt = new JTextField();
		tLnameTxt.setColumns(10);
		tLnameTxt.setBounds(128, 68, 196, 19);
		CrudOpPanel.add(tLnameTxt);

		ContactNoTxt = new JTextField();
		ContactNoTxt.setColumns(10);
		ContactNoTxt.setBounds(128, 179, 196, 19);
		CrudOpPanel.add(ContactNoTxt);

		JRadioButton MaleChoose = new JRadioButton("Male");
		MaleChoose.setFont(new Font("Arial", Font.PLAIN, 14));
		MaleChoose.setBounds(125, 142, 60, 21);
		CrudOpPanel.add(MaleChoose);

		JRadioButton FemaleChoose = new JRadioButton("Female");
		FemaleChoose.setFont(new Font("Arial", Font.PLAIN, 14));
		FemaleChoose.setBounds(188, 142, 77, 21);
		CrudOpPanel.add(FemaleChoose);

		JRadioButton OtherChoose = new JRadioButton("Other");
		OtherChoose.setFont(new Font("Arial", Font.PLAIN, 14));
		OtherChoose.setBounds(268, 142, 77, 21);
		CrudOpPanel.add(OtherChoose);

		ButtonGroup genderButtonGroup = new ButtonGroup();
		genderButtonGroup.add(MaleChoose);
		genderButtonGroup.add(FemaleChoose);
		genderButtonGroup.add(OtherChoose);

		JDateChooser DoBChoose = new JDateChooser();
		DoBChoose.setBounds(128, 105, 196, 19);
		CrudOpPanel.add(DoBChoose);

		//INSERT Operation-----------------------------------------------------------------------------------------

		JButton btnInsert = new JButton("INSERT");


		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Create a single instance (Object) of Student Model.
				Teacher t1 = new Teacher();

				String tNo, fName, lName, Gender, DoB, SubjectN, ClassN;

				fName = tFnameTxt.getText();
				lName = tLnameTxt.getText();
				tNo = ContactNoTxt.getText();
				ClassN = ClassTxt.getText();
				SubjectN = SubjectTxt.getText();

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
				if (fName.isEmpty() || lName.isEmpty() || tNo.isEmpty() || ClassN.isEmpty() || Gender.isEmpty() || DoB.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter all fields");
					return;
				}

				// Validate the class name
				if (!isValidClassName(ClassN)) {
					JOptionPane.showMessageDialog(null, "Enter a valid Class Name (e.g., 1A, 2H, 11C, 13E)");
					return;
				}

				// Validate the contact number
				if (!isContactNumberValid(tNo)) {
					return; // Exit the method if the contact number is invalid
				}

				//set the retrieved data into the s1 object
				t1.setFirst_Name(fName);
				t1.setLast_Name(lName);
				t1.setDate_of_Birth(DoB);
				t1.setGender(Gender);
				t1.setContact_No(tNo);
				t1.setClass_Name(ClassN);
				t1.setSubject(SubjectN);

				try {
					pst = con.prepareStatement("insert into teachers(First_Name,Last_Name,Date_of_Birth,Gender,Contact_No,Subject,Class_Name)values(?,?,?,?,?,?,?)");

					pst.setString(1, t1.getFirst_Name());
					pst.setString(2, t1.getLast_Name());
					pst.setString(3, t1.getDate_of_Birth());
					pst.setString(4, t1.getGender());
					pst.setString(5, t1.getContact_No());
					pst.setString(6, t1.getSubject());
					pst.setString(7, t1.getClass_Name());

					int rowsInserted = pst.executeUpdate();

					if (rowsInserted > 0) {
						JOptionPane.showMessageDialog(null, "Record Added Successfully!");

						table_load();

						tFnameTxt.setText("");
						tLnameTxt.setText("");
						ContactNoTxt.setText("");
						ClassTxt.setText("");
						SubjectTxt.setText("");

						// Reset radio buttons and date chooser as needed	
						genderButtonGroup.clearSelection();
						DoBChoose.setDate(null);

						tFnameTxt.requestFocus();

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
		btnInsert.setBounds(24, 284, 77, 21);
		CrudOpPanel.add(btnInsert);

		//UPDATE Operation-----------------------------------------------------------------------------------------

		JButton btnUpdate = new JButton("UPDATE");

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Create a single instance of Student Model.
				Teacher t1 = new Teacher();

				String tID, tNo, fName, lName, Gender, SubjectN, ClassN, formattedDate;

				tID = searchTxt.getText();
				fName = tFnameTxt.getText();
				lName = tLnameTxt.getText();
				tNo = ContactNoTxt.getText();
				ClassN = ClassTxt.getText();
				SubjectN = SubjectTxt.getText();

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
					formattedDate = sdf.format(DoBChoose.getDate());
				} else {
					JOptionPane.showMessageDialog(null, "Please enter a valid Teacher ID to perform an update.");
					return;
				}

				// Check if any of the fields are empty
				if (tID.isEmpty() || fName.isEmpty() || lName.isEmpty() || tNo.isEmpty() || ClassN.isEmpty() || Gender.isEmpty() || formattedDate.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a valid Teacher ID to perform an update.");
					return;
				}

				// Validate the class name
				if (!isValidClassName(ClassN)) {
					JOptionPane.showMessageDialog(null, "Enter a valid Class Name (e.g., 1A, 2H, 11C, 13E)");
					return;
				}

				// Validate the contact number
				if (!isContactNumberValid(tNo)) {
					return; // Exit the method if the contact number is invalid
				}

				//set the retrieved data into the s1 object
				t1.setFirst_Name(fName);
				t1.setLast_Name(lName);
				t1.setDate_of_Birth(formattedDate);
				t1.setGender(Gender);
				t1.setContact_No(tNo);
				t1.setClass_Name(ClassN);
				t1.setSubject(SubjectN);
				t1.setTeacher_ID(tID);


				try {
					pst = con.prepareStatement("update teachers set First_Name = ?, Last_Name = ?, Date_of_Birth = ?, Gender = ?, Contact_No = ?, Subject = ?, Class_Name = ? where Teacher_ID = ?");

					pst.setString(1, t1.getFirst_Name());
					pst.setString(2, t1.getLast_Name());
					pst.setString(3, t1.getDate_of_Birth());
					pst.setString(4, t1.getGender());
					pst.setString(5, t1.getContact_No());
					pst.setString(6, t1.getSubject());
					pst.setString(7, t1.getClass_Name());
					pst.setString(8, t1.getTeacher_ID());

					int rowsUpdated = pst.executeUpdate();

					if (rowsUpdated > 0) {
						JOptionPane.showMessageDialog(null, "Record Updated Successfully!");

						table_load();

						tFnameTxt.setText("");
						tLnameTxt.setText("");
						ContactNoTxt.setText("");
						ClassTxt.setText("");
						SubjectTxt.setText("");

						// Reset radio buttons and date chooser as needed	
						genderButtonGroup.clearSelection();
						DoBChoose.setDate(null);

						searchTxt.setText(""); // Clear the search text field

						tFnameTxt.requestFocus();

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
		btnUpdate.setBounds(104, 284, 77, 21);
		CrudOpPanel.add(btnUpdate);

		//DELETE Operation-----------------------------------------------------------------------------------------

		JButton btnDelete = new JButton("DELETE");

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String tID;

				tID = searchTxt.getText();

				// Check if any of the fields are empty
				if (tID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a valid Teacher ID to delete.");
				} 
				else {

					int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected record?", "Confirmation", JOptionPane.YES_NO_OPTION);

					if (choice == JOptionPane.YES_OPTION) {
						try {
							pst = con.prepareStatement("delete from teachers where Teacher_ID = ?");

							pst.setString(1, tID);

							pst.executeUpdate();

							JOptionPane.showMessageDialog(null, "Record Deleted Successfully!");

							table_load();

							tFnameTxt.setText("");
							tLnameTxt.setText("");
							ContactNoTxt.setText("");
							ClassTxt.setText("");
							SubjectTxt.setText("");

							// Reset radio buttons and date chooser as needed	
							genderButtonGroup.clearSelection();
							DoBChoose.setDate(null);

							searchTxt.setText(""); // Clear the search text field

							tFnameTxt.requestFocus();

						}
						catch (SQLException e1){

							e1.printStackTrace();

						}

					}

				}

			}
		});

		btnDelete.setFont(new Font("Arial", Font.BOLD, 10));
		btnDelete.setBounds(184, 284, 77, 21);
		CrudOpPanel.add(btnDelete);

		//Clear-----------------------------------------------------------------------------------------

		JButton btnClear = new JButton("CLEAR");

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tFnameTxt.setText("");
				tLnameTxt.setText("");
				ContactNoTxt.setText("");
				ClassTxt.setText("");
				SubjectTxt.setText("");

				// Reset radio buttons and date chooser as needed	
				genderButtonGroup.clearSelection();
				DoBChoose.setDate(null);

				searchTxt.setText(""); // Clear the search text field

				tFnameTxt.requestFocus();

			}
		});

		btnClear.setFont(new Font("Arial", Font.BOLD, 10));
		btnClear.setBounds(264, 284, 77, 21);
		CrudOpPanel.add(btnClear);

		SubjectTxt = new JTextField();
		SubjectTxt.setColumns(10);
		SubjectTxt.setBounds(128, 211, 196, 19);
		CrudOpPanel.add(SubjectTxt);

		ClassTxt = new JTextField();
		ClassTxt.setColumns(10);
		ClassTxt.setBounds(128, 242, 196, 19);
		CrudOpPanel.add(ClassTxt);

		JLabel cNameValidLabel = new JLabel("*Grade between 1-13 with class name A-H");
		cNameValidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cNameValidLabel.setFont(new Font("Tahoma", Font.PLAIN, 8));
		cNameValidLabel.setBounds(151, 262, 173, 13);
		CrudOpPanel.add(cNameValidLabel);

		// Create a panel for search

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(405, 82, 300, 58);
		add(panel);

		JLabel lblSearchByTeacher = new JLabel("Search by Teacher ID");
		lblSearchByTeacher.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSearchByTeacher.setBounds(10, 15, 144, 30);
		panel.add(lblSearchByTeacher);

		//SEARCH Operation-----------------------------------------------------------------------------------------

		searchTxt = new JTextField();

		searchTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {

					String tID = searchTxt.getText();

					pst = con.prepareStatement("select First_Name,Last_Name,Date_of_Birth,Gender,Contact_No,Subject,Class_Name from teachers where Teacher_ID = ?");

					pst.setString(1, tID);

					ResultSet rs = pst.executeQuery();

					if(rs.next()==true) {
						String fName = rs.getString(1);
						String lName = rs.getString(2);
						String doB = rs.getString(3);
						String gen = rs.getString(4);
						String cno = rs.getString(5);
						String sub = rs.getString(6);
						String cls = rs.getString(7);

						tFnameTxt.setText(fName);
						tLnameTxt.setText(lName);
						ContactNoTxt.setText(cno);
						SubjectTxt.setText(sub);
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

						tFnameTxt.setText("");
						tLnameTxt.setText("");
						ContactNoTxt.setText("");
						SubjectTxt.setText("");
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

		searchTxt.setColumns(10);
		searchTxt.setBounds(154, 21, 134, 19);
		panel.add(searchTxt);

		//Read Data when clicked on a row --------------------------------------------------------------------

		TeachersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = TeachersTable.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel model = (DefaultTableModel) TeachersTable.getModel();
					String tID = model.getValueAt(selectedRow, 0).toString();
					String firstName = model.getValueAt(selectedRow, 1).toString();
					String lastName = model.getValueAt(selectedRow, 2).toString();
					String dateOfBirth = model.getValueAt(selectedRow, 3).toString();
					String gender = model.getValueAt(selectedRow, 4).toString();
					String contactNo = model.getValueAt(selectedRow, 5).toString();
					String subjectName = model.getValueAt(selectedRow, 6).toString();
					String className = model.getValueAt(selectedRow, 7).toString();

					// Set the retrieved data into the text fields
					searchTxt.setText(tID);
					tFnameTxt.setText(firstName);
					tLnameTxt.setText(lastName);
					ContactNoTxt.setText(contactNo);
					SubjectTxt.setText(subjectName);
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
		JLabel TeachersIco = new JLabel("");
		TeachersIco.setHorizontalAlignment(SwingConstants.CENTER);
		TeachersIco.setBounds(405, 150, 295, 225);
		add(TeachersIco);
		setVisible(true);
		TeachersIco.setIcon(new ImageIcon(img_teacher2));

		//Give a title to the table separately
		JLabel lblTeachersDatabase = new JLabel("-- Teachers Database --");
		lblTeachersDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeachersDatabase.setFont(new Font("Arial", Font.ITALIC, 17));
		lblTeachersDatabase.setBounds(258, 417, 197, 38);
		add(lblTeachersDatabase);

	}

	//Method to validate contact number - validation
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
