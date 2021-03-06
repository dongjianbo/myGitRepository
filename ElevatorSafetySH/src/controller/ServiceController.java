package controller;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.CitylistService;
import service.HistoryService;
import service.History_listService;
import service.Maint_report_idService;
import service.OperatorService;
import service.ServiceService;
import service.ServicerService;
import service.System_settingService;
import util.DateUtils;
import util.MD5;
import vo.Elevator;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Maint_report_id;
import vo.Operator;
import vo.Service1;
import vo.Servicer;
import vo.System_setting;

@Controller
@RequestMapping("service")
public class ServiceController {
	@Resource
	public ServiceService serviceService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@Resource
	public Maint_report_idService mriService;
	@Resource
	public CitylistService cityService;
	@Resource
	public ServicerService servicerService;
	@Resource
	public System_settingService system_settingService;
	@Resource
	public OperatorService operatorService;
	public final String PASSWORD_DEFAULT="123456";
	public MD5 md5=new MD5();
    @RequestMapping("list")
    public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/serviceList");
		List<Service1> slist=serviceService.list(key, 12, request);
		for(Service1 s:slist){
			s.setRegistCity(cityService.listBy_Idcity(s.getRegisterArea()));
		}
		mav.addObject("serviceList",slist);
		return mav;
	}
    /**
     * 技术监督员的维保单位查询
     * @param key
     * @param request
     * @return
     */
    @RequestMapping("list1")
    public ModelAndView list1(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/serviceList1");
		List<Service1> slist=serviceService.list(key, 12, request);
		for(Service1 s:slist){
			s.setRegistCity(cityService.listBy_Idcity(s.getRegisterArea()));
		}
		mav.addObject("serviceList",slist);
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

		int idservice=-1;
		Serializable ser=null;
		try {
			ser = serviceService.insert(service);
		} catch (Exception e) {
			System.out.println("添加维保单位异常");
			return idservice+"";
		}
		if(ser!=null){
			//添加history
			idservice=Integer.parseInt(ser.toString());
			History history=new History();
			history.setType(5);//类型
			Operator op=(Operator)request.getSession().getAttribute("login");
			history.setOperator(op.getIdoperator());//登录人id
			Date date=new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s=format.format(date);
			history.setDatetime(s);//当前时间
			Serializable ser1=null;
			try {
				ser1 = historyService.insert(history);
			} catch (Exception e) {
				System.out.println("添加history异常，事务回滚，删除已添加的维保单位");
				serviceService.delete(service);
			}
			History_list hilist=new History_list();
			if(ser1!=null){
				//添加historylist
				int idhistory=Integer.parseInt(ser1.toString());
				//插入信息到systemstate表中（待写）
			    
			    History_listKey key=new History_listKey();
			    key.setIdhistory(idhistory);
			    key.setKey(5);
		        hilist.setKey(key);//key表示复合主键的类
		        hilist.setValue(idservice+"");
				try {
					history_listService.insert(hilist);
				} catch (Exception e) {
					System.out.println("添加historylist异常，事务回滚，删除已添加的service和history");
					serviceService.delete(service);
					historyService.delete(history);
					idservice=-1;
				}
			}else{
				//插入不成功则删除维保单位
				serviceService.delete(service);
				idservice=-1;
			}
			//自动加入两个操作员(20160726号需求)
			for(int i=0;i<=1;i++){
				Operator op1=new Operator();
				op1.setLoginname("wb"+service.getIdservice()+i);
				op1.setPassword(md5.getMD5ofStr(PASSWORD_DEFAULT));
				op1.setIdcard("");
				op1.setIdcity("");
				op1.setIddistrict("");
				op1.setIdsubdistrict("");
				op1.setIdOrganization(idservice);
				op1.setIdprivilege(12+i);
				op1.setName("-");
				op1.setStatus("1");
				op1.setTypeOperator(10+i+"");
				try {
					operatorService.insert(op1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//添加操作员异常，回滚事务，删除已添加的维保单位和历史记录
					serviceService.delete(service);
					historyService.delete(history);
					history_listService.delete(hilist);
					idservice=-1;
				}
			}
			
		}
		return idservice+"";

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
	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delete(Service1 service){
		if(!serviceService.haveOperator(service)){
			return "no";
		}else{
			try {
				serviceService.delete(service);
				return "yes";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "no";
			}
		}
	}
	@ResponseBody
    @RequestMapping(value="selectId_service",produces="text/html;charset=utf-8")
    public String selectId_service(){
		List<Service1> list=serviceService.select_Idservice();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
	//维保单位任务提醒
	@RequestMapping("search")
	public ModelAndView search(HttpServletRequest request){
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("10")&&!op.getTypeOperator().equals("11")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","当前登录人非维保单位人员!");
			return mav;
		}else{
			//登录人维保单位
			int id_service=op.getIdOrganization();
			Service1 service=serviceService.findById(id_service);
			// 电梯总数
			int count = serviceService.getCount(id_service);
			// 已注册数量
			int count_registed = serviceService.getCount_Registed(id_service);
			// 停用数量
			int count_stop = serviceService.getCount_Stop(id_service);
			// 已注销数量
			int count_destory = serviceService.getCount_Destory(id_service);
			// 未注册数量
			int count_noregist = serviceService.getCount_NoRegist(id_service);
			//年检正常数量
			int count_rounds_normal=serviceService.getCount_Rounds_Normal(id_service);
			//年检提示数量
			int count_rounds_warnning=serviceService.getCount_Rounds_Warnning(id_service);
			//年检逾期数量
			int count_rounds_overdue=serviceService.getCount_Rounds_Overdue(id_service);
			// 半月维保正常数量
			int count_15service_normal = serviceService.getCount_15service_Normal(id_service);
			// 半月维保提示数量
			int count_15service_warnning = serviceService.getCount_15service_Warnning(id_service);
			// 半月维保逾期数量
			int count_15service_overdue = serviceService.getCount_15service_Overdue(id_service);
			// 季度维保正常数量
			int count_90service_normal = serviceService.getCount_90service_Normal(id_service);
			// 季度维保提示数量
			int count_90service_warnning = serviceService.getCount_90service_Warnning(id_service);
			// 季度维保逾期数量
			int count_90service_overdue = serviceService.getCount_90service_Overdue(id_service);
			// 半年维保正常数量
			int count_180service_normal = serviceService.getCount_180service_Normal(id_service);
			// 半年维保提示数量
			int count_180service_warnning = serviceService.getCount_180service_Warnning(id_service);
			// 半年维保逾期数量
			int count_180service_overdue = serviceService.getCount_180service_Overdue(id_service);
			// 年度维保正常数量
			int count_360service_normal = serviceService.getCount_360service_Normal(id_service);
			// 年度维保提示数量
			int count_360service_warnning = serviceService.getCount_360service_Warnning(id_service);
			// 年度维保逾期数量
			int count_360service_overdue = serviceService.getCount_360service_Overdue(id_service);

			ModelAndView mav = new ModelAndView("system/serviceTongji");
			mav.addObject("service",service);
			mav.addObject("count", count);
			mav.addObject("count_registed", count_registed);
			mav.addObject("count_stop", count_stop);
			mav.addObject("count_destory", count_destory);
			mav.addObject("count_noregist", count_noregist);
			mav.addObject("count_rounds_normal",count_rounds_normal);
			mav.addObject("count_rounds_warnning",count_rounds_warnning);
			mav.addObject("count_rounds_overdue",count_rounds_overdue);
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
			int id_service=op.getIdOrganization();
			// 电梯总数量
			if (key.equals("count")) {
				list = serviceService.listCount(search, 10, request,id_service);
			}
			// 电梯已注册数量
			if (key.equals("count_registed")) {
				list = serviceService.listCount_Registed(search, 10, request,id_service);
			}
			// 电梯未注册数量
			if (key.equals("count_noregist")) {
				list = serviceService.listCount_NoRegist(search, 10, request,id_service);
			}
			// 电梯已停用数量
			if (key.equals("count_stop")) {
				list = serviceService.listCount_Stop(search, 10, request,id_service);
			}
			// 电梯已注销数量
			if (key.equals("count_destory")) {
				list = serviceService.listCount_Destory(search, 10, request,id_service);
			}
			//电梯年检正常数量
			if(key.equals("count_rounds_normal")){
				list=serviceService.listCount_Rounds_Normal(search, 10, request,id_service);
			}
			//电梯年检提示数量
			if(key.equals("count_rounds_warnning")){
				list=serviceService.listCount_Rounds_Warnning(search, 10, request,id_service);
			}
			//电梯年检逾期数量
			if(key.equals("count_rounds_overdue")){
				list=serviceService.listCount_Rounds_Overdue(search, 10, request,id_service);
			}
			// 电梯半月维保正常数量
			if (key.equals("count_15service_normal")) {
				list = serviceService.listCount_15service_Normal(search, 10, request,id_service);
			}
			// 电梯半月维保提示数量
			if (key.equals("count_15service_warnning")) {
				list = serviceService.listCount_15service_Warnning(search, 10, request,id_service);
			}
			// 电梯半月维保逾期数量
			if (key.equals("count_15service_overdue")) {
				list = serviceService.listCount_15service_Overdue(search, 10, request,id_service);
			}
			// 电梯季度维保正常数量
			if (key.equals("count_90service_normal")) {
				list = serviceService.listCount_90service_Normal(search, 10, request,id_service);
			}
			// 电梯季度维保提示数量
			if (key.equals("count_90service_warnning")) {
				list = serviceService.listCount_90service_Warnning(search, 10, request,id_service);
			}
			// 电梯季度维保逾期数量
			if (key.equals("count_90service_overdue")) {
				list = serviceService.listCount_90service_Overdue(search, 10, request,id_service);
			}
			// 电梯半年维保正常数量
			if (key.equals("count_180service_normal")) {
				list = serviceService.listCount_180service_Normal(search, 10, request,id_service);
			}
			//电梯半年维保提示数量
			if (key.equals("count_180service_warnning")) {
				list = serviceService.listCount_180service_Warnning(search, 10, request,id_service);
			}
			//电梯半年维保逾期数量
			if (key.equals("count_180service_overdue")) {
				list = serviceService.listCount_180service_Overdue(search, 10, request,id_service);
			}
			//电梯年度维保正常数量
			if (key.equals("count_360service_normal")) {
				list = serviceService.listCount_360service_Normal(search, 10, request,id_service);
			}
			//电梯年度维保提示数量
			if (key.equals("count_360service_warnning")) {
				list = serviceService.listCount_360service_Warnning(search, 10, request,id_service);
			}
			//电梯年度维保逾期数量
			if (key.equals("count_360service_overdue")) {
				list = serviceService.listCount_360service_Overdue(search, 10, request,id_service);
			}

			ModelAndView mav = new ModelAndView("system/elevatorList");
			mav.addObject("list", list);
			mav.addObject("key",key);
			mav.addObject("search",search);
			mav.addObject("requestMapping", "service");
			
			return mav;
		}
		//维保单位任务量统计
		@RequestMapping("task")
		public ModelAndView task(int idservicer,String start,String end,HttpServletRequest request){
			Operator op=(Operator)request.getSession().getAttribute("login");
			if(!op.getTypeOperator().equals("10")&&!op.getTypeOperator().equals("11")){
				ModelAndView mav=new ModelAndView("error");
				mav.addObject("error","当前登录人非维保单位人员!");
				return mav;
			}else{
				//得到当前登录人的单位编号
				int id_service=op.getIdOrganization();
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
				//查询当前单位的维保人员
				List<Servicer> slist=servicerService.listByIdService(id_service);
				Map<String, Integer> data=serviceService.getTask(id_service,start, end,idservicer);
				ModelAndView mav=new ModelAndView("system/serviceTask");
				mav.addObject("data",data);
				mav.addObject("slist", slist);
				mav.addObject("start",start);
				mav.addObject("end",end);
				return mav;
			}
		}
		//任务量列表
		@RequestMapping("listForTask")
		public ModelAndView listForTask(int type,String start,String end,int idservicer,HttpServletRequest request){
			Operator op=(Operator)request.getSession().getAttribute("login");
			//得到当前登录人的单位编号
			int id_service=op.getIdOrganization();
			List<Maint_report_id> list=serviceService.listByTaskType(id_service,type, start, end,idservicer,request);
			ModelAndView mav=new ModelAndView("system/serviceListForTask");
			//String typeName=mriService.getTypeNameById(type);
			mav.addObject("list",list);
			if(type==0){
				mav.addObject("typeName","巡视");
			}else{
				mav.addObject("typeName","维保");
			}
			
			return mav;
		}
		@RequestMapping(value="listByObject", produces = "text/html; charset=utf-8")
		@ResponseBody
		public String getListByIdCity(){
			//查询所有的电梯
			List<Service1> serviceList=serviceService.list();
			JSONArray jsonarray=JSONArray.fromObject(serviceList);
			return jsonarray.toString();
		}
}
