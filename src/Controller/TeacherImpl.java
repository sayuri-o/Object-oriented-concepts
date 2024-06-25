package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Teacher;
import Util.dbUtil;

public class TeacherImpl implements ITeacher{

	public TeacherImpl() {

	}

	public ArrayList<Teacher> getAllTeacher() {

		ArrayList<Teacher> teacherList = new ArrayList<>();

		PreparedStatement pst;
		ResultSet rs;

		Connection con;
		try {
			con = dbUtil.getDBConnection();
			try {
				pst = con.prepareStatement("select * from teachers");
				rs = pst.executeQuery();

				while(rs.next()) {

					Teacher teacher = new Teacher();

					teacher.setTeacher_ID(rs.getString(1));
					teacher.setFirst_Name(rs.getString(2));
					teacher.setLast_Name(rs.getString(3));
					teacher.setDate_of_Birth(rs.getString(4));
					teacher.setGender(rs.getString(5));
					teacher.setContact_No(rs.getString(6));
					teacher.setSubject(rs.getString(7));
					teacher.setClass_Name(rs.getString(8));

					teacherList.add(teacher);

				}

			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return teacherList;
	}

}
