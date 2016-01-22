package controller;

import java.text.SimpleDateFormat;
import java.util.Date;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import net.sf.json.JSONObject;

import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.HistoryService;
import service.History_listService;
import service.OperatorService;
import util.MD5;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;

@Controller
@RequestMapping("operator")
public class OperatorController  {
  @Resource
  public OperatorService operatorService;
  @Resource
  public HistoryService  historyService;
  @Resource
  public History_listService  history_listService; 
  @RequestMapping("list")
  public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/operatorList");
		mav.addObject("operatorList",operatorService.list(key, 10, request));
		return mav;
	}
  @RequestMapping("list1")
  public ModelAndView list1(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/operatorListRole");
		mav.addObject("operatorList",operatorService.list(key, 10, request));
		return mav;
	}
//  
   @RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Operator operator,HttpServletRequest request){
	    System.out.println("���:"+operator.getIdcard());
	    System.out.println("����"+operator.getIdcity());
	    System.out.println("��"+operator.getIddistrict());
	    System.out.println("������λ˳���"+operator.getIdOrganization());
	    System.out.println("��ɫ��"+operator.getIdprivilege());
	    System.out.println("�ֵ�:"+operator.getIdsubdistrict());
	    System.out.println("dengluming:"+operator.getLoginname());
	    System.out.println("������"+operator.getName());
	    System.out.println("���룺"+operator.getPassword());
	    System.out.println("״̬��"+operator.getStatus());
	    System.out.println("���ͣ�"+operator.getTypeOperator());
	   //���뼼���ල�ֲ���Ա��Ϣ
		int idoperator=Integer.parseInt( operatorService.insert(operator).toString());//���ر������
		//����history��Ϣ
		History history=new History();
		history.setType(11);//����
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//��½��id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//��ǰʱ��
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		System.out.println("idhistory:"+idhistory);
		
		
		//����histroy_list��Ϣ
		History_list  history_list=new History_list();
		History_listKey  history_listKey=new History_listKey();  
		history_listKey.setIdhistory(idhistory);
		history_listKey.setKey(11);
		history_list.setKey(history_listKey);;
		history_list.setValue(idoperator+"");
		history_listService.insert(history_list);
		
			
		return "ok";
	}
 //��ѯ����
   @RequestMapping(value="selectById",produces="text/html;charset=utf-8")
   @ResponseBody
	public String selectById(String password,HttpServletRequest request){
	   //��ǰ̨�������ת��
	   MD5 md=new MD5();
		String pd=md.getMD5ofStr(password);
	   Operator op=(Operator)request.getSession().getAttribute("login");
	   if(!pd.equals(op.getPassword())){
		  return "no";
		 
	   }else{
		  return "yes";
	   }
	   
		
	}
   //�޸�����
   @RequestMapping(value="updatePassword",produces="text/html;charset=utf-8")
  	
  	public String  updatePassword(String password,HttpServletRequest request){
  	   Operator op=(Operator)request.getSession().getAttribute("login");
  	   System.out.println(op.getIdoperator()+"ooooo");
  	   //ת��
  	   MD5 md=new MD5();
  	   String pwd=md.getMD5ofStr(password);
  	   int i=operatorService.updatePassword(op.getIdoperator(), pwd);
  	     if(i==1){
  	    	return "nologin";
  	     }else
  	    	return "system/shibai";
  	}
  //�޸Ľ�ɫ
   @RequestMapping(value="updateRole",produces="text/html;charset=utf-8")
	@ResponseBody
	public String updateRole(Operator operator){
		operatorService.updateRole(operator);
		return "ok";
	}
   
   @RequestMapping(value="insert1",produces="text/html;charset=utf-8")
  	@ResponseBody
  	public String insert1(String idOrganization1,Operator operator,HttpServletRequest request){
  	    //���뼼���ල�ֲ���Ա��Ϣ
	   System.out.println(idOrganization1+"-----");
	   MD5 md5=new MD5();//������ת��֮��������ݿ���
	   String psd=md5.getMD5ofStr(operator.getPassword());
	   operator.setPassword(psd);
	   operator.setIdOrganization(Integer.parseInt(idOrganization1));
	   System.out.println(operator.getPassword());
  		operatorService.insert(operator);//���ر������  		
  		return "ok";
  	}
	
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Operator operator){
		operator=operatorService.findById(operator.getIdoperator());
		//��Ϊrole�������ö�Զ��ϵ �µ�menusû�м�����ѯ������תjson��ʱ����Ҫ��ȡmenus���ͻ�һֱ�������ش��� ���������ǲ�����Ҫ���menus������������ֱ�Ӹ�menus����һ����ֵ�Ϳ�����
		operator.getRole().setMenus(null);
		JSONObject object=JSONObject.fromObject(operator);
		return object.toString();
	}
	@RequestMapping(value="UpdateStatus",produces="text/html;charset=utf-8")
	@ResponseBody
	public String UpdateStatus(int idoperator){
		operatorService.updateStatus(idoperator);
		return "ok";
	}
	@RequestMapping(value="UpdateStatus1",produces="text/html;charset=utf-8")
	@ResponseBody
	public String UpdateStatus1(int idoperator){
		operatorService.updateStatus1(idoperator);
		return "ok";
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Operator operator){
		operatorService.update(operator);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Operator operator){
		operatorService.delete(operator);
		return "redirect:/operator/list.do";
	}
	
}
