package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.ComplainDao;
import vo.Complain;

@Service
public class ComplainService {
	@Resource
	public ComplainDao  complainDao;
	public Serializable insert(Complain complain){
		return complainDao.save(complain);
	}
	//使用单位查所有投诉信息
	@SuppressWarnings("unchecked")
	public List<Complain> selectList(int id_user, ArrayList<Integer> ids,int pageSize,HttpServletRequest request, List<Integer> idList) {
		DetachedCriteria dc=DetachedCriteria.forClass(Complain.class);
		if(ids!=null){
			dc.add(Restrictions.or(Restrictions.and(Restrictions.in("id_object",ids),
					Restrictions.eq("type_object", 2)),
					Restrictions.and(Restrictions.in("id_object",idList),
							Restrictions.eq("type_object", 0))));
		}else{
			dc.add(Restrictions.or(Restrictions.and(Restrictions.eq("id_object", id_user),
					Restrictions.eq("type_object", 2)),
					Restrictions.and(Restrictions.in("id_object",idList),
							Restrictions.eq("type_object", 0))));
		}
		dc.addOrder(Order.desc("input1"));
		return complainDao.findPageByDcQuery(dc, pageSize, request);
	}
	//维保单位查所有投诉信息
	@SuppressWarnings("unchecked")
	public List<Complain> selectList(int id_service, int pageSize, HttpServletRequest request, List<Integer> idList) {
		DetachedCriteria dc=DetachedCriteria.forClass(Complain.class);
		dc.add(Restrictions.or(Restrictions.and(Restrictions.eq("id_object", id_service),
				Restrictions.eq("type_object", 1)),
				Restrictions.and(Restrictions.in("id_object",idList),
						Restrictions.eq("type_object", 0))));
		dc.addOrder(Order.desc("input1"));
		return complainDao.findPageByDcQuery(dc, pageSize, request);
	}
	//监管部门查所有投诉信息
	@SuppressWarnings("unchecked")
	public List<Complain> selectList( int id_service,int id_user,int source,int type_object, List<Integer> idList,int pageSize,HttpServletRequest request) {
		DetachedCriteria dc=DetachedCriteria.forClass(Complain.class);
		if(type_object!=-1){
			dc.add(Restrictions.eq("type_object", type_object));
		}
		if(idList.isEmpty()){
			dc.add(Restrictions.ne("type_object", 0));
		}
		if(type_object==0&&!idList.isEmpty()){
			dc.add(Restrictions.in("id_object", idList));
		}else if(type_object==1&&id_service!=0){
			dc.add(Restrictions.eq("id_object", id_service));
		}else if(type_object==2&&id_user!=0){
			dc.add(Restrictions.eq("id_object", id_user));
		}else{
			if(!idList.isEmpty()&&id_service!=0&&id_user!=0){
				dc.add(Restrictions.or(
						Restrictions.or(
								Restrictions.and(Restrictions.eq("id_object", id_service),Restrictions.eq("type_object", 1)),
								Restrictions.and(Restrictions.eq("id_object", id_user),Restrictions.eq("type_object", 2))),
								Restrictions.and(Restrictions.in("id_object",idList),Restrictions.eq("type_object", 0))));
			}else if(idList.isEmpty()&&id_service!=0&&id_user!=0){
				dc.add(Restrictions.or(
								Restrictions.and(Restrictions.eq("id_object", id_service),Restrictions.eq("type_object", 1)),
								Restrictions.and(Restrictions.eq("id_object", id_user),Restrictions.eq("type_object", 2))));
			}else if(!idList.isEmpty()&&id_service==0&&id_user==0){
				dc.add(Restrictions.or(Restrictions.or(Restrictions.and(Restrictions.in("id_object",idList),Restrictions.eq("type_object", 0)),Restrictions.eq("type_object", 1)),Restrictions.eq("type_object", 2)));
			}else if(!idList.isEmpty()&&id_service==0&&id_user!=0){
				dc.add(Restrictions.or(Restrictions.or(
						Restrictions.and(Restrictions.in("id_object",idList),Restrictions.eq("type_object", 0)),
						Restrictions.and(Restrictions.eq("id_object", id_user),Restrictions.eq("type_object", 2))),Restrictions.eq("type_object", 1)));
			}else if(!idList.isEmpty()&&id_service!=0&&id_user==0){
				dc.add(Restrictions.or(Restrictions.or(
						Restrictions.and(Restrictions.in("id_object",idList),Restrictions.eq("type_object", 0)),
						Restrictions.and(Restrictions.eq("id_object", id_service),Restrictions.eq("type_object", 1))),Restrictions.eq("type_object", 2)));
			}else if(idList.isEmpty()&&id_service==0&&id_user!=0){
				dc.add(Restrictions.or(Restrictions.and(Restrictions.eq("id_object", id_user),Restrictions.eq("type_object", 2)),Restrictions.eq("type_object", 1)));
			}else if(idList.isEmpty()&&id_service!=0&&id_user==0){
				dc.add(Restrictions.or(Restrictions.and(Restrictions.eq("id_object", id_service),Restrictions.eq("type_object", 1)),Restrictions.eq("type_object", 2)));
			}
					
		}
		if(source!=-1){
			dc.add(Restrictions.eq("source", source));
		}
		dc.addOrder(Order.desc("input1"));
		return complainDao.findPageByDcQuery(dc, pageSize, request);
	}
	//修改
	public void update(Complain complain){
		complainDao.update(complain);
	}
	public Complain getComplainById(int cid) {
		DetachedCriteria dc=DetachedCriteria.forClass(Complain.class);
		dc.add(Restrictions.eq("cid", cid));
		return (Complain)complainDao.getListByDc(dc).get(0);
	}
}

