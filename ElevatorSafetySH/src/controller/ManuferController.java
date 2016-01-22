package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.CitylistService;
import service.HistoryService;
import service.History_listService;
import service.ManuferService;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Manufer;
import vo.Operator;


@Controller
@RequestMapping("manufer")
public class ManuferController {
	@Resource
	public ManuferService manuferService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@Resource
	public CitylistService cityService;
	@RequestMapping("list")
	public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/manuferList");
		List<Manufer> list=manuferService.list(key, 12, request);
		for(Manufer m:list){
			m.setRegistCity(cityService.listBy_Idcity(m.getRegister_area()));
		}
		mav.addObject("manuferList",list);
		return mav;
	}
	
	 @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
	    @ResponseBody
	    public String list_json(){
	    	List<Manufer> manuferList=manuferService.list();
	    	JSONArray array=JSONArray.fromObject(manuferList);
	    	return array.toString();
	    }
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Manufer manufer,HttpServletRequest request){
		int idmanufer=Integer.parseInt(manuferService.insert(manufer).toString());
		History history=new History();
		history.setType(2);//类型
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//登录人id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//当前时间
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//插入信息到systemstate表中（待写）
		//插入信息到history_list表中
		History_list hilist=new History_list();		
		History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(2);
        hilist.setKey(key);//key表示复合主键的类
		hilist.setValue(idmanufer+"");
	    history_listService.insert(hilist);
		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Manufer manufer){
		manufer=manuferService.findById(manufer.getIdmanufer());
		JSONObject object=JSONObject.fromObject(manufer);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Manufer manufer){
		manuferService.update(manufer);
		return "ok";
	}
	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delete(Manufer manufer){
		try {
			manuferService.delete(manufer);
			return "yes";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "no";
	}
	
	@RequestMapping(value="selectId_manufer",produces="text/html;charset=utf-8")
	@ResponseBody
	public String selectId_manufer(){
		List<Manufer> list=manuferService.getId_manufer();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
}
