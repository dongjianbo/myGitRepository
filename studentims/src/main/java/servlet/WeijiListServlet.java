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
import dao.WeiJiDao;
import dao.WeiJiDaoImp;
import table.BanJi;
import table.Student;
import utils.Pagenation;

/**
 * Servlet implementation class WeijiListServlet
 */
@WebServlet("/teacher/WeijiListServlet")
public class WeijiListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeijiListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡҳ���ύ�Ĺؼ���
		request.setCharacterEncoding("UTF-8");
		String bjid=request.getParameter("bjid");
		int page=Integer.parseInt(request.getParameter("page"));
		//������ҳ���
		Pagenation p=new Pagenation();
		p.setPage(page);
		//����dao����ط���,��ȡ�ؼ����µ�������¼
		WeiJiDao wjdao=new WeiJiDaoImp();
		List<Student> slist=wjdao.wjStudentList(bjid, p);
		request.setAttribute("slist", slist);
		request.setAttribute("p", p);
		//ȡ�����еİ༶��Ϣ������ʼ���༶������
		BanJiDao bjdao=new BanJiDaoImp();
		List<BanJi> bjlist=bjdao.getBanJi();
		request.setAttribute("bjlist", bjlist);
		
		//ת�����ض���
		RequestDispatcher rd=request.getRequestDispatcher("/weiJiList2.jsp");
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
