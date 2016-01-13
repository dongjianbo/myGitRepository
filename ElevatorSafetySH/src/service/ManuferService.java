package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Manufer;
import vo.Service1;
import dao.ManuferDao;

@Service
public class ManuferService {
	@Resource
    public ManuferDao anuferDao;
	@SuppressWarnings("unchecked")
	public List<Manufer> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Manufer.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return anuferDao.findPageByDcQuery(dc, pageSize, request);
	}
	 public List<Manufer> getId_manufer(){
	    	DetachedCriteria dc=DetachedCriteria.forClass(Manufer.class);
	    	return anuferDao.getListByDc(dc);
			
		}
	@SuppressWarnings("unchecked")
	public List<Manufer> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Manufer.class);
		return anuferDao.getListByDc(dc);
	}
	public Serializable insert(Manufer manufer){
		return anuferDao.save(manufer);
	}
	public Manufer findById(int idmanufer){
		return anuferDao.get(Manufer.class, idmanufer);
	}
	public void update(Manufer manufer){
		anuferDao.update(manufer);
	}
	public void delete(Manufer manufer){
		anuferDao.delete(manufer);
	}
	
}
