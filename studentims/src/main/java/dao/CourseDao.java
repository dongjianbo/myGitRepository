package dao;

import java.util.List;

import table.BanJi;
import table.Course;

public interface CourseDao {
	/**
	 * 获取所有的课程信息
	 */
	public List<Course> getCourse();
	/**
	 * 插入课程
	 */
	public int insertCourse(Course c);
	/**
	 * 修改课程
	 */
	public int updateCourse(Course c);
	/**
	 * 删除课程
	 */
	public int deleteCourse(int cid);
}
