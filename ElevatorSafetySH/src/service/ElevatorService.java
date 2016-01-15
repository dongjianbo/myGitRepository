package service;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import dao.ElevatorDao;
import vo.Elevator;



@Service
public class ElevatorService {
	@Resource
	public ElevatorDao elevatorDao;
	public Serializable insert(Elevator elevator){
		return elevatorDao.save(elevator);
	}
	@SuppressWarnings("unchecked")
	public int getCount(){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.setProjection(Projections.count("id_elevator"));
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//δע���������
	@SuppressWarnings("unchecked")
	public int getCount_NoRegist(){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.setProjection(Projections.count("id_elevator"));
		dc.add(Restrictions.eq("register_status", "0"));
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//��ע���������
	@SuppressWarnings("unchecked")
	public int getCount_Registed(){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.setProjection(Projections.count("id_elevator"));
		dc.add(Restrictions.eq("register_status", "1"));
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//��ע����������
	@SuppressWarnings("unchecked")
	public int getCount_Destory(){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.setProjection(Projections.count("id_elevator"));
		dc.add(Restrictions.eq("register_status", "2"));
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//��ͣ�õ�������
	@SuppressWarnings("unchecked")
	public int getCount_Stop(){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.setProjection(Projections.count("id_elevator"));
		dc.add(Restrictions.eq("register_status", "3"));
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//�����������
	@SuppressWarnings("rawtypes")
	public int getCount_Rounds_Normal(){
		String sql="select count(e.id_elevator) from elevator e left join "
				+ "elevator_state es on e.id_elevator=es.id_elevator "
				+ "where to_days(es.last_rounds)-to_days(now())>("
				+ "select alarm_rounds from system_setting limit 0,1)";
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//�����ʾ����
	@SuppressWarnings("rawtypes")
	public int getCount_Rounds_Warnning(){
		String sql="select count(e.id_elevator) from elevator e left join "
				+ "elevator_state es on e.id_elevator=es.id_elevator "
				+ "where (to_days(es.last_rounds)-to_days(now())) "
				+ "between 0 and (select alarm_rounds from system_setting limit 0,1)";
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//�����������
	@SuppressWarnings("rawtypes")
	public int getCount_Rounds_Overdue(){
		String sql="select count(e.id_elevator) from elevator e left join "
				+ "elevator_state es on e.id_elevator=es.id_elevator "
				+ "where to_days(es.last_rounds)-to_days(now())<0";
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���¼���������
	@SuppressWarnings("rawtypes")
	public int getCount_15service_Normal(){
		String sql="select count(e.id_elevator) from elevator e left join "
				+ "elevator_state es on e.id_elevator=es.id_elevator "
				+ "where to_days(es.last_15_service)-to_days(now())>("
				+ "select alarm_15_service from system_setting limit 0,1)";
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���¼���ʾ����
	@SuppressWarnings("rawtypes")
	public int getCount_15service_Warnning(){
		String sql="select count(e.id_elevator) from elevator e left join "
				+ "elevator_state es on e.id_elevator=es.id_elevator "
				+ "where (to_days(es.last_15_service)-to_days(now())) "
				+ "between 0 and (select alarm_15_service from system_setting limit 0,1)";
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���¼�Ԥ������
	@SuppressWarnings("rawtypes")
	public int getCount_15service_Overdue(){
		String sql="select count(e.id_elevator) from elevator e left join "
				+ "elevator_state es on e.id_elevator=es.id_elevator "
				+ "where to_days(es.last_15_service)-to_days(now())<0 ";
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���ȼ���������
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Normal(){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_90_service)-to_days(now())>("
					+ "select alarm_90_service from system_setting limit 0,1)";
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ȼ���ʾ����
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Warnning(){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_90_service)-to_days(now())) "
					+ "between 0 and (select alarm_90_service from system_setting limit 0,1)";
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ȼ�Ԥ������
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Overdue(){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_90_service)-to_days(now())<0 ";
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//�������������
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Normal(){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_180_service)-to_days(now())>("
					+ "select alarm_180_service from system_setting limit 0,1)";
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//�������ʾ����
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Warnning(){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_180_service)-to_days(now())) "
					+ "between 0 and (select alarm_180_service from system_setting limit 0,1)";
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//�����Ԥ������
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Overdue(){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_180_service)-to_days(now())<0 ";
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ά����������
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Normal(){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)-to_days(now())>("
					+ "select alarm_360_service from system_setting limit 0,1)";
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ά����ʾ����
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Warnning(){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_360_service)-to_days(now())) "
					+ "between 0 and (select alarm_360_service from system_setting limit 0,1)";
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//���ά��Ԥ������
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Overdue(){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)-to_days(now())<0 ";
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
}
