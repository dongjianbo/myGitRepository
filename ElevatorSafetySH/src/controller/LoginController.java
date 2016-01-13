package controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.OperatorService;
import vo.Operator;

@Controller
@RequestMapping("login")
public class LoginController {
	@Resource
	public OperatorService perService;
   @RequestMapping("check")
   public String CheckLogin(Operator operator,HttpServletRequest request,HttpServletResponse response)
		    {
	   int i=perService.check(operator);
	   request.setAttribute("op", operator);
	   if(i==1){
		   request.setAttribute("massage1","<font color='red'>用户名不存在</font>");
		   return "login";
	   }else{
		   if(i==2){
			   request.setAttribute("massage2","<font color='red'>密码不正确</font>");
			   return "login";
		   }else{
			   Operator op=perService.getOper(operator);
			   request.getSession().setAttribute("login",op);
				 return "main";
		   }
	   }
   }
}
