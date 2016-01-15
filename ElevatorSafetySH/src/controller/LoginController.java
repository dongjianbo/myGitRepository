package controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.OperatorService;
import service.Operator_typeService;
import vo.Operator;
import vo.Operator_type;

@Controller
@RequestMapping("login")
public class LoginController {
	@Resource
	public OperatorService perService;
	@Resource 
	public Operator_typeService operator_typeService;
   @RequestMapping("check")
   public String CheckLogin(Operator operator,HttpServletRequest request,HttpServletResponse response)
		    {
	   int i=perService.check(operator);//判断是否可以登陆 
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
			   //级联查询登录人的单位
			   Operator_type type= operator_typeService.getOperType(op.getTypeOperator());
			   request.getSession().setAttribute("operator_type", type);
			   request.getSession().setAttribute("login",op);
				 return "main";
		   }
	   }
   }
}
