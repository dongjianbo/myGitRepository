package controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.HistoryService;
import service.History_listService;
import service.ModellistService;
import service.ServicerService;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Modellist;
import vo.Operator;

@Controller
@RequestMapping("modellist")
public class ModellistController {
	@Resource
	public ModellistService modellistService;
	@Resource
	public ServicerService servicerService;
    @Resource
    public HistoryService  historyService;
    @Resource
    public History_listService  history_listService;
	@RequestMapping("list")
	public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/modelList");
		mav.addObject("modelList", modellistService.list(key,12,request));
		return mav;
	}
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Modellist modellist,HttpServletRequest request,HttpServletResponse response){
		//插入电梯型号modellist表
		int idmodel=Integer.parseInt(modellistService.insert(modellist).toString());
		//插入history信息
		History history=new History();
		history.setType(18);//类型
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
		history_listKey.setKey(18);
		history_list.setKey(history_listKey);;
		history_list.setValue(idmodel+"");
		history_listService.insert(history_list);

		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Modellist modellist){
		modellist=modellistService.findById(modellist.getIdmodel());
		JSONObject object=JSONObject.fromObject(modellist);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Modellist modellist){
		modellistService.update(modellist);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Modellist modellist){
		modellistService.delete(modellist);
		return "redirect:/modellist/list.do";
	}

}
