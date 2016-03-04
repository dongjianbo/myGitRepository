package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import vo.History_list;

import dao.History_listDao;

@Service
public class History_listService {
  @Resource
  public History_listDao history_listDao;
  public Serializable insert(History_list h){
	  return history_listDao.save(h);
  }
  public void delete(History_list h){
	  history_listDao.delete(h);
  }
  @SuppressWarnings("unchecked")
  public int getIdBySQL(String sql){
	  List<Integer> ids=history_listDao.getListBySQL(sql);
	  if(ids!=null&&ids.size()>0){
		  return ids.get(0);
	  }else{
		  return -1;
	  }
  }
}
