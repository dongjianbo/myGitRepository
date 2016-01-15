package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;



import vo.Servicer_type_def;
import vo.Status_def;
import dao.Status_defDao;

@Service
public class Status_defService {
 @Resource
 public Status_defDao  status_defDao;
 @SuppressWarnings("unchecked")
	public List<Status_def> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Status_def.class);
		return status_defDao.getListByDc(dc);
	} 
	public Status_def findById(String idstatus){
		return status_defDao.get(Status_def.class, idstatus);
	}
}
