package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.data.category.DefaultCategoryDataset;

import database.MySqlDataSource;

public class ChartDaoImp {
	public DefaultCategoryDataset getDataSet_JGL(){
		String sql="select sum(case when s.score>=60 then 1 else 0 end)/count(s.score) as 及格率,c.cname as 课程名,bj.cname as 班级名 "
				+ "from score s "
				+"left join student st on s.sid=st.sid " 
				+"left join course c on s.cid=c.id "
				+"left join banji bj on st.bjid=bj.id "
				+"group by c.cname,bj.cname";
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		Connection con=MySqlDataSource.getCon();
		try {
			ResultSet res=con.createStatement().executeQuery(sql);
			while(res.next()){
				dataset.setValue(res.getDouble(1), res.getString(2), res.getString(3));
			}
			res.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataset;
	}
	
	public DefaultCategoryDataset getDataSet_PJF(){
		String sql="select b.cname as 班级,c.cname as  课程,avg(s.score) as 平均分  "
				+"from course c right join score s on c.id=s.cid "
				+ "left join student st on s.sid=st.sid "
				+ "left join banji b on st.bjid=b.id "
				+ "group by b.cname,c.cname";
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		Connection con=MySqlDataSource.getCon();
		try {
			ResultSet res=con.createStatement().executeQuery(sql);
			while(res.next()){
				dataset.setValue(res.getDouble(3), res.getString(2), res.getString(1));
			}
			res.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataset;
	}
}
