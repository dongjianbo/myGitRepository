package controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import service.Maint_report_idService;
import vo.Maint_detail;
import vo.Maint_report_id;
import vo.Operator;

@Controller
@RequestMapping("maint_report_id")
public class Maint_report_idController {
	@Resource
	public Maint_report_idService maint_report_idService;
	
	//�������б�
	@RequestMapping("listForTask")
	public ModelAndView listForTask(int maint_type,int elevator_id,HttpServletRequest request){
		List<Maint_report_id> list=maint_report_idService.listByTaskType(maint_type,elevator_id,request);
		ModelAndView mav=new ModelAndView("system/maint_report_idList");
//		String typeName="";
//		if(maint_type==123){
//			typeName="ά������������ά��������ά��������ά����";
//		}else{
//			typeName=maint_report_idService.getTypeNameById(maint_type);
//		}
		
		mav.addObject("list",list);
		if(maint_type==0){
			mav.addObject("typeName","Ѳ��");
		}else{
			mav.addObject("typeName","ά��");
		}
		return mav;
	}
	/**
	 * 
	 * @return
	 * ��ѯ���ϸ�ά����¼��ϸ�б�
	 * 2017-5��������
	 */
	@RequestMapping("listForMaintDetail_NO")
	public ModelAndView listForMaintDetail_NO(HttpServletRequest request){
		List<Maint_detail> list=maint_report_idService.getListMaintDefailNO(10,request);
		ModelAndView mav=new ModelAndView("system/maint_detail_NO_List");
		mav.addObject("list", list);
		return mav;
	}
	/**
	 * �����ϸ�ά����¼
	 */
	@RequestMapping("updateMaint_Note")
	public ModelAndView updateMaint_Note(Maint_detail md,HttpServletRequest request){
		//ȡ����ǰ��¼��id
		Operator op=(Operator)request.getSession().getAttribute("login");
		maint_report_idService.updateMaint_Note(md,op.getIdoperator());
		return new ModelAndView("redirect:/maint_report_id/listForMaintDetail_NO.do");
	}
}
