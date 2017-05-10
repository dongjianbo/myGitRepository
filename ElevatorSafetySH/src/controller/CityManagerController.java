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
import service.CityManagerService;
import service.OperatorService;
import service.System_settingService;
import vo.CityManager;
import vo.Elevator;
import vo.Operator;
import vo.System_setting;

@Controller
@RequestMapping("citymanager")
public class CityManagerController {
	@Resource
	public OperatorService operatorService;
	@Resource
	public CityManagerService cityManagerService;
	@Resource
	public System_settingService system_settingService;
	//�����ල����ͳ�Ʋ�ѯ
	@RequestMapping("search")
	public ModelAndView search(String key,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc,HttpServletRequest request) {
		//���ҵ�ǰ��¼��
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("50")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","��ǰ��¼�˷Ƿ��ܲ�����Ա!");
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
//					try {
//						if(desc!=null){
//							System.out.println(desc);
//							desc=new String(desc.getBytes("ISO-8859-1"),"UTF-8");
//							System.out.println(desc+"==========");
//						}
//						
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
			// ��������
			int count = cityManagerService.getCount(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ��ע������
			int count_registed = cityManagerService.getCount_Registed(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ͣ������
			int count_stop = cityManagerService.getCount_Stop(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ��ע������
			int count_destory = cityManagerService.getCount_Destory(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// δע������
			int count_noregist = cityManagerService.getCount_NoRegist(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// �����������
			int count_rounds_normal = cityManagerService.getCount_Rounds_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// �����ʾ����
			int count_rounds_warnning = cityManagerService.getCount_Rounds_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ���Ԥ������
			int count_rounds_overdue = cityManagerService.getCount_Rounds_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ����ά����������
			int count_15service_normal = cityManagerService.getCount_15service_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ����ά����ʾ����
			int count_15service_warnning = cityManagerService.getCount_15service_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ����ά����������
			int count_15service_overdue = cityManagerService.getCount_15service_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ����ά����������
			int count_90service_normal = cityManagerService.getCount_90service_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ����ά����ʾ����
			int count_90service_warnning = cityManagerService.getCount_90service_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ����ά����������
			int count_90service_overdue = cityManagerService.getCount_90service_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ����ά����������
			int count_180service_normal = cityManagerService.getCount_180service_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ����ά����ʾ����
			int count_180service_warnning = cityManagerService.getCount_180service_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ����ά����������
			int count_180service_overdue = cityManagerService.getCount_180service_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ���ά����������
			int count_360service_normal = cityManagerService.getCount_360service_Normal(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ���ά����ʾ����
			int count_360service_warnning = cityManagerService.getCount_360service_Warnning(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
			// ���ά����������
			int count_360service_overdue = cityManagerService.getCount_360service_Overdue(id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
	
			ModelAndView mav = new ModelAndView("system/cityManagerTongji");
			mav.addObject("count", count);
			mav.addObject("count_registed", count_registed);
			mav.addObject("count_stop", count_stop);
			mav.addObject("count_destory", count_destory);
			mav.addObject("count_noregist", count_noregist);
			mav.addObject("count_rounds_normal", count_rounds_normal);
			mav.addObject("count_rounds_warnning", count_rounds_warnning);
			mav.addObject("count_rounds_overdue", count_rounds_overdue);
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
			mav.addObject("id_city", id_city);
			mav.addObject("id_district", id_district);
			mav.addObject("id_subdistrict", id_subdistrict);
			mav.addObject("desc",desc);
			//ϵͳ�����е���ʾ����
			List<System_setting> system_settingList=system_settingService.list();
			if(system_settingList!=null&&system_settingList.size()>0){
				mav.addObject("system_setting",system_settingList.get(0));
			}
			return mav;
		}
	}

	// ���ͳ���е����ֽ�������б�
	@RequestMapping("listForSearch")
	public ModelAndView listForSearch(String key, String search, HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc) {
		
		//
		List<Elevator> list = new ArrayList<Elevator>();
		//��Ϊ����url���洫�����ģ�������Ҫת��
//				try {
//					if(desc!=null){
//						desc=new String(desc.getBytes("ISO-8859-1"),"UTF-8");
//					}
//					
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	
		//��ѯ�ؼ���
		if(search==null){
			search="";
		}
		// ����������
		if (key.equals("count")) {
			list = cityManagerService.listCount(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ������ע������
		if (key.equals("count_registed")) {
			list = cityManagerService.listCount_Registed(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ����δע������
		if (key.equals("count_noregist")) {
			list = cityManagerService.listCount_NoRegist(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ������ͣ������
		if (key.equals("count_stop")) {
			list = cityManagerService.listCount_Stop(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ������ע������
		if (key.equals("count_destory")) {
			list = cityManagerService.listCount_Destory(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���������������
		if (key.equals("count_rounds_normal")) {
			list = cityManagerService.listCount_Rounds_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���������ʾ����
		if (key.equals("count_rounds_warnning")) {
			list = cityManagerService.listCount_Rounds_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���������������
		if (key.equals("count_rounds_overdue")) {
			list = cityManagerService.listCount_Rounds_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���ݰ���ά����������
		if (key.equals("count_15service_normal")) {
			list = cityManagerService.listCount_15service_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���ݰ���ά����ʾ����
		if (key.equals("count_15service_warnning")) {
			list = cityManagerService.listCount_15service_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���ݰ���ά����������
		if (key.equals("count_15service_overdue")) {
			list = cityManagerService.listCount_15service_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���ݼ���ά����������
		if (key.equals("count_90service_normal")) {
			list = cityManagerService.listCount_90service_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���ݼ���ά����ʾ����
		if (key.equals("count_90service_warnning")) {
			list = cityManagerService.listCount_90service_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���ݼ���ά����������
		if (key.equals("count_90service_overdue")) {
			list = cityManagerService.listCount_90service_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		// ���ݰ���ά����������
		if (key.equals("count_180service_normal")) {
			list = cityManagerService.listCount_180service_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		//���ݰ���ά����ʾ����
		if (key.equals("count_180service_warnning")) {
			list = cityManagerService.listCount_180service_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		//���ݰ���ά����������
		if (key.equals("count_180service_overdue")) {
			list = cityManagerService.listCount_180service_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		//�������ά����������
		if (key.equals("count_360service_normal")) {
			list = cityManagerService.listCount_360service_Normal(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		//�������ά����ʾ����
		if (key.equals("count_360service_warnning")) {
			list = cityManagerService.listCount_360service_Warnning(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}
		//�������ά����������
		if (key.equals("count_360service_overdue")) {
			list = cityManagerService.listCount_360service_Overdue(search, 10, request,id_city,id_district,id_subdistrict,id_service,id_user,id_test,keyType,desc);
		}

		ModelAndView mav = new ModelAndView("system/elevatorList");
		mav.addObject("list", list);
		mav.addObject("key",key);
		mav.addObject("search",search);
		mav.addObject("requestMapping", "elevator");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value="selectId_user",produces="text/html;charset=utf-8")
	public String selectId_user(){
		List<CityManager> list=cityManagerService.list();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
}
