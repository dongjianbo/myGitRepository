package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.WeiJiDao;
import dao.WeiJiDaoImp;

/**
 * Servlet implementation class WeijiRollbackServlet
 */
@WebServlet("/teacher/WeijiRollbackServlet")
public class WeijiRollbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeijiRollbackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sid=request.getParameter("id");
		String ssid=request.getParameter("sid");
		Integer id=Integer.parseInt(sid);
		WeiJiDao wjdao=new WeiJiDaoImp();
		wjdao.rollback(id);
		response.sendRedirect(request.getContextPath()+"/teacher/WeiJiDetailServlet?sid="+ssid);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
