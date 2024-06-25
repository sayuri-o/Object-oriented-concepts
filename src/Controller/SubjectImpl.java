package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Subject;
import Util.dbUtil;

public class SubjectImpl implements ISubject{

	public SubjectImpl() {

	}

	public ArrayList<Subject> getAllSubject() {

		ArrayList<Subject> subjectList = new ArrayList<>();

		PreparedStatement pst;
		ResultSet rs;

		Connection con;
		try {
			con = dbUtil.getDBConnection();
			try {
				pst = con.prepareStatement("select * from subjects");
				rs = pst.executeQuery();

				while(rs.next()) {

					Subject subject = new Subject();

					subject.setSubject_ID(rs.getString(1));
					subject.setSubject_Name(rs.getString(2));
					subject.setSubject_Description(rs.getString(3));
					subject.setSubject_Credit(rs.getString(4));

					subjectList.add(subject);

				}

			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return subjectList;
	}

}
