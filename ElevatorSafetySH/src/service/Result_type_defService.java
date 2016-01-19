package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import vo.Result_type_def;

import dao.Result_type_defDao;
@Service
public class Result_type_defService {
	   @Resource
	   public Result_type_defDao result_type_defDao;
	   public List<Result_type_def> list(){
		   DetachedCriteria dc=DetachedCriteria.forClass(Result_type_def.class);
		   return result_type_defDao.getListByDc(dc);
	   }
}
