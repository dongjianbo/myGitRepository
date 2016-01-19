package controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.Elevator_stateService;
import vo.Elevator_state;



@Controller
@RequestMapping("elevator_state")
public class Elevator_stateController {
	@Resource
	public Elevator_stateService elevator_stateService;
	
	@RequestMapping("list")
	public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/designerList");
		mav.addObject("elevator_stateList", elevator_stateService.list(key,10,request));
		return mav;
	}
	
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Elevator_state elevator_state ,HttpServletRequest request,HttpServletResponse response){
		elevator_stateService.insert(elevator_state);
		
		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Elevator_state elevator_state){
		elevator_state=elevator_stateService.findById(elevator_state.getIdelevator());
		JSONObject object=JSONObject.fromObject(elevator_state);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Elevator_state elevator_state){
		elevator_stateService.update(elevator_state);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Elevator_state elevator_state){
		elevator_stateService.delete(elevator_state);
		return "redirect:/elevator_state/list.do";
	}          
	@RequestMapping("insertElevator_state")
	public String insertElevator_state(Elevator_state elevator_state,HttpServletRequest request,HttpServletResponse response){
		elevator_state.setIdelevator(Integer.parseInt(request.getSession().getAttribute("id_elevator").toString()));
		System.out.println(elevator_state.getIdelevator());
		elevator_state.setLabelwrite("1");
		elevator_stateService.insert(elevator_state);
		//添加成功  待电梯详细查询写好后 转向电梯详细查询连接 先转向成功界面
		return "/system/successElevator";
	}
	@RequestMapping("yuanElevator_state")
	public String yuanElevator_state(Elevator_state elevator_state,HttpServletRequest request,HttpServletResponse response){
		elevator_state.setIdelevator(Integer.parseInt(request.getSession().getAttribute("yuanid_elevator").toString()));
		System.out.println(elevator_state.getIdelevator());
		elevator_state.setLabelwrite("0");
		elevator_stateService.insert(elevator_state);
		//添加成功  待电梯详细查询写好后 转向电梯详细查询连接 先转向成功界面
		return "/system/yuansuccessElevator";
	}
	@RequestMapping("insertElevator_state1")
	public String insertElevator_state1(Elevator_state elevator_state,HttpServletRequest request,HttpServletResponse response){
		elevator_state.setIdelevator(Integer.parseInt(request.getSession().getAttribute("id_elevator1").toString()));
		elevator_state.setLabelwrite("0");
		elevator_stateService.update(elevator_state);
		//添加成功
		return "redirect:/elevator/list.do";
	}
}
