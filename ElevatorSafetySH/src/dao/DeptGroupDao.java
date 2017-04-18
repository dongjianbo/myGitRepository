package dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import vo.DeptGroup;
@Repository
public class DeptGroupDao extends BaseDao{
	@SuppressWarnings("rawtypes")
	public DeptGroup getDGByOperatorid(int operatorid){
		Session session=this.getSessionFactory().openSession();
		SQLQuery sqlquery=session.createSQLQuery("select dg.* from deptgroup dg where dg.id=(select gid from dept_group_user where uid=?)");
		sqlquery.setInteger(1, operatorid);
		sqlquery.addEntity("dg", DeptGroup.class);
		List list=sqlquery.list();
		if(list!=null&&!list.isEmpty()){
			DeptGroup dg=(DeptGroup)list.get(0);
			return dg;
		}else{
			return null;
		}
	}
}
