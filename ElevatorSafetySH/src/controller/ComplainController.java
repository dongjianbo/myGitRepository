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

import net.sf.json.JSONObject;
import service.ComplainService;
import service.DeptGroupService;
import service.ElevatorService;
import service.OperatorService;
import service.ServiceService;
import service.UserService;
import vo.Complain;
import vo.Elevator;
import vo.Operator;
import vo.Service1;
import vo.Servicer;
import vo.User;

@Controller
@RequestMapping("complain")
public class ComplainController {
	@Resource
    public ComplainService complainService;
    @Resource
    public DeptGroupService deptgroupService;
    @Resource
    public UserService userService;
	@Resource
	public ServiceService serviceService;
	@Resource
	public OperatorService operatorService;
	@Resource
	public ElevatorService elevatorService;
	
	//��ܲ���Ͷ����Ϣ��ѯ
	 @RequestMapping(value="selectList",produces="text/html;charset=utf-8")
	public ModelAndView selectListAll(String key,int id_service,int id_user,int id_test,int  type_object,int source,String desc,String id_city,String id_district,String id_subdistrict,HttpServletRequest request){
		
		//���ҵ�ǰ��¼��
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("00")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","��ǰ��¼�˷Ǽ����ල������Ա!");
			return mav;
		}else{
			op=operatorService.findById(op.getIdoperator());
			//��¼����������
			if(key!=null&&key.equals("first")){
				//��һ�ε�¼��û�в�ѯ�����Եô�session��ȡ��ַ
				id_city=op.getIdcity();
				id_district=op.getIddistrict();
				id_subdistrict=op.getIdsubdistrict();
				System.out.println("id_city:"+id_city);
				System.out.println("id_district:"+id_district);
				System.out.println("id_subdistrict:"+id_subdistrict);
			}
			ModelAndView mav=new ModelAndView("system/complainList");
			//���������ĵ��ݱ��
			List<Integer> idList=elevatorService.getElevatorIds(id_city, id_district, id_subdistrict, id_service, id_user, id_test, desc);
			//��ѯ����Ͷ����Ϣ
			List<Complain> list=complainService.selectList(  id_service, id_user,source,type_object, idList, 10, request);
			//Ͷ�ߵ�������
			int countElevator=0;
			//Ͷ��ά������
			int countService=0;
			//Ͷ��ʹ������
			int countUse=0;
			//Ͷ������
			int count=list.size();
			
			//����Ͷ�߶���
			for(Complain com:list){
				if(com.getType_object()==0){
					//Ͷ�߶���Ϊ����
					Elevator e=elevatorService.getEById(com.getId_object());
					if(e!=null){
						com.setObjectName(e.getDesc());
					}
					countElevator++;
				}
				if(com.getType_object()==1){
					//Ͷ�߶���Ϊά����˾
					Service1 s=serviceService.findById(com.getId_object());
					if(s!=null){
						com.setObjectName(s.getName());
					}
					countService++;
				}
				if(com.getType_object()==2){
					//Ͷ�߶���Ϊʹ�õ�λ
					User u=userService.findById(com.getId_object());
					if(u!=null){
						com.setObjectName(u.getName());
					}
					countUse++;
				}
			}
			mav.addObject("countElevator", countElevator);
			mav.addObject("countService", countService);
			mav.addObject("countUse", countUse);
			mav.addObject("count", count);
			mav.addObject("list", list);
			mav.addObject("id_city", id_city);
			mav.addObject("id_district", id_district);
			mav.addObject("id_subdistrict", id_subdistrict);
			mav.addObject("type_object", type_object);
			mav.addObject("source", source);
			mav.addObject("desc",desc);
			return mav;
		}
	}
	//ʹ�õ�λͶ����Ϣ��ѯ
	 @RequestMapping(value="list",produces="text/html;charset=utf-8")
	public ModelAndView selectList(HttpServletRequest request,int iduser){
		
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("20")&&!op.getTypeOperator().equals("21")&&!op.getTypeOperator().equals("40")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","��ǰ��¼�˷�ʹ�õ�λ��Ա!");
			return mav;
		}else{
			ModelAndView mav=new ModelAndView("system/complainList1");
			//��¼��ʹ�õ�λ
			int id_user=op.getIdOrganization();
//			ArrayList<User> users=null;
			ArrayList<Integer> ids=null;
			if(op.getTypeOperator().equals("40")){
				//����Ǽ��ŵ�λ��Ա����ѯ���ü���������λ���
				ids=deptgroupService.getUserIds(id_user);
				//����Ǽ��ŵ�λ��Ա����ѯ���ü���������λ
//				users=deptgroupService.getUsers(id_user);
			}
			//ҳ��ѡ��������λ
			if(iduser!=0){
				id_user=iduser;
				ids=null;
			}
			//��ǰʹ�õ�λ��Ӧ����
			List<Integer> idList=userService.idList(id_user, ids);
			//��ѯ��ǰ����Ͷ����Ϣ
			List<Complain> list=complainService.selectList(id_user, ids,10, request, idList);
			//����Ͷ�߶���
			for(Complain com:list){
				if(com.getType_object()==0){
					//Ͷ�߶���Ϊ����
					Elevator e=elevatorService.getEById(com.getId_object());
					if(e!=null){
						com.setObjectName(e.getDesc());
					}
				}
				if(com.getType_object()==1){
					//Ͷ�߶���Ϊά����˾
					Service1 s=serviceService.findById(com.getId_object());
					if(s!=null){
						com.setObjectName(s.getName());
					}
				}
				if(com.getType_object()==2){
					//Ͷ�߶���Ϊʹ�õ�λ
					User u=userService.findById(com.getId_object());
					if(u!=null){
						com.setObjectName(u.getName());
					}
				}
			}
			mav.addObject("list", list);
			return mav;
		}
	}
	//ά����ԱͶ����Ϣ��ѯ
	 @RequestMapping(value="list2",produces="text/html;charset=utf-8")
	public ModelAndView selectList2(HttpServletRequest request){
		
		 Operator op=(Operator)request.getSession().getAttribute("login");
			if(!op.getTypeOperator().equals("10")&&!op.getTypeOperator().equals("11")){
				ModelAndView mav=new ModelAndView("error");
				mav.addObject("error","��ǰ��¼�˷�ά����λ��Ա!");
				return mav;
			}else{
			ModelAndView mav=new ModelAndView("system/complainList1");
			//��¼��ά����λ
			int id_service=op.getIdOrganization();
//			Service1 service=serviceService.findById(id_service);
			//��ǰά����Ӧ���е���
			List<Integer> idList=serviceService.idList(id_service);
			//��ѯ����Ͷ����Ϣ
			List<Complain> list=complainService.selectList(id_service,10, request, idList);
			//����Ͷ�߶���
			for(Complain com:list){
				if(com.getType_object()==0){
					//Ͷ�߶���Ϊ����
					Elevator e=elevatorService.getEById(com.getId_object());
					if(e!=null){
						com.setObjectName(e.getDesc());
					}
				}
				if(com.getType_object()==1){
					//Ͷ�߶���Ϊά����˾
					Service1 s=serviceService.findById(com.getId_object());
					if(s!=null){
						com.setObjectName(s.getName());
					}
				}
				if(com.getType_object()==2){
					//Ͷ�߶���Ϊʹ�õ�λ
					User u=userService.findById(com.getId_object());
					if(u!=null){
						com.setObjectName(u.getName());
					}
				}
			}
			mav.addObject("list", list);
			return mav;
		}
	}
	 @RequestMapping(value="dealComplain",produces="text/html;charset=utf-8")
		@ResponseBody
		public String dealComplain(Complain complain,HttpServletRequest request){
		 	Complain complainX=	complainService.getComplainById(complain.getCid());
		   	Date date=new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			String s=format.format(date);
			complainX.setResult(complain.getResult());
			complainX.setDate2(s);
			Operator op=(Operator)request.getSession().getAttribute("login");
			complainX.setIput2(op.getName());
			complainService.update(complainX);
			return "ok";
		}
	//�����ͺ���Ų�ѯͶ�߶�����Ϣ
		@RequestMapping(value="getObjectByType",produces="text/html;charset=utf-8")
		@ResponseBody
		public String getObjectByType(int type,int object,HttpServletRequest request){
			Complain complain=new Complain();
			if(type==0){
				Elevator e=elevatorService.getEById(object);
				complain.setId_object(e.getId_elevator());
			}else if(type==1){
				Service1 s=serviceService.findById(object);
				complain.setId_object(s.getIdservice());
			}else if(type==2){
				User u=userService.findById(object);
				complain.setId_object(u.getIduser());
			}
			JSONObject jobject=JSONObject.fromObject(complain);
			return jobject.toString();
		}
		
		@RequestMapping(value="insertComplain",produces="text/html;charset=utf-8")
		@ResponseBody
		public String insertComplain(Complain complain,HttpServletRequest request,HttpServletResponse response){
			Operator op=(Operator)request.getSession().getAttribute("login");
			complain.setStatus(0);
			complain.setInput1(op.getName());
			complainService.insert(complain);
	        return "ok";
		}
}
