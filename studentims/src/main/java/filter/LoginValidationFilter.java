package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import table.Account;

/**
 * Servlet Filter implementation class LoginValidationFilter
 */
@WebFilter(filterName="/LoginValidationFilter",urlPatterns="/*")
public class LoginValidationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginValidationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		System.out.println("开始验证登录权限");
		//查看session中是否有user
		Object obj=req.getSession().getAttribute("user");
		//对于登录相关的特殊路径需要直接放行
		String addr=((HttpServletRequest)request).getRequestURI();
		System.out.println("当前访问路径是:"+addr);
		String root=req.getContextPath();
		if(obj!=null
				||addr.equals(root)
				||addr.equals(root+"/login.jsp")
				||addr.equals(root+"/YanZhengMaServlet")
				||addr.equals(root+"/LoginServlet")
				||addr.endsWith(".jpg")
				||addr.endsWith(".css")
				||addr.endsWith(".js")
				||addr.startsWith(root+"/webservice")
				){
			chain.doFilter(request, response);
		}else{
			System.out.println("没有登录,页面跳转到登录页面!");
			((HttpServletResponse)response).sendRedirect(request.getServletContext().getContextPath()+"/login.jsp");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
