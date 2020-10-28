package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySqlDataSource;
import table.Score;

public class ScoreDaoImp implements ScoreDao {

	@Override
	public int createScore(Score score) {
		//如果该生此科目有成绩,作出修改
		String sql1="update score set score=? where cid=? and sid=?";
		String sql="insert into score values(null,?,?,?)";
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps1=con.prepareStatement(sql1);
			ps1.setInt(2, score.getCid());
			ps1.setInt(3, score.getSid());
			ps1.setInt(1,score.getScore());
			int i=ps1.executeUpdate();
			if(i==0){
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setInt(1, score.getCid());
				ps.setInt(2, score.getSid());
				ps.setInt(3,score.getScore());
				i=ps.executeUpdate();
				ps.close();
			}
			ps1.close();
			con.close();
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public List<Score> scoreList(Score sc){
		String sql="select s.*,st.sname,c.cname,bj.cname from score s "
				+ "left join student st on s.sid=st.sid "
				+ "left join course c on s.cid=c.id "
				+ "left join banji bj on st.bjid=bj.id "
				+ "where  (st.sname like ? or st.sid=?) and c.cname like ? and bj.cname like ?";
		List<Score> scoreList=new ArrayList<>();
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, "%"+sc.getSname()+"%");
			ps.setString(2, sc.getSname());
			ps.setString(3, "%"+sc.getCname()+"%");
			ps.setString(4, "%"+sc.getBjname()+"%");
			ResultSet res=ps.executeQuery();
			while(res.next()){
				Score s=new Score();
				s.setId(res.getInt(1));
				s.setCid(res.getInt(2));
				s.setSid(res.getInt(3));
				s.setScore(res.getInt(4));
				s.setSname(res.getString(5));
				s.setCname(res.getString(6));
				s.setBjname(res.getString(7));
				scoreList.add(s);
			}
			res.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scoreList;
	}
	@Override
	public List<Score> scoreList(int sid) {
		String sql="select s.*,st.sname,c.cname,bj.cname from score s "
				+ "left join student st on s.sid=st.sid "
				+ "left join course c on s.cid=c.id "
				+ "left join banji bj on st.bjid=bj.id "
				+ "where  st.sid=?";
		List<Score> scoreList=new ArrayList<>();
		Connection con=MySqlDataSource.getCon();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, sid);
			ResultSet res=ps.executeQuery();
			while(res.next()){
				Score s=new Score();
				s.setId(res.getInt(1));
				s.setCid(res.getInt(2));
				s.setSid(res.getInt(3));
				s.setScore(res.getInt(4));
				s.setSname(res.getString(5));
				s.setCname(res.getString(6));
				s.setBjname(res.getString(7));
				scoreList.add(s);
			}
			res.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scoreList;
	}
	
}
