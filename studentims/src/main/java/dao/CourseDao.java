package dao;

import java.util.List;

import table.BanJi;
import table.Course;

public interface CourseDao {
	/**
	 * ��ȡ���еĿγ���Ϣ
	 */
	public List<Course> getCourse();
	/**
	 * ����γ�
	 */
	public int insertCourse(Course c);
	/**
	 * �޸Ŀγ�
	 */
	public int updateCourse(Course c);
	/**
	 * ɾ���γ�
	 */
	public int deleteCourse(int cid);
}
