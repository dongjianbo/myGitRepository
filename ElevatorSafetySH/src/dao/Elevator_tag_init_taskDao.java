package dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class Elevator_tag_init_taskDao extends BaseDao{
	public void updateSQL(String sql){
		Session session=this.getSessionFactory().openSession();
		Transaction tran=null;
		try {
			tran=session.beginTransaction();
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		
	}
}
