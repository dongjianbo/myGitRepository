package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.CitylistDao;

import vo.Citylist;

@Service
public class CitylistService {
	@Resource
	public CitylistDao citylistDao;
	@SuppressWarnings("unchecked")
	public List<Citylist> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Citylist.class);
		dc.add(Restrictions.ne("name_city", "ʡֱ"));
		return citylistDao.getListByDc(dc);
	}
	@SuppressWarnings("unchecked")
	public Citylist listBy_Idcity(String id_city){
		DetachedCriteria dc=DetachedCriteria.forClass(Citylist.class);
		dc.add(Restrictions.eq("id_city", id_city));
		List<Citylist> list=citylistDao.getListByDc(dc);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
}
