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
	//未注册电梯数量
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
	//已注册电梯数量
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
	//已注销电梯数量
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
	//已停用电梯数量
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
	//年检正常数量
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
	//年检提示数量
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
	//年检逾期数量
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
	//半月检正常数量
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
	//半月检提示数量
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
	//半月检预期数量
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
	//季度检正常数量
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
		//季度检提示数量
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
		//季度检预期数量
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
		//半年检正常数量
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
		//半年检提示数量
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
		//半年检预期数量
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
		//年度维保正常数量
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
		//年度维保提示数量
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
		//年度维保预期数量
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
