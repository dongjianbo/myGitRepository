package service;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import vo.User;
import dao.ElevatorDao;
import dao.Maint_report_idDao;
import dao.UserDao;
import util.DateUtils;

@Service
public class UserService {
	@Resource
    public UserDao userDao;
	@Resource
	public ElevatorDao elevatorDao;
	@SuppressWarnings("unchecked")
	public List<User> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return userDao.findPageByDcQuery(dc, pageSize, request);
	}
	@SuppressWarnings("unchecked")
	public List<User> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		return userDao.getListByDc(dc);
	}
	public Serializable insert(User user){
		return userDao.save(user);
	}
	public User findById(int iduser){
		return userDao.get(User.class, iduser);
	}
	public void update(User user){
		userDao.update(user);
	}
	public void delete(User user){
		userDao.delete(user);
	}
	//电梯总数量
		@SuppressWarnings("unchecked")
		public int getCount(int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//电梯总数量列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//未注册电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_NoRegist(int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "0"));
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//未注册电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_NoRegist(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "0"));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		
		
		//已注册电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_Registed(int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "1"));
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//已注册电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Registed(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "1"));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//已注销电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_Destory(int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "2"));
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//已注销电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Destory(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "2"));
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//已停用电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_Stop(int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "3"));
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			List<Long> list=elevatorDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//已停用电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Stop(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "3"));
			if(ids!=null){
				dc.add(Restrictions.in("id_user", ids));
			}else{
				dc.add(Restrictions.eq("id_user", id_user));
			}
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Normal(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_test)<365";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年检正常数量列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Rounds_Normal(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_test)<365";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Warnning(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_test)+365 between to_days(now()) and "
					+ "to_days(now())+(select alarm_rounds from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_Rounds_Warnning(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_test)+365 between to_days(now()) and "
					+ "to_days(now())+(select alarm_rounds from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年检逾期数量
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Overdue(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_test)>=365";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年检逾期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_Rounds_Overdue(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(now())-to_days(es.last_test)>=365";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半月检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Normal(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半月检正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Normal(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半月检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Warnning(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15 between to_days(now()) and "
					+ "to_days(now())+(select alarm_15_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半月检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Warnning(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15 between to_days(now()) and "
					+ "to_days(now())+(select alarm_15_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半月检预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Overdue(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15<to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半月检预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Overdue(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15<to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//季度检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Normal(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//季度检正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Normal(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//季度检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Warnning(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90 between to_days(now()) and to_days(now())+(select alarm_90_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//季度检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Warnning(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90 between to_days(now()) and to_days(now())+(select alarm_90_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//季度检预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Overdue(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90<to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//季度检预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Overdue(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90<to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半年检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Normal(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半年检正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Normal(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半年检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Warnning(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180 between to_days(now()) and to_days(now())+(select alarm_180_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半年检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Warnning(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180 between to_days(now()) and to_days(now())+(select alarm_180_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半年检预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Overdue(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180<to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半年检预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Overdue(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180<to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年度维保正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Normal(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)+365>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年度维保正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Normal(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)+365>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年度维保提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Warnning(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)+365 between to_days(now()) and to_days(now())+(select alarm_360_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年度维保提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Warnning(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)+365 between to_days(now()) and to_days(now())+(select alarm_360_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年度维保预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Overdue(int id_user,ArrayList<Integer> ids){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)+365<to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List list=elevatorDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年度维保预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Overdue(String search,int pageSize,HttpServletRequest request,int id_user,ArrayList<Integer> ids){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_360_service)+365<to_days(now())";
			sql+=" and e.register_status='1'";
			if(ids!=null&&ids.size()>0){
				String idstr="(";
				for(Integer id:ids){
					idstr+=id+",";
				}
				idstr=idstr.substring(0, idstr.length()-1);
				idstr+=")";
				System.out.println(idstr);
				sql+=" and e.id_user in "+idstr;
			}else{
				sql+=" and e.id_user="+id_user;
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
		//本使用单位巡检次数
		public int getCountMaintType0(int id_user,String start,String end){
			String sql="select count(*) from maint_report_id where maint_type=0 and elevator_id in(select id_elevator from elevator where id_user="+id_user+")";
			if(start!=null&&end!=null){
				sql+=" and  (maint_date between '"+start+"' and '"+end+"')";
			}
			Object obj=userDao.getObjectBySQL(sql);
			if(obj!=null){
				return Integer.parseInt(obj.toString());
			}else{
				return 0;
			}
		}
		//本使用单位共配合维保次数
		public int getCountMaint(int id_user,String start,String end){
			String sql="select count(*) from maint_report_id where maint_type!=0 and elevator_id in(select id_elevator from elevator where id_user="+id_user+")";
			if(start!=null&&end!=null){
				sql+=" and  (maint_date between '"+start+"' and '"+end+"')";
			}
			Object obj=userDao.getObjectBySQL(sql);
			if(obj!=null){
				return Integer.parseInt(obj.toString());
			}else{
				return 0;
			}
		}
		
		@Resource
		public Maint_report_idDao mriDao;
		//获得维保记录列表，传入使用单位ID
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List<Maint_report_id> listByType(int id_user,int maint_type,String start,String end,HttpServletRequest request){
			String sql="select id_elevator from elevator where id_user="+id_user;
			List ids=mriDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Maint_report_id.class);
			if(maint_type!=-1){
				dc.add(Restrictions.eq("maint_type", maint_type));
			}else{
				dc.add(Restrictions.ne("maint_type", 0));
			}
			if(!ids.isEmpty()){
				dc.add(Restrictions.in("elevator_id", ids));
			}
			if(start!=null&&end!=null&&!"".equals(start)&&!"".equals(end)){
				Date startTime=new Date(DateUtils.parse(start).getTime());
				Date endTime=new Date(DateUtils.parse(end).getTime());
				dc.add(Restrictions.between("maint_date", startTime, endTime));
			}
			//按照工作时间降序排列
			dc.addOrder(Order.desc("maint_date"));
			return mriDao.findPageByDcQuery(dc, 10, request);
		}
		public boolean haveOperator(User user){
			String sql="select 1 from operator where type_operator in('20','21') and id_organization="+user.getIduser();
			Object obj=userDao.getObjectBySQL(sql);
			return obj==null;
		}
		/**
		 * 按市级查找使用单位的数量
		 *
		 */
		public int getCountForCity(String id_city, String id_district, String id_subdistrict){
			String sql="select count(distinct id_user) from elevator e where 1=1 ";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)&&!"00".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			Object obj=userDao.getObjectBySQL(sql);
			if(obj!=null){
				return Integer.parseInt(obj.toString());
			}else{
				return 0;
			}
		}
}
