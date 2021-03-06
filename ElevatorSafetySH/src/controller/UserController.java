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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.CitylistService;
import service.DeptGroupService;
import service.HistoryService;
import service.History_listService;
import service.Maint_report_idService;
import service.OperatorService;
import service.System_settingService;
import service.UserService;
import util.DateUtils;
import util.MD5;
import vo.Elevator;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Maint_report_id;
import vo.Operator;
import vo.System_setting;
import vo.User;

@Controller
@RequestMapping("user")
public class UserController {
   @Resource
   public UserService userService;
   @Resource
   public HistoryService historyService;
   @Resource
   public History_listService history_listService;
   @Resource
   public Maint_report_idService mriService;
   @Resource
   public CitylistService cityService;
   @Resource
   public System_settingService system_settingService;
   @Resource
   public OperatorService operatorService;
   @Resource
   public DeptGroupService deptgroupService;
   public final String PASSWORD_DEFAULT="123456";
   public MD5 md5=new MD5();
   @RequestMapping("list")
   public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/userList");
		List<User> ulist=userService.list(key, 12, request);
		for(User u:ulist){
			u.setRegistCity(cityService.listBy_Idcity(u.getRegisterArea()));
		}
		mav.addObject("userList",ulist);
		return mav;
	}
   /**
    * 技术监督员：使用单位查询
    * @param key
    * @param request
    * @return
    */
   @RequestMapping("list1")
   public ModelAndView list1(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/userList1");
		List<User> ulist=userService.list(key, 12, request);
		for(User u:ulist){
			u.setRegistCity(cityService.listBy_Idcity(u.getRegisterArea()));
		}
		mav.addObject("userList",ulist);
		return mav;
	}
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<User> userList=userService.list();
   	JSONArray array=JSONArray.fromObject(userList);
   	return array.toString();
   }
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(User user,HttpServletRequest request){

		Serializable ser=userService.insert(user);
		int iduser=-1;
		if(ser!=null){
			iduser=Integer.parseInt(ser.toString());
		}

		History history=new History();
		history.setType(7);//类型
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//登录人id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//当前时间
		int idhistory=-1;
		idhistory = Integer.parseInt(historyService.insert(history).toString());
		//插入信息到systemstate表中（待写）
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(7);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(iduser+"");
		System.out.println( history_listService.insert(hilist).toString());
		//自动添加两个操作员
		//自动加入两个操作员(20160726号需求)
		for(int i=0;i<=1;i++){
			Operator op1=new Operator();
			op1.setLoginname("sy"+user.getIduser()+i);
			op1.setPassword(md5.getMD5ofStr(PASSWORD_DEFAULT));
			op1.setIdcard("");
			op1.setIdcity("");
			op1.setIddistrict("");
			op1.setIdsubdistrict("");
			op1.setIdOrganization(iduser);
			op1.setIdprivilege(14+i);
			op1.setName("-");
			op1.setStatus("1");
			op1.setTypeOperator(20+i+"");
			try {
				operatorService.insert(op1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//添加操作员异常，回滚事务，删除已添加的维保单位和历史记录
				userService.delete(user);
				historyService.delete(history);
				history_listService.delete(hilist);
				iduser=-1;
			}
		}
		return iduser+"";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(User user){
		user=userService.findById(user.getIduser());
		JSONObject object=JSONObject.fromObject(user);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(User user){
		userService.update(user);
		return "ok";
	}
	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delete(User user){
		if(!userService.haveOperator(user)){
			return "no";
		}else{
			try {
				userService.delete(user);
				return "yes";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "no";
			}
		}
	}
	@ResponseBody
	@RequestMapping(value="selectId_user",produces="text/html;charset=utf-8")
	public String selectId_user(){
		List<User> list=userService.list();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
	//使用单位年检提醒
		@RequestMapping("search")
		public ModelAndView search(int iduser,HttpServletRequest request) {
			Operator op=(Operator)request.getSession().getAttribute("login");
			if(!op.getTypeOperator().equals("20")&&!op.getTypeOperator().equals("21")&&!op.getTypeOperator().equals("40")){
				ModelAndView mav=new ModelAndView("error");
				mav.addObject("error","当前登录人非使用单位人员!");
				return mav;
			}else{
				//登录人使用单位
				int id_user=op.getIdOrganization();
				ArrayList<User> users=null;
				ArrayList<Integer> ids=null;
				if(op.getTypeOperator().equals("40")){
					//如果是集团单位成员，查询出该集团下属单位编号
					ids=deptgroupService.getUserIds(id_user);
					//如果是集团单位成员，查询出该集团下属单位
					users=deptgroupService.getUsers(id_user);
				}
				//页面选择下属单位
				if(iduser!=0){
					id_user=iduser;
					ids=null;
				}
				// 电梯总数
				int count = userService.getCount(id_user,ids);
				// 已注册数量
				int count_registed = userService.getCount_Registed(id_user,ids);
				// 停用数量
				int count_stop = userService.getCount_Stop(id_user,ids);
				// 已注销数量
				int count_destory = userService.getCount_Destory(id_user,ids);
				// 未注册数量
				int count_noregist = userService.getCount_NoRegist(id_user,ids);
				// 年检正常数量
				int count_rounds_normal = userService.getCount_Rounds_Normal(id_user,ids);
				// 年检提示数量
				int count_rounds_warnning = userService.getCount_Rounds_Warnning(id_user,ids);
				// 年检预期数量
				int count_rounds_overdue = userService.getCount_Rounds_Overdue(id_user,ids);
				// 半月维保正常数量
				int count_15service_normal = userService.getCount_15service_Normal(id_user,ids);
				// 半月维保提示数量
				int count_15service_warnning = userService.getCount_15service_Warnning(id_user,ids);
				// 半月维保逾期数量
				int count_15service_overdue = userService.getCount_15service_Overdue(id_user,ids);
				// 季度维保正常数量
				int count_90service_normal = userService.getCount_90service_Normal(id_user,ids);
				// 季度维保提示数量
				int count_90service_warnning = userService.getCount_90service_Warnning(id_user,ids);
				// 季度维保逾期数量
				int count_90service_overdue = userService.getCount_90service_Overdue(id_user,ids);
				// 半年维保正常数量
				int count_180service_normal = userService.getCount_180service_Normal(id_user,ids);
				// 半年维保提示数量
				int count_180service_warnning = userService.getCount_180service_Warnning(id_user,ids);
				// 半年维保逾期数量
				int count_180service_overdue = userService.getCount_180service_Overdue(id_user,ids);
				// 年度维保正常数量
				int count_360service_normal = userService.getCount_360service_Normal(id_user,ids);
				// 年度维保提示数量
				int count_360service_warnning = userService.getCount_360service_Warnning(id_user,ids);
				// 年度维保逾期数量
				int count_360service_overdue = userService.getCount_360service_Overdue(id_user,ids);
	
				ModelAndView mav = new ModelAndView("system/userTongji");
				mav.addObject("users",users);
				mav.addObject("iduser",iduser);
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
		public ModelAndView listForSearch(String key, String search,int iduser, HttpServletRequest request) {
			//
			List<Elevator> list = new ArrayList<Elevator>();
			//查询关键字
			if(search==null){
				search="";
			}
			//登录人信息
			Operator op=(Operator)request.getSession().getAttribute("login");
			int id_user=op.getIdOrganization();
			ArrayList<Integer> ids=null;
			if(op.getTypeOperator().equals("40")){
				//如果是集团单位成员，查询出该集团下属单位编号
				ids=deptgroupService.getUserIds(id_user);
			}
			//页面选择下属单位
			if(iduser!=0){
				id_user=iduser;
				ids=null;
			}
			// 电梯总数量
			if (key.equals("count")) {
				list = userService.listCount(search, 10, request,id_user,ids);
			}
			// 电梯已注册数量
			if (key.equals("count_registed")) {
				list = userService.listCount_Registed(search, 10, request,id_user,ids);
			}
			// 电梯未注册数量
			if (key.equals("count_noregist")) {
				list = userService.listCount_NoRegist(search, 10, request,id_user,ids);
			}
			// 电梯已停用数量
			if (key.equals("count_stop")) {
				list = userService.listCount_Stop(search, 10, request,id_user,ids);
			}
			// 电梯已注销数量
			if (key.equals("count_destory")) {
				list = userService.listCount_Destory(search, 10, request,id_user,ids);
			}
			// 电梯年检正常数量
			if (key.equals("count_rounds_normal")) {
				list = userService.listCount_Rounds_Normal(search, 10, request,id_user,ids);
			}
			// 电梯年检提示数量
			if (key.equals("count_rounds_warnning")) {
				list = userService.listCount_Rounds_Warnning(search, 10, request,id_user,ids);
			}
			// 电梯年检逾期数量
			if (key.equals("count_rounds_overdue")) {
				list = userService.listCount_Rounds_Overdue(search, 10, request,id_user,ids);
			}
			// 电梯半月维保正常数量
			if (key.equals("count_15service_normal")) {
				list = userService.listCount_15service_Normal(search, 10, request,id_user,ids);
			}
			// 电梯半月维保提示数量
			if (key.equals("count_15service_warnning")) {
				list = userService.listCount_15service_Warnning(search, 10, request,id_user,ids);
			}
			// 电梯半月维保逾期数量
			if (key.equals("count_15service_overdue")) {
				list = userService.listCount_15service_Overdue(search, 10, request,id_user,ids);
			}
			// 电梯季度维保正常数量
			if (key.equals("count_90service_normal")) {
				list = userService.listCount_90service_Normal(search, 10, request,id_user,ids);
			}
			// 电梯季度维保提示数量
			if (key.equals("count_90service_warnning")) {
				list = userService.listCount_90service_Warnning(search, 10, request,id_user,ids);
			}
			// 电梯季度维保逾期数量
			if (key.equals("count_90service_overdue")) {
				list = userService.listCount_90service_Overdue(search, 10, request,id_user,ids);
			}
			// 电梯半年维保正常数量
			if (key.equals("count_180service_normal")) {
				list = userService.listCount_180service_Normal(search, 10, request,id_user,ids);
			}
			//电梯半年维保提示数量
			if (key.equals("count_180service_warnning")) {
				list = userService.listCount_180service_Warnning(search, 10, request,id_user,ids);
			}
			//电梯半年维保逾期数量
			if (key.equals("count_180service_overdue")) {
				list = userService.listCount_180service_Overdue(search, 10, request,id_user,ids);
			}
			//电梯年度维保正常数量
			if (key.equals("count_360service_normal")) {
				list = userService.listCount_360service_Normal(search, 10, request,id_user,ids);
			}
			//电梯年度维保提示数量
			if (key.equals("count_360service_warnning")) {
				list = userService.listCount_360service_Warnning(search, 10, request,id_user,ids);
			}
			//电梯年度维保逾期数量
			if (key.equals("count_360service_overdue")) {
				list = userService.listCount_360service_Overdue(search, 10, request,id_user,ids);
			}

			ModelAndView mav = new ModelAndView("system/elevatorList");
			mav.addObject("list", list);
			mav.addObject("key",key);
			mav.addObject("search",search);
			mav.addObject("requestMapping", "user");
			return mav;
		}
		//安全员任务量统计
		@RequestMapping("task")
		public ModelAndView task(String start,String end,HttpServletRequest request){
			Operator op=(Operator)request.getSession().getAttribute("login");
			if(!op.getTypeOperator().equals("20")&&!op.getTypeOperator().equals("21")){
				ModelAndView mav=new ModelAndView("error");
				mav.addObject("error","当前登录人非使用单位人员!");
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
				//查询本使用单位的巡检任务量
				int countType0=userService.getCountMaintType0(op.getIdOrganization(), start, end);
				int countType=userService.getCountMaint(op.getIdOrganization(), start, end);
				ModelAndView mav=new ModelAndView("system/userTask");
				mav.addObject("countType0", countType0);
				mav.addObject("countType",countType);
				mav.addObject("start",start);
				mav.addObject("end",end);
				return mav;
			}
		}
		//任务量列表
		@RequestMapping("listForTask")
		public ModelAndView listForTask(int type,String start,String end,HttpServletRequest request){
			Operator op=(Operator)request.getSession().getAttribute("login");
			//获取当前登录人的单位编号
			int id_user=op.getIdOrganization();
			List<Maint_report_id> list=userService.listByType(id_user,type, start, end,request);
			ModelAndView mav=new ModelAndView("system/serviceListForTask");
//			String typeName="";
//			if(type!=-1){
//				typeName=mriService.getTypeNameById(type);
//			}else{
//				typeName="配合维保";
//			}
			
			mav.addObject("list",list);
			if(type==0){
				mav.addObject("typeName","巡视");
			}else{
				mav.addObject("typeName","维保");
			}
			return mav;
		}
}
