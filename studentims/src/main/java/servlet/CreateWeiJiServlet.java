package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import dao.WeiJiDao;
import dao.WeiJiDaoImp;
import table.WeiJi;

/**
 * Servlet implementation class CreateWeiJiServlet
 */
@WebServlet("/teacher/CreateWeiJiServlet")
public class CreateWeiJiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateWeiJiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		WeiJi wj=new WeiJi();
		try {
			BeanUtils.populate(wj, request.getParameterMap());
			WeiJiDao wjDao=new WeiJiDaoImp();
			int i=wjDao.createWeiJi(wj);
			if(i==1){
				response.sendRedirect(request.getContextPath()+"/createWeiJiSuccess.jsp");
			}else{
				response.sendRedirect(request.getContextPath()+"/500.html");
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
