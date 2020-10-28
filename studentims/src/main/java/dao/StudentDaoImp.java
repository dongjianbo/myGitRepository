package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySqlDataSource;
import table.Student;
import utils.Pagenation;

public class StudentDaoImp implements StudentDao{

	@Override
	public int createStudent(Student st) {
		String sql="insert into student values(null,?,?,?,?,?,?,100,1)";
		String sql1="select max(sid) from student";
		Connection con=MySqlDataSource.getCon();
		
		int sid=-1;
		try {
			con.setAutoCommit(false);
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, st.getBid());
			ps.setString(2, st.getName());
			ps.setString(3,st.getSex());
			ps.setString(4, st.getJiguan());
			ps.setString(5,st.getSchool());
			ps.setString(6, st.getTel());
			int i=ps.executeUpdate();
			if(i==1){
				ps=con.prepareStatement(sql1);
				ResultSet res=ps.executeQuery();
				if(res.next()){
					sid=res.getInt(1);
				}
			}
			con.commit();
			ps.close();
			return sid;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int updateStudent(Student st) {
		String sql="update student set sname=?,ssex=?,jiguan=?,biyexuexiao=?,tel=? where sid=?";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, st.getName());
			ps.setString(2,st.getSex());
			ps.setString(3, st.getJiguan());
			ps.setString(4,st.getSchool());
			ps.setString(5, st.getTel());
			ps.setInt(6, st.getSid());
			int i=ps.executeUpdate();
			ps.close();
			
			return i;
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public Student getStudentById(int sid) {
		String sql="select s.*,bj.cname,a.uname from student s left join banji bj on s.bjid=bj.id left join account a on s.sid=a.sid where s.sid="+sid;
		Connection con=MySqlDataSource.getCon();
		try {
			ResultSet res=con.createStatement().executeQuery(sql);
			res.next();
			Student st=new Student();
			st.setSid(res.getInt(1));
			st.setBid(res.getInt(2));
			st.setName(res.getString(3));
			st.setSex(res.getString(4));
			st.setJiguan(res.getString(5));
			st.setSchool(res.getString(6));
			st.setTel(res.getString(7));
			st.setScore(res.getInt(8));
			st.setState(res.getInt(9));
			st.setCname(res.getString(10));
			st.setUname(res.getString(11));
			res.close();
			con.close();
			return st;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int exitStudent(int sid) {
		String sql="update student set state=0 where sid="+sid;
		int i=MySqlDataSource.executeUpdate(sql);
		return i;
	}

	@Override
	public List<Student> studentList() {
		List<Student> stlist=new ArrayList<>();
		String sql="SELECT s.*,bj.cname,a.uname "
				+ "from student s left join banji bj on s.bjid=bj.id left join account a on s.sid=a.sid "
				+ "order by bj.cname desc,s.sname asc;";
		Connection con=MySqlDataSource.getCon();
		
		try {
			ResultSet res=con.createStatement().executeQuery(sql);
			while(res.next()){
				Student st=new Student();
				st.setSid(res.getInt(1));
				st.setBid(res.getInt(2));
				st.setName(res.getString(3));
				st.setSex(res.getString(4));
				st.setJiguan(res.getString(5));
				st.setSchool(res.getString(6));
				st.setTel(res.getString(7));
				st.setScore(res.getInt(8));
				st.setState(res.getInt(9));
				st.setCname(res.getString(10));
				st.setUname(res.getString(11));
				stlist.add(st);
			}
			res.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stlist;
	}
	@Override
	public List<Student> studentList(String key,Pagenation p) {
		
		List<Student> stlist=new ArrayList<>();
		String sql="SELECT s.*,bj.cname,a.uname from student s left join banji bj on s.bjid=bj.id left join account a on s.sid=a.sid "
				+ "where sname like ? or bj.cname like ? or jiguan like ? or ssex like ? "+
				"limit "+(p.getPage()-1)*p.getRows()+","+p.getRows();
		//需要获取当前key条件下一共有多少条?
		String sql_count="SELECT count(*) from student s left join banji bj on s.bjid=bj.id left join account a on s.sid=a.sid "
				+ "where sname like ? or bj.cname like ? or jiguan like ? or ssex like ?";
		
		try {
			Connection con=MySqlDataSource.getCon();
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, "%"+key+"%");
			ps.setString(2, "%"+key+"%");
			ps.setString(3, "%"+key+"%");
			ps.setString(4, "%"+key+"%");
			ResultSet res=ps.executeQuery();
			
			while(res.next()){
				Student st=new Student();
				st.setSid(res.getInt(1));
				st.setBid(res.getInt(2));
				st.setName(res.getString(3));
				st.setSex(res.getString(4));
				st.setJiguan(res.getString(5));
				st.setSchool(res.getString(6));
				st.setTel(res.getString(7));
				st.setScore(res.getInt(8));
				st.setState(res.getInt(9));
				st.setCname(res.getString(10));
				st.setUname(res.getString(11));
				stlist.add(st);
			}
			res.close();
			PreparedStatement ps_count=con.prepareStatement(sql_count);
			ps_count.setString(1, "%"+key+"%");
			ps_count.setString(2, "%"+key+"%");
			ps_count.setString(3, "%"+key+"%");
			ps_count.setString(4, "%"+key+"%");
			ResultSet res_count=ps_count.executeQuery();
			if(res_count.next()){
				int total=res_count.getInt(1);
				p.setTotal(total);//总条数
				p.setPages((total+p.getRows()-1)/p.getRows());//总页数
			}
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stlist;
	}
	
	@Override
	public List<Student> studentUpdate(String key) {
		List<Student> stlist=new ArrayList<>();
		String sql="SELECT s.*,bj.cname from student s left join banji bj on s.bjid=bj.id "
				+ "where sid=? or sname like ?";
		try {
			Connection con=MySqlDataSource.getCon();
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, key);
			ps.setString(2, "%"+key+"%");
			ResultSet res=ps.executeQuery();
			while(res.next()){
				Student st=new Student();
				st.setSid(res.getInt(1));
				st.setBid(res.getInt(2));
				st.setName(res.getString(3));
				st.setSex(res.getString(4));
				st.setJiguan(res.getString(5));
				st.setSchool(res.getString(6));
				st.setTel(res.getString(7));
				st.setScore(res.getInt(8));
				st.setState(res.getInt(9));
				st.setCname(res.getString(10));
				stlist.add(st);
			}
			res.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stlist;
	}

	
	@Override
	public boolean exists(String sname) {
		try {
			String sql="SELECT 1 from student where sname=?";
			Connection con=MySqlDataSource.getCon();
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, sname);
			ResultSet res=ps.executeQuery();
			boolean b=res.next();
			res.close();
			ps.close();
			con.close();
			return b;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public int changeBanJi(int sid, int bjid) {
		String sql="update student set bjid=? where sid=?";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, bjid);
			ps.setInt(2, sid);
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

	
}
