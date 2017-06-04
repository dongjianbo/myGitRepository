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
import vo.User;
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
	public int getCountForCity(String id_city, String id_district, String id_subdistrict){
		String sql="select count(id_safer) from safer s where s.id_user "
				+ "in(select distinct id_user from elevator e where 1=1 ";
		if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
			sql+=" and e.id_city='"+id_city+"'";
		}
		if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
			sql+=" and e.id_district='"+id_district+"'";
		}
		if(id_subdistrict!=null&&!"".equals(id_subdistrict)&&!"00".equals(id_subdistrict)){
			sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
		}
		sql+= ")";
		Object obj=saferDao.getObjectBySQL(sql);
		if(obj!=null){
			return Integer.parseInt(obj.toString());
		}else{
			return 0;
		}
		
	}
	/**
	 * 按市级查找使用单位的数量列表lz
	 */
	@SuppressWarnings({"unchecked" })
	public List<Safer> listForCity(String id_city, String id_district,int pageSize,HttpServletRequest request, String id_subdistrict){
		String sql="select id_safer from safer s where s.id_user "
				+ "in(select distinct id_user from elevator e where 1=1 ";
		if(id_city!=null&&!"".equals(id_city)&&!"00".equals(id_city)){
			sql+=" and e.id_city='"+id_city+"'";
		}
		if(id_district!=null&&!"".equals(id_district)&&!"00".equals(id_district)){
			sql+=" and e.id_district='"+id_district+"'";
		}
		if(id_subdistrict!=null&&!"".equals(id_subdistrict)&&!"00".equals(id_subdistrict)){
			sql+=" and e.id_subdistrict='"+id_subdistrict+"'";
		}
		sql+= ")";
		
		List<Long> list=saferDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Safer.class);
		dc.add(Restrictions.in("idsafer", list));
		
		return saferDao.findPageByDcQuery(dc, pageSize, request);
	}
	
}
