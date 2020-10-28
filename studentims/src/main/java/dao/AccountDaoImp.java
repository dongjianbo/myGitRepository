package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import database.MySqlDataSource;
import table.Account;
import utils.Pagenation;

public class AccountDaoImp implements AccountDao{

	public int login(Account account) {
		//获取数据连接
		//编写sql语句
		//封装数据
		
		
		//使用数据库已经编写好的存储过程进行判断
		Connection con=MySqlDataSource.getCon();
		try {
			CallableStatement cs=con.prepareCall("{call login(?,?,?)}");
			cs.registerOutParameter(3, Types.INTEGER);
			cs.setString(1, account.getUname());
			cs.setString(2, account.getPassword());
			cs.execute();
			int i=cs.getInt(3);
			System.out.println("返回的i是:"+i);
			con.close();
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public Account getAccount(String uname, String pwd) {
		String sql="select * from account where uname=? and password=password(?)";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, uname);
			ps.setString(2, pwd);
			ResultSet res=ps.executeQuery();
			Account acc=new Account();
			res.next();
			acc.setId(res.getInt(1));
			acc.setUname(res.getString(2));
			acc.setPassword(res.getString(3));
			acc.setState(res.getInt(4));
			acc.setTruename(res.getString(5));
			acc.setRole(res.getInt(6));
			acc.setSid(res.getInt(7));
			res.close();
			con.close();
			return acc;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public int save(Account account) {
		String sql="insert into account values(null,?,password(?),?,?,?,?)";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, account.getUname());
			ps.setString(2, account.getPassword());
			ps.setInt(3,1);
			ps.setString(4,account.getTruename());
			ps.setInt(5,account.getRole());
			ps.setInt(6, account.getSid());
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
	
	public List<Account> accList(String key,Pagenation p){
		List<Account> acclist=new ArrayList<>();
		String sql="select * from account where truename like ? order by role asc"
				+" limit "+(p.getPage()-1)*p.getRows()+","+p.getRows();;
		String sql1="select count(*) from account where truename like ?";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, "%"+key+"%");
			ResultSet res=ps.executeQuery();
			while(res.next()){
				Account acc=new Account();
				acc.setId(res.getInt(1));
				acc.setUname(res.getString(2));
				acc.setPassword(res.getString(3));
				acc.setState(res.getInt(4));
				acc.setTruename(res.getString(5));
				acc.setRole(res.getInt(6));
				acclist.add(acc);
			}
			ps=con.prepareStatement(sql1);
			ps.setString(1, "%"+key+"%");
			ResultSet set1=ps.executeQuery();
			if(set1.next()){
				int count=set1.getInt(1);
				p.setTotal(count);
				p.setPages((count+p.getRows()-1)/p.getRows());//总页数
			}
			res.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return acclist;
	}
	
	public int changeState(int id,int state){
		String sql="update account set state=? where id=?";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, state);
			ps.setInt(2,id);
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
	public int changePassword(int id,String password, String newpassword) {
		int i=-1;
		String sql1="select 1 from account where password=password(?) and id=?";
		
		String sql="update account set password=password(?) where id=?";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps1=con.prepareStatement(sql1);
			ps1.setString(1, password);
			ps1.setInt(2,id);
			ResultSet res=ps1.executeQuery();
			if(res.next()){
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setString(1, newpassword);
				ps.setInt(2,id);
				i=ps.executeUpdate();
				ps.close();
			}else{
				i=0;
			}
			ps1.close();
			con.close();
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	@Override
	public boolean exists(String uname) {
		try {
			String sql="SELECT 1 from account where uname=?";
			Connection con=MySqlDataSource.getCon();
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, uname);
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
}
