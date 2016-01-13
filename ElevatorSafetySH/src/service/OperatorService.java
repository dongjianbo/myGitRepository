package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Operator;

import dao.OperatorDao;

@Service
public class OperatorService {
   @Resource
   public OperatorDao operatorDao;
   @SuppressWarnings("unchecked")
 	public List<Operator> list(String key,int pageSize,HttpServletRequest request){
 		DetachedCriteria dc=DetachedCriteria.forClass(Operator.class);
 		if(key!=null&&!"".equals(key.trim())){
 			dc.add(Restrictions.or(Restrictions.like("idcard", key,MatchMode.ANYWHERE),
 					Restrictions.like("name", key,MatchMode.ANYWHERE)));
 			
 		}
 		return operatorDao.findPageByDcQuery(dc, pageSize, request);
 	}
 	public Serializable insert(Operator operator){
 		return operatorDao.save(operator);
 	}
 	public Operator findById(int idoperator){
 		return operatorDao.get(Operator.class, idoperator);
 	}
 	public void update(Operator operator){
 		operatorDao.update(operator);
 	}
 	public void delete(Operator operator){
 		operatorDao.delete(operator);
 	}
 	public int check(Operator operator){
 		DetachedCriteria dc=DetachedCriteria.forClass(Operator.class);
 		dc.add(Restrictions.eq("loginname", operator.getLoginname().trim()));
 		List<Operator> operlist=operatorDao.getListByDc(dc);
 		if(operlist==null||operlist.size()==0){
 			return 1;//表示用户名不存在
 		}else{
 			System.out.println(operator.getLoginname());
 			if(!operator.getPassword().equals(operlist.get(0).getPassword())){
 					return 2;//表示密码不正确
 			}else{
 				return 3;//用户密码都正确
 			}
 		}
 	}
 	public Operator getOper(Operator operator){
 		DetachedCriteria dc=DetachedCriteria.forClass(Operator.class);
 		dc.add(Restrictions.eq("loginname", operator.getLoginname().trim()));
 		List<Operator> operlist=operatorDao.getListByDc(dc);
 		Operator o=operlist.get(0);
 		return o;
 	}
}
