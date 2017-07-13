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
	 * 维保逾期通知单业务
	 */
	@SuppressWarnings("unchecked")
	public void createTask(){
		Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"SPRING定时器任务已启动...");
		/*
			更新记录notice中this_service为空的记录
	
			  半月维保：elevator_state 中 15，90，180，360 四个时间都小
			            于notice.last_service,不更新
			            否则，notice.this_service = 大于last_service中最小
			            的那个
			  季度维保：elevator_state 中 90，180，360  三个
			  半年维保：elevator_state 中 180，360 二个
			  年度维保：elevator_state 中 360 一个
		*/
		//查询出所有this_service为空的记录
		DetachedCriteria dc_notice=DetachedCriteria.forClass(Notice.class);
		dc_notice.add(Restrictions.isNull("this_service"));
		List<Notice> nList=nDao.getListByDc(dc_notice);
		for(Notice n:nList){
			//查询出四个时间
			Elevator_state es=esDao.get(Elevator_state.class, n.getId_elevator());
			long last_15_service=DateUtils.parse(es.getLast_15_service()).getTime();
			long last_90_service=DateUtils.parse(es.getLast_90_service()).getTime();
			long last_180_service=DateUtils.parse(es.getLast_180_service()).getTime();
			long last_360_service=DateUtils.parse(es.getLast_360_service()).getTime();
			long last_service=DateUtils.parse(n.getLast_service()).getTime();
			//判断是否要更新
			if(n.getMaint_type()==1){
				//如果是半月维保，取所有大于last_service中最小的那个
				List<Long> services=new ArrayList<Long>();
				//凡是大于last_service的添加到services中去。
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
					//找出集合中的最小值
					long min=Collections.min(services);
					n.setThis_service(DateUtils.format1(new Date(min)));
					nDao.update(n);
				}
			}
			if(n.getMaint_type()==2){
				//如果是季度维保，取90,180,360中大于last_service中最小的那个
				List<Long> services=new ArrayList<Long>();
				//凡是大于last_service的添加到services中去。
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
					//找出集合中的最小值
					long min=Collections.min(services);
					n.setThis_service(DateUtils.format1(new Date(min)));
					nDao.update(n);
				}
			}
			if(n.getMaint_type()==3){
				//如果是半年维保，取180,360大于last_service中最小的那个
				List<Long> services=new ArrayList<Long>();
				//凡是大于last_service的添加到services中去。
				if(last_180_service>last_service){
					services.add(last_180_service);
				}
				if(last_360_service>last_service){
					services.add(last_360_service);
				}
				if(services.size()>0){
					//找出集合中的最小值
					long min=Collections.min(services);
					n.setThis_service(DateUtils.format1(new Date(min)));
					nDao.update(n);
				}
			}
			if(n.getMaint_type()==4){
				//如果是年度维保，取360大于last_service
				if(last_360_service>last_service){
					n.setThis_service(DateUtils.format1(new Date(last_360_service)));
					nDao.update(n);
				}
			}
		}
		/*
			插入记录
			目前逾期电梯中，以id_elevator，maint_type，last_service为
			联合关键字，notice表中没有相应记录的插入记录
			date_notice 为系统日期
			this_service 为空   
			根据maint_type的不同，last_service取值不同
			半月维保 last_service=last_15_service
			季度维保 last_service=last_90_service
			半年维保 last_service=last_180_service
			全年维保 last_service=last_360_service
		*/
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator_state.class);
		//只查询已注册的电梯
		List<Integer> ids=eDao.getListBySQL("select id_elevator from elevator where register_status=1");
		dc.add(Restrictions.in("idelevator", ids));
		//巡检不查询
		List<Elevator_state> eslist= esDao.getListByDc(dc);
		for(Elevator_state es:eslist){
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"现在检查"+es.getIdelevator()+"号电梯的维保记录。");
			//取出四个日期
			long last_15_service=DateUtils.parse(es.getLast_15_service()).getTime();
			long last_90_service=DateUtils.parse(es.getLast_90_service()).getTime();
			long last_180_service=DateUtils.parse(es.getLast_180_service()).getTime();
			long last_360_service=DateUtils.parse(es.getLast_360_service()).getTime();
			List<Long> services=new ArrayList<Long>();
			services.add(last_15_service);
			services.add(last_90_service);
			services.add(last_180_service);
			services.add(last_360_service);
			//找出四个中的最大值
			long max_4=Collections.max(services);
			//找出后三个中的最大值
			services.remove(0);
			long max_3=Collections.max(services);
			//找出后两个中的最大值
			services.remove(0);
			long max_2=Collections.max(services);
			//找出最后一个值
			long max_1=last_360_service;
			//获得当前日期
			long today=DateUtils.parse(DateUtils.format1(new Date())).getTime();
			//获得当前电梯的维保单位
			int id_service=eDao.get(Elevator.class, es.getIdelevator()).getId_service();
			//获得当前电梯的使用单位
			int id_user=eDao.get(Elevator.class, es.getIdelevator()).getId_user();
			
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"today is:"+today);
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"最后一次半月维保时间是："+max_4+",至今已过："+(today-max_4));
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"最后一次季度维保时间是："+max_3+",至今已过："+(today-max_3));
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"最后一次半年维保时间是："+max_2+",至今已过："+(today-max_2));
			Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,"最后一次年度维保时间是："+max_1+",至今已过："+(today-max_1));
			//判断该电梯半月维保是否逾期
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
					Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,es.getIdelevator()+"号电梯半月维保已逾期！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//判断该电梯季度维保是否逾期
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
					Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,es.getIdelevator()+"号电梯季度维保已逾期！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//判断该电梯半年维保是否逾期
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
					Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,es.getIdelevator()+"号电梯半年维保已逾期！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//判断该电梯半年维保是否逾期
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
					Logger.getLogger(this.getClass().getName()).log(Priority.DEBUG,es.getIdelevator()+"号电梯全年维保已逾期！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	@SuppressWarnings("unchecked")
	public void saveOrUpdateNotice(Notice notice)throws Exception{
		System.out.println("发布逾期通知。。。"+notice.getId_elevator()+"=="+notice.getMaint_type());
		//判断该记录是否存在
		DetachedCriteria dc=DetachedCriteria.forClass(Notice.class);
		dc.add(Restrictions.eq("id_elevator", notice.getId_elevator()));
		dc.add(Restrictions.eq("maint_type", notice.getMaint_type()));
		dc.add(Restrictions.eq("last_service", notice.getLast_service()));
		List<Notice> nlist=nDao.getListByDc(dc);
		
		if(nlist==null||nlist.isEmpty()){
			System.out.println("检查："+notice.getId_elevator()+",已存在逾期记录："+nlist.size());
			//插入该记录
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
			//只查询已处理的
			dc.add(Restrictions.isNotNull("this_service"));
		}
		if(noticeType==2){
			//只查询未处理的
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
