package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlConnection {
	//驱动类的全名
	private	static String driverClass="com.mysql.jdbc.Driver";
	//url
	private	static String url="jdbc:mysql://localhost:3306/studentims";
	//用户名
	private	static String uname="root";
	//密码
	private	static String pwd="root";
	//数据库连接
	private static Connection con=null;
	private MySqlConnection() {
		
	}
	public static Connection getCon(){
		if(con==null){
			try {
				//加载驱动类
				Class.forName(driverClass);
				con = DriverManager.getConnection(url, uname, pwd);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con;
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
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * 执行读语句
	 */
	public static ResultSet executeQuery(String sql){
		try {
			Connection con=getCon();
			Statement st=con.createStatement();
			ResultSet r=st.executeQuery(sql);
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			System.out.println(getCon());
		}
	}
	
}
