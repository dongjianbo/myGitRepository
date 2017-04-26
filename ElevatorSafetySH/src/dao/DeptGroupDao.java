package dao;


import java.util.ArrayList;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import vo.User;

@Repository
public class DeptGroupDao extends BaseDao{
	@SuppressWarnings("unchecked")
	public ArrayList<Integer> getUserIds(int dgid){
		String sql="select uid from dept_group_user where gid="+dgid;
		return (ArrayList<Integer>)super.getListBySQL(sql);
	}
	@SuppressWarnings("unchecked")
	public ArrayList<User> getUsers(int dgid){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.in("iduser", getUserIds(dgid)));
		return (ArrayList<User>)super.getListByDc(dc);
	} 
	
}
