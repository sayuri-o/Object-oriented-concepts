package View;

//Import necessary Java libraries
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Controller.SubjectImpl;
import Controller.ISubject;
import Model.Subject;
import Util.dbUtil;

//Define the SubjectsPanel class, which extends JPanel
public class SubjectsPanel extends JPanel {

	// Load image and scale it
	private Image img_subject2 = new ImageIcon(LoginFrame.class.getResource("res/subject2.png")).getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);

	private static final long serialVersionUID = 1L;
	private JTextField SubjectNameTxt;
	private JTextField CreditTxt;
	private JTextField searchTxt;
	private JTextArea SubjectDesTxt;

	private JTable SubjectsTable;

	PreparedStatement pst;
	ResultSet rs;

	private Connection con;

	// Method to load data into the table
	public void table_load(){

		ISubject subjectImpl = new SubjectImpl();
		ArrayList<Subject> subjectList = subjectImpl.getAllSubject();

		// Convert the ArrayList to a DefaultTableModel
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Subject ID");
		model.addColumn("Subject Name");
		model.addColumn("Subject Description");
		model.addColumn("Credit Point");

		for (Subject subject : subjectList) {
			model.addRow(new Object[] {
					subject.getSubject_ID(),
					subject.getSubject_Name(),
					subject.getSubject_Description(),
					subject.getSubject_Credit()
			});
		}

		SubjectsTable.setModel(model);

	}

	/**
	 * Create the panel. (Constructor)
	 */
	public SubjectsPanel() throws ClassNotFoundException, SQLException {
		setBorder(new LineBorder(new Color(30, 144, 255), 2, true));
		setBounds(0,0,735,716);
		setLayout(null);
		setVisible(true);

		SubjectsTable = new JTable();


		try {
			con = dbUtil.getDBConnection(); // Initialize the database connection

			table_load();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: Unable to connect to the database.");
		}

		SubjectsTable = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 458, 677, 225);
		add(scrollPane);

		SubjectsTable = new JTable();
		scrollPane.setViewportView(SubjectsTable);

		//load the table
		table_load();

		// Create and configure a label for the title
		JLabel lblSubjects = new JLabel("SUBJECTS");
		lblSubjects.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubjects.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblSubjects.setBounds(270, 10, 185, 74);
		add(lblSubjects);

		// Create a panel for CRUD operations -----------------------------------------------------------------------------------------------------------

		JPanel CrudOpPanel = new JPanel();
		CrudOpPanel.setLayout(null);
		CrudOpPanel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		CrudOpPanel.setBounds(28, 82, 362, 299);
		add(CrudOpPanel);

		JLabel SubjectNameLabel = new JLabel("Subject Name");
		SubjectNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		SubjectNameLabel.setBounds(33, 25, 96, 30);
		CrudOpPanel.add(SubjectNameLabel);

		JLabel SubjectDesLabel = new JLabel("<html>Subject<br>Description</html>");
		SubjectDesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		SubjectDesLabel.setBounds(33, 92, 85, 42);
		CrudOpPanel.add(SubjectDesLabel);

		//JTextArea
		SubjectDesTxt = new JTextArea();
		SubjectDesTxt.setLineWrap(true); // Enable line wrap
		SubjectDesTxt.setWrapStyleWord(true); // Wrap text at word boundaries
		SubjectDesTxt.setBorder(new LineBorder(new Color(192, 192, 192)));

		SubjectDesTxt.setBounds(128, 70, 196, 86);
		CrudOpPanel.add(SubjectDesTxt);

		SubjectNameTxt = new JTextField();
		SubjectNameTxt.setColumns(10);
		SubjectNameTxt.setBounds(128, 32, 196, 19);
		CrudOpPanel.add(SubjectNameTxt);

		CreditTxt = new JTextField();
		CreditTxt.setColumns(10);
		CreditTxt.setBounds(128, 182, 196, 19);
		CrudOpPanel.add(CreditTxt);

		JLabel CreditsLabel = new JLabel("Credit Point");
		CreditsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		CreditsLabel.setBounds(33, 176, 85, 30);
		CrudOpPanel.add(CreditsLabel);

		// Create a custom cell renderer for the "SubjectDesTxt" column
		TableColumn subjectDesColumn = SubjectsTable.getColumnModel().getColumn(2); // Assuming SubjectDesTxt is the third column

		subjectDesColumn.setCellRenderer(new MultiLineTableCellRenderer());

		//INSERT Operation-----------------------------------------------------------------------------------------

		JButton btnInsert = new JButton("INSERT");

		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Create a single instance of Student Model.
				Subject sub1 = new Subject();

				String sName, sDes, sCredit;

				sName = SubjectNameTxt.getText();
				sDes = SubjectDesTxt.getText();
				sCredit = CreditTxt.getText();

				// Check if any of the fields are empty
				if (sName.isEmpty() || sDes.isEmpty() || sCredit.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter all fields");
					return;
				}

				// Validate credit point using the isValidCredit method
				if (!isValidCredit(sCredit)) {
					JOptionPane.showMessageDialog(null, "Invalid credit point. Please enter a value between 1 and 4.");
					return;
				}

				sub1.setSubject_Name(sName);
				sub1.setSubject_Description(sDes);
				sub1.setSubject_Credit(sCredit);

				try {
					pst = con.prepareStatement("insert into subjects(Subject_Name,Subject_Description,Credit_Point)values(?,?,?)");

					pst.setString(1, sub1.getSubject_Name());
					pst.setString(2, sub1.getSubject_Description());
					pst.setString(3, sub1.getSubject_Credit());

					int rowsInserted = pst.executeUpdate();

					if (rowsInserted > 0) {
						JOptionPane.showMessageDialog(null, "Record Added Successfully!");

						table_load();

						SubjectNameTxt.setText("");
						SubjectDesTxt.setText("");
						CreditTxt.setText("");

						SubjectNameTxt.requestFocus();

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
		btnInsert.setBounds(104, 225, 77, 21);
		CrudOpPanel.add(btnInsert);

		//UPDATE Operation-----------------------------------------------------------------------------------------

		JButton btnUpdate = new JButton("UPDATE");

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Create a single instance of Student Model.
				Subject sub1 = new Subject();

				String subID, sName, sDes, sCredit;

				subID = searchTxt.getText();
				sName = SubjectNameTxt.getText();
				sDes = SubjectDesTxt.getText();
				sCredit = CreditTxt.getText();

				// Check if any of the fields are empty
				if (subID.isEmpty() ||sName.isEmpty() || sDes.isEmpty() || sCredit.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a valid Subject ID to perform an update.");
					return;
				}

				// Validate credit point using the isValidCredit method
				if (!isValidCredit(sCredit)) {
					JOptionPane.showMessageDialog(null, "Invalid credit point. Please enter a value between 1 and 4.");
					return;
				}

				sub1.setSubject_Name(sName);
				sub1.setSubject_Description(sDes);
				sub1.setSubject_Credit(sCredit);
				sub1.setSubject_ID(subID);

				try {
					pst = con.prepareStatement("update subjects set Subject_Name = ?, Subject_Description = ?, Credit_Point = ? where Subject_ID = ?");

					pst.setString(1, sub1.getSubject_Name());
					pst.setString(2, sub1.getSubject_Description());
					pst.setString(3, sub1.getSubject_Credit());
					pst.setString(4, sub1.getSubject_ID());

					int rowsUpdated = pst.executeUpdate();

					if (rowsUpdated > 0) {
						JOptionPane.showMessageDialog(null, "Record Updated Successfully!");

						table_load();

						SubjectNameTxt.setText("");
						SubjectDesTxt.setText("");
						CreditTxt.setText("");

						searchTxt.setText("");

						SubjectNameTxt.requestFocus();

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
		btnUpdate.setBounds(184, 225, 77, 21);
		CrudOpPanel.add(btnUpdate);

		//DELETE Operation-----------------------------------------------------------------------------------------

		JButton btnDelete = new JButton("DELETE");

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String subID;

				subID = searchTxt.getText();

				// Check if any of the fields are empty
				if (subID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a valid Subject ID to delete.");
				} 
				else {

					int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected record?", "Confirmation", JOptionPane.YES_NO_OPTION);

					if (choice == JOptionPane.YES_OPTION) {
						try {
							pst = con.prepareStatement("delete from subjects where Subject_ID = ?");

							pst.setString(1, subID);

							pst.executeUpdate();

							JOptionPane.showMessageDialog(null, "Record Deleted Successfully!");

							table_load();

							SubjectNameTxt.setText("");
							SubjectDesTxt.setText("");
							CreditTxt.setText("");

							searchTxt.setText("");

							SubjectNameTxt.requestFocus();

						}
						catch (SQLException e1){

							e1.printStackTrace();

						}

					}

				}

			}
		});

		btnDelete.setFont(new Font("Arial", Font.BOLD, 10));
		btnDelete.setBounds(104, 256, 77, 21);
		CrudOpPanel.add(btnDelete);

		//Clear----------------------------------------------------------------------------------------------------

		JButton btnClear = new JButton("CLEAR");

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SubjectNameTxt.setText("");
				SubjectDesTxt.setText("");
				CreditTxt.setText("");

				searchTxt.setText("");

				SubjectNameTxt.requestFocus();
			}
		});

		btnClear.setFont(new Font("Arial", Font.BOLD, 10));
		btnClear.setBounds(184, 256, 77, 21);
		CrudOpPanel.add(btnClear);

		JLabel PointValidLabel = new JLabel("*must be a value between 1-4");
		PointValidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		PointValidLabel.setFont(new Font("Tahoma", Font.PLAIN, 8));
		PointValidLabel.setBounds(151, 203, 173, 13);
		CrudOpPanel.add(PointValidLabel);

		// Create a panel for search

		JPanel SearchPanel = new JPanel();
		SearchPanel.setLayout(null);
		SearchPanel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SearchPanel.setBounds(405, 82, 300, 58);
		add(SearchPanel);

		JLabel lblSearchBySubject = new JLabel("Search by Subject ID");
		lblSearchBySubject.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSearchBySubject.setBounds(10, 15, 144, 30);
		SearchPanel.add(lblSearchBySubject);

		//SEARCH Operation-----------------------------------------------------------------------------------------

		searchTxt = new JTextField();

		searchTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {

					String subID = searchTxt.getText();

					pst = con.prepareStatement("select Subject_Name, Subject_Description, Credit_Point from subjects where Subject_ID = ?");

					pst.setString(1, subID);

					ResultSet rs = pst.executeQuery();

					if(rs.next()==true) {
						String subName = rs.getString(1);
						String subDes = rs.getString(2);
						String subCredit = rs.getString(3);

						SubjectNameTxt.setText(subName);
						SubjectDesTxt.setText(subDes);
						CreditTxt.setText(subCredit);

					}
					else {

						SubjectNameTxt.setText("");
						SubjectDesTxt.setText("");
						CreditTxt.setText("");

						SubjectNameTxt.requestFocus();
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

		SubjectsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = SubjectsTable.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel model = (DefaultTableModel) SubjectsTable.getModel();
					String subID = model.getValueAt(selectedRow, 0).toString();
					String subName = model.getValueAt(selectedRow, 1).toString();
					String subDes = model.getValueAt(selectedRow, 2).toString();
					String subCredit = model.getValueAt(selectedRow, 3).toString();

					// Set the retrieved data into the text fields
					searchTxt.setText(subID);
					SubjectNameTxt.setText(subName);
					SubjectDesTxt.setText(subDes);
					CreditTxt.setText(subCredit);

				}
			}
		});

		//Set class image
		JLabel SubjectIco = new JLabel("");
		SubjectIco.setHorizontalAlignment(SwingConstants.CENTER);
		SubjectIco.setBounds(405, 150, 295, 225);
		add(SubjectIco);
		setVisible(true);
		SubjectIco.setIcon(new ImageIcon(img_subject2));

		//Give a title to the table separately
		JLabel lblSubjectsDatabase = new JLabel("-- Subjects Database --");
		lblSubjectsDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubjectsDatabase.setFont(new Font("Arial", Font.ITALIC, 17));
		lblSubjectsDatabase.setBounds(258, 413, 197, 38);
		add(lblSubjectsDatabase);

	}

	// Custom cell renderer for multi-line text
	private class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {

		private static final long serialVersionUID = 1L;

		public MultiLineTableCellRenderer() {
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setText(value != null ? value.toString() : "");
			adjustRowHeight(table, row, column);
			return this;
		}

		private List<Integer> rowHeight = new ArrayList<>(); // Declare rowHeight as an instance variable

		private void adjustRowHeight(JTable table, int row, int column) {
			int cWidth = table.getTableHeader().getColumnModel().getColumn(column).getWidth();
			setSize(new Dimension(cWidth, 1000));
			int preferredHeight = getPreferredSize().height;
			while (rowHeight.size() <= row) {
				rowHeight.add(0);
			}
			rowHeight.set(row, Math.max(rowHeight.get(row), preferredHeight));
			table.setRowHeight(row, rowHeight.get(row));
		}
	}

	// Create a method to validate credit points
	private boolean isValidCredit(String credit) {
		try {
			int creditPoint = Integer.parseInt(credit);
			return creditPoint >= 1 && creditPoint <= 4;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
