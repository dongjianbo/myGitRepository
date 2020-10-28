package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySqlConnection;
import database.MySqlDataSource;
import table.Course;

public class CourseDaoImp implements CourseDao {

	@Override
	public List<Course> getCourse() {
		String sql="select * from course";
		Connection con=MySqlDataSource.getCon();
		List<Course> bjlist=new ArrayList<>();
		try {
			ResultSet res=con.createStatement().executeQuery(sql);
			while(res.next()){
				Course bj=new Course();
				bj.setId(res.getInt(1));
				bj.setCname(res.getString(2));
				bj.setCinfo(res.getString(3));
				bjlist.add(bj);
			}
			res.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bjlist;
	}

	@Override
	public int insertCourse(Course c) {
		String sql="insert into course values(null,?,?)";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, c.getCname());
			ps.setString(2, c.getCinfo());
			int i=ps.executeUpdate();
			ps.close();
			con.close();
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int updateCourse(Course c) {
		String sql="update course set cname=?,cinfo=? where id=?";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, c.getCname());
			ps.setString(2, c.getCinfo());
			ps.setInt(3, c.getId());
			int i=ps.executeUpdate();
			ps.close();
			con.close();
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteCourse(int cid) {
		String sql="select 1 from score where cid=?";
		String sql_del="delete from course where id=?";
		Connection con=MySqlDataSource.getCon();
		int i=-1;
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, cid);
			ResultSet res=ps.executeQuery();
			if(res.next()){
				i=0;
			}else{
				ps=con.prepareStatement(sql_del);
				ps.setInt(1, cid);
				i=ps.executeUpdate();
			}
			ps.close();
			con.close();
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
}
