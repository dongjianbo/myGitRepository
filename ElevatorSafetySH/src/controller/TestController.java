package controller;

import java.util.List;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.CitylistService;
import service.HistoryService;
import service.History_listService;
import service.Maint_report_idService;
import service.OperatorService;
import service.TestService;
import vo.Elevator;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Maint_report_id;
import vo.Operator;
import vo.Test;

@Controller
@RequestMapping("test")
public class TestController {
	@Resource
	public TestService testService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@Resource
	public Maint_report_idService mriService;
	@Resource
	public CitylistService cityService;
	@Resource
	public OperatorService operatorService;
	@RequestMapping("list")
	public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/testList");
		List<Test> tlist=testService.list(key,12,request);
		for(Test t:tlist){
			t.setRegistCity(cityService.listBy_Idcity(t.getRegisterArea()));
		}
		mav.addObject("testList", tlist);
		return mav;
	}
	 @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
	    @ResponseBody
	    public String list_json(){
	    	List<Test> testList=testService.list();
	    	JSONArray array=JSONArray.fromObject(testList);
	    	return array.toString();
	    }
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Test test,HttpServletRequest request){
		Serializable res=testService.insert(test);
		int idtest=-1;
		if(res!=null){
			idtest=Integer.parseInt(res.toString());
		}
		History history=new History();
		history.setType(4);//类型
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
	    key.setKey(4);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(idtest+"");
        history_listService.insert(hilist);
        return idtest+"";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Test test){
		test=testService.findById(test.getIdtest());
		JSONObject object=JSONObject.fromObject(test);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Test test){
		testService.update(test);
		return "ok";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delete(Test test){
		//查询该单位下是否有操作员
		DetachedCriteria dc=DetachedCriteria.forClass(Operator.class);
		dc.add(Restrictions.in("typeOperator",new String[]{"30","31"}));
		dc.add(Restrictions.eq("idOrganization", test.getIdtest()));
		List list=operatorService.listOperatorByDc(dc);
		if(list!=null&&!list.isEmpty()){
			return "no";
		}else{
			try {
				testService.delete(test);
				return "yes";
			} catch (Exception e) {
				return "no";
			}
			
		}
	}
	@RequestMapping(value="selectId_test",produces="text/html;charset=utf-8")
	@ResponseBody
	public String selectId_test(){
		List<Test> list=testService.selectId_test();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
	@RequestMapping("search")
	public ModelAndView search(HttpServletRequest request) {
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("30")&&!op.getTypeOperator().equals("31")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","当前登录人非检测单位人员!");
			return mav;
		}else{
			//登录人使用单位
			int id_test=op.getIdOrganization();
			Test test=testService.findById(id_test);
			// 电梯总数
			int count = testService.getCount(id_test);
			// 已注册数量
			int count_registed = testService.getCount_Registed(id_test);
			// 停用数量
			int count_stop = testService.getCount_Stop(id_test);
			// 已注销数量
			int count_destory = testService.getCount_Destory(id_test);
			// 未注册数量
			int count_noregist = testService.getCount_NoRegist(id_test);
			// 年检正常数量
			int count_rounds_normal = testService.getCount_Rounds_Normal(id_test);
			// 年检提示数量
			int count_rounds_warnning = testService.getCount_Rounds_Warnning(id_test);
			// 年检逾期数量
			int count_rounds_overdue = testService.getCount_Rounds_Overdue(id_test);
		

			ModelAndView mav = new ModelAndView("system/testTongji");
			mav.addObject("test",test);
			mav.addObject("count", count);
			mav.addObject("count_registed", count_registed);
			mav.addObject("count_stop", count_stop);
			mav.addObject("count_destory", count_destory);
			mav.addObject("count_noregist", count_noregist);
			mav.addObject("count_rounds_normal", count_rounds_normal);
			mav.addObject("count_rounds_warnning", count_rounds_warnning);
			mav.addObject("count_rounds_overdue", count_rounds_overdue);
			
			return mav;
		}
	}
	// 点击统计中的数字进入电梯列表
	@RequestMapping("listForSearch")
	public ModelAndView listForSearch(String key, String search, HttpServletRequest request) {
		//
		List<Elevator> list = new ArrayList<Elevator>();
		//查询关键字
		if(search==null){
			search="";
		}
		//登录人维保单位
		Operator op=(Operator)request.getSession().getAttribute("login");
		int id_test=op.getIdOrganization();
		Test test=testService.findById(id_test);
		// 电梯总数量
		if (key.equals("count")) {
			list = testService.listCount(search, 10, request,id_test);
		}
		// 电梯已注册数量
		if (key.equals("count_registed")) {
			list = testService.listCount_Registed(search, 10, request,id_test);
		}
		// 电梯未注册数量
		if (key.equals("count_noregist")) {
			list = testService.listCount_NoRegist(search, 10, request,id_test);
		}
		// 电梯已停用数量
		if (key.equals("count_stop")) {
			list = testService.listCount_Stop(search, 10, request,id_test);
		}
		// 电梯已注销数量
		if (key.equals("count_destory")) {
			list = testService.listCount_Destory(search, 10, request,id_test);
		}
		// 电梯年检正常数量
		if (key.equals("count_rounds_normal")) {
			list = testService.listCount_Rounds_Normal(search, 10, request,id_test);
		}
		// 电梯年检提示数量
		if (key.equals("count_rounds_warnning")) {
			list = testService.listCount_Rounds_Warnning(search, 10, request,id_test);
		}
		// 电梯年检逾期数量
		if (key.equals("count_rounds_overdue")) {
			list = testService.listCount_Rounds_Overdue(search, 10, request,id_test);
		}
		ModelAndView mav = new ModelAndView("system/elevatorList");
		mav.addObject("list", list);
		mav.addObject("key",key);
		mav.addObject("search",search);
		mav.addObject("requestMapping", "test");
		mav.addObject("test",test);
		return mav;
	}
	//安全员任务量统计
	@RequestMapping("task")
	public ModelAndView task(String start,String end,HttpServletRequest request){
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("30")&&!op.getTypeOperator().equals("31")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","当前登录人非检验检测单位人员!");
			return mav;
		}else{
			//查询本使用单位的巡检任务量
			int countType0=testService.getCountMaintType0(op.getIdOrganization(), start, end);
			int countType=testService.getCountMaint(op.getIdOrganization(), start, end);
			ModelAndView mav=new ModelAndView("system/testTask");
			mav.addObject("countType0", countType0);
			mav.addObject("countType",countType);
			return mav;
		}
	}
	//任务量列表
	@RequestMapping("listForTask")
	public ModelAndView listForTask(int type,String start,String end,HttpServletRequest request){
		Operator op=(Operator)request.getSession().getAttribute("login");
		//获取当前登录人的单位编号
		int id_test=op.getIdOrganization();
		List<Maint_report_id> list=testService.listByType(id_test,type, start, end,request);
		ModelAndView mav=new ModelAndView("system/serviceListForTask");
		String typeName="";
		if(type!=-1){
			typeName=mriService.getTypeNameById(type);
		}else{
			typeName="配合维保";
		}
		
		mav.addObject("list",list);
		mav.addObject("typeName",typeName);
		return mav;
	}
}
