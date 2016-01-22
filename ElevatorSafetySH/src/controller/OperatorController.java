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
 //查询密码
   @RequestMapping(value="selectById",produces="text/html;charset=utf-8")
   @ResponseBody
	public String selectById(String password,HttpServletRequest request){
	   //将前台密码进行转码
	   MD5 md=new MD5();
		String pd=md.getMD5ofStr(password);
	   Operator op=(Operator)request.getSession().getAttribute("login");
	   if(!pd.equals(op.getPassword())){
		  return "no";
		 
	   }else{
		  return "yes";
	   }
	   
		
	}
   //修改密码
   @RequestMapping(value="updatePassword",produces="text/html;charset=utf-8")
  	
  	public String  updatePassword(String password,HttpServletRequest request){
  	   Operator op=(Operator)request.getSession().getAttribute("login");
  	   System.out.println(op.getIdoperator()+"ooooo");
  	   //转码
  	   MD5 md=new MD5();
  	   String pwd=md.getMD5ofStr(password);
  	   int i=operatorService.updatePassword(op.getIdoperator(), pwd);
  	     if(i==1){
  	    	return "nologin";
  	     }else
  	    	return "system/shibai";
  	}
  //修改角色
   @RequestMapping(value="updateRole",produces="text/html;charset=utf-8")
	@ResponseBody
	public String updateRole(Operator operator){
		operatorService.updateRole(operator);
		return "ok";
	}
   
   @RequestMapping(value="insert1",produces="text/html;charset=utf-8")
  	@ResponseBody
  	public String insert1(String idOrganization1,Operator operator,HttpServletRequest request){
  	    //插入技术监督局操作员信息
	   System.out.println(idOrganization1+"-----");
	   MD5 md5=new MD5();//将密码转码之后存在数据库中
	   String psd=md5.getMD5ofStr(operator.getPassword());
	   operator.setPassword(psd);
	   operator.setIdOrganization(Integer.parseInt(idOrganization1));
	   System.out.println(operator.getPassword());
  		operatorService.insert(operator);//返回表的主键  		
  		return "ok";
  	}
	
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Operator operator){
		operator=operatorService.findById(operator.getIdoperator());
		//因为role类下配置多对多关系 下的menus没有级联查询，导致转json的时候需要获取menus（就会一直报懒加载错误） ，但是我们并不需要这个menus附属对象，所以直接给menus赋予一个空值就可以了
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
