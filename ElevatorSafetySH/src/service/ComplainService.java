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
import vo.Elevator;

@Service
public class ComplainService {
	@Resource
	public ComplainDao  complainDao;
	public Serializable insert(Complain complain){
		return complainDao.save(complain);
	}
	//ʹ�õ�λ������Ͷ����Ϣ
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
	//ά����λ������Ͷ����Ϣ
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
	//��ܲ��Ų�����Ͷ����Ϣ
	@SuppressWarnings("unchecked")
	public List<Complain> selectList(int type_object,int source,int status,int pageSize,HttpServletRequest request) {
		DetachedCriteria dc=DetachedCriteria.forClass(Complain.class);
		if(type_object!=-1){
			dc.add(Restrictions.eq("type_object", type_object));
		}
		if(source!=-1){
			dc.add(Restrictions.eq("source", source));
		}
		if(status!=-1){
			dc.add(Restrictions.eq("status", status));
		}
		dc.addOrder(Order.desc("input1"));
		return complainDao.findPageByDcQuery(dc, pageSize, request);
	}
	//�޸�
	public void update(Complain complain){
		complainDao.update(complain);
	}
	public Complain getComplainById(int cid) {
		DetachedCriteria dc=DetachedCriteria.forClass(Complain.class);
		dc.add(Restrictions.eq("cid", cid));
		return (Complain)complainDao.getListByDc(dc).get(0);
	}
}
