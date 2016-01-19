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
	public String insert(Modellist modellist,String parameter12,String parameter14,String parameter21,String parameter23,String parameter24,String parameter32,String parameter34,String parameter41,HttpServletRequest request,HttpServletResponse response){
		//把前台文本框中的 类型转换成Integer类型
		if(parameter12.length()!=0){
			Integer parameter12_a=Integer.parseInt(parameter12);
			modellist.setParameter12(parameter12_a);
		}
		if(parameter14.length()!=0){
			Integer parameter14_a=Integer.parseInt(parameter14);
			modellist.setParameter14(parameter14_a);
		}
		if(parameter21.length()!=0){
			Integer parameter21_a=Integer.parseInt(parameter21);
			modellist.setParameter21(parameter21_a);
		}
		if(parameter23.length()!=0){
			Integer parameter23_a=Integer.parseInt(parameter23);
			modellist.setParameter23(parameter23_a);
		}
		if(parameter24.length()!=0){
			Integer parameter24_a=Integer.parseInt(parameter24);
			modellist.setParameter24(parameter24_a);
		}
		if(parameter32.length()!=0){
			Integer parameter32_a=Integer.parseInt(parameter32);
			modellist.setParameter32(parameter32_a);
		}
		if(parameter34.length()!=0){
			Integer parameter34_a=Integer.parseInt(parameter34);
			modellist.setParameter34(parameter34_a);
		}
		if(parameter41.length()!=0){
			Integer parameter41_a=Integer.parseInt(parameter41);
			modellist.setParameter41(parameter41_a);
		}
		
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
	public String update(Modellist modellist,String parameter12,String parameter14,String parameter21,String parameter23,String parameter24,String parameter32,String parameter34,String parameter41){
		//把前台文本框中的 类型转换成Integer类型
		if(parameter12.length()!=0){
			Integer parameter12_a=Integer.parseInt(parameter12);
			modellist.setParameter12(parameter12_a);
		}
		if(parameter14.length()!=0){
			Integer parameter14_a=Integer.parseInt(parameter14);
			modellist.setParameter14(parameter14_a);
		}
		if(parameter21.length()!=0){
			Integer parameter21_a=Integer.parseInt(parameter21);
			modellist.setParameter21(parameter21_a);
		}
		if(parameter23.length()!=0){
			Integer parameter23_a=Integer.parseInt(parameter23);
			modellist.setParameter23(parameter23_a);
		}
		if(parameter24.length()!=0){
			Integer parameter24_a=Integer.parseInt(parameter24);
			modellist.setParameter24(parameter24_a);
		}
		if(parameter32.length()!=0){
			Integer parameter32_a=Integer.parseInt(parameter32);
			modellist.setParameter32(parameter32_a);
		}
		if(parameter34.length()!=0){
			Integer parameter34_a=Integer.parseInt(parameter34);
			modellist.setParameter34(parameter34_a);
		}
		if(parameter41.length()!=0){
			Integer parameter41_a=Integer.parseInt(parameter41);
			modellist.setParameter41(parameter41_a);
		}
		modellistService.update(modellist);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Modellist modellist){
		modellistService.delete(modellist);
		return "redirect:/modellist/list.do";
	}

}
