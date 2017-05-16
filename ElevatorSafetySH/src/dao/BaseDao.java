package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getListBySQL(String sql){
		Session session=this.getSessionFactory().openSession();
		List list=session.createSQLQuery(sql).list();
		session.close();
		if(list.isEmpty()){
			list.add(null);
		}
		return list;
	}
	@SuppressWarnings({ "rawtypes"})
	public List listBySQLQuery(String sql){
		Session session=this.getSessionFactory().openSession();
		List list=session.createSQLQuery(sql).list();
		session.close();
		return list;
	}
	@SuppressWarnings({ "rawtypes" })
	public Object getObjectBySQL(String sql){
		Session session=this.getSessionFactory().openSession();
		List list=session.createSQLQuery(sql).list();
		session.close();
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	public void executeUpdate(String sql){
		Session session=this.getSessionFactory().openSession();
		session.createSQLQuery(sql).executeUpdate();
		session.close();
	}
}
