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

/**
 * Servlet implementation class ToWeiJiListForKeyServlet
 */
@WebServlet("/teacher/ToWeiJiListForKeyServlet")
public class ToWeiJiListForKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToWeiJiListForKeyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key=request.getParameter("key");
		WeiJiDao wjdao=new WeiJiDaoImp();
		List<Student> stList=wjdao.wjStudentList(key);
		request.setAttribute("slist", stList);
		request.getRequestDispatcher("/weiJiList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
