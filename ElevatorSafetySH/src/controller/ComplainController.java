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
	
	//监管部门投诉信息查询
	 @RequestMapping(value="selectList",produces="text/html;charset=utf-8")
	public ModelAndView selectListAll(String key,int id_service,int id_user,int id_test,int  type_object,int source,String desc,String id_city,String id_district,String id_subdistrict,HttpServletRequest request){
		
		//查找当前登录人
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("00")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","当前登录人非技术监督部门人员!");
			return mav;
		}else{
			op=operatorService.findById(op.getIdoperator());
			//登录人所属区域
			if(key!=null&&key.equals("first")){
				//第一次登录，没有查询，所以得从session中取地址
				id_city=op.getIdcity();
				id_district=op.getIddistrict();
				id_subdistrict=op.getIdsubdistrict();
				System.out.println("id_city:"+id_city);
				System.out.println("id_district:"+id_district);
				System.out.println("id_subdistrict:"+id_subdistrict);
			}
			ModelAndView mav=new ModelAndView("system/complainList");
			//符合条件的电梯编号
			List<Integer> idList=elevatorService.getElevatorIds(id_city, id_district, id_subdistrict, id_service, id_user, id_test, desc);
			//查询所有投诉信息
			List<Complain> list=complainService.selectList(  id_service, id_user,source,type_object, idList, 10, request);
			//投诉电梯数量
			int countElevator=0;
			//投诉维保数量
			int countService=0;
			//投诉使用数量
			int countUse=0;
			//投诉总数
			int count=list.size();
			
			//设置投诉对象
			for(Complain com:list){
				if(com.getType_object()==0){
					//投诉对象为电梯
					Elevator e=elevatorService.getEById(com.getId_object());
					if(e!=null){
						com.setObjectName(e.getDesc());
					}
					countElevator++;
				}
				if(com.getType_object()==1){
					//投诉对象为维保公司
					Service1 s=serviceService.findById(com.getId_object());
					if(s!=null){
						com.setObjectName(s.getName());
					}
					countService++;
				}
				if(com.getType_object()==2){
					//投诉对象为使用单位
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
	//使用单位投诉信息查询
	 @RequestMapping(value="list",produces="text/html;charset=utf-8")
	public ModelAndView selectList(HttpServletRequest request,int iduser){
		
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("20")&&!op.getTypeOperator().equals("21")&&!op.getTypeOperator().equals("40")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","当前登录人非使用单位人员!");
			return mav;
		}else{
			ModelAndView mav=new ModelAndView("system/complainList1");
			//登录人使用单位
			int id_user=op.getIdOrganization();
//			ArrayList<User> users=null;
			ArrayList<Integer> ids=null;
			if(op.getTypeOperator().equals("40")){
				//如果是集团单位成员，查询出该集团下属单位编号
				ids=deptgroupService.getUserIds(id_user);
				//如果是集团单位成员，查询出该集团下属单位
//				users=deptgroupService.getUsers(id_user);
			}
			//页面选择下属单位
			if(iduser!=0){
				id_user=iduser;
				ids=null;
			}
			//当前使用单位对应电梯
			List<Integer> idList=userService.idList(id_user, ids);
			//查询当前所有投诉信息
			List<Complain> list=complainService.selectList(id_user, ids,10, request, idList);
			//设置投诉对象
			for(Complain com:list){
				if(com.getType_object()==0){
					//投诉对象为电梯
					Elevator e=elevatorService.getEById(com.getId_object());
					if(e!=null){
						com.setObjectName(e.getDesc());
					}
				}
				if(com.getType_object()==1){
					//投诉对象为维保公司
					Service1 s=serviceService.findById(com.getId_object());
					if(s!=null){
						com.setObjectName(s.getName());
					}
				}
				if(com.getType_object()==2){
					//投诉对象为使用单位
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
	//维保人员投诉信息查询
	 @RequestMapping(value="list2",produces="text/html;charset=utf-8")
	public ModelAndView selectList2(HttpServletRequest request){
		
		 Operator op=(Operator)request.getSession().getAttribute("login");
			if(!op.getTypeOperator().equals("10")&&!op.getTypeOperator().equals("11")){
				ModelAndView mav=new ModelAndView("error");
				mav.addObject("error","当前登录人非维保单位人员!");
				return mav;
			}else{
			ModelAndView mav=new ModelAndView("system/complainList1");
			//登录人维保单位
			int id_service=op.getIdOrganization();
//			Service1 service=serviceService.findById(id_service);
			//当前维保对应所有电梯
			List<Integer> idList=serviceService.idList(id_service);
			//查询所有投诉信息
			List<Complain> list=complainService.selectList(id_service,10, request, idList);
			//设置投诉对象
			for(Complain com:list){
				if(com.getType_object()==0){
					//投诉对象为电梯
					Elevator e=elevatorService.getEById(com.getId_object());
					if(e!=null){
						com.setObjectName(e.getDesc());
					}
				}
				if(com.getType_object()==1){
					//投诉对象为维保公司
					Service1 s=serviceService.findById(com.getId_object());
					if(s!=null){
						com.setObjectName(s.getName());
					}
				}
				if(com.getType_object()==2){
					//投诉对象为使用单位
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
	//按类型和序号查询投诉对象信息
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
