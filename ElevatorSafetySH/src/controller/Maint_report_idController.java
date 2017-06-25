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
	public ModelAndView listForMaintDetail_NO(int resultType,HttpServletRequest request){
		//��õ�ǰ��¼��������λ
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("20")&&!op.getTypeOperator().equals("21")&&!op.getTypeOperator().equals("40")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","��ǰ��¼�˷�ʹ�õ�λ��Ա!");
			return mav;
		}else{
			op=operatorService.findById(op.getIdoperator());
			List<Maint_detail> list=null;
			
			if(op.getTypeOperator().equals("40")){
				//����Ǽ��ŵ�λ��Ա,��ѯ�ü��ŵ�λ�µ�ʹ�õ�λid
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
	 * �����ϸ�ά����¼
	 */
	@RequestMapping("updateMaint_Note")
	@ResponseBody
	public String updateMaint_Note(Maint_detail md,HttpServletRequest request){
		//ȡ����ǰ��¼��id
		Operator op=(Operator)request.getSession().getAttribute("login");
		maint_report_idService.updateMaint_Note(md,op.getIdoperator());
		return "ok";
	}
}
