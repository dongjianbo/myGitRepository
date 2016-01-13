package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Service1;
import dao.ServiceDao;

@Service
public class ServiceService {
	@Resource
    public ServiceDao serviceDao;
	@SuppressWarnings("unchecked")
	public List<Service1> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Service1.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return serviceDao.findPageByDcQuery(dc, pageSize, request);
	}
	public List<Service1> select_Idservice(){
		DetachedCriteria dc=DetachedCriteria.forClass(Service1.class);
		return serviceDao.getListByDc(dc);
	}
	@SuppressWarnings("unchecked")
	public List<Service1> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Service1.class);
		return serviceDao.getListByDc(dc);
	}
	public Serializable insert(Service1 service){
		return serviceDao.save(service);
	}
	public Service1 findById(int idService){
		return serviceDao.get(Service1.class, idService);
	}
	public void update(Service1 service){
		serviceDao.update(service);
	}
	public void delete(Service1 service){
		serviceDao.delete(service);
	}
  
}
