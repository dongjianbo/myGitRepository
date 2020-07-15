package service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.EDeviceDao;
import dao.ElevatorDao;
import dao.Elevator_tag_init_taskDao;
import vo.Approve_ack;
import vo.E_Device;
import vo.E_data_alarm;
import vo.E_data_history;
import vo.Elevator;



@Service
public class ElevatorService {
	@Resource
	public ElevatorDao elevatorDao;
	@Resource
	public Elevator_tag_init_taskDao etitDao;
	@Resource
	public EDeviceDao edeviceDao;
	public Serializable insert(Elevator elevator){
		return elevatorDao.save(elevator);
	}

	//�޸ĵ���
	public void update(Elevator elevator){
		elevatorDao.update(elevator);
		//������ݼ���޸ģ�ͬʱ����elevator-tag-init-task �� elevator_id��Ӧ�ĵ��ݼ��title
		String sql="update elevator_tag_init_task set title='"+elevator.getDesc()+"' where elevator_id="+elevator.getId_elevator();
		etitDao.updateSQL(sql);
	}
	//ͨ������id��ѯ����
	public Elevator getEById(int id_elevator){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.eq("id_elevator", id_elevator));
		return (Elevator)elevatorDao.getListByDc(dc).get(0);
	}
	//���ط��������ĵ��ݱ��(����ʹ�õ�λ)
	@SuppressWarnings("unchecked")
	public List<Integer> getElevatorIds(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,String desc){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
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
		}
		if(id_test!=0){
			dc.add(Restrictions.eq("id_test", id_test));
		}
		if(desc!=null&&!"".equals(desc.trim())){
			dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
		}
		List<Elevator> elist=elevatorDao.getListByDc(dc);
		List<Integer> ids=new ArrayList<Integer>();
		for(Elevator e:elist){
			ids.add(e.getId_elevator());
		}
		return ids;
			
	}
	//���ط��������ĵ��ݱ��(���ŵ�λ�������ʹ�õ�λ)
	@SuppressWarnings("unchecked")
	public List<Integer> getElevatorIds(String id_city,String id_district,String id_subdistrict,int id_service,ArrayList<Integer> id_users,int id_test,String desc){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
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
		if(id_users!=null&&id_users.size()>0){
			dc.add(Restrictions.in("id_user", id_users));
		}
		if(id_test!=0){
			dc.add(Restrictions.eq("id_test", id_test));
		}
		if(desc!=null&&!"".equals(desc.trim())){
			dc.add(Restrictions.like("desc", desc,MatchMode.ANYWHERE));
		}
		List<Elevator> elist=elevatorDao.getListByDc(dc);
		List<Integer> ids=new ArrayList<Integer>();
		for(Elevator e:elist){
			ids.add(e.getId_elevator());
		}
		return ids;
			
	}
	//����������
	@SuppressWarnings("unchecked")
	public int getCount(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc,String gis_type){
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
		}
		if(id_test!=0){
			dc.add(Restrictions.eq("id_test", id_test));
		}
		if(gis_type!=null&&!"".equals(gis_type)){
			if("A".equals(gis_type)){
				dc.add(Restrictions.in("gis_type", new String[]{"0","1","2","3","4","5"}));
			}else if("B".equals(gis_type)){
				dc.add(Restrictions.in("gis_type", new String[]{"6","7"}));
			}else if("C".equals(gis_type)){
				dc.add(Restrictions.in("gis_type", new String[]{"8"}));
			}else{
				dc.add(Restrictions.eq("gis_type", gis_type));
			}
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
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//�����������б�
	@SuppressWarnings("unchecked")
	public List<Elevator> listCount(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc,String gis_type){
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
		}
		if(gis_type!=null&&!"".equals(gis_type)){
			if("A".equals(gis_type)){
				dc.add(Restrictions.in("gis_type", new String[]{"0","1","2","3","4","5"}));
			}else if("B".equals(gis_type)){
				dc.add(Restrictions.in("gis_type", new String[]{"6","7"}));
			}else if("C".equals(gis_type)){
				dc.add(Restrictions.in("gis_type", new String[]{"8"}));
			}else{
				dc.add(Restrictions.eq("gis_type", gis_type));
			}
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
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//�����������б�
		@SuppressWarnings("unchecked")
		public List<Elevator> listCount1(HttpServletRequest request){
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			return elevatorDao.getListByDc(dc);
		}
	//δע���������
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
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//δע������б�
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
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	
	
	//��ע���������
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
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//��ע������б�
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
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//��ע����������
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
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//��ע�������б�
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
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//��ͣ�õ�������
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
		List<Long> list=elevatorDao.getListByDc(dc);
		if(list!=null){
			return list.get(0).intValue();
		}else{
			return -1;
		}
	}
	//��ͣ�õ����б�
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
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//�����������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//������������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//�����ʾ����
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//�����ʾ�����б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//�����������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//������������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//���¼���������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���¼����������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//���¼���ʾ����
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���¼���ʾ�����б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//���¼�Ԥ������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���¼�Ԥ�������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//���ȼ���������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���ȼ����������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//���ȼ���ʾ����
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���ȼ���ʾ�����б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//���ȼ���������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���ȼ�Ԥ�������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//�������������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//��������������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//�������ʾ����
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//�������ʾ�����б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//�����Ԥ������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//�����Ԥ�������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//���ά����������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���ά�����������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//���ά����ʾ����
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���ά����ʾ�����б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//���ά����������
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
		List list=elevatorDao.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0).toString());
		}else{
			return -1;
		}
	}
	//���ά��Ԥ�������б�
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
		List<Long> list=elevatorDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", list));
		if(!"".equals(search)){
			dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
		}
		return elevatorDao.findPageByDcQuery(dc, pageSize, request);
	}
	//��ѯ���ݵ�id�ͼ�ƣ�������ʹ��
	@SuppressWarnings("unchecked")
	public List<Object[]> listElevator(){
		String sql="select id_elevator,`desc` from elevator";
		return elevatorDao.getListBySQL(sql);
	}
	/**
	 * 20170726������
	 */
	//Ͻ����Χ�ڳ������ڳ���15�����ͳ��
	public int getCountFor15Years(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,String desc,String gis_type){
		String sql="select count(e.id_elevator) from elevator e where 1=1 ";
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
		}
		if(gis_type!=null&&!"".equals(gis_type)){
			if("A".equals(gis_type)){
				sql+=" and e.gis_type in ('0','1','2','3','4','5')";
			}else if("B".equals(gis_type)){
				sql+=" and e.gis_type in ('6','7')";
			}else if("C".equals(gis_type)){
				sql+=" and e.gis_type in ('8')";
			}else{
				sql+=" and e.gis_type="+gis_type;
			}
		}
		if(id_test!=0){
			sql+=" and e.id_test="+id_test;
		}
		if(desc!=null&&!"".equals(desc.trim())){
			sql+=" and e.desc like '%"+desc+"%'";
		}
		sql+=" and TIMESTAMPDIFF(YEAR,date_enable,now())>15";
		Object obj=elevatorDao.getObjectBySQL(sql);
		if(obj!=null){
			return Integer.parseInt(obj.toString());
		}else{
			return 0;
		}
	}
	//���ݲ�ͬ���������б�lz
		@SuppressWarnings({"unchecked" })
		public List<Elevator> getListFor15Years(String search,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc,String gis_type){
			String sql="select e.id_elevator from elevator e where 1=1  ";
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
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(gis_type!=null&&!"".equals(gis_type)){
				if("A".equals(gis_type)){
					sql+=" and e.gis_type in ('0','1','2','3','4','5')";
				}else if("B".equals(gis_type)){
					sql+=" and e.gis_type in ('6','7')";
				}else if("C".equals(gis_type)){
					sql+=" and e.gis_type in ('8')";
				}else{
					sql+=" and e.gis_type="+gis_type;
				}
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			sql+=" and TIMESTAMPDIFF(YEAR,e.date_enable,now())>15";
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
	/**
	 * Ͻ����Χ�ڵ��ݰ�����ͳ������
	 */
	
	@SuppressWarnings("rawtypes")
	public Map<String, Integer> getCountForType(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,String desc,String gis_type){
		//��ѯ��������
		String sql="select t.name,count(e.id_elevator) from elevator_type_def t left join modellist m"+
				" on m.type_elevator=t.elevator_type left join elevator e on m.id_model=e.id_elevator_model ";
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
		}
		if(id_test!=0){
			sql+=" and e.id_test="+id_test;
		}
		if(gis_type!=null&&!"".equals(gis_type)){
			if("A".equals(gis_type)){
				sql+=" and e.gis_type in ('0','1','2','3','4','5')";
			}else if("B".equals(gis_type)){
				sql+=" and e.gis_type in ('6','7')";
			}else if("C".equals(gis_type)){
				sql+=" and e.gis_type in ('8')";
			}else{
				sql+=" and e.gis_type="+gis_type;
			}
		}
		if(desc!=null&&!"".equals(desc.trim())){
			sql+=" and e.desc like '%"+desc+"%'";
		}
		sql+=" group by t.name";
		List list=elevatorDao.getListBySQL(sql);
		Map<String, Integer> map=new HashMap<String, Integer>();
		for(Object obj:list){
			if(obj!=null){
				Object[] res=(Object[])obj;
				map.put(res[0].toString(),Integer.parseInt(res[1].toString()));
			}
		}
		return map;
	}
	//���ݲ�ͬ���������б�lz
		@SuppressWarnings({"unchecked" })
		public List<Elevator> listCountByType(int year,String search,String elevator_type,int pageSize,HttpServletRequest request,String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,int keyType,String desc,String gis_type){
			String sql="select e.id_elevator from elevator e left join "
					+ "modellist m on m.id_model = e.id_elevator_model left join elevator_type_def t on m.type_elevator = t.elevator_type "
					+ "where t.elevator_type='"+elevator_type+"'";
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
			}
			if(id_test!=0){
				sql+=" and e.id_test="+id_test;
			}
			if(gis_type!=null&&!"".equals(gis_type)){
				if("A".equals(gis_type)){
					sql+=" and e.gis_type in ('0','1','2','3','4','5')";
				}else if("B".equals(gis_type)){
					sql+=" and e.gis_type in ('6','7')";
				}else if("C".equals(gis_type)){
					sql+=" and e.gis_type in ('8')";
				}else{
					sql+=" and e.gis_type="+gis_type;
				}
			}
			if(desc!=null&&!"".equals(desc.trim())){
				if(keyType==1){
					sql+=" and e.desc like '%"+desc+"%'";
				}
				if(keyType==2){
					
					sql+=" and m.model_name like '%"+desc+"%'";
				}
			}
			if(year!=0){
				sql+=" and TIMESTAMPDIFF(YEAR,e.date_enable,now())>15";
			}
			List<Long> list=elevatorDao.getListBySQL(sql);
			DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
			dc.add(Restrictions.in("id_elevator", list));
			if(!"".equals(search)){
				dc.add(Restrictions.like("code_manufer", search,MatchMode.ANYWHERE));
			}
			return elevatorDao.findPageByDcQuery(dc, pageSize, request);
		}
	/**
	 * Ͻ����Χ��15�����ϵ��ݰ�����ͳ������lz
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Integer> getCountFor15YearsForType(String id_city,String id_district,String id_subdistrict,int id_service,int id_user,int id_test,String desc,String gis_type){
		//��ѯ��������
		String sql="select t.name,count(e.id_elevator) from elevator_type_def t left join modellist m"+
				" on m.type_elevator=t.elevator_type left join elevator e on m.id_model=e.id_elevator_model ";
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
		if(gis_type!=null&&!"".equals(gis_type)){
			if("A".equals(gis_type)){
				sql+=" and e.gis_type in ('0','1','2','3','4','5')";
			}else if("B".equals(gis_type)){
				sql+=" and e.gis_type in ('6','7')";
			}else if("C".equals(gis_type)){
				sql+=" and e.gis_type in ('8')";
			}else{
				sql+=" and e.gis_type="+gis_type;
			}
		}
		if(id_user!=0){
			sql+=" and e.id_user="+id_user;
		}
		if(id_test!=0){
			sql+=" and e.id_test="+id_test;
		}
		if(desc!=null&&!"".equals(desc.trim())){
			sql+=" and e.desc like '%"+desc+"%'";
		}
		sql+=" and TIMESTAMPDIFF(YEAR,date_enable,now())>15";
		sql+=" group by t.name";
		List list=elevatorDao.getListBySQL(sql);
		Map<String, Integer> map=new HashMap<String, Integer>();
		for(Object obj:list){
			if(obj!=null){
				Object[] res=(Object[])obj;
				map.put(res[0].toString(),Integer.parseInt(res[1].toString()));
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public List<Elevator> list() {
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		return elevatorDao.getListByDc(dc);
	}
	//ͨ��idlist�����
	@SuppressWarnings("unchecked")
	public List<Elevator> getElevatorByIdList(List<Integer> idList) {
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator.class);
		dc.add(Restrictions.in("id_elevator", idList));
		return elevatorDao.getListByDc(dc);
	}
	//��ȡ����ʵʱ����״̬
	public List<E_data_history> getElevatorRunningStatus(int eid){
		//��Ҫ���ݣ��������з����ſ���״̬��¥�㣬������� �Ƿ�����
		String sql="select h.* from e_data_history h join e_data_status s on h.e_data_history_id=s.e_data_history_id "+
					"join edevice d on s.e_no=d.e_no where d.id_elevator="+eid;
		return edeviceDao.listBySQLQuery(sql, E_data_history.class);
	}
	//�Ƿ��е���ʵʱ���м�¼
	public Object getEdevice(int eid){
		String sql="select 1 from edevice where id_elevator="+eid;
		return edeviceDao.getObjectBySQL(sql);
	}
	@SuppressWarnings("rawtypes")
	public List getEDataAlarms(){
		String sql="select d.id_elevator,a.*,e.desc from e_data_alarm a "
				+ "join e_data_status s on a.e_data_alarm_id=s.e_data_alarm_id "
				+ "join edevice d on s.e_no=d.e_no "
				+ "join elevator e on e.id_elevator=d.id_elevator";
		List alarmList=elevatorDao.listBySQLQuery(sql);
		return alarmList;
	}
	public void updateReceivedTime(int e_data_alarm_id){
		String sql="update e_data_alarm set received=now() where e_data_alarm_id="+e_data_alarm_id;
		elevatorDao.executeUpdate(sql);
	}
	public void updateFinishedTime(int e_data_alarm_id){
		String sql="update e_data_alarm set finished=now() where e_data_alarm_id="+e_data_alarm_id;
		elevatorDao.executeUpdate(sql);
	}
	
	
}
