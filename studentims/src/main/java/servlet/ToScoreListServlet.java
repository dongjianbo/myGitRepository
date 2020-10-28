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
import table.BanJi;
import table.Course;

/**
 * Servlet implementation class ToScoreListServlet
 */
@WebServlet("/teacher/ToScoreListServlet")
public class ToScoreListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToScoreListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//查询所有的班级做下拉框
		BanJiDao bjDao=new BanJiDaoImp();
		List<BanJi> bjlist=bjDao.getBanJi();
		request.setAttribute("bjlist", bjlist);
		//查询所有的课程做下拉框
		CourseDao cDao=new CourseDaoImp();
		List<Course> clist=cDao.getCourse();
		request.setAttribute("clist", clist);
		request.getRequestDispatcher("/scoreList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
