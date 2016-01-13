package dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

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
	public List<Account> accountList(){
		SqlSessionFactory factory=BaseSessionFactory.getSqlSessionFactory();
		SqlSession session=factory.openSession();
		SqlSession session1=factory.openSession();
		int[] ids={1001,1002,1003};
		List<Integer> list=new ArrayList<Integer>();
		list.add(1001);
		list.add(1002);
		System.out.println("第一次查询");
 		List<Account> acclist=session.selectList("accountMapper.accountList",ids);
// 		session.close();
 		System.out.println("第二次查询");
 		
 		List<Account> acclist1=session.selectList("accountMapper.accountList",ids);
// 		session1.close();
		return acclist;
	}
	public String getNameById(int id){
		SqlSessionFactory factory=BaseSessionFactory.getSqlSessionFactory();
		SqlSession session=factory.openSession();
		String name=session.selectOne("accountMapper.getNameById",id);
		return name;
	}
	public static void main(String[] args) {
		AccountDao dao=new AccountDao();
		System.out.println(dao.getNameById(1001));
//		Account acc=new Account();
//		acc.setUname("abc");
//		acc.setPassword("123");
//		acc.setActive(1);
//		dao.insertAccount(acc);
//		acc.setId(1006);
//		dao.updateAccount(acc);
//		for(Account acc:dao.accountList()){
//			System.out.println(acc.getId()+":"+acc.getUname()+"\t"+acc.getPassword()+"\t"+acc.getActive());
//		}
	}
}
