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
	//电梯总数量
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
		//电梯总数量列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount(String search,int pageSize,HttpServletRequest request,int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("id_service", id_service));
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//未注册电梯数量
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
		//未注册电梯列表
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
		//已注册电梯数量
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
		//已注册电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Registed(String search,int pageSize,HttpServletRequest request,int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "1"));
			dc.add(Restrictions.eq("id_service", id_service));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//已注销电梯数量
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
		//已注销电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Destory(String search,int pageSize,HttpServletRequest request,int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "2"));
			dc.add(Restrictions.eq("id_service", id_service));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//已停用电梯数量
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
		//已停用电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Stop(String search,int pageSize,HttpServletRequest request,int id_service){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "3"));
			dc.add(Restrictions.eq("id_service", id_service));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		
		//半月检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Normal(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_15_service)-to_days(now())>("
					+ "select alarm_15_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半月检逾期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Normal(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_15_service)-to_days(now())>("
					+ "select alarm_15_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半月检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Warnning(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_15_service)-to_days(now())) "
					+ "between 0 and (select alarm_15_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半月检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Warnning(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_15_service)-to_days(now())) "
					+ "between 0 and (select alarm_15_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半月检预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Overdue(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_15_service)-to_days(now())<0 ";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半月检预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Overdue(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_15_service)-to_days(now())<0 ";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//季度检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Normal(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_90_service)-to_days(now())>("
					+ "select alarm_90_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//季度检正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Normal(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_90_service)-to_days(now())>("
					+ "select alarm_90_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//季度检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Warnning(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_90_service)-to_days(now())) "
					+ "between 0 and (select alarm_90_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//季度检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Warnning(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_90_service)-to_days(now())) "
					+ "between 0 and (select alarm_90_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//季度检预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Overdue(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_90_service)-to_days(now())<0 ";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//季度检预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Overdue(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_90_service)-to_days(now())<0 ";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半年检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Normal(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_180_service)-to_days(now())>("
					+ "select alarm_180_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半年检正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Normal(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_180_service)-to_days(now())>("
					+ "select alarm_180_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半年检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Warnning(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_180_service)-to_days(now())) "
					+ "between 0 and (select alarm_180_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半年检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Warnning(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_180_service)-to_days(now())) "
					+ "between 0 and (select alarm_180_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半年检预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Overdue(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_180_service)-to_days(now())<0 ";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半年检预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Overdue(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_180_service)-to_days(now())<0 ";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年度维保正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Normal(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)-to_days(now())>("
					+ "select alarm_360_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年度维保正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Normal(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)-to_days(now())>("
					+ "select alarm_360_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年度维保提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Warnning(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_360_service)-to_days(now())) "
					+ "between 0 and (select alarm_360_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年度维保提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Warnning(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_360_service)-to_days(now())) "
					+ "between 0 and (select alarm_360_service from system_setting limit 0,1)";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年度维保预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Overdue(int id_service){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)-to_days(now())<0 ";
			sql+=" and e.id_service="+id_service;
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年度维保预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Overdue(String search,int pageSize,HttpServletRequest request,int id_service){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)-to_days(now())<0 ";
			sql+=" and e.id_service="+id_service;
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//维保单位任务量统计
		@SuppressWarnings("unchecked")
		public Map<String, Integer> getTask(int id_service,String start,String end){
			String sql="select mt.name,count(mr.maint_id) from "
					+ "maint_report_id mr right join maint_type_def mt "
					+ "on mr.maint_type=mt.maint_type "
					+ "where mt.maint_type!=0 and mr.elevator_id in"
					+ "(select id_elevator from elevator where id_service="+id_service+")";
			if(start!=null&&end!=null){
				sql+=" and  (mr.maint_date between '"+start+"' and '"+end+"')";
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
		@SuppressWarnings("unchecked")
		public List<Maint_report_id> listByTaskType(int id_service,int maint_type,String start,String end,HttpServletRequest request){
			String sql="select id_elevator from elevator where id_service="+id_service;
			List ids=mriDao.getListBySQL(sql);
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
			
			return mriDao.findPageByDcQuery(dc, 10, request);
		}	
  
}
