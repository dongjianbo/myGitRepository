package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.ApproveAckService;
import service.DeptGroupService;
import service.ElevatorService;
import service.OperatorService;
import service.RepairImageService;
import service.Repair_queryService;
import service.ServiceService;
import service.UserService;
import vo.Operator;
import vo.RepairImage;
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
	@Resource
	public OperatorService operatorService;
	@Resource
	public ElevatorService elevatorService;
	@Resource
	public RepairImageService repairImageService;
   
   
   
   @RequestMapping(value="list",produces="text/html;charset=utf-8")
   @ResponseBody
   public ModelAndView list(String key,int approve_ark,int id_service,int id_user,int id_test,String id_city,String id_district,String id_subdistrict,String desc,HttpServletRequest request){
	 //查找当前登录人
	Operator op=(Operator)request.getSession().getAttribute("login");
	if(!op.getTypeOperator().equals("00")&&!op.getTypeOperator().equals("50")){
		ModelAndView mav=new ModelAndView("error");
		mav.addObject("error","当前登录人非技术监督部门人员或房管部门人员!");
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
		ModelAndView mav = new ModelAndView("system/repair_query");
		//符合条件的电梯编号
		List<Integer> idList=elevatorService.getElevatorIds(id_city, id_district, id_subdistrict, id_service, id_user, id_test, desc);
		int count=repair_queryService.listCount(-1,idList);
	   	//查询所有维修申请记录
	   	List<Repair_query> listAll=repair_queryService.list(-1,count, request,idList );
		//查询维修申请记录
		List<Repair_query> list=repair_queryService.list(approve_ark,10, request,idList);
	   
	   	int count_approve=0;
	   	int count_repaired=repair_queryService.listCount(3,idList);
	   	int count_approved=repair_queryService.listCount(1,idList);
	   	for(Repair_query q:listAll){
	   		if(q.getRepairapprove()==null||q.getRepairapprove().getApproveack()==0){
	   			count_approve+=1;
	   		}
	   	}
	   	
	    //全部维修申请数量
		mav.addObject("count",count);
		//待批复维修申请数量
		mav.addObject("count_approve",count_approve);
		//已维修维修申请数量
		mav.addObject("count_repaired",count_repaired);
		//已批复维修申请数量
		mav.addObject("count_approved",count_approved);
		mav.addObject("approve_ark",approve_ark);
		mav.addObject("id_city", id_city);
		mav.addObject("id_district", id_district);
		mav.addObject("id_subdistrict", id_subdistrict);
		mav.addObject("desc",desc);
	   	mav.addObject("list", list);
	   	
	   	return mav;
	}
}
   /***
    * 
    * 详细信息
    * @param repair_query
    * @return
    */
   @RequestMapping(value="detail",produces="text/html;charset=utf-8")
	@ResponseBody
	public String detail(Repair_query repair_query){
	   repair_query=repair_queryService.findById(repair_query.getRid());
	   Operator operator=new Operator();
		if (repair_query!=null&&repair_query.getRepairapprove()!=null) {
			if(repair_query.getRepairapprove().getApprover()!=null){
				operator = operatorService.selectById(repair_query.getRepairapprove().getApprover());
				if(operator!=null){
					repair_query.getRepairapprove().setApprover_name(operator.getName());
				}else{
					repair_query.getRepairapprove().setApprover_name("");
				}
			}
			else{
				repair_query.getRepairapprove().setApprover_name("");
			}
			
		}
	   JSONObject object=JSONObject.fromObject(repair_query);
	   return object.toString();
	}
   
   @RequestMapping(value="listImageById",produces="text/html;charset=utf-8")
	@ResponseBody
	public String listImageById(int rid){
	   List<RepairImage> obj=repairImageService.getImageList(rid);
		JSONArray array=JSONArray.fromObject(obj);
		return array.toString();
	}
   
   
   @RequestMapping(value="list1",produces="text/html;charset=utf-8")
   @ResponseBody
   public ModelAndView list1(int approve_ark,HttpServletRequest request,int iduser){
	Operator op=(Operator)request.getSession().getAttribute("login");
	if(!op.getTypeOperator().equals("20")&&!op.getTypeOperator().equals("21")&&!op.getTypeOperator().equals("40")){
		ModelAndView mav=new ModelAndView("error");
		mav.addObject("error","当前登录人非使用单位人员!");
		return mav;
	}else{
		ModelAndView mav = new ModelAndView("system/repair_query1");
		//登录人使用单位
		int id_user=op.getIdOrganization();
		ArrayList<User> users=null;
		ArrayList<Integer> ids=null;
		if(op.getTypeOperator().equals("40")){
			//如果是集团单位成员，查询出该集团下属单位编号
			ids=deptgroupService.getUserIds(id_user);
			//如果是集团单位成员，查询出该集团下属单位
			users=deptgroupService.getUsers(id_user);
		}
		//页面选择下属单位
		if(iduser!=0){
			id_user=iduser;
			ids=null;
		}
		List<Integer> idList=userService.idList(id_user, ids);
		int count=repair_queryService.listCount(-1,idList);
	   	//查询所有维修申请记录
	   	List<Repair_query> listAll=repair_queryService.list(-1,count, request,idList);
		//查询维修申请记录
	   	List<Repair_query> list=repair_queryService.list(approve_ark,10, request,idList);
	   	mav.addObject("list", list);
	   	int count_approve=0;
	   	int count_repaired=repair_queryService.listCount(3,idList);
	   	int count_approved=repair_queryService.listCount(1,idList);
	   	for(Repair_query q:listAll){
	   		if(q.getRepairapprove()==null||q.getRepairapprove().getApproveack()==0){
	   			count_approve+=1;
	   		}
	   	}
	   	
	    //全部维修申请数量
		mav.addObject("count",count);
		//待批复维修申请数量
		mav.addObject("count_approve",count_approve);
		//已维修维修申请数量
		mav.addObject("count_repaired",count_repaired);
		//已批复维修申请数量
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
			mav.addObject("error","当前登录人非维保单位人员!");
			return mav;
		}else{
		ModelAndView mav = new ModelAndView("system/repair_query1");
		//登录人维保单位
		int id_service=op.getIdOrganization();
		Service1 service=serviceService.findById(id_service);
		//当前维保对应所有电梯
		List<Integer> idList=serviceService.idList(id_service);
		//根据类型查询维修申请记录
	   	List<Repair_query> list=repair_queryService.list(approve_ark,10, request,idList);
	   	//查询所有维修申请记录
	   	List<Repair_query> listAll=repair_queryService.list(-1,10, request,idList);
	   	mav.addObject("list", list);
	   	int count=repair_queryService.listCount(-1,idList);
	   	int count_approve=0;
	   	int count_repaired=repair_queryService.listCount(3,idList);
	   	int count_approved=repair_queryService.listCount(1,idList);
	   	for(Repair_query q:listAll){
	   		if(q.getRepairapprove()==null||q.getRepairapprove().getApproveack()==0){
	   			count_approve+=1;
	   		}
	   	}
	   	
	    //全部维修申请数量
		mav.addObject("count",count);
		//待批复维修申请数量
		mav.addObject("count_approve",count_approve);
		//已维修维修申请数量
		mav.addObject("count_repaired",count_repaired);
		//已批复维修申请数量
		mav.addObject("count_approved",count_approved);
		mav.addObject("approve_ark",approve_ark);
		mav.addObject("listUrl","list2");
		return mav;
	}
   }
}
