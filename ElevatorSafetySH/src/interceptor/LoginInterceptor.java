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
		 * �ֳֶ˶�ά���ѯ����Ҫ��¼
		 */
		if(uri!=null&&uri.endsWith("/elevator/QRCode.do")){
			return arg0.proceed();
		}
		if(uri!=null&&uri.endsWith("/login/check.do")){
			//ȥ��¼��uri
			return arg0.proceed();
		}else{
			//�ж��û��Ƿ��¼
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
