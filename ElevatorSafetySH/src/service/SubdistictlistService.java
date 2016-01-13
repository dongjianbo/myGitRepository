package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.SubdistictlistDao;

import vo.Subdistictlist;

@Service
public class SubdistictlistService {
	@Resource
	public SubdistictlistDao subdistictlistDao;
	@SuppressWarnings("unchecked")
	public List<Subdistictlist> getListById(String id_city,String id_distictlist){
		DetachedCriteria dc=DetachedCriteria.forClass(Subdistictlist.class);
		dc.add(Restrictions.eq("id_district", id_distictlist));
		dc.add(Restrictions.eq("id_city", id_city));
		return subdistictlistDao.getListByDc(dc); 
	}
}
