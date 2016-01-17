package controller;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.HistoryService;
import service.History_listService;
import service.ServiceService;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;
import vo.Service1;

@Controller
@RequestMapping("service")
public class ServiceController {
	@Resource
	public ServiceService serviceService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
    @RequestMapping("list")
    public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/serviceList");
		System.out.println(key);
		mav.addObject("serviceList",serviceService.list(key, 12, request));
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
		int idservice=Integer.parseInt(serviceService.insert(service).toString());
		request.getSession().setAttribute("idservice",idservice);
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
		System.out.println( history_listService.insert(hilist).toString());
		return "ok";
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
	@RequestMapping("delete")
	public String delete(Service1 service){
		serviceService.delete(service);
		return "redirect:/service/list.do";
	}
	@ResponseBody
    @RequestMapping(value="selectId_service",produces="text/html;charset=utf-8")
    public String selectId_service(){
		List<Service1> list=serviceService.select_Idservice();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
	//ά����λ��������
	@RequestMapping("serach")
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
}
