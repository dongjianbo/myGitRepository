package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import dao.ApproveAckDao;
import vo.Approve_ack;

@Service
public class ApproveAckService {
  @Resource
  public ApproveAckDao  approveAckDao;
  @SuppressWarnings("unchecked")
	public List<Approve_ack> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Approve_ack.class);
		return approveAckDao.getListByDc(dc);
	} 
	public Approve_ack findById(String id_servicer_type){
		return approveAckDao.get(Approve_ack.class, id_servicer_type);
	}
}

