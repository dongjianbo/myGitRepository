package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.ElevatorService;
import service.HistoryService;
import service.History_listService;
import service.SystemstateService;
import vo.Elevator;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;

@Controller
@RequestMapping("elevator")
public class ElevatorController{
	@Resource
	public ElevatorService elevatorService;
	@Resource
	public SystemstateService systemService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@RequestMapping("insert")
	public String insert(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		Elevator el=(Elevator)request.getSession().getAttribute("elevator");//存在第一页的值
		el.setCheck_construct(elevator.getCheck_construct());//
		el.setCheck_construct_code(elevator.getCheck_construct_code());//
		el.setId_installer(elevator.getId_installer());//
		el.setAddress(elevator.getAddress());//
		el.setDesc(elevator.getDesc());//
		el.setDate_declare(elevator.getDate_declare());//
		el.setNum_floor_elevator(elevator.getNum_floor_elevator());//
		el.setId_elevator_model(elevator.getId_elevator_model());//
		el.setDate_register(elevator.getDate_register());//
		el.setProject_duty(elevator.getProject_duty());//
		el.setId_service(elevator.getId_service());//
		el.setId_test(elevator.getId_test());//
		el.setRegister_status(elevator.getRegister_status());//
		el.setDate_enable(elevator.getDate_enable());//
		int id_elevator=Integer.parseInt(elevatorService.insert(el).toString());
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
        return "/system/insertElevator_state";
	}
	@RequestMapping("insertTo1")
	public String insert1(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("elevator", elevator);
		return "/system/insertElevatorDeclaration1";
	}
	
	//技术监督部门统计查询
	@RequestMapping("search")
	public ModelAndView search(){
		//电梯总数
		int count=elevatorService.getCount();
		//已注册数量
		int count_registed=elevatorService.getCount_Registed();
		//停用数量
		int count_stop=elevatorService.getCount_Stop();
		//已注销数量
		int count_destory=elevatorService.getCount_Destory();
		//未注册数量
		int count_noregist=elevatorService.getCount_NoRegist();
		//年检正常数量
		int count_rounds_normal=elevatorService.getCount_Rounds_Normal();
		//年检提示数量
		int count_rounds_warnning=elevatorService.getCount_Rounds_Warnning();
		//年检预期数量
		int count_rounds_overdue=elevatorService.getCount_Rounds_Overdue();
		//半月维保正常数量
		int count_15service_normal=elevatorService.getCount_15service_Normal();
		//半月维保提示数量
		int count_15service_warnning=elevatorService.getCount_15service_Warnning();
		//半月维保逾期数量
		int count_15service_overdue=elevatorService.getCount_15service_Overdue();
		//季度维保正常数量
		int count_90service_normal=elevatorService.getCount_90service_Normal();
		//季度维保提示数量
		int count_90service_warnning=elevatorService.getCount_90service_Warnning();
		//季度维保逾期数量
		int count_90service_overdue=elevatorService.getCount_90service_Overdue();
		//半年维保正常数量
		int count_180service_normal=elevatorService.getCount_180service_Normal();
		//半年维保提示数量
		int count_180service_warnning=elevatorService.getCount_180service_Warnning();
		//半年维保逾期数量
		int count_180service_overdue=elevatorService.getCount_180service_Overdue();
		//年度维保正常数量
		int count_360service_normal=elevatorService.getCount_360service_Normal();
		//年度维保提示数量
		int count_360service_warnning=elevatorService.getCount_360service_Warnning();
		//年度维保逾期数量
		int count_360service_overdue=elevatorService.getCount_360service_Overdue();
		
		ModelAndView mav=new ModelAndView("system/elevatorTongji");
		mav.addObject("count",count);
		mav.addObject("count_registed",count_registed);
		mav.addObject("count_stop",count_stop);
		mav.addObject("count_destory",count_destory);
		mav.addObject("count_noregist",count_noregist);
		mav.addObject("count_rounds_normal",count_rounds_normal);
		mav.addObject("count_rounds_warnning",count_rounds_warnning);
		mav.addObject("count_rounds_overdue",count_rounds_overdue);
		mav.addObject("count_15service_normal",count_15service_normal);
		mav.addObject("count_15service_warnning",count_15service_warnning);
		mav.addObject("count_15service_overdue",count_15service_overdue);
		mav.addObject("count_90service_normal",count_90service_normal);
		mav.addObject("count_90service_warnning",count_90service_warnning);
		mav.addObject("count_90service_overdue",count_90service_overdue);
		mav.addObject("count_180service_normal",count_180service_normal);
		mav.addObject("count_180service_warnning",count_180service_warnning);
		mav.addObject("count_180service_overdue",count_180service_overdue);
		mav.addObject("count_360service_normal",count_360service_normal);
		mav.addObject("count_360service_warnning",count_360service_warnning);
		mav.addObject("count_360service_overdue",count_360service_overdue);
		return mav;
	}
	//点击统计中的数字进入电梯列表
	@RequestMapping("listForSearch")
	public ModelAndView listForSearch(String key,HttpServletRequest request){
		//
		List<Elevator> list=new ArrayList<Elevator>();
		//电梯总数量
		if(key.equals("count")){
			list=elevatorService.listCount(10, request);
		}
		ModelAndView mav=new ModelAndView("system/elevatorList");
		mav.addObject("list",list);
		return mav;
	}
}
