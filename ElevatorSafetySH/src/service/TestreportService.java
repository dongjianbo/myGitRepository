package service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import vo.Testreport;

import dao.TestreportDao;

@Service
public class TestreportService {
  @Resource
  public TestreportDao testreportDao;
  //²åÈë¼ÇÂ¼
  public Serializable insert(Testreport testreport){
		return testreportDao.save(testreport);
	}
}
