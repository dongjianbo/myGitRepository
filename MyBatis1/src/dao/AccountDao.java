package dao;

import org.apache.ibatis.session.SqlSession;

import util.BaseSessionFactory;
import vo.Account;

public class AccountDao {
	public void insertAccount(Account account){
		SqlSession session=BaseSessionFactory.getSqlSession();
		session.insert("accountMapper.insertAccount", account);
		session.commit();
	}
	public void updateAccount(Account account){
		SqlSession session=BaseSessionFactory.getSqlSession();
		session.update("accountMapper.updateAccount", account);
		session.commit();
	}
	public static void main(String[] args) {
		AccountDao dao=new AccountDao();
		Account acc=new Account();
		acc.setUname("abc");
		acc.setPassword("123");
		acc.setActive(1);
		dao.insertAccount(acc);
		acc.setId(1006);
		dao.updateAccount(acc);
	}
}
