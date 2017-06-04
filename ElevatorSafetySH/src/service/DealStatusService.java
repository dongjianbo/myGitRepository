package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import dao.DealStatusDao;
import vo.DealStatus;

@Service
public class DealStatusService {
  @Resource
  public DealStatusDao  dealStatusDao;
  @SuppressWarnings("unchecked")
	public List<DealStatus> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(DealStatus.class);
		return dealStatusDao.getListByDc(dc);
	} 
	public DealStatus findById(String id_servicer_type){
		return dealStatusDao.get(DealStatus.class, id_servicer_type);
	}
}

