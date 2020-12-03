package dao;

import java.util.List;

import table.Student;
import utils.Pagenation;

public interface StudentDao {
	/**
	 * ѧ������
	 */
	public int createStudent(Student st);
	/**
	 * �޸�ѧ����Ϣ
	 * @param st
	 * @return Ӱ�������
	 */
	public int updateStudent(Student st);
	/**
	 * ����ѧ��ѧ�Ų�ѯ��Ϣ
	 * @param sid
	 * @return
	 */
	public Student getStudentById(int sid);
	/**
	 * ѧ���˵�
	 * @param sid
	 * @return
	 */
	public int exitStudent(int sid);
	/**
	 * ����ѧ���б�
	 * @return
	 */
	public List<Student> studentList();
	public List<Student> studentList(String grade,String key,Pagenation p);
	/**
	 * ѧ���޸�ҳ��֮ǰ�Ĳ�ѯ
	 *
	 */
	public List<Student> studentUpdate(String key);
	/**
	 * ѧ���Ƿ����
	 * @param sname
	 * @return
	 */
	public boolean exists(String sname);
	/**
	 * ѧ������
	 * @param sid
	 * @param bjid
	 * @return
	 */
	public int changeBanJi(int sid,int bjid);
	
}
