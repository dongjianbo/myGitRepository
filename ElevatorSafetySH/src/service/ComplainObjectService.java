package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import dao.ComplainObjectDao;
import vo.ComplianObject;

@Service
public class ComplainObjectService {
  @Resource
  public ComplainObjectDao  complainObjectDao;
  @SuppressWarnings("unchecked")
	public List<ComplianObject> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(ComplianObject.class);
		return complainObjectDao.getListByDc(dc);
	} 
	public ComplianObject findById(String id_type){
		return complainObjectDao.get(ComplianObject.class, id_type);
	}
}

