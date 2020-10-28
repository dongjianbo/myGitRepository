package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import dao.StudentDaoImp;
import dao.WeiJiDao;
import dao.WeiJiDaoImp;
import table.Student;
import table.WeiJi;

/**
 * Servlet implementation class WeiJiDetailServlet
 */
@WebServlet("/student/WeiJiDetailServlet")
public class WeiJiDetailServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeiJiDetailServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sid=request.getParameter("sid");
		Integer sid_int=Integer.parseInt(sid);
		StudentDao stDao=new StudentDaoImp();
		WeiJiDao wjDao=new WeiJiDaoImp();
		Student student=stDao.getStudentById(sid_int);
		List<WeiJi> wjlist=wjDao.wjList(sid_int);
		request.setAttribute("wjlist", wjlist);
		request.setAttribute("st", student);
		request.getRequestDispatcher("/weijiDetail_s.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
