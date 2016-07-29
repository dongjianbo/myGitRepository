package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
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
	public void updateCard(String idMifare,int idservicer){
		Safer s=saferDao.get(Safer.class, idservicer);
		s.setIdMifare(idMifare);
		saferDao.update(s);
	}
	/**
	 * 按市级查找安全人员的数量
	 *
	 */
	@SuppressWarnings("rawtypes")
	public int getCountForCity(String id_city){
		DetachedCriteria dc=DetachedCriteria.forClass(Safer.class);
		dc.setProjection(Projections.count("idsafer"));
		if(id_city!=null&&!"".equals(id_city)){
			dc.createAlias("user", "user");
			dc.add(Restrictions.eq("user.registerArea", id_city));
		}
		List obj=saferDao.getListByDc(dc);
		if(obj!=null&&!obj.isEmpty()){
			return Integer.parseInt(obj.get(0).toString());
		}else{
			return 0;
		}
	}
}
