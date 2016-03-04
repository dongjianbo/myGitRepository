package dao;


import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class OperatorDao extends BaseDao{
	public void delete(String sql){
		Session session=getHibernateTemplate().getSessionFactory().openSession();
		Transaction tran=session.beginTransaction();
		Connection con=session.connection();
		try {
			System.out.println(sql);
			con.createStatement().executeUpdate(sql);
			tran.commit();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tran.rollback();
		}finally {
			session.close();
		}
	}
}
