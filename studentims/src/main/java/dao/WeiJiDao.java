package dao;

import java.util.List;

import table.Student;
import table.WeiJi;
import utils.Pagenation;

public interface WeiJiDao {
	public int createWeiJi(WeiJi wj);
	public List<WeiJi> wjList(int sid);
	public int changeState(int id,String info);
	public List<Student> wjStudentList(String key);
	public void rollback(int id);
	public List<Student> wjStudentList(String bjid,Pagenation p);
}
