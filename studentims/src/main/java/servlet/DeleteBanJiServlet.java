package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BanJiDao;
import dao.BanJiDaoImp;

/**
 * Servlet implementation class DeleteBanJiServlet
 */
@WebServlet("/teacher/DeleteBanJiServlet")
public class DeleteBanJiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBanJiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bjid=request.getParameter("bjid");
		if(bjid!=null&&!"".equals(bjid)){
			try {
				int bj_id=Integer.parseInt(bjid);
				BanJiDao bjDao=new BanJiDaoImp();
				int i=bjDao.deleteBanJi(bj_id);
				response.getWriter().write(i+"");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
