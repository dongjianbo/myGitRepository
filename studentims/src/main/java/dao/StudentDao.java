package dao;

import java.util.List;

import table.Student;
import utils.Pagenation;

public interface StudentDao {
	/**
	 * 学生建档
	 */
	public int createStudent(Student st);
	/**
	 * 修改学生信息
	 * @param st
	 * @return 影响的行数
	 */
	public int updateStudent(Student st);
	/**
	 * 按照学生学号查询信息
	 * @param sid
	 * @return
	 */
	public Student getStudentById(int sid);
	/**
	 * 学生退档
	 * @param sid
	 * @return
	 */
	public int exitStudent(int sid);
	/**
	 * 返回学生列表
	 * @return
	 */
	public List<Student> studentList();
	public List<Student> studentList(String grade,String key,Pagenation p);
	/**
	 * 学生修改页面之前的查询
	 *
	 */
	public List<Student> studentUpdate(String key);
	/**
	 * 学生是否存在
	 * @param sname
	 * @return
	 */
	public boolean exists(String sname);
	/**
	 * 学生调班
	 * @param sid
	 * @param bjid
	 * @return
	 */
	public int changeBanJi(int sid,int bjid);
	
}
