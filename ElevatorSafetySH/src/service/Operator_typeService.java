package service;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Operator_type;

import dao.Operator_typeDao;

@Service
public class Operator_typeService {
   @Resource
   public Operator_typeDao operator_typeDao;
   public Operator_type getOperType(String id_operator_type){
	  /* DetachedCriteria dc=DetachedCriteria.forClass(Operator_type.class);
	   dc.add(Restrictions.eq("id_operator_type", id_operator_type));*/
	   return operator_typeDao.get(Operator_type.class, id_operator_type);
   }
}
