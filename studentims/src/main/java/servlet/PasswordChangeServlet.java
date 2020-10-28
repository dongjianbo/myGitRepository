package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDao;
import dao.AccountDaoImp;

/**
 * Servlet implementation class PasswordChangeServlet
 */
@WebServlet("/PasswordChangeServlet")
public class PasswordChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		String newpwd1=request.getParameter("newpwd1");
		AccountDao accDao=new AccountDaoImp();
		int i=accDao.changePassword(Integer.parseInt(id), password, newpwd1);
		if(i==-1){
			request.setAttribute("error", "–ﬁ∏ƒ“Ï≥£");
			request.getRequestDispatcher("/passwordChange.jsp").forward(request, response);
		}
		if(i==0){
			request.setAttribute("error", "‘≠√‹¬Î¥ÌŒÛ");
			request.getRequestDispatcher("/passwordChange.jsp").forward(request, response);
		}
		if(i==1){
			response.sendRedirect(request.getContextPath()+"/passwordChangeSuccess.jsp");
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
