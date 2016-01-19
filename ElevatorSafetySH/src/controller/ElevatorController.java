package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.CitylistService;
import service.DistictlistService;
import service.ElevatorService;
import service.Elevator_tag_init_taskService;
import service.HistoryService;
import service.History_listService;
import service.SubdistictlistService;
import service.SystemstateService;
import vo.Elevator;
import vo.Elevator_tag_init_task;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;

@Controller     
@RequestMapping("elevator")
public class ElevatorController {
	@Resource
	public ElevatorService elevatorService;
	@Resource
	public SystemstateService systemService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@Resource
	public CitylistService cityService;
	@Resource
	public DistictlistService distickService;
	@Resource
	public SubdistictlistService subService;
	@Resource
	public Elevator_tag_init_taskService elevator_tag_init_taskService;
	@RequestMapping("insert")
	public String insert(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		Elevator el=(Elevator)request.getSession().getAttribute("elevator");//���ڵ�һҳ��ֵ
		el.setId_city(elevator.getId_city());
		el.setId_district(elevator.getId_district());
		el.setId_subdistrict(elevator.getId_subdistrict());
		//��������λ�ü���ǰ�������������
		String city=cityService.listBy_Idcity(elevator.getId_city()).getName_city();
		String dist=distickService.getListByCityId(elevator.getId_city(),elevator.getId_district()).getName_district();
		String subs=subService.getListById(elevator.getId_city(), elevator.getId_district(), elevator.getId_subdistrict()).getName_subdistrict();
		el.setAddress(city+dist+subs+elevator.getAddress());
		el.setNum_floor_elevator(elevator.getNum_floor_elevator());
		el.setId_elevator_model(elevator.getId_elevator_model());
		el.setDate_declare(elevator.getDate_declare());
		int id_elevator=Integer.parseInt(elevatorService.insert(el).toString());
		request.getSession().setAttribute("id_elevator",id_elevator);
		//systemstate�޸��ֶ�version_elevator +1
		systemService.update_version_elevator();
		//����history��Ϣ
		History history=new History();
		history.setType(21);//����
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//��¼��id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//��ǰʱ��
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//������Ϣ��history_list����
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(21);
        hilist.setKey(key);//key��ʾ������������
        hilist.setValue(id_elevator+"");
        System.out.println( history_listService.insert(hilist).toString());
		//��ʱ����ɹ�֮�� ת��elevator_state����ȥ�������״̬����Ϣ
        return "/system/insertElevator_state";
	}
	@RequestMapping("yuaninsert")
	public String yuaninsert(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		Elevator el=(Elevator)request.getSession().getAttribute("yuanelevator");//���ڵ�һҳ��ֵ
		el.setRegister_status(elevator.getRegister_status());
		el.setId_city(elevator.getId_city());
		el.setId_district(elevator.getId_district());
		el.setId_subdistrict(elevator.getId_subdistrict());
		//��������λ�ü���ǰ�������������
		String city=cityService.listBy_Idcity(elevator.getId_city()).getName_city();
		String dist=distickService.getListByCityId(elevator.getId_city(),elevator.getId_district()).getName_district();
		String subs=subService.getListById(elevator.getId_city(), elevator.getId_district(), elevator.getId_subdistrict()).getName_subdistrict();
		el.setAddress(city+dist+subs+elevator.getAddress());
		el.setNum_floor_elevator(elevator.getNum_floor_elevator());
		el.setId_elevator_model(elevator.getId_elevator_model());
		el.setDate_declare(elevator.getDate_declare());
		el.setCheck_construct(elevator.getCheck_construct());
		el.setId_service(elevator.getId_service());
		el.setId_test(elevator.getId_test());
		el.setCheck_construct_code(elevator.getCheck_construct_code());
		el.setDate_register(elevator.getDate_register());
		el.setDate_enable(elevator.getDate_enable());
		int id_elevator=Integer.parseInt(elevatorService.insert(el).toString());
		request.getSession().setAttribute("yuanid_elevator",id_elevator);
		//systemstate�޸��ֶ�version_elevator +1
		systemService.update_version_elevator();
		//����history��Ϣ
		History history=new History();
		history.setType(23);//����
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//��¼��id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//��ǰʱ��
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//������Ϣ��history_list����
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(21);
        hilist.setKey(key);//key��ʾ������������
        hilist.setValue(id_elevator+"");
        System.out.println( history_listService.insert(hilist).toString());
        //elevator_tag_init_task��Ĳ���
        Elevator_tag_init_task elevator_tag_init_task=new Elevator_tag_init_task();
        elevator_tag_init_task.setTitle(el.getDesc());
        elevator_tag_init_task.setElevator_id(el.getId_elevator());
        //����֮�� ��ѯ����
        Elevator leix=elevatorService.getEById(id_elevator);
        elevator_tag_init_task.setElevator_type(leix.getElevatorType().getElevatortype());
        elevator_tag_init_task.setElevator_code(el.getDevice_code());
        elevator_tag_init_task.setElevator_address(el.getAddress());
        elevator_tag_init_task.setElevator_layer_number(el.getNum_floor_elevator());
        elevator_tag_init_taskService.insert(elevator_tag_init_task);
        //��ʱ����ɹ�֮�� ת��elevator_state����ȥ�������״̬����Ϣ
        return "/system/yuanElevator_state";
	}
	@RequestMapping("insertTo1")
	public String insert1(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("elevator", elevator);
		return "/system/insertElevatorDeclaration1";
	}
	@RequestMapping("insertTo2")
	public String insert2(Elevator elevator,HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("yuanelevator", elevator);
		return "/system/yuanElevatorDeclaration1";
	}

	//�����ල����ͳ�Ʋ�ѯ
		@RequestMapping("search")
		public ModelAndView search(HttpServletRequest request) {
			//���ҵ�ǰ��¼��
			Operator op=(Operator)request.getSession().getAttribute("login");
			if(!op.getTypeOperator().equals("00")){
				ModelAndView mav=new ModelAndView("error");
				mav.addObject("error","��ǰ��¼�˷Ǽ����ල������Ա!");
				return mav;
			}else{
				//��¼����������
				String id_city=op.getIdcity();
				String id_district=op.getIddistrict();
				String id_subdistrict=op.getIdsubdistrict();
				// ��������
				int count = elevatorService.getCount(id_city,id_district,id_subdistrict);
				// ��ע������
				int count_registed = elevatorService.getCount_Registed(id_city,id_district,id_subdistrict);
				// ͣ������
				int count_stop = elevatorService.getCount_Stop(id_city,id_district,id_subdistrict);
				// ��ע������
				int count_destory = elevatorService.getCount_Destory(id_city,id_district,id_subdistrict);
				// δע������
				int count_noregist = elevatorService.getCount_NoRegist(id_city,id_district,id_subdistrict);
				// �����������
				int count_rounds_normal = elevatorService.getCount_Rounds_Normal(id_city,id_district,id_subdistrict);
				// �����ʾ����
				int count_rounds_warnning = elevatorService.getCount_Rounds_Warnning(id_city,id_district,id_subdistrict);
				// ���Ԥ������
				int count_rounds_overdue = elevatorService.getCount_Rounds_Overdue(id_city,id_district,id_subdistrict);
				// ����ά����������
				int count_15service_normal = elevatorService.getCount_15service_Normal(id_city,id_district,id_subdistrict);
				// ����ά����ʾ����
				int count_15service_warnning = elevatorService.getCount_15service_Warnning(id_city,id_district,id_subdistrict);
				// ����ά����������
				int count_15service_overdue = elevatorService.getCount_15service_Overdue(id_city,id_district,id_subdistrict);
				// ����ά����������
				int count_90service_normal = elevatorService.getCount_90service_Normal(id_city,id_district,id_subdistrict);
				// ����ά����ʾ����
				int count_90service_warnning = elevatorService.getCount_90service_Warnning(id_city,id_district,id_subdistrict);
				// ����ά����������
				int count_90service_overdue = elevatorService.getCount_90service_Overdue(id_city,id_district,id_subdistrict);
				// ����ά����������
				int count_180service_normal = elevatorService.getCount_180service_Normal(id_city,id_district,id_subdistrict);
				// ����ά����ʾ����
				int count_180service_warnning = elevatorService.getCount_180service_Warnning(id_city,id_district,id_subdistrict);
				// ����ά����������
				int count_180service_overdue = elevatorService.getCount_180service_Overdue(id_city,id_district,id_subdistrict);
				// ���ά����������
				int count_360service_normal = elevatorService.getCount_360service_Normal(id_city,id_district,id_subdistrict);
				// ���ά����ʾ����
				int count_360service_warnning = elevatorService.getCount_360service_Warnning(id_city,id_district,id_subdistrict);
				// ���ά����������
				int count_360service_overdue = elevatorService.getCount_360service_Overdue(id_city,id_district,id_subdistrict);
		
				ModelAndView mav = new ModelAndView("system/elevatorTongji");
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
				return mav;
			}
		}

		// ���ͳ���е����ֽ�������б�
		@RequestMapping("listForSearch")
		public ModelAndView listForSearch(String key, String search, HttpServletRequest request) {
			Operator op=(Operator)request.getSession().getAttribute("login");
			//
			List<Elevator> list = new ArrayList<Elevator>();
			//��¼����������
			String id_city=op.getIdcity();
			String id_district=op.getIddistrict();
			String id_subdistrict=op.getIdsubdistrict();
			//��ѯ�ؼ���
			if(search==null){
				search="";
			}
			// ����������
			if (key.equals("count")) {
				list = elevatorService.listCount(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ������ע������
			if (key.equals("count_registed")) {
				list = elevatorService.listCount_Registed(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ����δע������
			if (key.equals("count_noregist")) {
				list = elevatorService.listCount_NoRegist(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ������ͣ������
			if (key.equals("count_stop")) {
				list = elevatorService.listCount_Stop(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ������ע������
			if (key.equals("count_destory")) {
				list = elevatorService.listCount_Destory(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���������������
			if (key.equals("count_rounds_normal")) {
				list = elevatorService.listCount_Rounds_Normal(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���������ʾ����
			if (key.equals("count_rounds_warnning")) {
				list = elevatorService.listCount_Rounds_Warnning(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���������������
			if (key.equals("count_rounds_overdue")) {
				list = elevatorService.listCount_Rounds_Overdue(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���ݰ���ά����������
			if (key.equals("count_15service_normal")) {
				list = elevatorService.listCount_15service_Normal(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���ݰ���ά����ʾ����
			if (key.equals("count_15service_warnning")) {
				list = elevatorService.listCount_15service_Warnning(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���ݰ���ά����������
			if (key.equals("count_15service_overdue")) {
				list = elevatorService.listCount_15service_Overdue(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���ݼ���ά����������
			if (key.equals("count_90service_normal")) {
				list = elevatorService.listCount_90service_Normal(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���ݼ���ά����ʾ����
			if (key.equals("count_90service_warnning")) {
				list = elevatorService.listCount_90service_Warnning(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���ݼ���ά����������
			if (key.equals("count_90service_overdue")) {
				list = elevatorService.listCount_90service_Overdue(search, 10, request,id_city,id_district,id_subdistrict);
			}
			// ���ݰ���ά����������
			if (key.equals("count_180service_normal")) {
				list = elevatorService.listCount_180service_Normal(search, 10, request,id_city,id_district,id_subdistrict);
			}
			//���ݰ���ά����ʾ����
			if (key.equals("count_180service_warnning")) {
				list = elevatorService.listCount_180service_Warnning(search, 10, request,id_city,id_district,id_subdistrict);
			}
			//���ݰ���ά����������
			if (key.equals("count_180service_overdue")) {
				list = elevatorService.listCount_180service_Overdue(search, 10, request,id_city,id_district,id_subdistrict);
			}
			//�������ά����������
			if (key.equals("count_360service_normal")) {
				list = elevatorService.listCount_360service_Normal(search, 10, request,id_city,id_district,id_subdistrict);
			}
			//�������ά����ʾ����
			if (key.equals("count_360service_warnning")) {
				list = elevatorService.listCount_360service_Warnning(search, 10, request,id_city,id_district,id_subdistrict);
			}
			//�������ά����������
			if (key.equals("count_360service_overdue")) {
				list = elevatorService.listCount_360service_Overdue(search, 10, request,id_city,id_district,id_subdistrict);
			}

			ModelAndView mav = new ModelAndView("system/elevatorList");
			mav.addObject("list", list);
			mav.addObject("key",key);
			mav.addObject("search",search);
			mav.addObject("requestMapping", "elevator");
			return mav;
		}
	
				
	//ͨ��id��ѯ���ݵ���ϸ��Ϣ
	@RequestMapping("selectElevatorByID")
	public ModelAndView selectElevatorByID(Elevator e){
		System.out.println(e.getId_elevator());
		ModelAndView mav=new ModelAndView("system/elevatorinfrom");
		mav.addObject("eById",elevatorService.getEById(e.getId_elevator()));
		return mav;
	}
	//��ѯδע��ĵ���
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/ElevatorRegist");
		mav.addObject("elevListRegist", elevatorService.listCount_NoRegist("",12, request,null,null,null));
		return mav;
	}
	@RequestMapping("list1")
	public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/ElevatorRegist");
		System.out.println(key);
		mav.addObject("elevListRegist", elevatorService.listCount_NoRegist(key,12, request,null,null,null));
		return mav;
	}
	
	@RequestMapping("regist")
	public String regist(Elevator e,HttpServletRequest request){
		//��ѯ�õ���
		request.getSession().setAttribute("id_elevator1", e.getId_elevator());
		Elevator elevator=elevatorService.getEById(e.getId_elevator());
		elevator.setRegister_status("1");
		elevator.setId_service(e.getId_service());
		elevator.setId_test(e.getId_test());
		elevator.setCheck_construct_code(e.getCheck_construct_code());
		//ע��ʱ��Ϊ��ǰʱ��
		Date time=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String s=format.format(time);
		elevator.setDate_register(s);
		elevator.setDate_enable(e.getDate_enable());
		//ע��
		elevatorService.update(elevator);//�޸�
		//systemstate�޸��ֶ�version_elevator +1
		systemService.update_version_elevator();
		//����history��Ϣ
		History history=new History();
		history.setType(22);//����
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//��¼��id
		Date date=new Date();
		SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s2=format1.format(date);
		history.setDatetime(s2);//��ǰʱ��
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//������Ϣ��history_list����
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(21);
        hilist.setKey(key);//key��ʾ������������
        hilist.setValue(e.getId_elevator()+"");
        System.out.println( history_listService.insert(hilist).toString());
		//elevator_tag_init_task��Ĳ���
        Elevator_tag_init_task elevator_tag_init_task=new Elevator_tag_init_task();
        elevator_tag_init_task.setTitle(elevator.getDesc());
        elevator_tag_init_task.setElevator_id(e.getId_elevator());
        elevator_tag_init_task.setElevator_type(elevator.getElevatorType().getElevatortype());
        elevator_tag_init_task.setElevator_code(elevator.getDevice_code());
        elevator_tag_init_task.setElevator_address(elevator.getAddress());
        elevator_tag_init_task.setElevator_layer_number(elevator.getNum_floor_elevator());
        elevator_tag_init_taskService.insert(elevator_tag_init_task);
        return "/system/insertElevator_stateRegist";//���
		
	}
	//��ѯ���еĵ���
	@RequestMapping(value="elevatorIdList",produces="text/html;charset=utf-8")
	@ResponseBody
	public String elevatorIdList(HttpServletRequest request){
	List<Elevator> e=elevatorService.listCount1(request);
	JSONArray array=JSONArray.fromObject(e);
	return array.toString();
	}
	
}
