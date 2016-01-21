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
		dc.add(Restrictions.ne("name_subdistrict", "��ֱ"));
		return subdistictlistDao.getListByDc(dc); 
	}
	public String getListById(String id_city,String id_distictlist,String sub){
		System.out.println(sub+"----------------------sub");
		if(!id_distictlist.equals("00")&&!sub.equals("00")&&!sub.equals("0")){
			DetachedCriteria dc=DetachedCriteria.forClass(Subdistictlist.class);
			dc.add(Restrictions.eq("id_district", id_distictlist));
			dc.add(Restrictions.eq("id_city", id_city));
			dc.add(Restrictions.eq("id_subdistrict", sub));
			return ((Subdistictlist)subdistictlistDao.getListByDc(dc).get(0)).getName_subdistrict(); 
		}else{
			return "";
		}
		
	}
}
