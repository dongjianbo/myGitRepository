package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import dao.ComplainLevelDao;
import vo.ComplianLevel;

@Service
public class ComplainLevelService {
  @Resource
  public ComplainLevelDao  complainLevelDao;
  @SuppressWarnings("unchecked")
	public List<ComplianLevel> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(ComplianLevel.class);
		return complainLevelDao.getListByDc(dc);
	} 
	public ComplianLevel findById(String id_servicer_type){
		return complainLevelDao.get(ComplianLevel.class, id_servicer_type);
	}
}

