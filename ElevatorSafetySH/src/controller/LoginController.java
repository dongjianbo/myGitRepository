package controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.CitylistService;
import service.DeptGroupService;
import service.DistictlistService;
import service.OperatorService;
import service.Operator_typeService;
import service.ServiceService;
import service.SubdistictlistService;
import service.TestService;
import service.UserService;
import vo.DeptGroup;
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
	@Resource
	public DeptGroupService dbService;
   @RequestMapping("check")
   public String CheckLogin(Operator operator,HttpServletRequest request,HttpServletResponse response)
		    {
	   //���ûỰ������ʱ
	   request.getSession().setMaxInactiveInterval(-1);
	   int i=perService.check(operator);//�ж��Ƿ���Ե�½ 
	   request.setAttribute("op", operator);
	   if(i==1){
		   request.setAttribute("massage1","<font color='black' size='-1'>�û���������</font>");
		   return "login";
	   }
		if(i==2){
			   request.setAttribute("massage2","<font color='black' size='-1'>���벻��ȷ</font>");
			   return "login";
		}
		if(i==3){
			  Operator op=perService.getOper(operator);
			   //������ѯ��¼�˵�����
			   Operator_type type= operator_typeService.getOperType(op.getTypeOperator());
			   request.getSession().setAttribute("operator_type", type);
			   //������ѯ��¼�����ڵ�����
			   //������ѯ��¼�����ڵĵ�λ
			   String optype=op.getTypeOperator();
			   int deptid=op.getIdOrganization();
			   String deptName="";
			   if(optype.equals("00")||optype.equals("01")){
				   //�����ල����
				   deptName="�����ල��";
			   }
			   if(optype.equals("10")||optype.equals("11")){
				   //ά����λ
				   Service1 s=serviceService.findById(deptid);
				   if(s!=null){
					   deptName=s.getName();
				   }
			   }
			   if(optype.equals("20")||optype.equals("21")){
				   //�û���λ
				   User u=userService.findById(deptid);
				   if(u!=null){
					   deptName=u.getName();
				   }
			   }
			   if(optype.equals("30")||optype.equals("31")){
				   //���鵥λ
				   Test test=testService.findById(deptid);
				   if(test!=null){
					   deptName=test.getName();
				   }
			   }
			   if(optype.equals("40")){
				   //���ŵ�λ
				   DeptGroup dg=dbService.getDeptGroupById(deptid);
				   if(dg!=null){
					   deptName=dg.getName();
				   }
			   }
			  
			   request.getSession().setAttribute("deptName", deptName);
			   request.getSession().setAttribute("login",op);
			   //��ѯ��½������Ӧ�Ľ�ɫ
			    int role=op.getIdprivilege();
			    //�ѵ�½�˵Ľ�ɫ����session�У�Ȼ����main.jspҳ��ȡֵ
			    request.getSession().setAttribute("role", role);
				 return "main";
		}else{
			request.setAttribute("massage3","<font color='red'>���û������ã��޷���½</font>");
			return "login";
		}
			 
	 }
 }
