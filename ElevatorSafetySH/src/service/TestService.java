package service;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Elevator;
import vo.Maint_report_id;
import vo.Test;
import vo.User;
import dao.Maint_report_idDao;
import dao.TestDao;
import util.DateUtils;

@Service
public class TestService {
	@Resource
    public TestDao testDao;
	@SuppressWarnings("unchecked")
	public List<Test> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Test.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return testDao.findPageByDcQuery(dc, pageSize, request);
	}
	public List<Test> selectId_test(){
		DetachedCriteria dc=DetachedCriteria.forClass(Test.class);
		return testDao.getListByDc(dc);
	}
	@SuppressWarnings("unchecked")
	public List<Test> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Test.class);
		return testDao.getListByDc(dc);
	}
	public Serializable insert(Test Test){
		return testDao.save(Test);
	}
	public Test findById(int idTest){
		return testDao.get(Test.class, idTest);
	}
	public void update(Test Test){
		testDao.update(Test);
	}
	public void delete(Test Test){
		testDao.delete(Test);
	}
	//电梯总数量
		@SuppressWarnings("unchecked")
		public int getCount(int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("id_test", id_test));
			List<Long> list=testDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//电梯总数量列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount(String search,int pageSize,HttpServletRequest request,int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("id_test", id_test));
			return testDao.findPageByDcQuery(dc, pageSize, request);
		}
		//未注册电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_NoRegist(int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "0"));
			dc.add(Restrictions.eq("id_test", id_test));
			List<Long> list=testDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//未注册电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_NoRegist(String search,int pageSize,HttpServletRequest request,int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "0"));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("id_test", id_test));
			return testDao.findPageByDcQuery(dc, pageSize, request);
		}
		
		
		//已注册电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_Registed(int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "1"));
			dc.add(Restrictions.eq("id_test", id_test));
			List<Long> list=testDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//已注册电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Registed(String search,int pageSize,HttpServletRequest request,int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "1"));
			dc.add(Restrictions.eq("id_test", id_test));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return testDao.findPageByDcQuery(dc, pageSize, request);
		}
		//已注销电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_Destory(int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "2"));
			dc.add(Restrictions.eq("id_test", id_test));
			List<Long> list=testDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//已注销电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Destory(String search,int pageSize,HttpServletRequest request,int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "2"));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("id_test", id_test));
			return testDao.findPageByDcQuery(dc, pageSize, request);
		}
		//已停用电梯数量
		@SuppressWarnings("unchecked")
		public int getCount_Stop(int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.setProjection(Projections.count("id_elevator"));
			dc.add(Restrictions.eq("register_status", "3"));
			dc.add(Restrictions.eq("id_test", id_test));
			List<Long> list=testDao.getListByDc(dc);
			if(list!=null){
				return list.get(0).intValue();
			}else{
				return -1;
			}
		}
		//已停用电梯列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Stop(String search,int pageSize,HttpServletRequest request,int id_test){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.eq("register_status", "3"));
			dc.add(Restrictions.eq("id_test", id_test));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return testDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年检正常数量
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Normal(int id_test){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_rounds)-to_days(now())>("
					+ "select alarm_rounds from system_setting limit 0,1)";
			sql+=" and e.id_test="+id_test;
			List list=testDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年检正常数量列表
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount_Rounds_Normal(String search,int pageSize,HttpServletRequest request,int id_test){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_rounds)-to_days(now())>("
					+ "select alarm_rounds from system_setting limit 0,1)";
			sql+=" and e.id_test="+id_test;
			List<Long> list=testDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return testDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年检提示数量
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Warnning(int id_test){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_rounds)-to_days(now())) "
					+ "between 0 and (select alarm_rounds from system_setting limit 0,1)";
			sql+=" and e.id_test="+id_test;
			List list=testDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年检提示数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_Rounds_Warnning(String search,int pageSize,HttpServletRequest request,int id_test){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where (to_days(es.last_rounds)-to_days(now())) "
					+ "between 0 and (select alarm_rounds from system_setting limit 0,1)";
			sql+=" and e.id_test="+id_test;
			List<Long> list=testDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return testDao.findPageByDcQuery(dc, pageSize, request);
		}
		//年检逾期数量
		@SuppressWarnings("rawtypes")
		public int getCount_Rounds_Overdue(int id_test){
			String sql="select count(e.id_elevator) from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_rounds)-to_days(now())<0";
			sql+=" and e.id_test="+id_test;
			List list=testDao.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				return Integer.parseInt(list.get(0).toString());
			}else{
				return -1;
			}
		}
		//年检逾期数量列表
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCount_Rounds_Overdue(String search,int pageSize,HttpServletRequest request,int id_test){
			String sql="select e.id_elevator from elevator e left join "
					+ "elevator_state es on e.id_elevator=es.id_elevator "
					+ "where to_days(es.last_rounds)-to_days(now())<0";
			sql+=" and e.id_test="+id_test;
			List<Long> list=testDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return testDao.findPageByDcQuery(dc, pageSize, request);
		}
		//本检测单位巡检次数
		public int getCountMaintType0(int id_test,String start,String end){
			String sql="select count(*) from maint_report_id where maint_type=0 and elevator_id in(select id_elevator from elevator where id_test="+id_test+")";
			if(start!=null&&end!=null){
				sql+=" and  (maint_date between '"+start+"' and '"+end+"')";
			}
			Object obj=testDao.getObjectBySQL(sql);
			if(obj!=null){
				return Integer.parseInt(obj.toString());
			}else{
				return 0;
			}
		}
		//本检测单位共配合维保次数
		public int getCountMaint(int id_test,String start,String end){
			String sql="select count(*) from maint_report_id where elevator_id in(select id_elevator from elevator where id_test="+id_test+")";
			if(start!=null&&end!=null){
				sql+=" and  (maint_date between '"+start+"' and '"+end+"')";
			}
			Object obj=testDao.getObjectBySQL(sql);
			if(obj!=null){
				return Integer.parseInt(obj.toString());
			}else{
				return 0;
			}
		}
		
		@Resource
		public Maint_report_idDao mriDao;
		//获得维保记录列表，传入检测单位ID
		@SuppressWarnings("unchecked")
		public List<Maint_report_id> listByType(int id_test,int maint_type,String start,String end,HttpServletRequest request){
			String sql="select id_elevator from elevator where id_test="+id_test;
			List ids=mriDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Maint_report_id.class);
			if(maint_type!=-1){
				dc.add(Restrictions.eq("maint_type", maint_type));
			}
			if(!ids.isEmpty()){
				dc.add(Restrictions.in("elevator_id", ids));
			}
			if(start!=null&&end!=null&&!"".equals(start)&&!"".equals(end)){
				Date startTime=new Date(DateUtils.parse(start).getTime());
				Date endTime=new Date(DateUtils.parse(end).getTime());
				dc.add(Restrictions.between("maint_date", startTime, endTime));
			}
			
			return mriDao.findPageByDcQuery(dc, 10, request);
		}
}
