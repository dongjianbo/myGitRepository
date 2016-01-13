package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
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
		return citylistDao.getListByDc(dc);
	}
	
}
