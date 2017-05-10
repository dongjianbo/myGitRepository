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

import service.HistoryService;
import service.History_listService;
import service.SystemstateService;
import service.TesterService;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;
import vo.Service1;
import vo.Servicer;
import vo.Tester;

@Controller
@RequestMapping("tester")
public class TesterController {
  @Resource
  public TesterService testerService;
  @Resource
  public HistoryService  historyService;
  @Resource
  public History_listService  history_listService;
  @Resource
  public SystemstateService systemstateService; 
  @RequestMapping("list")
  public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/testerList");
		mav.addObject("testerList",testerService.list(key, 10, request));
		return mav;
	}
  /**
   * 技术监督员：测试人员查询
   * @param key
   * @param request
   * @return
   */
  @RequestMapping("list1")
  public ModelAndView list1(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/testerList1");
		mav.addObject("testerList",testerService.list(key, 10, request));
		return mav;
	}

  @RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Tester tester,HttpServletRequest request){
	 
	 //检验检测人员信息
		int idtester=Integer.parseInt(testerService.insert(tester).toString());//返回表的主键
		//插入systemstate信息
		systemstateService.update();
		//插入history信息
		History history=new History();
		history.setType(14);//类型
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
		history_listKey.setKey(14);
		history_list.setKey(history_listKey);;
		history_list.setValue(idtester+"");
		history_listService.insert(history_list);
		
		//插入IC卡信息
			
		return idtester+"";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Tester tester){
		tester=testerService.findById(tester.getIdtester());
		JSONObject object=JSONObject.fromObject(tester);
		
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Tester tester){
		testerService.update(tester);
		return "ok";
	}
	@RequestMapping(value="id_tester",produces="text/html;charset=utf-8")
	@ResponseBody
	public String id_tester(){
		List<Tester> list=testerService.getid_tester();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
	@RequestMapping("delete")
	public String delete(Tester tester){
		testerService.delete(tester);
		return "redirect:/tester/list.do";
	}
	@RequestMapping(value="getCard",produces="text/html;charset=utf-8")
	@ResponseBody
	public String getCard(String idMifare,int idservicer){
		testerService.updateCard(idMifare, idservicer);
		return "ok";
	}
}
