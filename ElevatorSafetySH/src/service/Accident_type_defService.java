package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import vo.Accident_type_def;

import dao.Accident_type_defDao;

@Service
public class Accident_type_defService {
   @Resource
   public Accident_type_defDao accident_type_defDao;
   public List<Accident_type_def> list(){
	   DetachedCriteria dc=DetachedCriteria.forClass(Accident_type_def.class);
	  return accident_type_defDao.getListByDc(dc);
   }
}
