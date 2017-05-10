package service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.CityManagerDao;
import vo.CityManager;
import vo.Elevator;

@Service
public class CityManagerService {
	@Resource
	public CityManagerDao cityManagerDao;
	//电梯总数量
		@SuppressWarnings("unchecked")
		public int getCount(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
				
			}
			List<Long> list=cityManagerDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//电梯总数量列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			if(!"".equals(search)){
				dc.add(Restrictions.like("desc", search,MatchMode.ANYWHERE));
			}
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//电梯总数量列表
			@SuppressWarnings("unchecked")
			public List<Elevator> listCount1(HttpServletRequest request){
				DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
				return cityManagerDao.getListByDc(dc);
			}
		//未注册电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_NoRegist(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "0"));
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
			}
			List<Long> list=cityManagerDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//未注册电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_NoRegist(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "0"));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		
		
		//已注册电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_Registed(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "1"));
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
			}
			List<Long> list=cityManagerDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//已注册电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Registed(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "1"));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//已注销电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_Destory(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "2"));
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
			}
			List<Long> list=cityManagerDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//已注销电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Destory(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "2"));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//已停用电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_Stop(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "3"));
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
			}
			List<Long> list=cityManagerDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//已停用电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Stop(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "3"));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				dc.add(Restrictions.eq("id_city", id_city));
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				dc.add(Restrictions.eq("id_district", id_district));
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				dc.add(Restrictions.eq("id_subdistrict", id_subdistrict));
			}
			if(id_service!=0){
				dc.add(Restrictions.eq("id_service", id_service));
			}
			if(id_user!=0){
				dc.add(Restrictions.eq("id_user", id_user));
			}else{
				//查询属于城管范围的使用单位
				List<Integer> ids=cityManagerDao.getIds();
				dc.add(Restrictions.in("id_user", ids));
				
			}
			if(id_test!=0){
				dc.add(Restrictions.eq("id_test", id_test));
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
				}
				if(keyType==2){
					dc.setFetchMode("model", FetchMode.JOIN);
					dc.createAlias("model", "model");
					dc.add(Restrictions.like("model.modelname", desc,MatchMode.ANYWHERE));
				}
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Normal(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(now())-to_days(es.last_test)<365";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年检正常数量列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Rounds_Normal(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(now())-to_days(es.last_test)<365";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Warnning(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(es.last_test)+365 between to_days(now()) and "
					+ "to_days(now())+(select alarm_rounds from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_Rounds_Warnning(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(es.last_test)+365 between to_days(now()) and "
					+ "to_days(now())+(select alarm_rounds from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年检逾期数量
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Overdue(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(now())-to_days(es.last_test)>=365";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年检逾期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_Rounds_Overdue(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(now())-to_days(es.last_test)>=365";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半月检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Normal(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半月检正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Normal(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半月检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Warnning(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15 between to_days(now()) and "
					+ "to_days(now())+(select alarm_15_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半月检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Warnning(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15 between to_days(now()) and "
					+ "to_days(now())+(select alarm_15_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半月检预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_15service_Overdue(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15<to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半月检预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_15service_Overdue(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_15_service,es.last_90_service,es.last_180_service,es.last_360_service))+15<to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//季度检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Normal(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//季度检正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Normal(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//季度检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Warnning(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90 between to_days(now()) and to_days(now())+(select alarm_90_service from system_setting limit 0,1)";
					
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//季度检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Warnning(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90 between to_days(now()) and to_days(now())+(select alarm_90_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//季度检逾期数量
		@SuppressWarnings("rawtypes")
		public int getCount_90service_Overdue(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90<to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//季度检预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_90service_Overdue(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_90_service,es.last_180_service,es.last_360_service))+90<to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半年检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Normal(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半年检正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Normal(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半年检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Warnning(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180 between to_days(now()) and to_days(now())+(select alarm_180_service from system_setting limit 0,1)";
					
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半年检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Warnning(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180 between to_days(now()) and to_days(now())+(select alarm_180_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//半年检预期数量
		@SuppressWarnings("rawtypes")
		public int getCount_180service_Overdue(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180<to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//半年检预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_180service_Overdue(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(greatest(es.last_180_service,es.last_360_service))+180<to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年度维保正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Normal(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(es.last_360_service)+365>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年度维保正常数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Normal(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(es.last_360_service)+365>=to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年度维保提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Warnning(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(es.last_360_service)+365 between to_days(now()) and to_days(now())+(select alarm_360_service from system_setting limit 0,1)";
					
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年度维保提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Warnning(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(es.last_360_service)+365 between to_days(now()) and to_days(now())+(select alarm_360_service from system_setting limit 0,1)";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年度维保逾期数量
		@SuppressWarnings("rawtypes")
		public int getCount_360service_Overdue(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(es.last_360_service)+365<to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List list=cityManagerDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年度维保预期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_360service_Overdue(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator  left join modellist m on e.id_elevator_model=m.id_model "
					+ "where to_days(es.last_360_service)+365<to_days(now())";
			sql+=" and e.register_status='1'";
			if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
				sql+=" and e.id_city='"+id_city+"'";
			}
			if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
				sql+=" and e.id_district='"+id_district+"'";
			}
			if(id_subdistrict!=null&&!"".equals(id_subdistrict)){
				sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
			}
			if(id_service!=0){
				sql+=" and e.id_service="+id_service;
			}
			if(id_user!=0){
				sql+=" and e.id_user="+id_user;
			}else{
				//查询属于城管范围的使用单位
				sql+=" and e.id_user in(select userid from citymanager)";
				
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			List<Long> list=cityManagerDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return cityManagerDao.findPageByDcQuery(dc, pageSize, request);
		}
		@SuppressWarnings("unchecked")
		public List<CityManager> list(){
			DetachedCriteria dc=DetachedCriteria.forClass(CityManager.class);
			return cityManagerDao.getListByDc(dc);
		}
}
