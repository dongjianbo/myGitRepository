package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import service.ApproveAckService;
import service.DeptGroupService;
import service.Repair_queryService;
import service.ServiceService;
import service.UserService;
import vo.Operator;
import vo.Repair_query;
import vo.Service1;
import vo.User;


@Controller
@RequestMapping("repair_query")
public class Repair_queryController {
   @Resource
   public Repair_queryService repair_queryService; 
   @Resource
   public ApproveAckService approveAckService;
   @Resource
   public DeptGroupService deptgroupService;
   @Resource
   public UserService userService;
	@Resource
	public ServiceService serviceService;
   
   
   
   @RequestMapping(value="list",produces="text/html;charset=utf-8")
   @ResponseBody
   public ModelAndView list(int approve_ark,HttpServletRequest request){
	ModelAndView mav = new ModelAndView("system/repair_query");
	//��ѯά�������¼
   	List<Repair_query> list=repair_queryService.list(approve_ark,10, request);
   	//��ѯ����ά�������¼
   	List<Repair_query> listAll=repair_queryService.list(-1,10, request);
   	mav.addObject("list", list);
   	int count=listAll.size();
   	int count_approve=0;
   	int count_repaired=repair_queryService.listCount(3);
   	int count_approved=repair_queryService.listCount(1);
   	for(Repair_query q:listAll){
   		if(q.getRepairapprove()==null){
   			count_approve+=1;
   		}
   	}
   	
    //ȫ��ά����������
	mav.addObject("count",count);
	//������ά����������
	mav.addObject("count_approve",count_approve);
	//��ά��ά����������
	mav.addObject("count_repaired",count_repaired);
	//������ά����������
	mav.addObject("count_approved",count_approved);
	mav.addObject("approve_ark",approve_ark);
   	
   	return mav;
   }
   @RequestMapping(value="detail",produces="text/html;charset=utf-8")
	@ResponseBody
	public String detail(Repair_query repair_query){
	   repair_query=repair_queryService.findById(repair_query.getRid());
	   JSONObject object=JSONObject.fromObject(repair_query);
	   return object.toString();
	}
   @RequestMapping(value="list1",produces="text/html;charset=utf-8")
   @ResponseBody
   public ModelAndView list1(int approve_ark,HttpServletRequest request,int iduser){
	Operator op=(Operator)request.getSession().getAttribute("login");
	if(!op.getTypeOperator().equals("20")&&!op.getTypeOperator().equals("21")&&!op.getTypeOperator().equals("40")){
		ModelAndView mav=new ModelAndView("error");
		mav.addObject("error","��ǰ��¼�˷�ʹ�õ�λ��Ա!");
		return mav;
	}else{
		ModelAndView mav = new ModelAndView("system/repair_query1");
		//��¼��ʹ�õ�λ
		int id_user=op.getIdOrganization();
		ArrayList<User> users=null;
		ArrayList<Integer> ids=null;
		if(op.getTypeOperator().equals("40")){
			//����Ǽ��ŵ�λ��Ա����ѯ���ü���������λ���
			ids=deptgroupService.getUserIds(id_user);
			//����Ǽ��ŵ�λ��Ա����ѯ���ü���������λ
			users=deptgroupService.getUsers(id_user);
		}
		//ҳ��ѡ��������λ
		if(iduser!=0){
			id_user=iduser;
			ids=null;
		}
		List<Integer> idList=userService.idList(id_user, ids);
		//��ѯά�������¼
	   	List<Repair_query> list=repair_queryService.list(approve_ark,10, request,idList);
	   	//��ѯ����ά�������¼
	   	List<Repair_query> listAll=repair_queryService.list(-1,10, request,idList);
	   	mav.addObject("list", list);
	   	int count=listAll.size();
	   	int count_approve=0;
	   	int count_repaired=repair_queryService.listCount(3,idList);
	   	int count_approved=repair_queryService.listCount(1,idList);
	   	for(Repair_query q:listAll){
	   		if(q.getRepairapprove()==null){
	   			count_approve+=1;
	   		}
	   	}
	   	
	    //ȫ��ά����������
		mav.addObject("count",count);
		//������ά����������
		mav.addObject("count_approve",count_approve);
		//��ά��ά����������
		mav.addObject("count_repaired",count_repaired);
		//������ά����������
		mav.addObject("count_approved",count_approved);
		mav.addObject("approve_ark",approve_ark);
		mav.addObject("iduser",iduser);
		mav.addObject("listUrl","list1");
		return mav;
	}
   }
   @RequestMapping(value="list2",produces="text/html;charset=utf-8")
   @ResponseBody
   public ModelAndView list2(int approve_ark,HttpServletRequest request){
	   Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("10")&&!op.getTypeOperator().equals("11")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","��ǰ��¼�˷�ά����λ��Ա!");
			return mav;
		}else{
		ModelAndView mav = new ModelAndView("system/repair_query1");
		//��¼��ά����λ
		int id_service=op.getIdOrganization();
		Service1 service=serviceService.findById(id_service);
		//��ǰά����Ӧ���е���
		List<Integer> idList=serviceService.idList(id_service);
		//�������Ͳ�ѯά�������¼
	   	List<Repair_query> list=repair_queryService.list(approve_ark,10, request,idList);
	   	//��ѯ����ά�������¼
	   	List<Repair_query> listAll=repair_queryService.list(-1,10, request,idList);
	   	mav.addObject("list", list);
	   	int count=listAll.size();
	   	int count_approve=0;
	   	int count_repaired=repair_queryService.listCount(3,idList);
	   	int count_approved=repair_queryService.listCount(1,idList);
	   	for(Repair_query q:listAll){
	   		if(q.getRepairapprove()==null){
	   			count_approve+=1;
	   		}
	   	}
	   	
	    //ȫ��ά����������
		mav.addObject("count",count);
		//������ά����������
		mav.addObject("count_approve",count_approve);
		//��ά��ά����������
		mav.addObject("count_repaired",count_repaired);
		//������ά����������
		mav.addObject("count_approved",count_approved);
		mav.addObject("approve_ark",approve_ark);
		mav.addObject("listUrl","list2");
		return mav;
	}
   }
}
