package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySqlDataSource;
import table.Student;
import table.WeiJi;
import utils.Pagenation;

public class WeiJiDaoImp implements WeiJiDao {

	@Override
	public int createWeiJi(WeiJi wj) {
		String sql="insert into weiji values(null,?,?,?,?,?,?,?)";
		String sql1="update student set kaohefenshu=kaohefenshu-"+wj.getKoufen()+" where sid=?";
		Connection con=MySqlDataSource.getCon();
		try {
			//不自动提交
			con.setAutoCommit(false);
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, wj.getSid());
			ps.setString(2, wj.getWjtime());
			ps.setString(3, wj.getWjyuanyin());
			ps.setInt(4, wj.getKoufen());
			ps.setInt(5, 1);
			ps.setString(6,wj.getInfo());
			ps.setString(7, wj.getChufaren());
			int i=ps.executeUpdate();
			if(i==1){
				ps=con.prepareStatement(sql1);
				ps.setInt(1, wj.getSid());
				i=ps.executeUpdate();
				if(i==1){
					//手动提交
					con.commit();
					ps.close();
					return 1;
				}else{
					con.rollback();
				}
			}else{
				//回滚事务
				con.rollback();
			}
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
		return -1;
	}
	public List<WeiJi> wjList(int sid){
		List<WeiJi> wjlist=new ArrayList<>();
		String sql="select * from weiji where sid=? order by wjtime desc";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, sid);
			ResultSet res=ps.executeQuery();
			while(res.next()){
				WeiJi wj=new WeiJi();
				wj.setId(res.getInt(1));
				wj.setSid(res.getInt(2));
				wj.setWjtime(res.getString(3));
				wj.setWjyuanyin(res.getString(4));
				wj.setKoufen(res.getInt(5));
				wj.setState(res.getInt(6));
				wj.setInfo(res.getString(7));
				wj.setChufaren(res.getString(8));
				wjlist.add(wj);
			}
			res.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wjlist;
	}
	@Override
	public int changeState(int id, String info) {
		String sql="update weiji set state=?,info=?  where id=?";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, 2);
			ps.setString(2, info);
			ps.setInt(3, id);

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
	public List<Student> wjStudentList(String key) {
		List<Student> stlist=new ArrayList<>();
		String sql="SELECT s.*,bj.cname from student s left join banji bj on s.bjid=bj.id "
				+ "where sid=? or sname like ? and exists(select 1 from weiji wj where wj.sid=s.sid) "
				+ "order by s.kaohefenshu";
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
	public void rollback(int id){
		String sql="select sid,koufen from weiji where id=?";
		Connection con=MySqlDataSource.getCon();
		try {
			
			con.setAutoCommit(false);
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet res=ps.executeQuery();
			if(res.next()){
				int sid=res.getInt(1);
				int koufen=res.getInt(2);
				String sql1="update student set kaohefenshu=kaohefenshu+"+koufen+" where sid="+sid;
				ps=con.prepareStatement(sql1);
				int i=ps.executeUpdate();
				if(i==1){
					String sql2="delete from weiji where id="+id;
					ps=con.prepareStatement(sql2);
					i=ps.executeUpdate();
					if(i==1){
						con.commit();
					}else{
						con.rollback();
					}
				}else{
					con.rollback();
				}
			}
			res.close();
			ps.close();
			
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
	}
	/**
	 * 带分页的查看违纪的学生列表
	 */
	@Override
	public List<Student> wjStudentList(String bjid,Pagenation p) {
		List<Student> stlist=new ArrayList<>();
		String sql="SELECT s.*,bj.cname from student s left join banji bj on s.bjid=bj.id "
				+ "where exists(select 1 from weiji wj where wj.sid=s.sid) ";
		if(!bjid.equals("0")){
			sql+="and s.bjid="+bjid;
		}
		sql+=" order by s.kaohefenshu ";
		sql+=" limit "+(p.getPage()-1)*p.getRows()+","+p.getRows();
		System.out.println(sql);
		String sql_count="SELECT count(s.sid) from student s "
				+ "where exists(select 1 from weiji wj where wj.sid=s.sid) ";
		if(!bjid.equals("0")){
			sql_count+="and s.bjid="+bjid;
		}
		try {
			Connection con=MySqlDataSource.getCon();
			PreparedStatement ps=con.prepareStatement(sql);
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
			PreparedStatement ps_count=con.prepareStatement(sql_count);
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
}
