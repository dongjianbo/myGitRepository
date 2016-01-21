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
		dc.add(Restrictions.ne("name_district", "ÊÐÖ±"));
		return distictlistDao.getListByDc(dc);
	}
	public String getListByCityId(String id_city,String dis){
		if(!dis.equals("00")){
			DetachedCriteria dc=DetachedCriteria.forClass(Distictlist.class);
			dc.add(Restrictions.eq("id_city", id_city));
			dc.add(Restrictions.eq("id_district", dis));
			return ((Distictlist)distictlistDao.getListByDc(dc).get(0)).getName_district();
		}else{
			return "";
		}
		
		
		
	}
}
