package controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.CitylistService;
import service.DesignerService;
import service.DistictlistService;
import service.DistinctGisService;
import service.ElevatorService;
import service.Elevator_doorService;
import service.Elevator_stateService;
import service.Elevator_tag_init_taskService;
import service.Elevator_type_defService;
import service.HistoryService;
import service.History_listService;
import service.InstallerService;
import service.ManuferService;
import service.ModellistService;
import service.OperatorService;
import service.OwnerService;
import service.Register_status_defService;
import service.SaferService;
import service.ServiceService;
import service.ServicerService;
import service.Site_defService;
import service.SubdistictlistService;
import service.System_settingService;
import service.SystemstateService;
import service.TestService;
import service.UserService;
import util.DateUtils;
import vo.DistinctGis;
import vo.E_data_history;
import vo.Elevator;
import vo.Elevator_door;
import vo.Elevator_doorKey;
import vo.Elevator_state;
import vo.Elevator_tag_init_task;
import vo.Elevator_type_def;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Manufer;
import vo.Modellist;
import vo.Operator;
import vo.Register_status_def;
import vo.Safer;
import vo.Service1;
import vo.Servicer;
import vo.Site_def;
import vo.System_setting;
import vo.Test;
import vo.User;

@Controller     
@RequestMapping("elevator")
public class ElevatorController {
	@Resource
	public ElevatorService elevatorService;
	@Resource
	public SystemstateService systemService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@Resource
	public CitylistService cityService;
	@Resource
	public DistictlistService distickService;
	@Resource
	public SubdistictlistService subService;
	@Resource
	public Elevator_tag_init_taskService elevator_tag_init_taskService;
	@Resource
	public Elevator_stateService esService;
	@Resource
	public OperatorService operatorService;
	@Resource
	public System_settingService system_settingService;
	@Resource
	public DesignerService designerService;
	@Resource
	public ManuferService manuferService;
	@Resource
	public InstallerService installerService;
	@Resource
	public OwnerService ownerService;
	@Resource
	public UserService userService;
	@Resource
	public ServiceService serviceService;
	@Resource
	public TestService testService;
	@Resource
	public ModellistService modellistService;
	@Resource
	public Register_status_defService regService;
	@Resource
	public Elevator_doorService doorService;
	@Resource
	public ServicerService servicerService;
	@Resource
	public SaferService saferService;
	@Resource
	public Site_defService siteDefService;
	@Resource
	public Elevator_type_defService elevatorTypeDefService;
	@Resource
	public DistinctGisService distinctGisService;
	@Resource
	public System_settingService systemSettingSevice;
	
	@RequestMapping("insert")
	public String insert(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		Elevator el=(Elevator)request.getSession().getAttribute("elevator");//存在第一页的值
		el.setId_city(elevator.getId_city());
		el.setId_district(elevator.getId_district());
		if(elevator.getId_district().equals("00")){
			el.setId_subdistrict("00");
		}else{
			if(elevator.getId_subdistrict().equals("0")||elevator.getId_subdistrict().equals("00")){
				el.setId_subdistrict("00");
			}else{
				el.setId_subdistrict(elevator.getId_subdistrict());
			}
			
		}
		//电梯所在位置加上前面的三个下拉框
		String city=cityService.listBy_Idcity(elevator.getId_city()).getName_city();
		String dist=distickService.getListByCityId(elevator.getId_city(),elevator.getId_district());
		String subs=subService.getListById(elevator.getId_city(), elevator.getId_district(), elevator.getId_subdistrict());
		el.setAddress(city+dist+subs+elevator.getAddress());
		el.setNum_floor_elevator(elevator.getNum_floor_elevator());
		el.setId_elevator_model(elevator.getId_elevator_model());
		el.setDate_declare(elevator.getDate_declare());
		int id_elevator=-1;
		try {
			id_elevator = Integer.parseInt(elevatorService.insert(el).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("电梯插入失败！");
			return "/system/insertElevatorDeclaration";
		}
		request.getSession().setAttribute("id_elevator",id_elevator);
		//systemstate修改字段version_elevator +1
		systemService.update_version_elevator();
		//插入history信息
		History history=new History();
		history.setType(21);//类型
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//登录人id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//当前时间
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//插入信息到history_list表中
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(21);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(id_elevator+"");
        System.out.println( history_listService.insert(hilist).toString());
		//此时插入成功之后 转到elevator_state界面去插入电梯状态的信息
        //根据新需求,插入elevator_state空记录
        Elevator_state es=new Elevator_state();
        es.setIdelevator(el.getId_elevator());
        es.setLabelwrite("0");
        es.setLabeldemo(el.getDesc());
        es.setLastmodified(DateUtils.format(new Date()));
        esService.insert(es);
        //根据新需求，插入elevtor_door记录
        Elevator_door door=new Elevator_door();
        Elevator_doorKey edKey=new Elevator_doorKey();
        edKey.setDoor_id("1");
        door.setNote("first");
        door.setLast_modified(new Date());
        edKey.setElevator_id(id_elevator);
        door.setKey(edKey);
        doorService.save(door);
        System.out.println("已插入elevator_door记录");
        return "/system/insertElevatorDeclaration";
	}
	@RequestMapping("yuaninsert")
	public String yuaninsert(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		Elevator el=(Elevator)request.getSession().getAttribute("yuanelevator");//存在第一页的值
		el.setRegister_status(elevator.getRegister_status());
		el.setId_city(elevator.getId_city());
		el.setId_district(elevator.getId_district());
		if(elevator.getId_district().equals("00")){
			el.setId_subdistrict("00");
		}else{
			if(elevator.getId_subdistrict().equals("0")||elevator.getId_subdistrict().equals("00")){
				el.setId_subdistrict("00");
			}else{
				el.setId_subdistrict(elevator.getId_subdistrict());
			}
		}
		el.setGis_type(elevator.getGis_type());
		//电梯所在位置加上前面的三个下拉框
		String city=cityService.listBy_Idcity(elevator.getId_city()).getName_city();
		String dist=distickService.getListByCityId(elevator.getId_city(),elevator.getId_district());
		String subs=subService.getListById(elevator.getId_city(), elevator.getId_district(), elevator.getId_subdistrict());
		el.setAddress(city+dist+subs+elevator.getAddress());
		el.setNum_floor_elevator(elevator.getNum_floor_elevator());
		el.setId_elevator_model(elevator.getId_elevator_model());
		el.setDate_declare(elevator.getDate_declare());
		el.setCheck_construct(elevator.getCheck_construct());
		el.setId_service(elevator.getId_service());
		el.setId_test(elevator.getId_test());
		el.setCheck_construct_code(elevator.getCheck_construct_code());
		el.setDate_register(elevator.getDate_register());
		el.setDate_enable(elevator.getDate_enable());
		el.setGis_x(elevator.getGis_x());
		el.setGis_y(elevator.getGis_y());
		int id_elevator=-1;
		try {
			id_elevator = Integer.parseInt(elevatorService.insert(el).toString());
		} catch (Exception e) {
			System.out.println("电梯信息插入失败！");
			return "/system/yuanElevatorDeclaration";
		}
		request.getSession().setAttribute("yuanid_elevator",id_elevator);
		//systemstate修改字段version_elevator +1
		systemService.update_version_elevator();
		//插入history信息
		History history=new History();
		history.setType(23);//类型
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//登录人id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//当前时间
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//插入信息到history_list表中
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(21);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(id_elevator+"");
        System.out.println( history_listService.insert(hilist).toString());
        //elevator_tag_init_task表的插入
        Elevator_tag_init_task elevator_tag_init_task=new Elevator_tag_init_task();
        elevator_tag_init_task.setTitle(el.getDesc());
        elevator_tag_init_task.setElevator_id(el.getId_elevator());
        //插入之后 查询类型
        Elevator leix=elevatorService.getEById(id_elevator);
        elevator_tag_init_task.setElevator_type(leix.getModel().getElevator_type_def().getElevatortype());
        elevator_tag_init_task.setElevator_code(el.getDevice_code());
        elevator_tag_init_task.setElevator_address(el.getAddress());
        elevator_tag_init_task.setElevator_layer_number(el.getNum_floor_elevator());
        elevator_tag_init_taskService.insert(elevator_tag_init_task);
        //根据新需求，插入elevtor_door记录
        Elevator_door door=new Elevator_door();
        Elevator_doorKey edKey=new Elevator_doorKey();
        edKey.setDoor_id("1");
        door.setNote("first");
        door.setLast_modified(new Date());
        edKey.setElevator_id(id_elevator);
        door.setKey(edKey);
        doorService.save(door);
        System.out.println("已插入elevator_door记录");
        //此时插入成功之后 转到elevator_state界面去插入电梯状态的信息
        
        
        return "/system/insertElevator_state";
	}
	@RequestMapping("insertTo1")
	public String insert1(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("elevator", elevator);
		return "/system/insertElevatorDeclaration1";
	}
	@RequestMapping("insertTo2")
	public String insert2(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("yuanelevator", elevator);
		return "/system/yuanElevatorDeclaration1";
	}
	

	//技术监督部门统计查询
		@RequestMapping("search")
		public ModelAndView search(String key,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc,HttpServletRequest request) {
			//查找当前登录人
			Operator op=(Operator)request.getSession().getAttribute("login");
			if(!op.getTypeOperator().equals("00")){
				ModelAndView mav=new ModelAndView("error");
				mav.addObject("error","当前登录人非技术监督部门人员!");
				return mav;
			}else{
				op=operatorService.findById(op.getIdoperator());
				//登录人所属区域
				if(key!=null&&key.equals("first")){
					//第一次登录，没有查询，所以得从session中取地址
					id_city=op.getIdcity();
					id_district=op.getIddistrict();
					id_subdistrict=op.getIdsubdistrict();
					System.out.println("id_city:"+id_city);
					System.out.println("id_district:"+id_district);
					System.out.println("id_subdistrict:"+id_subdistrict);
				}
//				try {
//					if(desc!=null){
//						System.out.println(desc);
//						desc=new String(desc.getBytes("ISO-8859-1"),"UTF-8");
//						System.out.println(desc+"==========");
//					}
//					
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				// 电梯总数
				int count = elevatorService.getCount(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc,"");
				// 已注册数量
				int count_registed = elevatorService.getCount_Registed(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 停用数量
				int count_stop = elevatorService.getCount_Stop(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 已注销数量
				int count_destory = elevatorService.getCount_Destory(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 未注册数量
				int count_noregist = elevatorService.getCount_NoRegist(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 年检正常数量
				int count_rounds_normal = elevatorService.getCount_Rounds_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 年检提示数量
				int count_rounds_warnning = elevatorService.getCount_Rounds_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 年检预期数量
				int count_rounds_overdue = elevatorService.getCount_Rounds_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 半月维保正常数量
				int count_15service_normal = elevatorService.getCount_15service_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 半月维保提示数量
				int count_15service_warnning = elevatorService.getCount_15service_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 半月维保逾期数量
				int count_15service_overdue = elevatorService.getCount_15service_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 季度维保正常数量
				int count_90service_normal = elevatorService.getCount_90service_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 季度维保提示数量
				int count_90service_warnning = elevatorService.getCount_90service_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 季度维保逾期数量
				int count_90service_overdue = elevatorService.getCount_90service_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 半年维保正常数量
				int count_180service_normal = elevatorService.getCount_180service_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 半年维保提示数量
				int count_180service_warnning = elevatorService.getCount_180service_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 半年维保逾期数量
				int count_180service_overdue = elevatorService.getCount_180service_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 年度维保正常数量
				int count_360service_normal = elevatorService.getCount_360service_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 年度维保提示数量
				int count_360service_warnning = elevatorService.getCount_360service_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
				// 年度维保逾期数量
				int count_360service_overdue = elevatorService.getCount_360service_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		
				ModelAndView mav = new ModelAndView("system/elevatorTongji");
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
				mav.addObject("id_city", id_city);
				mav.addObject("id_district", id_district);
				mav.addObject("id_subdistrict", id_subdistrict);
				
				try {
					if(desc!=null&&!"".equals(desc)){
						mav.addObject("desc",URLEncoder.encode(desc,"UTF-8"));
					}else{
						mav.addObject("desc",null);
					}
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

		public ModelAndView listForSearch(String key, String search, HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc,String elevator_type,String gis_type) {
			System.out.println( key+"-"+search+"-"+id_city+"-"+id_district+"-"+id_subdistrict+"-"+id_service+"-"+id_user+"-"+id_test+"-"+keyType+"-"+desc+"-"+elevator_type+"-"+gis_type);
			
			//
			List<Elevator> list = new ArrayList<Elevator>();
			//因为是在url后面传过来的，所以需要转码
//			try {
//				if(desc!=null){
//					desc=new String(desc.getBytes("ISO-8859-1"),"UTF-8");
//				}
//				
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
			//查询关键字
			if(search==null){
				search="";
			}
			// 电梯总数量
			if (key.equals("count")) {
				list = elevatorService.listCount(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc,gis_type);
			}
			// 电梯已注册数量
			if (key.equals("count_registed")) {
				list = elevatorService.listCount_Registed(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯未注册数量
			if (key.equals("count_noregist")) {
				list = elevatorService.listCount_NoRegist(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯已停用数量
			if (key.equals("count_stop")) {
				list = elevatorService.listCount_Stop(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯已注销数量
			if (key.equals("count_destory")) {
				list = elevatorService.listCount_Destory(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯年检正常数量
			if (key.equals("count_rounds_normal")) {
				list = elevatorService.listCount_Rounds_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯年检提示数量
			if (key.equals("count_rounds_warnning")) {
				list = elevatorService.listCount_Rounds_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯年检逾期数量
			if (key.equals("count_rounds_overdue")) {
				list = elevatorService.listCount_Rounds_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯半月维保正常数量
			if (key.equals("count_15service_normal")) {
				list = elevatorService.listCount_15service_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯半月维保提示数量
			if (key.equals("count_15service_warnning")) {
				list = elevatorService.listCount_15service_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯半月维保逾期数量
			if (key.equals("count_15service_overdue")) {
				list = elevatorService.listCount_15service_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯季度维保正常数量
			if (key.equals("count_90service_normal")) {
				list = elevatorService.listCount_90service_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯季度维保提示数量
			if (key.equals("count_90service_warnning")) {
				list = elevatorService.listCount_90service_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯季度维保逾期数量
			if (key.equals("count_90service_overdue")) {
				list = elevatorService.listCount_90service_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			// 电梯半年维保正常数量
			if (key.equals("count_180service_normal")) {
				list = elevatorService.listCount_180service_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			//电梯半年维保提示数量
			if (key.equals("count_180service_warnning")) {
				list = elevatorService.listCount_180service_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			//电梯半年维保逾期数量
			if (key.equals("count_180service_overdue")) {
				list = elevatorService.listCount_180service_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			//电梯年度维保正常数量
			if (key.equals("count_360service_normal")) {
				list = elevatorService.listCount_360service_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			//电梯年度维保提示数量
			if (key.equals("count_360service_warnning")) {
				list = elevatorService.listCount_360service_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			//电梯年度维保逾期数量
			if (key.equals("count_360service_overdue")) {
				list = elevatorService.listCount_360service_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			}
			//电梯不同类型数量
			if(key.equals("countByType")){
				list = elevatorService.listCountByType(0,search, elevator_type, 10, request, id_city, id_district, id_subdistrict, id_service, id_user, id_test, keyType, desc,gis_type);
			}
			//15年以上电梯数量
			if(key.equals("count15Years")){
				list = elevatorService.getListFor15Years(search, 10, request, id_city, id_district, id_subdistrict, id_service, id_user, id_test, keyType, desc,gis_type);
			}
			//15年以上电梯不同类型数量
			if(key.equals("count15YearsByType")){
				list = elevatorService.listCountByType(15,search, elevator_type, 10, request, id_city, id_district, id_subdistrict, id_service, id_user, id_test, keyType, desc,gis_type);
			}
			ModelAndView mav = new ModelAndView("system/elevatorList");
			mav.addObject("list", list);
			mav.addObject("key",key);
			mav.addObject("search",search);
			mav.addObject("requestMapping", "elevator");
			return mav;
		}
	
				
	//通过id查询电梯的详细信息
	@RequestMapping("selectElevatorByID")
	public ModelAndView selectElevatorByID(Elevator e){
		ModelAndView mav=new ModelAndView("system/elevatorinfrom");
		Elevator_state es=esService.findById(e.getId_elevator());
		mav.addObject("eById",elevatorService.getEById(e.getId_elevator()));
		mav.addObject("es",es);
		return mav;
	}
	//查询未注册的电梯
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/ElevatorRegist");
		mav.addObject("elevListRegist", elevatorService.listCount_NoRegist("",12, request,null,null,null,0,0,0,0,null));
		return mav;
	}
	@RequestMapping("list1")
	public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/ElevatorRegist");
		System.out.println(key);
		mav.addObject("elevListRegist", elevatorService.listCount_NoRegist(key,12, request,null,null,null,0,0,0,0,null));
		return mav;
	}
	
	@RequestMapping(value="regist",produces="text/html;charset=utf-8")
	@ResponseBody
	public String regist(Elevator e,HttpServletRequest request){
		//查询该电梯
		request.getSession().setAttribute("id_elevator1", e.getId_elevator());
		Elevator elevator=elevatorService.getEById(e.getId_elevator());
		elevator.setRegister_status("1");
		elevator.setId_service(e.getId_service());
		elevator.setId_test(e.getId_test());
		elevator.setCheck_construct_code(e.getCheck_construct_code());
		//注册时间为当前时间
		Date time=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String s=format.format(time);
		elevator.setDate_register(s);
		elevator.setDate_enable(e.getDate_enable());
		//注册
		elevatorService.update(elevator);//修改
		//systemstate修改字段version_elevator +1
		systemService.update_version_elevator();
		//插入history信息
		History history=new History();
		history.setType(22);//类型
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//登录人id
		Date date=new Date();
		SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s2=format1.format(date);
		history.setDatetime(s2);//当前时间
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//插入信息到history_list表中
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(21);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(e.getId_elevator()+"");
        System.out.println( history_listService.insert(hilist).toString());
		//elevator_tag_init_task表的插入
        Elevator_tag_init_task elevator_tag_init_task=new Elevator_tag_init_task();
        elevator_tag_init_task.setTitle(elevator.getDesc());
        elevator_tag_init_task.setElevator_id(e.getId_elevator());
        elevator_tag_init_task.setElevator_type(elevator.getModel().getElevator_type_def().getElevatortype());
        elevator_tag_init_task.setElevator_code(elevator.getDevice_code());
        elevator_tag_init_task.setElevator_address(elevator.getAddress());
        elevator_tag_init_task.setElevator_layer_number(elevator.getNum_floor_elevator());
        elevator_tag_init_taskService.insert(elevator_tag_init_task);
        //修改elevator_state中的时间为当前时间
        Elevator_state es=esService.findById(e.getId_elevator());
        if(es==null){
        	es=new Elevator_state();
            es.setIdelevator(e.getId_elevator());
            es.setLabelwrite("0");
            es.setLabeldemo(e.getDesc());
            es.setLastmodified(DateUtils.format(new Date()));
            es.setLast_15_service(e.getDate_enable());
            es.setLast_180_service(e.getDate_enable());
            es.setLast_360_service(e.getDate_enable());
            es.setLast_90_service(e.getDate_enable());
            es.setLastrounds(e.getDate_enable());
            es.setLasttest(e.getDate_enable());
            esService.insert(es);
        }else{
        	 es.setLast_15_service(e.getDate_enable());
             es.setLast_180_service(e.getDate_enable());
             es.setLast_360_service(e.getDate_enable());
             es.setLast_90_service(e.getDate_enable());
             es.setLastrounds(e.getDate_enable());
             es.setLasttest(e.getDate_enable());
             esService.update(es);
        }
        return "ok";//最后
		
	}
	//查询所有的电梯
	@RequestMapping(value="elevatorIdList",produces="text/html;charset=utf-8")
	@ResponseBody
	public String elevatorIdList(HttpServletRequest request){
		List<Elevator> e=elevatorService.listCount1(request);
		JSONArray array=JSONArray.fromObject(e);
		return array.toString();
	}
	//到电梯维护页面
	@RequestMapping("toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/elevatorUpdate");
		//查询所有的电梯
		List<Object[]> elevatorList=elevatorService.listElevator();
		mav.addObject("elevatorList", elevatorList);
		//查询所有的设计单位
		List<Object[]> designerList=designerService.listDesigner();
		//查询所有的生产单位
		List<Manufer> manuferList=manuferService.list();
		//查询所有的安装单位
		List<Object[]> installerList=installerService.listInstaller();
		//查询所有的产权单位
		List<Object[]> ownerList=ownerService.listOwner();
		//查询所有的使用单位 
		List<User> userList=userService.list();
		//查询所有的维保单位
		List<Service1> serviceList=serviceService.list();
		//查询所有的检测单位
		List<Test> testList=testService.list();
		//查询所有的电梯型号
		List<Modellist> modelList=modellistService.list();
		//查询所有的注册状态
		List<Register_status_def> regList=regService.list();
		//查询所有的安装场所类型
		List<Site_def> siteDefList=siteDefService.list();
		mav.addObject("designerList",designerList);
		mav.addObject("manuferList", manuferList);
		mav.addObject("installerList",installerList);
		mav.addObject("ownerList", ownerList);
		mav.addObject("userList", userList);
		mav.addObject("serviceList", serviceList);
		mav.addObject("testList", testList);
		mav.addObject("modelList",modelList);
		mav.addObject("regList",regList);
		mav.addObject("siteDefList",siteDefList);
		return mav;
	}
	//电梯基本信息维护
	@RequestMapping("update")
	public String update(Elevator e,HttpServletRequest request){
		Elevator e_old=elevatorService.getEById(e.getId_elevator());
		e.setId_city(e_old.getId_city());
		e.setId_district(e_old.getId_district());
		e.setId_subdistrict(e_old.getId_subdistrict());
//		e.setGis_x(e_old.getGis_x());
//		e.setGis_y(e_old.getGis_y());
//		e.setGis_type(e_old.getGis_type());
		if(e.getId_elevator()!=-1&&e.getId_elevator()!=0){
			elevatorService.update(e);
		}
		return "redirect:/elevator/toUpdate.do";
	}
	//按ID查询电梯信息
	@RequestMapping(value="getElevatorById",produces="text/html;charset=utf-8")
	@ResponseBody
	public String getElevatorById(int eid,HttpServletRequest request){
		Elevator e=elevatorService.getEById(eid);
		JSONObject jobject=JSONObject.fromObject(e);
		return jobject.toString();
	}
	/**
	 * 2016年7月26新需求
	 * @param key
	 * @param id_city
	 * @param id_district
	 * @param id_subdistrict
	 * @param id_service
	 * @param id_user
	 * @param id_test
	 * @param desc
	 * @param request
	 * @return
	 */
	//监管部门相关统计数字
		@RequestMapping("statistics")
		public ModelAndView statistics(String key,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int keyType,int id_test,String desc,HttpServletRequest request,String gis_type){
			//查找当前登录人
			Operator op=(Operator)request.getSession().getAttribute("login");
			if(!op.getTypeOperator().equals("00")){
				ModelAndView mav=new ModelAndView("error");
				mav.addObject("error","当前登录人非技术监督部门人员!");
				return mav;
			}else{
				op=operatorService.findById(op.getIdoperator());
				if(key!=null&&key.equals("first")){
					//第一次登录，没有查询，所以得从session中取地址
					id_city=op.getIdcity();
					id_district=op.getIddistrict();
					id_subdistrict=op.getIdsubdistrict();
					System.out.println("id_city:"+id_city);
					System.out.println("id_district:"+id_district);
					System.out.println("id_subdistrict:"+id_subdistrict);
				}
				//电梯总数量
				int countFor=elevatorService.getCount(id_city, id_district, id_subdistrict,id_service,id_user,id_test,keyType, desc,gis_type);
				//辖区范围内出厂日期超过15年电梯统计
				int countFor15Years=elevatorService.getCountFor15Years(id_city, id_district, id_subdistrict,id_service,id_user,id_test,desc,gis_type);
				//辖区范围内出厂日期超过15年电梯按类型统计数量
				Map<String, Integer> countForTypeOld=elevatorService.getCountFor15YearsForType(id_city, id_district, id_subdistrict,id_service,id_user,id_test,desc,gis_type);
				List<Elevator_type_def> list=elevatorTypeDefService.list();
				//辖区范围内电梯按类型统计数量
				Map<String, Integer> countForType=elevatorService.getCountForType(id_city, id_district, id_subdistrict,id_service,id_user,id_test,desc,gis_type);
				List<String> keylist=new ArrayList<String>();
				Map<String, Integer> values=new LinkedHashMap<String, Integer>();
				Map<String, Integer> oldvalues=new LinkedHashMap<String, Integer>();
				for(Entry<String, Integer> entry:countForType.entrySet()){
					keylist.add(entry.getKey());
					for(Elevator_type_def def:list){
						if (ObjectUtils.equals(def.getName(), entry.getKey())) {
							values.put(def.getElevatortype(), entry.getValue());
							for(Entry<String, Integer> entry2:countForTypeOld.entrySet()){
								if(ObjectUtils.equals(entry.getKey(), entry2.getKey())){
									oldvalues.put(def.getElevatortype(),entry2.getValue());
									continue;
								}
							}
						}
					}
					
				}
				//辖区内电梯相关使用单位数量、维保单位数量、维保人员数量、安全人员数量
				int userCountForCity=userService.getCountForCity(id_city, id_district, id_subdistrict);
				int serviceCountForCity=serviceService.getCountForCity(id_city, id_district, id_subdistrict);
				int servicerCountForCity=servicerService.getCountForCity(id_city, id_district, id_subdistrict);
				int saferCountForCity=saferService.getCountForCity(id_city, id_district, id_subdistrict);
				ModelAndView mav=new ModelAndView("/system/elevatorStatistics");
				mav.addObject("id_city",id_city);
				mav.addObject("id_district",id_district);
				mav.addObject("id_subdistrict",id_subdistrict);
				mav.addObject("countFor15Years",countFor15Years);
				mav.addObject("countFor",countFor);
				mav.addObject("countForType",countForType);
				mav.addObject("keylist",keylist);
				mav.addObject("values",values);
				mav.addObject("oldvalues",oldvalues);
				mav.addObject("userCountForCity",userCountForCity);
				mav.addObject("serviceCountForCity",serviceCountForCity);
				mav.addObject("servicerCountForCity",servicerCountForCity);
				mav.addObject("gis_type",gis_type);
				mav.addObject("saferCountForCity",saferCountForCity);
				mav.addObject("desc",desc);
				return mav;
			}
		}
	/**
	 * 2017年4月17日新需求
	 * 二维码电梯信息查询
	 * 通过微信扫描二维码
	 * 转到电梯详细信息页面	
	 */
	@RequestMapping("QRCode")
	public ModelAndView QRCode(int eid){
		Elevator e=elevatorService.getEById(eid);
		Elevator_state es=esService.findById(eid);
		ModelAndView mav=new ModelAndView("system/elevatorQRCode");
		mav.addObject("e", e);
		ArrayList<Date> dlist=new ArrayList<Date>();
		dlist.add(DateUtils.parse(es.getLast_15_service()));
		dlist.add(DateUtils.parse(es.getLast_90_service()));
		dlist.add(DateUtils.parse(es.getLast_180_service()));
		dlist.add(DateUtils.parse(es.getLast_360_service()));
		Date max_15=Collections.max(dlist);
		dlist.remove(0);
		Date max_90=Collections.max(dlist);
		dlist.remove(0);
		Date max_180=Collections.max(dlist);
		//半月维保情况
		if(es!=null){
			try {
				String service_15="";
				int days_15=DateUtils.daysBetween(max_15,new Date());
				if(days_15<=15){
					service_15="正常";
				}else{
					service_15="逾期"+(days_15-15)+"天";
				}
				mav.addObject("service_15",service_15);
				String service_90="";
				int days_90=DateUtils.daysBetween(max_90,new Date());
				if(days_90<=90){
					service_90="正常";
				}else{
					service_90="逾期"+(days_90-90)+"天";
				}
				mav.addObject("service_90",service_90);
				String service_180="";
				int days_180=DateUtils.daysBetween(max_180,new Date());
				if(days_180<=180){
					service_180="正常";
				}else{
					service_180="逾期"+(days_180-180)+"天";
				}
				mav.addObject("service_180",service_180);
				String service_360="";
				int days_360=DateUtils.daysBetween(DateUtils.parse(es.getLast_360_service()),new Date());
				if(days_360<=365){
					service_360="正常";
				}else{
					service_360="逾期"+(days_360-365)+"天";
				}
				mav.addObject("service_360",service_360);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mav.addObject("last_rounds",es.getLastrounds());
			//下一年年检时间
			Calendar nextRounds=Calendar.getInstance();
			nextRounds.setTime(DateUtils.parse(es.getLasttest()));
			nextRounds.add(Calendar.YEAR, 1);
			String next_Rounds=DateUtils.format1(nextRounds.getTime());
			mav.addObject("next_rounds",next_Rounds);
			
		}
		
		
		return mav;
	}
	
	// 点击维保单位数量进入单位列表lz
	@RequestMapping("listForCountUser")
	public ModelAndView listForCountUser(HttpServletRequest request,String id_city,String id_district,String id_subdistrict) {
		List<User> list = new ArrayList<User>();
		list =userService.listForCity(id_city, id_district, 10, request, id_subdistrict);
		ModelAndView mav = new ModelAndView("system/userList1");
		mav.addObject("userList", list);
		mav.addObject("requestMapping", "elevator");
		return mav;
	}
	// 点击维保人员数量进入单位列表lz
	@RequestMapping("listForCountService")
	public ModelAndView listForCountService(HttpServletRequest request,String id_city,String id_district,String id_subdistrict) {
		List<Service1> list = new ArrayList<Service1>();
		list =serviceService.listForCity(id_city, id_district, 10, request, id_subdistrict);
		ModelAndView mav = new ModelAndView("system/serviceList1");
		mav.addObject("serviceList", list);
		mav.addObject("requestMapping", "elevator");
		return mav;
	}
	// 点击维保人员数量进入单位列表lz
	@RequestMapping("listForCountServicer")
	public ModelAndView listForCountServicer(HttpServletRequest request,String id_city,String id_district,String id_subdistrict) {
		List<Servicer> list = new ArrayList<Servicer>();
		list =servicerService.listForCity(id_city, id_district, 10, request, id_subdistrict);
		ModelAndView mav = new ModelAndView("system/servicerList1");
		mav.addObject("servicerList", list);
		mav.addObject("requestMapping", "elevator");
		return mav;
	}
	// 点击安全人员数量进入单位列表lz
	@RequestMapping("listForCountSafer")
	public ModelAndView listForCountSafer(HttpServletRequest request,String id_city,String id_district,String id_subdistrict) {
		List<Safer> list = new ArrayList<Safer>();
		list =saferService.listForCity(id_city, id_district, 10, request, id_subdistrict);
		ModelAndView mav = new ModelAndView("system/saferList1");
		mav.addObject("saferList", list);
		mav.addObject("requestMapping", "elevator");
		return mav;
	}
	//查询未注册的电梯
	@RequestMapping("gis")
	public ModelAndView gis(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/testBMap");
		return mav;
	}
	@RequestMapping(value="listByObject", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getListByIdCity(){
		//查询所有的电梯
		List<Elevator> elevatorList=elevatorService.list();
		JSONArray jsonarray=JSONArray.fromObject(elevatorList);
		return jsonarray.toString();
	}
	// 地图
	@RequestMapping(value="map",produces="text/html;charset=utf-8")
	@ResponseBody
	public String map(HttpServletRequest request) {
		Map<String, Object> result=new HashMap<String, Object>();
		//查找当前登录人
		Operator op=(Operator)request.getSession().getAttribute("login");
//		op=operatorService.findById(op.getIdoperator());
		//查找该登录人所在的区域
		String id_city=op.getIdcity();
		String id_district=op.getIddistrict();
		String id_subdistrict=op.getIdsubdistrict();
		DistinctGis distinctGis=distinctGisService.selectByDisId(op.getIddistrict());
		result.put("distinctGis", distinctGis);
		//符合条件的电梯编号
		List<Integer> idList=elevatorService.getElevatorIds(id_city, id_district, id_subdistrict, 0, 0, 0, null);
		List<Elevator> elList=elevatorService.getElevatorByIdList(idList);
		//检验电梯是否有逾期、通知的维保记录
		/*
		 判断逾期规则：
		  半月维保：elevator_state 中 15，90，180，360 四个时间离今天都超过了15天。
		  季度维保：elevator_state 中 90，180，360  三个中最大值离今天都超过了90天。
		  半年维保：elevator_state 中 180，360 二个中的最大值离今天都超过了180天。
		  年度维保：elevator_state 中 360 离今天已超过了360天。
		 年检：last_round中的时间距今天已经超过一年。
		 */
		//查询出系统中设置的提示时间
		System_setting ss=systemSettingSevice.list().get(0);
		for(Elevator e:elList){
			boolean is15=false;//半月维保是否逾期
			boolean is90=false;//季度维保是否逾期
			boolean is180=false;//半年维保是否逾期
			boolean is360=false;//年度维保是否逾期
			boolean isround=false;//年检是否逾期
			boolean pro15=false;//半月维保是否到了提示时间
			boolean pro90=false;//季度维保是否到了提示时间
			boolean pro180=false;//半年维保是否到了提示时间
			boolean pro360=false;//年度维保是否到了提示时间
			boolean proround=false;//年检是否到了提示时间
			//查询该电梯的维保记录
			Elevator_state es=e.getElevator_state();
			if(es!=null){
				//取出四个日期
				long last_15_service=DateUtils.parse(es.getLast_15_service()).getTime();
				long last_90_service=DateUtils.parse(es.getLast_90_service()).getTime();
				long last_180_service=DateUtils.parse(es.getLast_180_service()).getTime();
				long last_360_service=DateUtils.parse(es.getLast_360_service()).getTime();
				long last_round=DateUtils.parse(es.getLastrounds()).getTime();
				List<Long> services=new ArrayList<Long>();
				services.add(last_15_service);
				services.add(last_90_service);
				services.add(last_180_service);
				services.add(last_360_service);
				//找出四个中的最大值
				long max_4=Collections.max(services);
				//找出后三个中的最大值
				services.remove(0);
				long max_3=Collections.max(services);
				//找出后两个中的最大值
				services.remove(0);
				long max_2=Collections.max(services);
				//找出最后一个值
				long max_1=last_360_service;
				//获得当前日期
				long today=DateUtils.parse(DateUtils.format1(new Date())).getTime();
				
				//判断该电梯半月维保是否逾期
				if((today-max_4)>(15L*24*60*60*1000)){
					is15=true;
				}else if((today-max_4)>(15L*24*60*60*1000-ss.getAlarm_15_service()*24L*60*60*1000)){
					//判断该电梯半月维保是否已到提示时间
					pro15=true;
				}
				//判断该电梯季度维保是否逾期
				if((today-max_3)>(90L*24*60*60*1000)){
					is90=true;
				}else if((today-max_4)>(90L*24*60*60*1000-ss.getAlarm_90_service()*24L*60*60*1000)){
					//判断该电梯季度维保是否已到提示时间
					pro90=true;
				}
				//判断该电梯半年维保是否逾期
				if((today-max_2)>(180L*24*60*60*1000)){
					is180=true;
				}else if((today-max_4)>(180L*24*60*60*1000-ss.getAlarm_180_service()*24L*60*60*1000)){
					//判断该电梯半年维保是否已到提示时间
					pro180=true;
				}
				//判断该电梯半年维保是否逾期
				if((today-max_1)>(360L*24*60*60*1000)){
					is360=true;
				}else if((today-max_4)>(360L*24*60*60*1000-ss.getAlarm_360_service()*24L*60*60*1000)){
					//判断该电梯半月维保是否已到提示时间
					pro360=true;
				}
				//判断该电梯年检是否逾期
				if((today-last_round)>(365L*24*60*60*1000)){
					isround=true;
				}else if((today-max_4)>(365L*24*60*60*1000-ss.getAlarm_rounds()*24L*60*60*1000)){
					//判断该电梯半月维保是否已到提示时间
					proround=true;
				}
				/*
				 * 地图上电梯颜色显示规则：
				 * 有任何逾期：红色
				 * 无逾期，有任何一个提示：蓝色
				 * 无逾期，无提示：绿色
				 */
				if(is15||is90||is180||is360||isround){
					e.setMarkerType("red");
				}else if(pro15||pro90||pro180||pro360||proround){
					e.setMarkerType("blue");
				}else{
					e.setMarkerType("green");
				}
			}else{
				//没有维保记录，一律以红色显示
				e.setMarkerType("red");
			}
			
		}
		result.put("roleId",op.getRole().getIdrole());
		result.put("elList",elList);
		JSONObject jsonMap=JSONObject.fromObject(result);
		return jsonMap.toString();
		
	}
	//部门位置信息更改
	@RequestMapping("updateGis")
	@ResponseBody
	public String updateDistinctGis(DistinctGis e,HttpServletRequest request){
		DistinctGis e_old=distinctGisService.getByDId(e.getId_distinct());
		e_old.setGis_x(e.getGis_x());
		e_old.setGis_y(e.getGis_y());
		e_old.setLevel(e.getLevel());
		distinctGisService.update(e_old);
		return "ok";
	}
	// 地图
	@RequestMapping("map2")
	public ModelAndView map2(HttpServletRequest request) {
		//查找当前登录人
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("00")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","当前登录人非技术监督部门人员!");
			return mav;
		}else{
			op=operatorService.findById(op.getIdoperator());
				//第一次登录，没有查询，所以得从session中取地址
				String id_city=op.getIdcity();
				String id_district=op.getIddistrict();
				String id_subdistrict=op.getIdsubdistrict();
				DistinctGis distinctGis=distinctGisService.selectByDisId(op.getIddistrict());
				double gis_x=distinctGis.getGis_x();
				double gis_y=distinctGis.getGis_y();
				int level=distinctGis.getLevel();
				System.out.println("id_city:"+id_city);
				System.out.println("id_district:"+id_district);
				System.out.println("id_subdistrict:"+id_subdistrict);
				//符合条件的电梯编号
				/*List<Integer> idList=elevatorService.getElevatorIds(id_city, id_district, id_subdistrict, 0, 0, 0, null);
				List<Elevator> elList=elevatorService.getElevatorByIdList(idList);
				JSONArray jsonarray=JSONArray.fromObject(elList);*/
				ModelAndView mav = new ModelAndView("system/testBMap2");
				mav.addObject("gis_x",gis_x);
				mav.addObject("gis_y",gis_y);
				mav.addObject("level",level);
//				mav.addObject("elList",jsonarray);
				return mav;
		}
	}
	/**
	 * 2020-7-4增加实时运行状态
	 */
	@RequestMapping("getRunningStatus")
	public ModelAndView getRunningStatus(HttpServletRequest request,int eid){
		Elevator e=elevatorService.getEById(eid);
		List<E_data_history> hlist=elevatorService.getElevatorRunningStatus(eid);
		ModelAndView mav=new ModelAndView("system/elevatorRunningStatus");
		mav.addObject("e",e);
		if(hlist!=null&&hlist.size()!=0){
			E_data_history history=hlist.get(0);
			mav.addObject("history", history);
		}else{
			mav.addObject("history", null);
		}
		return mav;
	}
	/**
	 * 实时运行页面刷新
	 * @param eid
	 * @return
	 */
	@RequestMapping(value="jsonRunningStatus",produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String jsonRunningStatus(int eid){
		Elevator e=elevatorService.getEById(eid);
		List<E_data_history> hlist=elevatorService.getElevatorRunningStatus(eid);
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("e", e);
		if(hlist!=null&&hlist.size()!=0){
			E_data_history history=hlist.get(0);
			map.put("history", history);
		}else{
			map.put("history", null);
		}
		return JSONObject.fromObject(map).toString();
	}
	@RequestMapping("existsEdevice")
	@ResponseBody
	public String existsEdevice(int eid){
		Object obj=elevatorService.getEdevice(eid);
		return String.valueOf(obj==null);
	}
	/**
	 * 2020-7-4紧急救援
	 */
	@RequestMapping(value="emergencyRescue",produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String emergencyRescue(HttpServletRequest request){
		@SuppressWarnings("rawtypes")
		List alarmList=elevatorService.getEDataAlarms();
		JSONArray array=JSONArray.fromObject(alarmList);
		return array.toString();
	}
	@RequestMapping("setReveice")
	@ResponseBody
	public String setReveice(HttpServletRequest request,int e_data_alarm_id){
		elevatorService.updateReceivedTime(e_data_alarm_id);
		return "success";
	}
	@RequestMapping("setFinished")
	public ModelAndView setFinished(HttpServletRequest request,int e_data_alarm_id){
		elevatorService.updateFinishedTime(e_data_alarm_id);
		ModelAndView mav=new ModelAndView("system/testBMap");
		return mav;
	}
}
