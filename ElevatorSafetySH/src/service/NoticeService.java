package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
			//判断是否要更新
			if(n.getMaint_type()==1){
				//半月维保
				if(max_4>last_service){
					n.setThis_service(DateUtils.format1(new Date(max_4)));
					nDao.update(n);
				}
			}
			if(n.getMaint_type()==2){
				//季度维保
				if(max_3>last_service){
					n.setThis_service(DateUtils.format1(new Date(max_3)));
					nDao.update(n);
				}
			}
			if(n.getMaint_type()==3){
				if(max_2>last_service){
					n.setThis_service(DateUtils.format1(new Date(max_2)));
					nDao.update(n);
				}
			}
			if(n.getMaint_type()==4){
				if(max_1>last_service){
					n.setThis_service(DateUtils.format1(new Date(max_1)));
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
		//巡检不查询
		List<Elevator_state> eslist= esDao.getListByDc(dc);
		for(Elevator_state es:eslist){
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
			long today=System.currentTimeMillis();
			//获得当前电梯的维保单位
			int id_service=eDao.get(Elevator.class, es.getIdelevator()).getId_service();
			//获得当前电梯的使用单位
			int id_user=eDao.get(Elevator.class, es.getIdelevator()).getId_user();
			//判断该电梯半月维保是否逾期
			if(today-max_4>(15*24*60*60*1000)){
				Notice notice=new Notice();
				notice.setMaint_type(1);
				notice.setId_elevator(es.getIdelevator());
				notice.setId_service(id_service);
				notice.setId_user(id_user);
				notice.setLast_service(es.getLast_15_service());
				notice.setDate_notice(DateUtils.format1(new Date()));
				try {
					saveOrUpdateNotice(notice);
					System.out.println(es.getIdelevator()+"号电梯半月维保已逾期！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//判断该电梯季度维保是否逾期
			if(today-max_3>(90*24*60*60*1000)){
				Notice notice=new Notice();
				notice.setMaint_type(2);
				notice.setId_elevator(es.getIdelevator());
				notice.setId_service(id_service);
				notice.setId_user(id_user);
				notice.setLast_service(es.getLast_90_service());
				notice.setDate_notice(DateUtils.format1(new Date()));
				try {
					saveOrUpdateNotice(notice);
					System.out.println(es.getIdelevator()+"号电梯季度维保已逾期！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//判断该电梯半年维保是否逾期
			if(today-max_2>(180*24*60*60*1000)){
				Notice notice=new Notice();
				notice.setMaint_type(3);
				notice.setId_elevator(es.getIdelevator());
				notice.setId_service(id_service);
				notice.setId_user(id_user);
				notice.setLast_service(es.getLast_180_service());
				notice.setDate_notice(DateUtils.format1(new Date()));
				try {
					saveOrUpdateNotice(notice);
					System.out.println(es.getIdelevator()+"号电梯半年维保已逾期！");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//判断该电梯半年维保是否逾期
			if(today-max_1>(360*24*60*60*1000)){
				Notice notice=new Notice();
				notice.setMaint_type(4);
				notice.setId_elevator(es.getIdelevator());
				notice.setId_service(id_service);
				notice.setId_user(id_user);
				notice.setLast_service(es.getLast_360_service());
				notice.setDate_notice(DateUtils.format1(new Date()));
				try {
					saveOrUpdateNotice(notice);
					System.out.println(es.getIdelevator()+"号电梯全年维保已逾期！");
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
