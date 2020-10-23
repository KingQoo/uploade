package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Upload;

public class UploadDAO extends DAO {

	public List<Upload> search(String filename) throws Exception {
		List<Upload> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select * from upload where filename like ?");
		st.setString(1, "%"+filename+"%");
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			Upload p=new Upload();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setFilename(rs.getString("filename"));
			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}

	public int insert(String filename, String name) throws Exception {
		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"insert into upload values(null, ?, ?)");
		st.setString(1, name);
		st.setString(2, filename);
		int line=st.executeUpdate();

		st.close();
		con.close();
		return line;
	}
}
