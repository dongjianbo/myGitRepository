package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;


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
}
