package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.History;

import dao.HistoryDao;

@Service
public class HistoryService {
  @Resource
  public HistoryDao historyDao;
  public Serializable insert(History history){
		return historyDao.save(history);
	}
  public History getHistory(History history){
	  DetachedCriteria dc=DetachedCriteria.forClass(History.class);
	  dc.add(Restrictions.eq("datetime",history.getDatetime()));
	 List<History> list=historyDao.getListByDc(dc);
	 return list.get(0);
  }
}
