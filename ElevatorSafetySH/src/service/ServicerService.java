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
	@SuppressWarnings("rawtypes")
	public int getCountForCity(String id_city){
		DetachedCriteria dc=DetachedCriteria.forClass(Servicer.class);
		dc.setProjection(Projections.count("idservicer"));
		if(id_city!=null&&!"".equals(id_city)){
			dc.createAlias("service1", "service1");
			dc.add(Restrictions.eq("service1.registerArea", id_city));
		}
		List obj=servicerDao.getListByDc(dc);
		if(obj!=null&&!obj.isEmpty()){
			return Integer.parseInt(obj.get(0).toString());
		}else{
			return 0;
		}
	}
}
