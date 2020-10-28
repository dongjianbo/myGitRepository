package dao;

import java.util.List;

import table.Account;
import utils.Pagenation;

public interface AccountDao {
	public int login(Account account);
	public Account getAccount(String uname,String pwd);
	public int save(Account account);
	public List<Account> accList(String key,Pagenation p);
	public int changeState(int id,int state);
	public int changePassword(int id,String password,String newpassword);
	public boolean exists(String uname);
}
