package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;


import dao.ElevatorDao;
import dao.Elevator_stateDao;
import dao.Maint_report_idDao;
import dao.NoticeDao;
import util.DateUtils;
import vo.Elevator;
import vo.Elevator_state;
import vo.Maint_report_id;
import vo.Notice;

@Service("overdueTaskService")
public class NoticeService {
	@Resource
	public NoticeDao nDao;
	@Resource
	public ElevatorDao eDao;
	@Resource
	public Maint_report_idDao mriDao;
	@Resource
	public Elevator_stateDao esDao;
	/**
	 * ά������֪ͨ��ҵ��
	 */
	@SuppressWarnings("unchecked")
	public void createTask(){
		Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"SPRING��ʱ������������...");
		/*
			���¼�¼notice��this_serviceΪ�յļ�¼
	
			  ����ά����elevator_state �� 15��90��180��360 �ĸ�ʱ�䶼С
			            ��notice.last_service,������
			            ����notice.this_service = ����last_service����С
			            ���Ǹ�
			  ����ά����elevator_state �� 90��180��360  ����
			  ����ά����elevator_state �� 180��360 ����
			  ���ά����elevator_state �� 360 һ��
		*/
		//��ѯ������this_serviceΪ�յļ�¼
		DetachedCriteria dc_notice=DetachedCriteria.forClass(Notice.class);
		dc_notice.add(Restrictions.isNull("this_service"));
		List<Notice> nList=nDao.getListByDc(dc_notice);
		for(Notice n:nList){
			//��ѯ���ĸ�ʱ��
			Elevator_state es=esDao.get(Elevator_state.class, n.getId_elevator());
			long last_15_service=DateUtils.parse(es.getLast_15_service()).getTime();
			long last_90_service=DateUtils.parse(es.getLast_90_service()).getTime();
			long last_180_service=DateUtils.parse(es.getLast_180_service()).getTime();
			long last_360_service=DateUtils.parse(es.getLast_360_service()).getTime();
			long last_service=DateUtils.parse(n.getLast_service()).getTime();
			//�ж��Ƿ�Ҫ����
			if(n.getMaint_type()==1){
				//����ǰ���ά����ȡ���д���last_service����С���Ǹ�
				List<Long> services=new ArrayList<Long>();
				//���Ǵ���last_service����ӵ�services��ȥ��
				if(last_15_service>last_service){
					services.add(last_15_service);
				}
				if(last_90_service>last_service){
					services.add(last_90_service);
				}
				if(last_180_service>last_service){
					services.add(last_180_service);
				}
				if(last_360_service>last_service){
					services.add(last_360_service);
				}
				if(services.size()>0){
					//�ҳ������е���Сֵ
					long min=Collections.min(services);
					n.setThis_service(DateUtils.format1(new Date(min)));
					nDao.update(n);
				}
			}
			if(n.getMaint_type()==2){
				//����Ǽ���ά����ȡ90,180,360�д���last_service����С���Ǹ�
				List<Long> services=new ArrayList<Long>();
				//���Ǵ���last_service����ӵ�services��ȥ��
				if(last_90_service>last_service){
					services.add(last_90_service);
				}
				if(last_180_service>last_service){
					services.add(last_180_service);
				}
				if(last_360_service>last_service){
					services.add(last_360_service);
				}
				if(services.size()>0){
					//�ҳ������е���Сֵ
					long min=Collections.min(services);
					n.setThis_service(DateUtils.format1(new Date(min)));
					nDao.update(n);
				}
			}
			if(n.getMaint_type()==3){
				//����ǰ���ά����ȡ180,360����last_service����С���Ǹ�
				List<Long> services=new ArrayList<Long>();
				//���Ǵ���last_service����ӵ�services��ȥ��
				if(last_180_service>last_service){
					services.add(last_180_service);
				}
				if(last_360_service>last_service){
					services.add(last_360_service);
				}
				if(services.size()>0){
					//�ҳ������е���Сֵ
					long min=Collections.min(services);
					n.setThis_service(DateUtils.format1(new Date(min)));
					nDao.update(n);
				}
			}
			if(n.getMaint_type()==4){
				//��������ά����ȡ360����last_service
				if(last_360_service>last_service){
					n.setThis_service(DateUtils.format1(new Date(last_360_service)));
					nDao.update(n);
				}
			}
		}
		/*
			�����¼
			Ŀǰ���ڵ����У���id_elevator��maint_type��last_serviceΪ
			���Ϲؼ��֣�notice����û����Ӧ��¼�Ĳ����¼
			date_notice Ϊϵͳ����
			this_service Ϊ��   
			����maint_type�Ĳ�ͬ��last_serviceȡֵ��ͬ
			����ά�� last_service=last_15_service
			����ά�� last_service=last_90_service
			����ά�� last_service=last_180_service
			ȫ��ά�� last_service=last_360_service
		*/
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator_state.class);
		//ֻ��ѯ��ע��ĵ���
		List<Integer> ids=eDao.getListBySQL("select id_elevator from elevator where register_status=1");
		dc.add(Restrictions.in("idelevator", ids));
		//Ѳ�첻��ѯ
		List<Elevator_state> eslist= esDao.getListByDc(dc);
		for(Elevator_state es:eslist){
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"���ڼ��"+es.getIdelevator()+"�ŵ��ݵ�ά����¼��");
			//ȡ���ĸ�����
			long last_15_service=DateUtils.parse(es.getLast_15_service()).getTime();
			long last_90_service=DateUtils.parse(es.getLast_90_service()).getTime();
			long last_180_service=DateUtils.parse(es.getLast_180_service()).getTime();
			long last_360_service=DateUtils.parse(es.getLast_360_service()).getTime();
			List<Long> services=new ArrayList<Long>();
			services.add(last_15_service);
			services.add(last_90_service);
			services.add(last_180_service);
			services.add(last_360_service);
			//�ҳ��ĸ��е����ֵ
			long max_4=Collections.max(services);
			//�ҳ��������е����ֵ
			services.remove(0);
			long max_3=Collections.max(services);
			//�ҳ��������е����ֵ
			services.remove(0);
			long max_2=Collections.max(services);
			//�ҳ����һ��ֵ
			long max_1=last_360_service;
			//��õ�ǰ����
			long today=DateUtils.parse(DateUtils.format1(new Date())).getTime();
			//��õ�ǰ���ݵ�ά����λ
			int id_service=eDao.get(Elevator.class, es.getIdelevator()).getId_service();
			//��õ�ǰ���ݵ�ʹ�õ�λ
			int id_user=eDao.get(Elevator.class, es.getIdelevator()).getId_user();
			
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"today is:"+today);
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"���һ�ΰ���ά��ʱ���ǣ�"+max_4+",�����ѹ���"+(today-max_4));
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"���һ�μ���ά��ʱ���ǣ�"+max_3+",�����ѹ���"+(today-max_3));
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"���һ�ΰ���ά��ʱ���ǣ�"+max_2+",�����ѹ���"+(today-max_2));
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"���һ�����ά��ʱ���ǣ�"+max_1+",�����ѹ���"+(today-max_1));
			//�жϸõ��ݰ���ά���Ƿ�����
			if((today-max_4)>(15L*24*60*60*1000)){
				Notice notice=new Notice();
				notice.setMaint_type(1);
				notice.setId_elevator(es.getIdelevator());
				notice.setId_service(id_service);
				notice.setId_user(id_user);
				notice.setLast_service(es.getLast_15_service());
				notice.setDate_notice(DateUtils.format1(new Date()));
				try {
					saveOrUpdateNotice(notice);
					Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,es.getIdelevator()+"�ŵ��ݰ���ά�������ڣ�");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//�жϸõ��ݼ���ά���Ƿ�����
			if((today-max_3)>(90L*24*60*60*1000)){
				Notice notice=new Notice();
				notice.setMaint_type(2);
				notice.setId_elevator(es.getIdelevator());
				notice.setId_service(id_service);
				notice.setId_user(id_user);
				notice.setLast_service(es.getLast_90_service());
				notice.setDate_notice(DateUtils.format1(new Date()));
				try {
					saveOrUpdateNotice(notice);
					Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,es.getIdelevator()+"�ŵ��ݼ���ά�������ڣ�");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//�жϸõ��ݰ���ά���Ƿ�����
			if((today-max_2)>(180L*24*60*60*1000)){
				Notice notice=new Notice();
				notice.setMaint_type(3);
				notice.setId_elevator(es.getIdelevator());
				notice.setId_service(id_service);
				notice.setId_user(id_user);
				notice.setLast_service(es.getLast_180_service());
				notice.setDate_notice(DateUtils.format1(new Date()));
				try {
					saveOrUpdateNotice(notice);
					Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,es.getIdelevator()+"�ŵ��ݰ���ά�������ڣ�");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//�жϸõ��ݰ���ά���Ƿ�����
			if((today-max_1)>(360L*24*60*60*1000)){
				Notice notice=new Notice();
				notice.setMaint_type(4);
				notice.setId_elevator(es.getIdelevator());
				notice.setId_service(id_service);
				notice.setId_user(id_user);
				notice.setLast_service(es.getLast_360_service());
				notice.setDate_notice(DateUtils.format1(new Date()));
				try {
					saveOrUpdateNotice(notice);
					Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,es.getIdelevator()+"�ŵ���ȫ��ά�������ڣ�");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	@SuppressWarnings("unchecked")
	public void saveOrUpdateNotice(Notice notice)throws Exception{
		System.out.println("��������֪ͨ������"+notice.getId_elevator()+"=="+notice.getMaint_type());
		//�жϸü�¼�Ƿ����
		DetachedCriteria dc=DetachedCriteria.forClass(Notice.class);
		dc.add(Restrictions.eq("id_elevator", notice.getId_elevator()));
		dc.add(Restrictions.eq("maint_type", notice.getMaint_type()));
		dc.add(Restrictions.eq("last_service", notice.getLast_service()));
		List<Notice> nlist=nDao.getListByDc(dc);
		
		if(nlist==null||nlist.isEmpty()){
			System.out.println("��飺"+notice.getId_elevator()+",�Ѵ������ڼ�¼��"+nlist.size());
			//����ü�¼
			nDao.save(notice);	
		}
	}
	@SuppressWarnings("unchecked")
	public List<Notice> listByIds(List<Integer> ids,int noticeType,int pagesize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Notice.class);
		if(ids!=null&&!ids.isEmpty()){
			dc.add(Restrictions.in("id_elevator", ids));
		}
		
		if(noticeType==1){
			//ֻ��ѯ�Ѵ����
			dc.add(Restrictions.isNotNull("this_service"));
		}
		if(noticeType==2){
			//ֻ��ѯδ�����
			dc.add(Restrictions.isNull("this_service"));
		}
		return nDao.findPageByDcQuery(dc, 5, request);
	}
	@SuppressWarnings("unchecked")
	public List<Notice> listByIds(List<Integer> ids){
		DetachedCriteria dc=DetachedCriteria.forClass(Notice.class);
		if(ids!=null&&!ids.isEmpty()){
			dc.add(Restrictions.in("id_elevator", ids));
		}
		return nDao.getListByDc(dc);
	}
}
