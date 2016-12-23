package service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Elevator;
import vo.Maint_report_id;
import vo.Service1;
import dao.ElevatorDao;
import dao.Maint_report_idDao;
import dao.ServiceDao;
import util.DateUtils;

@Service
public class ServiceService {
	@Resource
    public ServiceDao serviceDao;
	@Resource
	public ElevatorDao elevatorDao;
	@SuppressWarnings("unchecked")
	public List<Service1> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Service1.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return serviceDao.findPageByDcQuery(dc, pageSize, request);
	}
	@SuppressWarnings("unchecked")
	public List<Service1> select_Idservice(){
		DetachedCriteria dc=DetachedCriteria.forClass(Service1.class);
		return serviceDao.getListByDc(dc);
	}
	@SuppressWarnings("unchecked")
	public List<Service1> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Service1.class);
		return serviceDao.getListByDc(dc);
	}
	public Serializable insert(Service1 service){
		return serviceDao.save(service);
	}
	public Service1 findById(int idService){
		return serviceDao.get(Service1.class, idService);
	}
	public void update(Service1 service){
		serviceDao.update(service);
	}
	public void delete(Service1 service){
		serviceDao.delete(service);
	}
	//����������
		@SuppressWarnings("unchecked")
		public int getCount(int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("id_service", id_service));
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//�����������б�
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount(String search,int pageSize,HttpServletRequest request,int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("id_service", id_service));
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//δע���������
		@SuppressWarnings("unchecked")
		public int getCount_NoRegist(int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "0"));
			dc.add(Restrictions.eq("id_service", id_service));
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//δע������б�
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_NoRegist(String search,int pageSize,HttpServletRequest request,int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "0"));
			dc.add(Restrictions.eq("id_service", id_service));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//��ע���������
		@SuppressWarnings("unchecked")
		public int getCount_Registed(int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "1"));
			dc.add(Restrictions.eq("id_service", id_service));
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//��ע������б�
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Registed(String search,int pageSize,HttpServletRequest request,int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "1"));
			dc.add(Restrictions.eq("id_service", id_service));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//��ע����������
		@SuppressWarnings("unchecked")
		public int getCount_Destory(int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "2"));
			dc.add(Restrictions.eq("id_service", id_service));
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//��ע�������б�
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Destory(String search,int pageSize,HttpServletRequest request,int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "2"));
			dc.add(Restrictions.eq("id_service", id_service));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//��ͣ�õ�������
		@SuppressWarnings("unchecked")
		public int getCount_Stop(int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "3"));
			dc.add(Restrictions.eq("id_service", id_service));
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//��ͣ�õ����б�
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Stop(String search,int pageSize,HttpServletRequest request,int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "3"));
			dc.add(Restrictions.eq("id_service", id_service));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//�����������
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Normal(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_test)<365";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//������������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_Rounds_Normal(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_test)<365";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//�����ʾ����
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Warnning(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(now())-to_days(es.last_test)) "
					+ "between (365-(select alarm_rounds from system_setting limit 0,1)) and 365";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//�����ʾ�����б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_Rounds_Warnning(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_15_service) "
					+ "between (15-(select alarm_15_service from system_setting limit 0,1)) and 15";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//�����������
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Overdue(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_test)>365";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//������������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_Rounds_Overdue(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_test)>365";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//���¼���������
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Normal(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_15_service)<15";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���¼����������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Normal(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_15_service)<15";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//���¼���ʾ����
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Warnning(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_15_service) "
					+ "between (15-(select alarm_15_service from system_setting limit 0,1)) and 15";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���¼���ʾ�����б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Warnning(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_15_service) "
					+ "between (15-(select alarm_15_service from system_setting limit 0,1)) and 15";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//���¼�Ԥ������
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Overdue(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_15_service)>15 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���¼�Ԥ�������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Overdue(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_15_service)>15 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//���ȼ���������
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Normal(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_90_service)<90";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ȼ����������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Normal(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_90_service)<90";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//���ȼ���ʾ����
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Warnning(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_90_service) "
					+ "between (90-(select alarm_90_service from system_setting limit 0,1)) and 90";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ȼ���ʾ�����б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Warnning(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_90_service) "
					+ "between (90-(select alarm_90_service from system_setting limit 0,1)) and 90";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//���ȼ�Ԥ������
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Overdue(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_90_service)>90 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ȼ�Ԥ�������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Overdue(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_90_service)>90 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//�������������
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Normal(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_180_service)<180";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//��������������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Normal(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_180_service)<180";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//�������ʾ����
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Warnning(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_180_service) "
					+ "between (180-(select alarm_180_service from system_setting limit 0,1)) and 180";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//�������ʾ�����б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Warnning(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_180_service) "
					+ "between (180-(select alarm_180_service from system_setting limit 0,1)) and 180";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//�����Ԥ������
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Overdue(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_180_service)>180 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//�����Ԥ�������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Overdue(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_180_service)>180 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//���ά����������
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Normal(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_360_service)<360 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ά�����������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Normal(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_360_service)<360 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//���ά����ʾ����
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Warnning(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_360_service) "
					+ "between (360-(select alarm_360_service from system_setting limit 0,1)) and 360";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ά����ʾ�����б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Warnning(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_360_service) "
					+ "between (360-(select alarm_360_service from system_setting limit 0,1)) and 360";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//���ά��Ԥ������
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Overdue(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_360_service)>360 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ά��Ԥ�������б�
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Overdue(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_360_service)>360 ";
			sql+=" and e.register_status='1'";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//ά����λ������ͳ��
		@SuppressWarnings("unchecked")
		public Map<String, Integer> getTask(int id_service,String start,String end,int idservicer){
			String sql="select mt.name,count(mr.maint_id) from "
					+ "maint_report_id mr right join maint_type_def mt "
					+ "on mr.maint_type=mt.maint_type "
					+ "where mt.maint_type!=0 and mr.elevator_id in"
					+ "(select id_elevator from elevator where id_service="+id_service+")";
			if(start!=null&&end!=null&&!"".equals(start)&&!"".equals(end)){
				sql+=" and  (mr.maint_date between '"+start+"' and '"+end+"')";
			}
			if(idservicer!=0){
				sql+=" and (mr.user1_id="+idservicer+" or mr.user2_id="+idservicer+") ";
			}
			sql+= "group by (mt.name) ";
			List<Object[]> list=serviceDao.getListBySQL(sql);
			Map<String, Integer> map=new HashMap<String, Integer>();
			for(Object[] objs:list){
				if((objs!=null)&&objs[0]!=null&&objs[1]!=null){
					map.put(objs[0].toString(), Integer.parseInt(objs[1].toString()));
				}
				
			}
			return map;
		} 
		@Resource
		public Maint_report_idDao mriDao;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List<Maint_report_id> listByTaskType(int id_service,int maint_type,String start,String end,int idservicer,HttpServletRequest request){
			String sql1="select id_elevator from elevator where id_service="+id_service;
			List ids=mriDao.getListBySQL(sql1);
			DetachedCriteria dc=DetachedCriteria.forClass(Maint_report_id.class);
			if(maint_type!=-1){
				dc.add(Restrictions.eq("maint_type", maint_type));
			}
			if(!ids.isEmpty()){
				dc.add(Restrictions.in("elevator_id", ids));
			}
			if(!"".equals(start)&&!"".equals(end)){
				Date startTime=new Date(DateUtils.parse(start).getTime());
				Date endTime=new Date(DateUtils.parse(end).getTime());
				dc.add(Restrictions.between("maint_date", startTime, endTime));
			}
			if(idservicer!=0){
				dc.add(Restrictions.or(Restrictions.eq("user1_id", idservicer), Restrictions.eq("user2_id", idservicer)));
			}
			//������ʱ�併������
			dc.addOrder(Order.desc("maint_date"));
			List<Maint_report_id> list= mriDao.findPageByDcQuery(dc, 10, request);
			for(Maint_report_id mri:list){
				//�ж��Ƿ�����
				if(mri.getMaint_type()==1){
					//����ǰ���ά�������һ�Σ�������ά��������ά��������ά����ȫ��ά�����뱾��  ʱ�䳬��15��
					//��ѯ��һ��ά��ʱ��
					String maint_upload=DateUtils.format(mri.getMaint_upload());
					String sql="select to_days('"+maint_upload+"')-to_days(max(maint_upload)) from maint_report_id "
							+ "where maint_upload<'"+maint_upload+"' "
									+ "and elevator_id="+mri.getElevator_id()+" "
											+ "and maint_type in (1,2,3,4)";
					Object obj=mriDao.getObjectBySQL(sql);
					if(obj!=null){
						Integer days=Integer.parseInt(obj.toString());
						if(days>15){
							mri.setOverdue(1);
						}else{
							mri.setOverdue(0);
						}
					}
				}
				if(mri.getMaint_type()==2){
					//�жϼ���ά�����ڣ����һ�Σ�������ά��������ά����ȫ��ά�����뱾��ʱ�䳬��90��
					String maint_upload=DateUtils.format(mri.getMaint_upload());
					String sql="select to_days('"+maint_upload+"')-to_days(max(maint_upload)) from maint_report_id "
							+ "where maint_upload<'"+maint_upload+"' "
									+ "and elevator_id="+mri.getElevator_id()+" "
											+ "and maint_type in (2,3,4)";
					Object obj=mriDao.getObjectBySQL(sql);
					if(obj!=null){
						Integer days=Integer.parseInt(obj.toString());
						if(days>90){
							mri.setOverdue(1);
						}else{
							mri.setOverdue(0);
						}
					}
				}
				if(mri.getMaint_type()==3){
					//�жϰ���ά�����ڣ����һ�Σ�����ά����ȫ��ά�����뱾��ʱ�䳬��180��
					String maint_upload=DateUtils.format(mri.getMaint_upload());
					String sql="select to_days('"+maint_upload+"')-to_days(max(maint_upload)) from maint_report_id "
							+ "where maint_upload<'"+maint_upload+"' "
									+ "and elevator_id="+mri.getElevator_id()+" "
											+ "and maint_type in (3,4)";
					Object obj=mriDao.getObjectBySQL(sql);
					if(obj!=null){
						Integer days=Integer.parseInt(obj.toString());
						if(days>180){
							mri.setOverdue(1);
						}else{
							mri.setOverdue(0);
						}
					}
				}
				if(mri.getMaint_type()==4){
					//�ж�ȫ��ά�����ڣ��ϴ�ȫ��ά���뱾��ʱ�䳬��365��
					String maint_upload=DateUtils.format(mri.getMaint_upload());
					String sql="select to_days('"+maint_upload+"')-to_days(max(maint_upload)) from maint_report_id "
							+ "where maint_upload<'"+maint_upload+"' "
									+ "and elevator_id="+mri.getElevator_id()+" "
											+ "and maint_type=4";
					Object obj=mriDao.getObjectBySQL(sql);
					if(obj!=null){
						Integer days=Integer.parseInt(obj.toString());
						if(days>365){
							mri.setOverdue(1);
						}else{
							mri.setOverdue(0);
						}
					}
				}
			}
			return list;
		}	
		
	public boolean haveOperator(Service1 service){
		String sql="select 1 from operator where type_operator in('10','11') and id_organization="+service.getIdservice();
		Object obj=serviceDao.getObjectBySQL(sql);
		return obj==null;
	}
	
	/**
	 * ���м�����ά����λ������
	 *
	 */
	public int getCountForCity(String id_city, String id_district, String id_subdistrict){
		String sql="select count(distinct id_service) from elevator e where 1=1 ";
		if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
			sql+=" and e.id_city='"+id_city+"'";
		}
		if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
			sql+=" and e.id_district='"+id_district+"'";
		}
		if(id_subdistrict!=null&&!"".equals(id_subdistrict)&&!"00".equals(id_subdistrict)){
			sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
		}
		Object obj=serviceDao.getObjectBySQL(sql);
		if(obj!=null){
			return Integer.parseInt(obj.toString());
		}else{
			return 0;
		}
	}
  
}
