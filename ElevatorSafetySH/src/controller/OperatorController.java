package controller;

import java.text.SimpleDateFormat;
import java.util.Date;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
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
import vo.Safer;

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
   @RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Operator operator,HttpServletRequest request){
	    System.out.println("身份:"+operator.getIdcard());
	    System.out.println("城市"+operator.getIdcity());
	    System.out.println("区"+operator.getIddistrict());
	    System.out.println("所属单位顺序号"+operator.getIdOrganization());
	    System.out.println("角色好"+operator.getIdprivilege());
	    System.out.println("街道:"+operator.getIdsubdistrict());
	    System.out.println("dengluming:"+operator.getLoginname());
	    System.out.println("姓名："+operator.getName());
	    System.out.println("密码："+operator.getPassword());
	    System.out.println("状态："+operator.getStatus());
	    System.out.println("类型："+operator.getTypeOperator());
	   //插入技术监督局操作员信息
		int idoperator=Integer.parseInt( operatorService.insert(operator).toString());//返回表的主键
		//插入history信息
		History history=new History();
		history.setType(11);//类型
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//登陆的id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//当前时间
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		System.out.println("idhistory:"+idhistory);
		
		
		//插入histroy_list信息
		History_list  history_list=new History_list();
		History_listKey  history_listKey=new History_listKey();  
		history_listKey.setIdhistory(idhistory);
		history_listKey.setKey(11);
		history_list.setKey(history_listKey);;
		history_list.setValue(idoperator+"");
		history_listService.insert(history_list);
		
			
		return "ok";
	}
   @RequestMapping(value="insert1",produces="text/html;charset=utf-8")
  	@ResponseBody
  	public String insert1(Operator operator,HttpServletRequest request){
  	    //插入技术监督局操作员信息
	   MD5 md5=new MD5();//将密码转码之后存在数据库中
	   String psd=md5.getMD5ofStr(operator.getPassword());
	   operator.setPassword(psd);
	   System.out.println(operator.getPassword());
  		operatorService.insert(operator);//返回表的主键  		
  		return "ok";
  	}
	
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Operator operator){
		operator=operatorService.findById(operator.getIdoperator());
		/*String jsonString ="{'typeOperator':'"+operator.getOperator_type_def().getName()+"',"
					    + "'name':'"+operator.getName()+"',"
						+ "'idcard':'"+operator.getIdcard()+"',"
					    + "'idcity':'"+operator.getCity().getName_city()+"',"
						+ "'iddistrict':'"+operator.getDistict().getName_district()+"',"
						+ "'idsubdistrict':'"+operator.getSubdistict().getName_subdistrict()+"',"
						+ "'loginname':'"+operator.getLoginname()+"',"
						+ "'password':'"+operator.getPassword()+"',"
						+ "'idOrganization':"+operator.getIdOrganization()+","
					    + "''idprivilege':'"+operator.getRole().getName_role()+"',':'"+operator.getOperator_type_def().getName()+"',"
					    + "'status':'"+operator.getStatus_def().getName()+"',"
					    + "'idoperator':"+operator.getIdoperator()+"}";*/
	  //System.out.println(jsonString);
		//return jsonString;
		operator.getRole().setMenus(null);
		JSONObject   object=JSONObject.fromObject(operator);
		return object.toString();
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
