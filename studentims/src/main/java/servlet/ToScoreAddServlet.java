package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BanJiDao;
import dao.BanJiDaoImp;
import dao.CourseDao;
import dao.CourseDaoImp;
import dao.StudentDao;
import dao.StudentDaoImp;
import table.BanJi;
import table.Course;
import table.Student;

/**
 * Servlet implementation class ToScoreAddServlet
 */
@WebServlet("/teacher/ToScoreAddServlet")
public class ToScoreAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToScoreAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sid=request.getParameter("sid");
		int sid_int=Integer.parseInt(sid);
		StudentDao stDao=new StudentDaoImp();
		Student st=stDao.getStudentById(sid_int);
		CourseDao cDao=new CourseDaoImp();
		List<Course> clist=cDao.getCourse();
		request.setAttribute("st", st);
		request.setAttribute("clist", clist);
		request.getRequestDispatcher("/scoreAdd.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
