package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import vo.Test_type_def;

import dao.Test_type_defDao;
@Service
public class Test_type_defService {
	   @Resource
	   public Test_type_defDao test_type_defDao;
	   public List<Test_type_def> list(){
		   DetachedCriteria dc=DetachedCriteria.forClass(Test_type_def.class);
		   return test_type_defDao.getListByDc(dc);
	   }
}
