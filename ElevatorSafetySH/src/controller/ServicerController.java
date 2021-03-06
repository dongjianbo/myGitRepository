package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.HistoryService;
import service.History_listService;
import service.ServicerService;
import service.SystemstateService;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;
import vo.Servicer;



@Controller
@RequestMapping("servicer")
public class ServicerController {
  @Resource
  public ServicerService servicerService;
  @Resource
  public HistoryService  historyService;
  @Resource
  public History_listService  history_listService; 
  @Resource
  public SystemstateService systemstateService; 
  @RequestMapping("list")
  public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/servicerList");
		List<Servicer> servicerList=servicerService.list(key, 10, request);
	    mav.addObject("servicerList",servicerList);
		return mav;
	}
  /**
   * 技术监督员：维保工人信息查询
   * @param key
   * @param request
   * @return
   */
  @RequestMapping("list1")
  public ModelAndView list1(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/servicerList1");
		List<Servicer> servicerList=servicerService.list(key, 10, request);
	    mav.addObject("servicerList",servicerList);
		return mav;
	}
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Servicer servicer,HttpServletRequest request){
		//插入维保人员信息
		int idservicer=Integer.parseInt(servicerService.insert(servicer).toString());//返回表的主键
		//修改systemstate信息
		systemstateService.update();
		//插入history信息
		History history=new History();
		history.setType(12);//类型
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
		history_listKey.setKey(12);
		history_list.setKey(history_listKey);;
		history_list.setValue(idservicer+"");
		history_listService.insert(history_list);
		//插入IC卡信息
		
		return idservicer+"";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Servicer servicer){
		servicer=servicerService.findById(servicer.getIdservicer());
		JSONObject object=JSONObject.fromObject(servicer);
		
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Servicer servicer){
		servicerService.update(servicer);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Servicer servicer){
		//查询该单位是否有操作员
		
		servicerService.delete(servicer);
		return "redirect:/servicer/list.do";
	}
	@RequestMapping(value="getCard",produces="text/html;charset=utf-8")
	@ResponseBody
	public String getCard(String idMifare,int idservicer){
		servicerService.updateCard(idMifare, idservicer);
		return "ok";
	}
}
