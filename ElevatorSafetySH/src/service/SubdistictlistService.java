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
	public String getListById(String id_city,String id_distictlist,String sub){
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
	@SuppressWarnings("unchecked")
	public Subdistictlist getSubdistictById(String id_city,String id_distictlist,String sub){
		if(!id_city.equals("00")&&!id_distictlist.equals("00")&&!sub.equals("")){
			DetachedCriteria dc=DetachedCriteria.forClass(Subdistictlist.class);
			dc.add(Restrictions.eq("id_district", id_distictlist));
			dc.add(Restrictions.eq("id_city", id_city));
			dc.add(Restrictions.eq("id_subdistrict", sub));
			List<Subdistictlist> list=subdistictlistDao.getListByDc(dc);
			if(list!=null&&!list.isEmpty()){
				return (Subdistictlist)list.get(0);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
}
