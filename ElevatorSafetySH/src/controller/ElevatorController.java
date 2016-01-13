package controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		Elevator e=(Elevator)request.getSession().getAttribute("el");
		e.setDate_enable(elevator.getDate_enable());
		e.setProject_duty(elevator.getProject_duty());
		e.setId_service(elevator.getId_service());
		e.setId_test(elevator.getId_test());
		e.setNum_floor_elevator(elevator.getNum_floor_elevator());
		e.setId_elevator_model(elevator.getId_elevator_model());
		e.setRegister_status(elevator.getRegister_status());
		e.setDesc(elevator.getDesc());
		e.setGis_x(elevator.getGis_x());
		e.setGis_y(elevator.getGis_y());
		e.setGis_type(elevator.getGis_type()); 
		int id_elevator=Integer.parseInt(elevatorService.insert(e).toString());
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
	@RequestMapping("insertTo2")
	public String insert2(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		Elevator el=(Elevator)request.getSession().getAttribute("elevator");//存在第一页的值
		el.setCheck_construct(elevator.getCheck_construct());
		el.setCheck_construct_code(elevator.getCheck_construct_code());
		el.setId_installer(elevator.getId_installer());
		el.setId_owner(elevator.getId_owner());
		el.setId_user(elevator.getId_user());
		el.setId_city(elevator.getId_city());
		el.setId_district(elevator.getId_district());
		el.setId_subdistrict(elevator.getId_subdistrict());
		el.setAddress(elevator.getAddress());
		el.setDate_declare(elevator.getDate_declare());
		el.setDate_register(elevator.getDate_register());
		request.getSession().setAttribute("el", el);
		return "/system/insertElevatorDeclaration2";
	}
}
