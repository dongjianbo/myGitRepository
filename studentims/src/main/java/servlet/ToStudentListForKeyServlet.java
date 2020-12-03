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
import utils.Pagenation;

/**
 * Servlet implementation class ToStudentListForKeyServlet
 */
@WebServlet("/teacher/ToStudentListForKeyServlet")
public class ToStudentListForKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToStudentListForKeyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡҳ���ύ�Ĺؼ���
		request.setCharacterEncoding("UTF-8");
		String key=request.getParameter("key");
		String grade=request.getParameter("grade");
		int page=Integer.parseInt(request.getParameter("page"));
		//������ҳ���
		Pagenation p=new Pagenation();
		p.setPage(page);
		//����dao����ط���,��ȡ�ؼ����µ�������¼
		StudentDao sdao=new StudentDaoImp();
		List<Student> slist=sdao.studentList(grade,key,p);
		request.setAttribute("slist", slist);
		request.setAttribute("p", p);
		//ת�����ض���
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
