package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Distictlist;

import dao.DistictlistDao;

@Service
public class DistictlistService {
	@Resource
	public DistictlistDao distictlistDao;
	@SuppressWarnings("unchecked")
	public List<Distictlist> getListByCityId(String id_city){
		DetachedCriteria dc=DetachedCriteria.forClass(Distictlist.class);
		dc.add(Restrictions.eq("id_city", id_city));
		return distictlistDao.getListByDc(dc);
	}
	public Distictlist getListByCityId(String id_city,String dis){
		DetachedCriteria dc=DetachedCriteria.forClass(Distictlist.class);
		dc.add(Restrictions.eq("id_city", id_city));
		dc.add(Restrictions.eq("id_district", dis));
		return (Distictlist)distictlistDao.getListByDc(dc).get(0);
	}
}
