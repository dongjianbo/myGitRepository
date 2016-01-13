package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Safer;

import dao.SaferDao;

@Service
public class SaferService {
  @Resource
  public SaferDao saferDao;
  @SuppressWarnings("unchecked")
	public List<Safer> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Safer.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("licencecode", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return saferDao.findPageByDcQuery(dc, pageSize, request);
	}
	public Serializable insert(Safer safer){
		return saferDao.save(safer);
	}
	public Safer findById(int idsafer){
		return saferDao.get(Safer.class, idsafer);
	}
	public void update(Safer safer){
		saferDao.update(safer);
	}
	public void delete(Safer safer){
		saferDao.delete(safer);
	}
}
