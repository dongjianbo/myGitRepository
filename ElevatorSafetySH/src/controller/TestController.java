package controller;

import java.util.List;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import service.System_settingService;
import service.TestService;
import service.TesterService;
import util.DateUtils;
import vo.Elevator;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Maint_report_id;
import vo.Operator;
import vo.System_setting;
import vo.Test;
import vo.Tester;

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
	@Resource
	public TesterService testerService;
	@Resource
	public System_settingService system_settingService;
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
	/**
	 * 技术监督员的测试单位查询
	 * @param key
	 * @param request
	 * @return
	 */
	@RequestMapping("list1")
	public ModelAndView list1(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/testList1");
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
			// 半月维保正常数量
			int count_15service_normal = testService.getCount_15service_Normal(id_test);
			// 半月维保提示数量
			int count_15service_warnning = testService.getCount_15service_Warnning(id_test);
			// 半月维保逾期数量
			int count_15service_overdue = testService.getCount_15service_Overdue(id_test);
			// 季度维保正常数量
			int count_90service_normal = testService.getCount_90service_Normal(id_test);
			// 季度维保提示数量
			int count_90service_warnning = testService.getCount_90service_Warnning(id_test);
			// 季度维保逾期数量
			int count_90service_overdue = testService.getCount_90service_Overdue(id_test);
			// 半年维保正常数量
			int count_180service_normal = testService.getCount_180service_Normal(id_test);
			// 半年维保提示数量
			int count_180service_warnning = testService.getCount_180service_Warnning(id_test);
			// 半年维保逾期数量
			int count_180service_overdue = testService.getCount_180service_Overdue(id_test);
			// 年度维保正常数量
			int count_360service_normal = testService.getCount_360service_Normal(id_test);
			// 年度维保提示数量
			int count_360service_warnning = testService.getCount_360service_Warnning(id_test);
			// 年度维保逾期数量
			int count_360service_overdue = testService.getCount_360service_Overdue(id_test);
	
			
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
			mav.addObject("count_15service_normal", count_15service_normal);
			mav.addObject("count_15service_warnning", count_15service_warnning);
			mav.addObject("count_15service_overdue", count_15service_overdue);
			mav.addObject("count_90service_normal", count_90service_normal);
			mav.addObject("count_90service_warnning", count_90service_warnning);
			mav.addObject("count_90service_overdue", count_90service_overdue);
			mav.addObject("count_180service_normal", count_180service_normal);
			mav.addObject("count_180service_warnning", count_180service_warnning);
			mav.addObject("count_180service_overdue", count_180service_overdue);
			mav.addObject("count_360service_normal", count_360service_normal);
			mav.addObject("count_360service_warnning", count_360service_warnning);
			mav.addObject("count_360service_overdue", count_360service_overdue);
			//系统设置中的提示天数
			List<System_setting> system_settingList=system_settingService.list();
			if(system_settingList!=null&&system_settingList.size()>0){
				mav.addObject("system_setting",system_settingList.get(0));
			}
			
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
		// 电梯半月维保正常数量
		if (key.equals("count_15service_normal")) {
			list = testService.listCount_15service_Normal(search, 10, request,id_test);
		}
		// 电梯半月维保提示数量
		if (key.equals("count_15service_warnning")) {
			list = testService.listCount_15service_Warnning(search, 10, request,id_test);
		}
		// 电梯半月维保逾期数量
		if (key.equals("count_15service_overdue")) {
			list = testService.listCount_15service_Overdue(search, 10, request,id_test);
		}
		// 电梯季度维保正常数量
		if (key.equals("count_90service_normal")) {
			list = testService.listCount_90service_Normal(search, 10, request,id_test);
		}
		// 电梯季度维保提示数量
		if (key.equals("count_90service_warnning")) {
			list = testService.listCount_90service_Warnning(search, 10, request,id_test);
		}
		// 电梯季度维保逾期数量
		if (key.equals("count_90service_overdue")) {
			list = testService.listCount_90service_Overdue(search, 10, request,id_test);
		}
		// 电梯半年维保正常数量
		if (key.equals("count_180service_normal")) {
			list = testService.listCount_180service_Normal(search, 10, request,id_test);
		}
		//电梯半年维保提示数量
		if (key.equals("count_180service_warnning")) {
			list = testService.listCount_180service_Warnning(search, 10, request,id_test);
		}
		//电梯半年维保逾期数量
		if (key.equals("count_180service_overdue")) {
			list = testService.listCount_180service_Overdue(search, 10, request,id_test);
		}
		//电梯年度维保正常数量
		if (key.equals("count_360service_normal")) {
			list = testService.listCount_360service_Normal(search, 10, request,id_test);
		}
		//电梯年度维保提示数量
		if (key.equals("count_360service_warnning")) {
			list = testService.listCount_360service_Warnning(search, 10, request,id_test);
		}
		//电梯年度维保逾期数量
		if (key.equals("count_360service_overdue")) {
			list = testService.listCount_360service_Overdue(search, 10, request,id_test);
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
	public ModelAndView task(int idtester,String start,String end,HttpServletRequest request){
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("30")&&!op.getTypeOperator().equals("31")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","当前登录人非检验检测单位人员!");
			return mav;
		}else{
			//第一次默认查询当前月份
			String from=request.getParameter("from");
			if("m".equals(from)){
				//设置到当前月的第一天
				Calendar c=Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH, 1);
				start=DateUtils.format1(c.getTime());
				//获取当月最后一天
				c.add(Calendar.MONTH, 1);
				c.add(Calendar.DAY_OF_MONTH, -1);
				end=DateUtils.format1(c.getTime());
			}
			//查询本使用单位的检测人员
			List<Tester> testerList=testerService.listByTestId(op.getIdOrganization());
			//查询本使用单位的巡检任务量
			int countType0=testService.getCountMaintType0(op.getIdOrganization(), start, end,idtester);
			int countType=testService.getCountMaint(op.getIdOrganization(), start, end,idtester);
			ModelAndView mav=new ModelAndView("system/testTask");
			mav.addObject("countType0", countType0);
			mav.addObject("countType",countType);
			mav.addObject("testerList",testerList);
			mav.addObject("start",start);
			mav.addObject("end",end);
			return mav;
		}
	}
	//任务量列表
	@RequestMapping("listForTask")
	public ModelAndView listForTask(int type,String start,String end,int idtester,HttpServletRequest request){
		Operator op=(Operator)request.getSession().getAttribute("login");
		//获取当前登录人的单位编号
		int id_test=op.getIdOrganization();
		List<Maint_report_id> list=testService.listByType(id_test,type, start, end,idtester,request);
		ModelAndView mav=new ModelAndView("system/serviceListForTask");
//		String typeName="";
//		if(type!=-1){
//			typeName=mriService.getTypeNameById(type);
//		}else{
//			typeName="配合维保";
//		}
		
		mav.addObject("list",list);
		if(type==0){
			mav.addObject("typeName","巡视");
		}else{
			mav.addObject("typeName","维保");
		}
		return mav;
	}
}
