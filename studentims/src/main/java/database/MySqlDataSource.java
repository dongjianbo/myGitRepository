package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MySqlDataSource {
	private static DataSource ds=null;
	private static Connection con=null;
	public static Connection getCon(){
		try {
			if(ds==null){
				//获取web服务器中配置好的数据源
				Context context=new InitialContext();
				ds=(DataSource)context.lookup("java:/comp/env/jdbc/studentims_ds");
			}
			//让数据源返回一个可用的连接
			Connection con=ds.getConnection();
			return con;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 执行写语句
	 */
	public static int executeUpdate(String sql){
		try {
			Connection con=getCon();
			Statement st=con.createStatement();
			int i=st.executeUpdate(sql);
			st.close();
			con.close();
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public static Connection getConForJDBC(){
		try {
			String url="jdbc:mysql://132.232.13.175:3306/studentims";
			String className="com.mysql.jdbc.Driver";
			if(con==null){
				Class.forName(className);
				con=DriverManager.getConnection(url, "root", "root1234");
			}
			return con;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public static void main(String[] args) {
		System.out.println(getConForJDBC());
	}
}
