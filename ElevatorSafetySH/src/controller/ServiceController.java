package controller;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.HistoryService;
import service.History_listService;
import service.ServiceService;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;
import vo.Service1;

@Controller
@RequestMapping("service")
public class ServiceController {
	@Resource
	public ServiceService serviceService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
    @RequestMapping("list")
    public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/serviceList");
		System.out.println(key);
		mav.addObject("serviceList",serviceService.list(key, 12, request));
		return mav;
	}
    @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
    @ResponseBody
    public String list_json(){
    	List<Service1> serviceList=serviceService.list();
    	JSONArray array=JSONArray.fromObject(serviceList);
    	return array.toString();
    }
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Service1 service,HttpServletRequest request){
		int idservice=Integer.parseInt(serviceService.insert(service).toString());
		request.getSession().setAttribute("idservice",idservice);
		History history=new History();
		history.setType(5);//类型
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//登录人id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//当前时间
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//插入信息到systemstate表中（待写）
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(5);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(idservice+"");
		System.out.println( history_listService.insert(hilist).toString());
		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Service1 service){
		service=serviceService.findById(service.getIdservice());
		JSONObject object=JSONObject.fromObject(service);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Service1 service){
		serviceService.update(service);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Service1 service){
		serviceService.delete(service);
		return "redirect:/service/list.do";
	}
	@ResponseBody
    @RequestMapping(value="selectId_service",produces="text/html;charset=utf-8")
    public String selectId_service(){
		List<Service1> list=serviceService.select_Idservice();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
}
