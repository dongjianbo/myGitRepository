package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySqlConnection;
import database.MySqlDataSource;
import table.BanJi;

public class BanJiDaoImp implements BanJiDao{

	@Override
	public List<BanJi> getBanJi() {
		String sql="select * from banji";
		Connection con=MySqlDataSource.getCon();
		List<BanJi> bjlist=new ArrayList<>();
		try {
			ResultSet res=con.createStatement().executeQuery(sql);
			while(res.next()){
				BanJi bj=new BanJi();
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
	public int insertBanJi(BanJi bj){
		String sql="insert into banji values(null,?,?)";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, bj.getCname());
			ps.setString(2, bj.getCinfo());
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
	public int updateBanJi(BanJi bj){
		String sql="update banji set cname=?,cinfo=? where id=?";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, bj.getCname());
			ps.setString(2, bj.getCinfo());
			ps.setInt(3, bj.getId());
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
	public int deleteBanJi(int bjid){
		String sql="select 1 from student where bjid=?";
		String sql_del="delete from banji where id=?";
		Connection con=MySqlDataSource.getCon();
		int i=-1;
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, bjid);
			ResultSet res=ps.executeQuery();
			if(res.next()){
				i=0;
			}else{
				ps=con.prepareStatement(sql_del);
				ps.setInt(1, bjid);
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
	
	public int getIdByName(String bjName){
		String sql="select id from banji where cname=?";
		Connection con=MySqlDataSource.getCon();
		int i=-1;
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, bjName);
			ResultSet res=ps.executeQuery();
			if(res.next()){
				i=res.getInt(1);
			}
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
