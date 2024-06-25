package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import Model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Util.dbUtil;


public class StudentImpl implements IStudent { 

	public StudentImpl() {

	}


	public ArrayList<Student> getAllStudent() {

		ArrayList<Student> studentList = new ArrayList<>();

		PreparedStatement pst;
		ResultSet rs;

		Connection con;
		try {
			con = dbUtil.getDBConnection();
			try {
				pst = con.prepareStatement("select * from students");
				rs = pst.executeQuery();

				while(rs.next()) {

					Student student = new Student();

					student.setStudent_ID(rs.getString(1));
					student.setFirst_Name(rs.getString(2));
					student.setLast_Name(rs.getString(3));
					student.setDate_of_Birth(rs.getString(4));
					student.setGender(rs.getString(5));
					student.setContact_No(rs.getString(6));
					student.setClass_Name(rs.getString(7));
					
					studentList.add(student);

				}

			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return studentList;
	}

}
