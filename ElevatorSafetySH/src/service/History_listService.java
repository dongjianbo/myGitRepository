package service;

import java.io.Serializable;

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
}
