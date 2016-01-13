package dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import util.HibernateByDCPageUtil;
@Repository
public class BaseDao extends HibernateByDCPageUtil{
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}
	@SuppressWarnings("rawtypes")
	public List getListByDc(DetachedCriteria dc){
		return this.getHibernateTemplate().findByCriteria(dc);
	}
	public Serializable save(Object obj){
		return getHibernateTemplate().save(obj);
	}
	public void update(Object obj){
		getHibernateTemplate().update(obj);
	}
	public void delete(Object obj){
		getHibernateTemplate().delete(obj);
	}
	public <T> T get(Class<T> entityName,Serializable id){
		return this.getHibernateTemplate().get(entityName, id);
	}
}
