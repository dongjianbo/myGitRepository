package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import dao.StudentDaoImp;
import table.Student;

/**
 * Servlet implementation class ToStudentListForKeyServlet
 */
@WebServlet("/teacher/ToStudentUpdateForKeyServlet")
public class ToStudentUpdateForKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToStudentUpdateForKeyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取页面提交的关键字
		request.setCharacterEncoding("UTF-8");
		String key=request.getParameter("key");
		System.out.println("=="+key);
		//调用dao的相关方法,获取关键字下的搜索记录
		StudentDao sdao=new StudentDaoImp();
		List<Student> slist=sdao.studentUpdate(key);
		request.setAttribute("slist", slist);
		//转发和重定向
		RequestDispatcher rd=request.getRequestDispatcher("/studentUpdate.jsp");
		//
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
