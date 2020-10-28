package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDao;
import dao.AccountDaoImp;
import table.Account;
import utils.Pagenation;

/**
 * Servlet implementation class AccountListServlet
 */
@WebServlet("/teacher/AccountListServlet")
public class AccountListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String key=request.getParameter("key");
		int page=Integer.parseInt(request.getParameter("page"));
		System.out.println("=="+key);
		System.out.println("=="+page);
		//创建分页组件
		Pagenation p=new Pagenation();
		p.setPage(page);
		AccountDao accDao=new AccountDaoImp();
		List<Account> acclist=accDao.accList(key,p);
		request.setAttribute("acclist", acclist);
		request.setAttribute("p", p);
		request.getRequestDispatcher("/accountList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
