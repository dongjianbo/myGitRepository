package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.jws.soap.InitParam;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDao;
import dao.AccountDaoImp;
import table.Account;

/**
 * Servlet implementation class LoginServlet
 */
/**
 * 
 * @WebServlet的注释
 * 这个注释用来设置访问这个servlet的path
 *
 */
@WebServlet(value="/LoginServlet",initParams={@WebInitParam(name="e",value="utf-8")})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     * web服务器在加载并创建servlet的时候会自动调用init()
     * init()代表这个serlvet的诞生
     */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}
	


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置字符编码为UTF-8
		response.setContentType("text/html; charset=UTF-8");
		// TODO Auto-generated method stub
		//response代表响应
		//response.getWriter();获得响应输出流,可以通过这个输出流将字符输出到响应页面上.
		//request.getContextPath();获得web应用名
		//获取页面提交的数据
		String uname=request.getParameter("username");
		String pwd=request.getParameter("password");
		String yzm=request.getParameter("yzm");
		//验证验证码是否正确
		HttpSession session=request.getSession();
		String trueyzm=session.getAttribute("yzm").toString();
		if(trueyzm!=null&&trueyzm.equalsIgnoreCase(yzm)){
			AccountDao adao=new AccountDaoImp();
			Account acc=new Account();
			acc.setUname(uname);
			acc.setPassword(pwd);
			int i=adao.login(acc);
			String errorMessage="";
			switch(i){
				case 1:errorMessage="用户名不存在";break;
				case 2:errorMessage="密码错误";break;
				case 3:errorMessage="账号异常,请与管理员联系";break;
			}
			if(i==1||i==2||i==3){
				//将em存入request
				request.setAttribute("em", errorMessage);
				//request的跳转目标
				RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);//当登录不成功时,跳回到登录页面重新登录
			}
			if(i==4){
				//将用户名和密码存入cookie,下次再登录的时候,可以自动显示
				Cookie cname=new Cookie("n", URLEncoder.encode(uname, "UTF-8"));
				Cookie pname=new Cookie("p", URLEncoder.encode(pwd, "UTF-8"));
				cname.setMaxAge(60*60);
				pname.setMaxAge(60*60);
				response.addCookie(cname);
				response.addCookie(pname);
				System.out.println("cookie存入成功!");
				//当登录成功后,查到登录人的信息,并传递到目标页面去供使用
				Account acc1=adao.getAccount(uname, pwd);
				//把这个对象存入到session中,会话中的其他页面也可以使用
				session.setAttribute("user", acc1);
				//request的跳转目标
				//RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				//rd.forward(request, response);//当登录成功后,跳转到首页
				//使用重定向可以避免重复的登录
				response.sendRedirect("index.jsp");
			}
		}else{
			//将em存入request
			request.setAttribute("em", "验证码填写错误!");
			//request的跳转目标
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);//当登录不成功时,跳回到登录页面重新登录
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
