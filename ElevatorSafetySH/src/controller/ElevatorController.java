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

import service.ElevatorService;
import service.HistoryService;
import service.History_listService;
import service.SystemstateService;
import vo.Elevator;
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

	@RequestMapping("insert")
	public String insert(Elevator elevator, HttpServletRequest request, HttpServletResponse response) {
		Elevator e = (Elevator) request.getSession().getAttribute("el");
		e.setDate_enable(elevator.getDate_enable());
		e.setProject_duty(elevator.getProject_duty());
		e.setId_service(elevator.getId_service());
		e.setId_test(elevator.getId_test());
		e.setNum_floor_elevator(elevator.getNum_floor_elevator());
		e.setId_elevator_model(elevator.getId_elevator_model());
		e.setRegister_status(elevator.getRegister_status());
		e.setDesc(elevator.getDesc());
		e.setGis_x(elevator.getGis_x());
		e.setGis_y(elevator.getGis_y());
		e.setGis_type(elevator.getGis_type());
		int id_elevator = Integer.parseInt(elevatorService.insert(e).toString());
		request.getSession().setAttribute("id_elevator", id_elevator);
		// systemstate�޸��ֶ�version_elevator +1
		systemService.update_version_elevator();
		// ����history��Ϣ
		History history = new History();
		history.setType(21);// ����
		Operator op = (Operator) request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());// ��¼��id
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = format.format(date);
		history.setDatetime(s);// ��ǰʱ��
		int idhistory = Integer.parseInt(historyService.insert(history).toString());
		// ������Ϣ��history_list����
		History_list hilist = new History_list();
		History_listKey key = new History_listKey();
		key.setIdhistory(idhistory);
		key.setKey(21);
		hilist.setKey(key);// key��ʾ������������
		hilist.setValue(id_elevator + "");
		System.out.println(history_listService.insert(hilist).toString());
		// ��ʱ����ɹ�֮�� ת��elevator_state����ȥ�������״̬����Ϣ
		return "/system/insertElevator_state";
	}

	@RequestMapping("insertTo1")
	public String insert1(Elevator elevator, HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("elevator", elevator);
		return "/system/insertElevatorDeclaration1";
	}

	@RequestMapping("insertTo2")
	public String insert2(Elevator elevator, HttpServletRequest request, HttpServletResponse response) {
		Elevator el = (Elevator) request.getSession().getAttribute("elevator");// ���ڵ�һҳ��ֵ
		el.setCheck_construct(elevator.getCheck_construct());
		el.setCheck_construct_code(elevator.getCheck_construct_code());
		el.setId_installer(elevator.getId_installer());
		el.setId_owner(elevator.getId_owner());
		el.setId_user(elevator.getId_user());
		el.setId_city(elevator.getId_city());
		el.setId_district(elevator.getId_district());
		el.setId_subdistrict(elevator.getId_subdistrict());
		el.setAddress(elevator.getAddress());
		el.setDate_declare(elevator.getDate_declare());
		el.setDate_register(elevator.getDate_register());
		request.getSession().setAttribute("el", el);
		return "/system/insertElevatorDeclaration2";
	}

	// �����ල����ͳ�Ʋ�ѯ
	@RequestMapping("search")
	public ModelAndView search() {
		// ��������
		int count = elevatorService.getCount();
		// ��ע������
		int count_registed = elevatorService.getCount_Registed();
		// ͣ������
		int count_stop = elevatorService.getCount_Stop();
		// ��ע������
		int count_destory = elevatorService.getCount_Destory();
		// δע������
		int count_noregist = elevatorService.getCount_NoRegist();
		// �����������
		int count_rounds_normal = elevatorService.getCount_Rounds_Normal();
		// �����ʾ����
		int count_rounds_warnning = elevatorService.getCount_Rounds_Warnning();
		// ���Ԥ������
		int count_rounds_overdue = elevatorService.getCount_Rounds_Overdue();
		// ����ά����������
		int count_15service_normal = elevatorService.getCount_15service_Normal();
		// ����ά����ʾ����
		int count_15service_warnning = elevatorService.getCount_15service_Warnning();
		// ����ά����������
		int count_15service_overdue = elevatorService.getCount_15service_Overdue();
		// ����ά����������
		int count_90service_normal = elevatorService.getCount_90service_Normal();
		// ����ά����ʾ����
		int count_90service_warnning = elevatorService.getCount_90service_Warnning();
		// ����ά����������
		int count_90service_overdue = elevatorService.getCount_90service_Overdue();
		// ����ά����������
		int count_180service_normal = elevatorService.getCount_180service_Normal();
		// ����ά����ʾ����
		int count_180service_warnning = elevatorService.getCount_180service_Warnning();
		// ����ά����������
		int count_180service_overdue = elevatorService.getCount_180service_Overdue();
		// ���ά����������
		int count_360service_normal = elevatorService.getCount_360service_Normal();
		// ���ά����ʾ����
		int count_360service_warnning = elevatorService.getCount_360service_Warnning();
		// ���ά����������
		int count_360service_overdue = elevatorService.getCount_360service_Overdue();

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

	// ���ͳ���е����ֽ�������б�
	@RequestMapping("listForSearch")
	public ModelAndView listForSearch(String key, String search, HttpServletRequest request) {
		//
		List<Elevator> list = new ArrayList<Elevator>();
		//��ѯ�ؼ���
		if(search==null){
			search="";
		}
		// ����������
		if (key.equals("count")) {
			list = elevatorService.listCount(search, 10, request);
		}
		// ������ע������
		if (key.equals("count_registed")) {
			list = elevatorService.listCount_Registed(search, 10, request);
		}
		// ����δע������
		if (key.equals("count_noregist")) {
			list = elevatorService.listCount_NoRegist(search, 10, request);
		}
		// ������ͣ������
		if (key.equals("count_stop")) {
			list = elevatorService.listCount_Stop(search, 10, request);
		}
		// ������ע������
		if (key.equals("count_destory")) {
			list = elevatorService.listCount_Destory(search, 10, request);
		}
		// ���������������
		if (key.equals("count_rounds_normal")) {
			list = elevatorService.listCount_Rounds_Normal(search, 10, request);
		}
		// ���������ʾ����
		if (key.equals("count_rounds_warnning")) {
			list = elevatorService.listCount_Rounds_Warnning(search, 10, request);
		}
		// ���������������
		if (key.equals("count_rounds_overdue")) {
			list = elevatorService.listCount_Rounds_Overdue(search, 10, request);
		}
		// ���ݰ���ά����������
		if (key.equals("count_15service_normal")) {
			list = elevatorService.listCount_15service_Normal(search, 10, request);
		}
		// ���ݰ���ά����ʾ����
		if (key.equals("count_15service_warnning")) {
			list = elevatorService.listCount_15service_Warnning(search, 10, request);
		}
		// ���ݰ���ά����������
		if (key.equals("count_15service_overdue")) {
			list = elevatorService.listCount_15service_Overdue(search, 10, request);
		}
		// ���ݼ���ά����������
		if (key.equals("count_90service_normal")) {
			list = elevatorService.listCount_90service_Normal(search, 10, request);
		}
		// ���ݼ���ά����ʾ����
		if (key.equals("count_90service_warnning")) {
			list = elevatorService.listCount_90service_Warnning(search, 10, request);
		}
		// ���ݼ���ά����������
		if (key.equals("count_90service_overdue")) {
			list = elevatorService.listCount_90service_Overdue(search, 10, request);
		}
		// ���ݰ���ά����������
		if (key.equals("count_180service_normal")) {
			list = elevatorService.listCount_180service_Normal(search, 10, request);
		}
		//���ݰ���ά����ʾ����
		if (key.equals("count_180service_warnning")) {
			list = elevatorService.listCount_180service_Warnning(search, 10, request);
		}
		//���ݰ���ά����������
		if (key.equals("count_180service_overdue")) {
			list = elevatorService.listCount_180service_Overdue(search, 10, request);
		}
		//�������ά����������
		if (key.equals("count_360service_normal")) {
			list = elevatorService.listCount_360service_Normal(search, 10, request);
		}
		//�������ά����ʾ����
		if (key.equals("count_360service_warnning")) {
			list = elevatorService.listCount_360service_Warnning(search, 10, request);
		}
		//�������ά����������
		if (key.equals("count_360service_overdue")) {
			list = elevatorService.listCount_360service_Overdue(search, 10, request);
		}

		ModelAndView mav = new ModelAndView("system/elevatorList");
		mav.addObject("list", list);
		mav.addObject("key",key);
		mav.addObject("search",search);
		return mav;
	}
}
