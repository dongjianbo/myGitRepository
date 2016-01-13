package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import vo.Citylist;
import vo.Systemstate;
import dao.SystemstateDao;

@Service
public class SystemstateService {
	@Resource
	public SystemstateDao systemstateDao;
	@SuppressWarnings("unchecked")
	public List<Systemstate> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Systemstate.class);
		return systemstateDao.getListByDc(dc);
	}
	
	 public void update(){
    	 String sql="update  systemstate set version_person=version_person+1";
    	 Session session=systemstateDao.getSessionFactory().openSession();//��daoҪSessionFactory�������������ݿ�����
    	 Transaction tran=session.beginTransaction();//��������
 		SQLQuery sqlquery=session.createSQLQuery(sql);//����sql���
 		int i=sqlquery.executeUpdate();
 		tran.commit();//�ύ����
		session.close();//�ر�����
	 }
	 public void update_version_elevator(){
    	 String sql="update  systemstate set version_elevator=version_elevator+1";
    	 Session session=systemstateDao.getSessionFactory().openSession();//��daoҪSessionFactory�������������ݿ�����
    	 Transaction tran=session.beginTransaction();//��������
 		SQLQuery sqlquery=session.createSQLQuery(sql);//����sql���
 		int i=sqlquery.executeUpdate();
 		tran.commit();//�ύ����
		session.close();//�ر�����
	 }
	
}
