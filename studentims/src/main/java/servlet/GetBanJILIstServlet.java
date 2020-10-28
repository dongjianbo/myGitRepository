package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BanJiDao;
import dao.BanJiDaoImp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import table.BanJi;

/**
 * Servlet implementation class GetBanJILIstServlet
 */
@WebServlet("/teacher/GetBanJILIstServlet")
public class GetBanJILIstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBanJILIstServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BanJiDao bjDao=new BanJiDaoImp();
		List<BanJi> bjlist=bjDao.getBanJi();
		JSONArray array=JSONArray.fromObject(bjlist);
		System.out.println(array.toString());
		response.getWriter().println(array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
