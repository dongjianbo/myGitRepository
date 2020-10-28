package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlConnection {
	//�������ȫ��
	private	static String driverClass="com.mysql.jdbc.Driver";
	//url
	private	static String url="jdbc:mysql://localhost:3306/studentims";
	//�û���
	private	static String uname="root";
	//����
	private	static String pwd="root";
	//���ݿ�����
	private static Connection con=null;
	private MySqlConnection() {
		
	}
	public static Connection getCon(){
		if(con==null){
			try {
				//����������
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
	 * ִ��д���
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
	 * ִ�ж����
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
