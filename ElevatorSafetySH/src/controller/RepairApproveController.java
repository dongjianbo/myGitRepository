package controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.RepairApproveService;
import vo.Operator;
import vo.Repair_approve;

@Controller
@RequestMapping("repairApprove")
public class RepairApproveController {
	 @Resource
	   public RepairApproveService repairApproveService;
	 
	 
	 @RequestMapping(value="update",produces="text/html;charset=utf-8")
		@ResponseBody
		public String update(Repair_approve repair_approve,HttpServletRequest request){
		   Date date=new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			String s=format.format(date);
			repair_approve.setApprove_date(s);
			repair_approve.setApproveack(1);
			Operator op=(Operator)request.getSession().getAttribute("login");
			repair_approve.setApprover(op.getIdoperator());
			repairApproveService.insert(repair_approve);
			return "ok";
		}
}
