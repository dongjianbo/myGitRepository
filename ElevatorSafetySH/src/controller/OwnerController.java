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
import service.OwnerService;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;
import vo.Owner;

@Controller
@RequestMapping("owner")
public class OwnerController {
  @Resource
  public OwnerService ownerService;
  @Resource
  public HistoryService historyService;
  @Resource
  public History_listService history_listService;
  @Resource
  public CitylistService cityService;
  @RequestMapping("list")
  public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/ownerList");
		List<Owner> olist=ownerService.list(key, 12, request);
		for(Owner o:olist){
			o.setRegistCity(cityService.listBy_Idcity(o.getRegisterArea()));
		}
		mav.addObject("ownerList",olist);
		return mav;
	}
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Owner owner,HttpServletRequest request){
		int idowner=Integer.parseInt(ownerService.insert(owner).toString());
		History history=new History();
		history.setType(6);//类型
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
	    key.setKey(6);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(idowner+"");
	    System.out.println( history_listService.insert(hilist).toString());
		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Owner owner){
		owner=ownerService.findById(owner.getIdowner());
		JSONObject object=JSONObject.fromObject(owner);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Owner owner){
		ownerService.update(owner);
		return "ok";
	}
	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delete(Owner owner){
		try {
			ownerService.delete(owner);
			return "yes";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "no";
	}
	@ResponseBody
	@RequestMapping(value="selectId_owner",produces="text/html;charset=utf-8")
	public String selectId_owner(){
		List<Owner> list=ownerService.selectId_owner();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
}
