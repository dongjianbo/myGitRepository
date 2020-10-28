package servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDao;
import dao.AccountDaoImp;

/**
 * Servlet implementation class AccountStateServlet
 */
@WebServlet("/teacher/AccountStateServlet")
public class AccountStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountStateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		String state=request.getParameter("state");
		String key=request.getParameter("key");
		String page=request.getParameter("page");
		AccountDao accdao=new AccountDaoImp();
		int i=accdao.changeState(Integer.parseInt(id), Integer.parseInt(state)^1);
		if(i==1){
			response.sendRedirect(request.getContextPath()+"/teacher/AccountListServlet?key="+URLEncoder.encode(key,"UTF-8")+"&page="+page);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
