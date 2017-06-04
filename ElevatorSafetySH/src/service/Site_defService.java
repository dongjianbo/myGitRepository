package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import dao.Site_defDao;
import vo.Site_def;

@Service
public class Site_defService {
  @Resource
  public Site_defDao  site_defDao;
  @SuppressWarnings("unchecked")
	public List<Site_def> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Site_def.class);
		return site_defDao.getListByDc(dc);
	} 
	public Site_def findById(String id_servicer_type){
		return site_defDao.get(Site_def.class, id_servicer_type);
	}
}

