package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import dao.StudentDaoImp;
import table.Student;

/**
 * Servlet implementation class ToStudentUpdateServlet
 */
@WebServlet("/teacher/ToStudentUpdateServlet")
public class ToStudentUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToStudentUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//把传递的sid取到
		String sid=request.getParameter("sid");
		int id=Integer.parseInt(sid);
		StudentDao sdao=new StudentDaoImp();
		Student st=sdao.getStudentById(id);
		request.setAttribute("st", st);
		request.getRequestDispatcher("/updateStudent.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
