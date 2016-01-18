package controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.CitylistService;
import service.DistictlistService;
import service.OperatorService;
import service.Operator_typeService;
import service.SubdistictlistService;
import vo.Operator;
import vo.Operator_type;

@Controller
@RequestMapping("login")
public class LoginController {
	@Resource
	public OperatorService perService;
	@Resource 
	public Operator_typeService operator_typeService;
	@Resource
	public CitylistService cityService;
	@Resource
	public DistictlistService distictService;
	@Resource
	public SubdistictlistService subdistictService;
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
			   //级联查询登录人的类型
			   Operator_type type= operator_typeService.getOperType(op.getTypeOperator());
			   request.getSession().setAttribute("operator_type", type);
			   //级联查询登录人所在的区域
			   //级联查询登录人所在的单位
			   request.getSession().setAttribute("login",op);
			   //查询登陆人所对应的角色
			    int role=op.getIdprivilege();
			    //把登陆人的角色存在session中，然后在main.jsp页面取值
			    request.getSession().setAttribute("role", role);
				 return "main";
		   }
	   }
   }
}
