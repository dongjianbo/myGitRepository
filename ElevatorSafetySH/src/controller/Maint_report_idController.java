package controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import service.Maint_report_idService;
import vo.Maint_report_id;

@Controller
@RequestMapping("maint_report_id")
public class Maint_report_idController {
	@Resource
	public Maint_report_idService maint_report_idService;
	
	//任务量列表
	@RequestMapping("listForTask")
	public ModelAndView listForTask(int maint_type,int elevator_id,HttpServletRequest request){
		List<Maint_report_id> list=maint_report_idService.listByTaskType(maint_type,elevator_id,request);
		ModelAndView mav=new ModelAndView("system/maint_report_idList");
		String typeName="";
		if(maint_type==123){
			typeName="维保（包括半月维保、季度维保、半年维保）";
		}else{
			typeName=maint_report_idService.getTypeNameById(maint_type);
		}
		mav.addObject("list",list);
		mav.addObject("typeName",typeName);
		return mav;
	}
}
