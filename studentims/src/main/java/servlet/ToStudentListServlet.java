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
 * Servlet implementation class ToStudentListServlet
 */
@WebServlet("/teacher/ToStudentListServlet")
public class ToStudentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToStudentListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用相关Dao,将所有的学生信息从数据库中抓取出来,传递给显示页面
		StudentDao sdao=new StudentDaoImp();
		List<Student> slist=sdao.studentList();
		request.setAttribute("slist", slist);
		//转发和重定向
		RequestDispatcher rd=request.getRequestDispatcher("/studentList.jsp");
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
