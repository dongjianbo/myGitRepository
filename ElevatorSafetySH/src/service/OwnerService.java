package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Owner;

import dao.OwnerDao;

@Service
public class OwnerService {
	@Resource
    public OwnerDao ownerDao;
	@SuppressWarnings("unchecked")
	public List<Owner> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Owner.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return ownerDao.findPageByDcQuery(dc, pageSize, request);
	}
	public List<Owner> selectId_owner(){
		DetachedCriteria dc=DetachedCriteria.forClass(Owner.class);
		return ownerDao.getListByDc(dc);
	}
	public Serializable insert(Owner owner){
		return ownerDao.save(owner);
	}
	public Owner findById(int idowner){
		return ownerDao.get(Owner.class, idowner);
	}
	public void update(Owner owner){
		ownerDao.update(owner);
	}
	public void delete(Owner owner){
		ownerDao.delete(owner);
	}
}
