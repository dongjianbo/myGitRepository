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
import service.ServiceService;
import service.SubdistictlistService;
import service.TestService;
import service.UserService;
import vo.Operator;
import vo.Operator_type;
import vo.Service1;
import vo.Test;
import vo.User;

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
	@Resource
	public ServiceService serviceService;
	@Resource
	public UserService userService;
	@Resource
	public TestService testService;
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
			   String optype=op.getTypeOperator();
			   int deptid=op.getIdOrganization();
			   String deptName="";
			   if(optype.equals("00")){
				   //技术监督部门
				   deptName="技术监督局";
			   }
			   if(optype.equals("10")||optype.equals("11")){
				   //维保单位
				   Service1 s=serviceService.findById(deptid);
				   if(s!=null){
					   deptName=s.getName();
				   }
			   }
			   if(optype.equals("20")||optype.equals("21")){
				   //用户单位
				   User u=userService.findById(deptid);
				   if(u!=null){
					   deptName=u.getName();
				   }
			   }
			   if(optype.equals("30")||optype.equals("31")){
				   //检验单位
				   Test test=testService.findById(deptid);
				   if(test!=null){
					   deptName=test.getName();
				   }
			   }
			  
			   request.getSession().setAttribute("deptName", deptName);
			   request.getSession().setAttribute("login",op);
				 return "main";
		   }
	   }
   }
}
