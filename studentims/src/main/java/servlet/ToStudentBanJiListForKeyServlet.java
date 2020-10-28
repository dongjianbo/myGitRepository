package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BanJiDao;
import dao.BanJiDaoImp;
import dao.StudentDao;
import dao.StudentDaoImp;
import table.BanJi;
import table.Student;

/**
 * Servlet implementation class ToStudentBanJiListForKeyServlet
 */
@WebServlet("/teacher/ToStudentBanJiListForKeyServlet")
public class ToStudentBanJiListForKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToStudentBanJiListForKeyServlet() {
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
		System.out.println("=="+key);
		//����dao����ط���,��ȡ�ؼ����µ�������¼
		StudentDao sdao=new StudentDaoImp();
		List<Student> slist=sdao.studentUpdate(key);
		request.setAttribute("slist", slist);
		BanJiDao bjdao=new BanJiDaoImp();
		List<BanJi> bjlist=bjdao.getBanJi();
		request.setAttribute("bjlist", bjlist);
		//ת�����ض���
		RequestDispatcher rd=request.getRequestDispatcher("/studentBanJi.jsp");
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
