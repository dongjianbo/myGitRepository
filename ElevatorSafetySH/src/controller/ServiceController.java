package controller;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.CitylistService;

import service.HistoryService;
import service.History_listService;
import service.Maint_report_idService;
import service.ServiceService;
import service.ServicerService;
import vo.Elevator;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Maint_report_id;
import vo.Operator;
import vo.Service1;
import vo.Servicer;

@Controller
@RequestMapping("service")
public class ServiceController {
	@Resource
	public ServiceService serviceService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@Resource
	public Maint_report_idService mriService;
	@Resource
	public CitylistService cityService;
	@Resource
	public ServicerService servicerService;
    @RequestMapping("list")
    public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/serviceList");
		List<Service1> slist=serviceService.list(key, 12, request);
		for(Service1 s:slist){
			s.setRegistCity(cityService.listBy_Idcity(s.getRegisterArea()));
		}
		mav.addObject("serviceList",slist);
		return mav;
	}
    @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
    @ResponseBody
    public String list_json(){
    	List<Service1> serviceList=serviceService.list();
    	JSONArray array=JSONArray.fromObject(serviceList);
    	return array.toString();
    }
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Service1 service,HttpServletRequest request){

		int idservice=-1;
		Serializable ser=serviceService.insert(service);
		if(ser!=null){
			idservice=Integer.parseInt(ser.toString());
		}


		History history=new History();
		history.setType(5);//����
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//��¼��id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//��ǰʱ��
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//������Ϣ��systemstate���У���д��
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(5);
        hilist.setKey(key);//key��ʾ������������
        hilist.setValue(idservice+"");
		System.out.println(history_listService.insert(hilist).toString());

		return idservice+"";

	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Service1 service){
		service=serviceService.findById(service.getIdservice());
		JSONObject object=JSONObject.fromObject(service);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Service1 service){
		serviceService.update(service);
		return "ok";
	}
	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delete(Service1 service){
		if(!serviceService.haveOperator(service)){
			return "no";
		}else{
			try {
				serviceService.delete(service);
				return "yes";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "no";
			}
		}
	}
	@ResponseBody
    @RequestMapping(value="selectId_service",produces="text/html;charset=utf-8")
    public String selectId_service(){
		List<Service1> list=serviceService.select_Idservice();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
	//ά����λ��������
	@RequestMapping("search")
	public ModelAndView search(HttpServletRequest request){
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("10")&&!op.getTypeOperator().equals("11")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","��ǰ��¼�˷�ά����λ��Ա!");
			return mav;
		}else{
			//��¼��ά����λ
			int id_service=op.getIdOrganization();
			Service1 service=serviceService.findById(id_service);
			// ��������
			int count = serviceService.getCount(id_service);
			// ��ע������
			int count_registed = serviceService.getCount_Registed(id_service);
			// ͣ������
			int count_stop = serviceService.getCount_Stop(id_service);
			// ��ע������
			int count_destory = serviceService.getCount_Destory(id_service);
			// δע������
			int count_noregist = serviceService.getCount_NoRegist(id_service);
			// ����ά����������
			int count_15service_normal = serviceService.getCount_15service_Normal(id_service);
			// ����ά����ʾ����
			int count_15service_warnning = serviceService.getCount_15service_Warnning(id_service);
			// ����ά����������
			int count_15service_overdue = serviceService.getCount_15service_Overdue(id_service);
			// ����ά����������
			int count_90service_normal = serviceService.getCount_90service_Normal(id_service);
			// ����ά����ʾ����
			int count_90service_warnning = serviceService.getCount_90service_Warnning(id_service);
			// ����ά����������
			int count_90service_overdue = serviceService.getCount_90service_Overdue(id_service);
			// ����ά����������
			int count_180service_normal = serviceService.getCount_180service_Normal(id_service);
			// ����ά����ʾ����
			int count_180service_warnning = serviceService.getCount_180service_Warnning(id_service);
			// ����ά����������
			int count_180service_overdue = serviceService.getCount_180service_Overdue(id_service);
			// ���ά����������
			int count_360service_normal = serviceService.getCount_360service_Normal(id_service);
			// ���ά����ʾ����
			int count_360service_warnning = serviceService.getCount_360service_Warnning(id_service);
			// ���ά����������
			int count_360service_overdue = serviceService.getCount_360service_Overdue(id_service);

			ModelAndView mav = new ModelAndView("system/serviceTongji");
			mav.addObject("service",service);
			mav.addObject("count", count);
			mav.addObject("count_registed", count_registed);
			mav.addObject("count_stop", count_stop);
			mav.addObject("count_destory", count_destory);
			mav.addObject("count_noregist", count_noregist);
			mav.addObject("count_15service_normal", count_15service_normal);
			mav.addObject("count_15service_warnning", count_15service_warnning);
			mav.addObject("count_15service_overdue", count_15service_overdue);
			mav.addObject("count_90service_normal", count_90service_normal);
			mav.addObject("count_90service_warnning", count_90service_warnning);
			mav.addObject("count_90service_overdue", count_90service_overdue);
			mav.addObject("count_180service_normal", count_180service_normal);
			mav.addObject("count_180service_warnning", count_180service_warnning);
			mav.addObject("count_180service_overdue", count_180service_overdue);
			mav.addObject("count_360service_normal", count_360service_normal);
			mav.addObject("count_360service_warnning", count_360service_warnning);
			mav.addObject("count_360service_overdue", count_360service_overdue);
			return mav;
		}
		
	}
	// ���ͳ���е����ֽ�������б�
		@RequestMapping("listForSearch")
		public ModelAndView listForSearch(String key, String search, HttpServletRequest request) {
			//
			List<Elevator> list = new ArrayList<Elevator>();
			//��ѯ�ؼ���
			if(search==null){
				search="";
			}
			//��¼��ά����λ
			Operator op=(Operator)request.getSession().getAttribute("login");
			int id_service=op.getIdOrganization();
			// ����������
			if (key.equals("count")) {
				list = serviceService.listCount(search, 10, request,id_service);
			}
			// ������ע������
			if (key.equals("count_registed")) {
				list = serviceService.listCount_Registed(search, 10, request,id_service);
			}
			// ����δע������
			if (key.equals("count_noregist")) {
				list = serviceService.listCount_NoRegist(search, 10, request,id_service);
			}
			// ������ͣ������
			if (key.equals("count_stop")) {
				list = serviceService.listCount_Stop(search, 10, request,id_service);
			}
			// ������ע������
			if (key.equals("count_destory")) {
				list = serviceService.listCount_Destory(search, 10, request,id_service);
			}
			// ���ݰ���ά����������
			if (key.equals("count_15service_normal")) {
				list = serviceService.listCount_15service_Normal(search, 10, request,id_service);
			}
			// ���ݰ���ά����ʾ����
			if (key.equals("count_15service_warnning")) {
				list = serviceService.listCount_15service_Warnning(search, 10, request,id_service);
			}
			// ���ݰ���ά����������
			if (key.equals("count_15service_overdue")) {
				list = serviceService.listCount_15service_Overdue(search, 10, request,id_service);
			}
			// ���ݼ���ά����������
			if (key.equals("count_90service_normal")) {
				list = serviceService.listCount_90service_Normal(search, 10, request,id_service);
			}
			// ���ݼ���ά����ʾ����
			if (key.equals("count_90service_warnning")) {
				list = serviceService.listCount_90service_Warnning(search, 10, request,id_service);
			}
			// ���ݼ���ά����������
			if (key.equals("count_90service_overdue")) {
				list = serviceService.listCount_90service_Overdue(search, 10, request,id_service);
			}
			// ���ݰ���ά����������
			if (key.equals("count_180service_normal")) {
				list = serviceService.listCount_180service_Normal(search, 10, request,id_service);
			}
			//���ݰ���ά����ʾ����
			if (key.equals("count_180service_warnning")) {
				list = serviceService.listCount_180service_Warnning(search, 10, request,id_service);
			}
			//���ݰ���ά����������
			if (key.equals("count_180service_overdue")) {
				list = serviceService.listCount_180service_Overdue(search, 10, request,id_service);
			}
			//�������ά����������
			if (key.equals("count_360service_normal")) {
				list = serviceService.listCount_360service_Normal(search, 10, request,id_service);
			}
			//�������ά����ʾ����
			if (key.equals("count_360service_warnning")) {
				list = serviceService.listCount_360service_Warnning(search, 10, request,id_service);
			}
			//�������ά����������
			if (key.equals("count_360service_overdue")) {
				list = serviceService.listCount_360service_Overdue(search, 10, request,id_service);
			}

			ModelAndView mav = new ModelAndView("system/elevatorList");
			mav.addObject("list", list);
			mav.addObject("key",key);
			mav.addObject("search",search);
			mav.addObject("requestMapping", "service");
			return mav;
		}
		//ά����λ������ͳ��
		@RequestMapping("task")
		public ModelAndView task(int idservicer,String start,String end,HttpServletRequest request){
			Operator op=(Operator)request.getSession().getAttribute("login");
			if(!op.getTypeOperator().equals("10")&&!op.getTypeOperator().equals("11")){
				ModelAndView mav=new ModelAndView("error");
				mav.addObject("error","��ǰ��¼�˷�ά����λ��Ա!");
				return mav;
			}else{
				//�õ���ǰ��¼�˵ĵ�λ���
				int id_service=op.getIdOrganization();
				//��ѯ��ǰ��λ��ά����Ա
				List<Servicer> slist=servicerService.listByIdService(id_service);
				Map<String, Integer> data=serviceService.getTask(id_service,start, end,idservicer);
				ModelAndView mav=new ModelAndView("system/serviceTask");
				mav.addObject("data",data);
				mav.addObject("slist", slist);
				return mav;
			}
		}
		//�������б�
		@RequestMapping("listForTask")
		public ModelAndView listForTask(int type,String start,String end,int idservicer,HttpServletRequest request){
			Operator op=(Operator)request.getSession().getAttribute("login");
			//�õ���ǰ��¼�˵ĵ�λ���
			int id_service=op.getIdOrganization();
			List<Maint_report_id> list=serviceService.listByTaskType(id_service,type, start, end,idservicer,request);
			ModelAndView mav=new ModelAndView("system/serviceListForTask");
			String typeName=mriService.getTypeNameById(type);
			mav.addObject("list",list);
			mav.addObject("typeName",typeName);
			return mav;
		}
}
