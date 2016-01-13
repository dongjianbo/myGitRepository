package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;



import vo.Servicer_type_def;
import dao.Servicer_type_defDao;

@Service
public class Servicer_type_defService {
  @Resource
  public Servicer_type_defDao  servicer_type_defDao;
  @SuppressWarnings("unchecked")
	public List<Servicer_type_def> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Servicer_type_def.class);
		return servicer_type_defDao.getListByDc(dc);
	} 
}
