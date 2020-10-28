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
 * Servlet Filter implementation class RoleValidationFilter_teacher
 */
@WebFilter(filterName="/RoleValidationFilter_student",urlPatterns="/student/*")
public class RoleValidationFilter_student implements Filter {

    /**
     * Default constructor. 
     */
    public RoleValidationFilter_student() {
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
		Object obj=((HttpServletRequest)request).getSession().getAttribute("user");
		System.out.println("检验普通用户权限");
		Account acc=(Account)obj;
		if(acc.getRole()==2){
			chain.doFilter(request, response);
		}else{
			System.out.println("不是普通用户!");
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
