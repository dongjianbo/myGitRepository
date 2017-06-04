package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import dao.ComplainSourceDao;
import vo.ComplianSource;

@Service
public class ComplainSourceService {
  @Resource
  public ComplainSourceDao  complainSourceDao;
  @SuppressWarnings("unchecked")
	public List<ComplianSource> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(ComplianSource.class);
		return complainSourceDao.getListByDc(dc);
	} 
	public ComplianSource findById(String id_servicer_type){
		return complainSourceDao.get(ComplianSource.class, id_servicer_type);
	}
}

