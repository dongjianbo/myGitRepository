package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import service.ElevatorService;
import service.NoticeService;
import service.OperatorService;
import vo.Elevator;
import vo.Notice;
import vo.Operator;
@Controller
@RequestMapping("notice")
public class NoticeController {
	@Resource
	public OperatorService operatorService;
	@Resource
	public ElevatorService elevatorService;
	@Resource
	public NoticeService noticeService;
	@RequestMapping("search")
	public ModelAndView search(String key,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,String desc,int noticeType,HttpServletRequest request) {
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
			ModelAndView mav = new ModelAndView("system/noticeTongji");
			//����֪ͨ������
			mav.addObject("noticeCount",0);
			//�Ѵ����֪ͨ������
			mav.addObject("noticeCount_processed",0);
			//δ�����֪ͨ������
			mav.addObject("noticeCount_noprocessed",0);
			//���������ĵ��ݱ��
			List<Integer> ids=elevatorService.getElevatorIds(id_city, id_district, id_subdistrict, id_service, id_user, id_test, desc);
			if(ids!=null&&!ids.isEmpty()){
				//����״̬��ѯ��Щ���ݵ����ڼ�¼
				List<Notice> noticeList=noticeService.listByIds(ids,noticeType,5,request);
				mav.addObject("nList",noticeList);
				//��ѯȫ��״̬�µ����ڼ�¼
				noticeList=noticeService.listByIds(ids);
				//��ѯ���ڼ�¼�ĸ���
				mav.addObject("noticeCount",noticeList.size());
				//��ѯ�Ѵ���ĸ���
				int noticeCount_processed=0;
				int noticeCount_noprocessed=0;
				for(Notice n:noticeList){
					if(n.getThis_service()!=null){
						noticeCount_processed++;
					}else{
						noticeCount_noprocessed++;
					}
				}
				mav.addObject("noticeCount_processed",noticeCount_processed);
				mav.addObject("noticeCount_noprocessed",noticeCount_noprocessed);
			}else{
				mav.addObject("nList",new ArrayList<Notice>());
			}
			mav.addObject("id_city", id_city);
			mav.addObject("id_district", id_district);
			mav.addObject("id_subdistrict", id_subdistrict);
			
			mav.addObject("desc",desc);
			mav.addObject("noticeType",noticeType);
			return mav;
		}
	}
	@RequestMapping("searchForService")
	public ModelAndView searchForService(String key,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,String desc,int noticeType,HttpServletRequest request) {
		//���ҵ�ǰ��¼��
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("10")&&!op.getTypeOperator().equals("11")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","��ǰ��¼�˷�ά����λ��Ա!");
			return mav;
		}else{
			op=operatorService.findById(op.getIdoperator());
			id_user=0;
			id_service=op.getIdOrganization();
			id_test=0;
			ModelAndView mav = new ModelAndView("system/noticeTongji_service");
			//����֪ͨ������
			mav.addObject("noticeCount",0);
			//�Ѵ����֪ͨ������
			mav.addObject("noticeCount_processed",0);
			//δ�����֪ͨ������
			mav.addObject("noticeCount_noprocessed",0);
			//���������ĵ��ݱ��
			List<Integer> ids=elevatorService.getElevatorIds(null, null, null, id_service, 0, 0, null);
			if(ids!=null&&!ids.isEmpty()){
				//��ѯ��Щ���ݵ����ڼ�¼
				List<Notice> noticeList=noticeService.listByIds(ids,noticeType,5,request);
				mav.addObject("nList",noticeList);
				//��ѯȫ��״̬�µ����ڼ�¼
				noticeList=noticeService.listByIds(ids);
				//��ѯ���ڼ�¼�ĸ���
				mav.addObject("noticeCount",noticeList.size());
				//��ѯ�Ѵ���ĸ���
				int noticeCount_processed=0;
				int noticeCount_noprocessed=0;
				for(Notice n:noticeList){
					if(n.getThis_service()!=null){
						noticeCount_processed++;
					}else{
						noticeCount_noprocessed++;
					}
				}
				mav.addObject("noticeCount_processed",noticeCount_processed);
				mav.addObject("noticeCount_noprocessed",noticeCount_noprocessed);
			}else{
				mav.addObject("nList",new ArrayList<Notice>());
			}
			mav.addObject("id_city", id_city);
			mav.addObject("id_district", id_district);
			mav.addObject("id_subdistrict", id_subdistrict);
			mav.addObject("id_service",id_service);
			mav.addObject("id_user",id_user);
			mav.addObject("id_test",id_test);
			mav.addObject("desc",desc);
			mav.addObject("noticeType",noticeType);
			return mav;
		}
	}
	@RequestMapping("searchForUser")
	public ModelAndView searchForUser(String key,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,String desc,int noticeType,HttpServletRequest request) {
		//���ҵ�ǰ��¼��
		Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("20")&&!op.getTypeOperator().equals("21")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","��ǰ��¼�˷�ʹ�õ�λ��Ա!");
			return mav;
		}else{
			op=operatorService.findById(op.getIdoperator());
			id_user=op.getIdOrganization();
			id_service=0;
			id_test=0;
			ModelAndView mav = new ModelAndView("system/noticeTongji_user");
			//����֪ͨ������
			mav.addObject("noticeCount",0);
			//�Ѵ����֪ͨ������
			mav.addObject("noticeCount_processed",0);
			//δ�����֪ͨ������
			mav.addObject("noticeCount_noprocessed",0);
			//���������ĵ��ݱ��
			List<Integer> ids=elevatorService.getElevatorIds(null, null, null, id_service, id_user, id_test, null);
			if(ids!=null&&!ids.isEmpty()){
				//��ѯ��Щ���ݵ����ڼ�¼
				List<Notice> noticeList=noticeService.listByIds(ids,noticeType,5,request);
				mav.addObject("nList",noticeList);
				//��ѯȫ��״̬�µ����ڼ�¼
				noticeList=noticeService.listByIds(ids);
				//��ѯ���ڼ�¼�ĸ���
				mav.addObject("noticeCount",noticeList.size());
				//��ѯ�Ѵ���ĸ���
				int noticeCount_processed=0;
				int noticeCount_noprocessed=0;
				for(Notice n:noticeList){
					if(n.getThis_service()!=null){
						noticeCount_processed++;
					}else{
						noticeCount_noprocessed++;
					}
				}
				mav.addObject("noticeCount_processed",noticeCount_processed);
				mav.addObject("noticeCount_noprocessed",noticeCount_noprocessed);
			}else{
				mav.addObject("nList",new ArrayList<Notice>());
			}
			mav.addObject("id_city", id_city);
			mav.addObject("id_district", id_district);
			mav.addObject("id_subdistrict", id_subdistrict);
			mav.addObject("id_service",id_service);
			mav.addObject("id_user",id_user);
			mav.addObject("id_test",id_test);
			mav.addObject("desc",desc);
			mav.addObject("noticeType",noticeType);
			return mav;
		}
	}
}
