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
import vo.Modellist;

import dao.ModellistDao;

@Service
public class ModellistService {
  @Resource
  public ModellistDao modellistDao;
  @SuppressWarnings("unchecked")
	public List<Modellist> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Modellist.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("modelname", key,MatchMode.ANYWHERE),
					Restrictions.like("suitplace", key,MatchMode.ANYWHERE)));
			
		}
		return modellistDao.findPageByDcQuery(dc, pageSize, request);
	}
	@SuppressWarnings("unchecked")
	public List<Modellist> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Modellist.class);
		return modellistDao.getListByDc(dc);
	}
	public Serializable insert(Modellist modellist){
		return modellistDao.save(modellist);
	}
	public Modellist findById(int idmodel){
		return modellistDao.get(Modellist.class, idmodel);
	}
	public void update(Modellist model){
		modellistDao.update(model);
	}
	public void delete(Modellist model){
		modellistDao.delete(model);
	}
}
