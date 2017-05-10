package interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements MethodInterceptor{
	@Resource
	HttpServletRequest request;

	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		String uri=request.getRequestURI();
		System.out.println("uri:"+uri);
		/*
		 * 手持端二维码查询不需要登录
		 */
		if(uri!=null&&uri.endsWith("/elevator/QRCode.do")){
			return arg0.proceed();
		}
		if(uri!=null&&uri.endsWith("/login/check.do")){
			//去登录的uri
			return arg0.proceed();
		}else{
			//判断用户是否登录
			Object obj=request.getSession().getAttribute("login");
			if(obj==null){
				ModelAndView mav=new ModelAndView("nologin");
				return mav;
			}else{
				return arg0.proceed();
			}
		}
		
	}
	
}
