package controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.DeptGroupDao;
import service.DeptGroupService;
import service.Maint_report_idService;
import service.OperatorService;
import vo.DeptGroup;
import vo.Maint_detail;
import vo.Maint_report_id;
import vo.Operator;

@Controller
@RequestMapping("maint_report_id")
public class Maint_report_idController {
	@Resource
	public Maint_report_idService maint_report_idService;
	@Resource
	public OperatorService operatorService;
	@Resource
	public DeptGroupService deptGroupService;
	
	//任务量列表
	@RequestMapping("listForTask")
	public ModelAndView listForTask(int maint_type,int elevator_id,HttpServletRequest request){
		List<Maint_report_id> list=maint_report_idService.listByTaskType(maint_type,elevator_id,request);
		ModelAndView mav=new ModelAndView("system/maint_report_idList");
//		String typeName="";
//		if(maint_type==123){
//			typeName="维保（包括半月维保、季度维保、半年维保）";
//		}else{
//			typeName=maint_report_idService.getTypeNameById(maint_type);
//		}
		
		mav.addObject("list",list);
		if(maint_type==0){
			mav.addObject("typeName","巡视");
		}else{
			mav.addObject("typeName","维保");
		}
		return mav;
	}
	/**
	 * 
	 * @return
	 * 查询不合格维保记录明细列表
	 * 2017-5月新需求
	 */
	@RequestMapping("listForMaintDetail_NO")
	public ModelAndView listForMaintDetail_NO(int resultType,HttpServletRequest request){
		//获得当前登录人所属单位
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("20")&&!op.getTypeOperator().equals("21")&&!op.getTypeOperator().equals("40")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","当前登录人非使用单位人员!");
			return mav;
		}else{
			op=operatorService.findById(op.getIdoperator());
			List<Maint_detail> list=null;
			
			if(op.getTypeOperator().equals("40")){
				//如果是集团单位成员,查询该集团单位下的使用单位id
				List<Integer> ids=deptGroupService.getUserIds(op.getIdOrganization());
				list=maint_report_idService.getListMaintDefailNO(resultType,ids,10,request);
			}else{
				list=maint_report_idService.getListMaintDefailNO(resultType,op.getIdOrganization(),10,request);
			}
			
			ModelAndView mav=new ModelAndView("system/maint_detail_NO_List");
			mav.addObject("resultType",resultType);
			mav.addObject("list", list);
			return mav;
		}
		
	}
	/**
	 * 处理不合格维保记录
	 */
	@RequestMapping("updateMaint_Note")
	@ResponseBody
	public String updateMaint_Note(Maint_detail md,HttpServletRequest request){
		//取出当前登录人id
		Operator op=(Operator)request.getSession().getAttribute("login");
		maint_report_idService.updateMaint_Note(md,op.getIdoperator());
		return "ok";
	}
}
