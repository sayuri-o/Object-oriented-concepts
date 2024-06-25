package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Util.dbUtil;

import Model.ClassM;

public class ClassImpl implements IClass{
	
	public ClassImpl() {

	}
	
	public ArrayList<ClassM> getAllClass() {

		ArrayList<ClassM> classList = new ArrayList<>();

		PreparedStatement pst;
		ResultSet rs;

		Connection con;
		try {
			con = dbUtil.getDBConnection();
			try {
				pst = con.prepareStatement("select * from classes");
				rs = pst.executeQuery();

				while(rs.next()) {

					ClassM classM = new ClassM();

					classM.setClass_ID(rs.getString(1));
					classM.setClass_Name(rs.getString(2));
					classM.setNo_Students(rs.getString(3));
					classM.setC_Room_No(rs.getString(4));
					classM.setC_Medium(rs.getString(5));
					classM.setC_Teacher(rs.getString(6));
					
					classList.add(classM);

				}

			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return classList;
	}

}
