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
import vo.Servicer;
import dao.ServicerDao;

@Service
public class ServicerService {
	@Resource
    public ServicerDao servicerDao;
	@SuppressWarnings("unchecked")
	public List<Servicer> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Servicer.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("licencecode", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return servicerDao.findPageByDcQuery(dc, pageSize, request);
	}
	@SuppressWarnings("unchecked")
	public List<Servicer> listByIdService(int idService){
		DetachedCriteria dc=DetachedCriteria.forClass(Servicer.class);
		dc.add(Restrictions.eq("idservice", idService));
		return servicerDao.getListByDc(dc);
	}
	public Serializable insert(Servicer servicer){
		return servicerDao.save(servicer);
	}
	public Servicer findById(int idservicer){
		return servicerDao.get(Servicer.class, idservicer);
	}
	public void update(Servicer servicer){
		servicerDao.update(servicer);
	}
	public void delete(Servicer servicer){
		servicerDao.delete(servicer);
	}
	public void updateCard(String idMifare,int idservicer){
		Servicer s=servicerDao.get(Servicer.class, idservicer);
		s.setIdMifare(idMifare);
		servicerDao.update(s);
	}
	/**
	 * 按市级查找维保人员的数量
	 *
	 */
	public int getCountForCity(String id_city, String id_district, String id_subdistrict){
		String sql="select count(id_servicer) from servicer s where s.id_service in "
				+ "(select distinct id_service from elevator e where 1=1 ";
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
		Object obj=servicerDao.getObjectBySQL(sql);
		if(obj!=null){
			return Integer.parseInt(obj.toString());
		}else{
			return 0;
		}
		
	}
}
