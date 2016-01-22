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
import service.InstallerService;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Installer;
import vo.Operator;
@Controller
@RequestMapping("installer")
public class InstallerController {
	@Resource
	public InstallerService installerService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@Resource
	public CitylistService cityService;
    @RequestMapping("list")
    public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/installerList");
		List<Installer> list=installerService.list(key, 12, request);
		for(Installer i:list){
			i.setRegistCity(cityService.listBy_Idcity(i.getRegister_area()));
		}
		mav.addObject("installerList",list);
		return mav;
	}
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Installer installer,HttpServletRequest request){
		int idinstaller=Integer.parseInt(installerService.insert(installer).toString());
		History history=new History();
		history.setType(3);//类型
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
	    key.setKey(3);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(idinstaller+"");
		System.out.println( history_listService.insert(hilist).toString());
		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Installer installer){
		installer=installerService.findById(installer.getIdinstaller());
		JSONObject object=JSONObject.fromObject(installer);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Installer installer){
		installerService.update(installer);
		return "ok";
	}
	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delete(Installer installer){
		try {
			installerService.delete(installer);
			return "yes";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "no";
	}
	@RequestMapping(value="selectId_installer",produces="text/html;charset=utf-8")
	@ResponseBody
	public String selectId_installer(){
		List<Installer> list=installerService.selectId_installer();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
  
}
