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
	   int i=perService.check(operator);//�ж��Ƿ���Ե�½ 
	   request.setAttribute("op", operator);
	   if(i==1){
		   request.setAttribute("massage1","<font color='red'>�û���������</font>");
		   return "login";
	   }else{
		   if(i==2){
			   request.setAttribute("massage2","<font color='red'>���벻��ȷ</font>");
			   return "login";
		   }else{
			   Operator op=perService.getOper(operator);
			   //������ѯ��¼�˵ĵ�λ
			   Operator_type type= operator_typeService.getOperType(op.getTypeOperator());
			   request.getSession().setAttribute("operator_type", type);
			   request.getSession().setAttribute("login",op);
				 return "main";
		   }
	   }
   }
}
