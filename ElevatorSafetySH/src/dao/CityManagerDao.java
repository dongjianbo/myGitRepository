package dao;

import java.util.List;


import org.springframework.stereotype.Repository;

@Repository
public class CityManagerDao extends BaseDao{
	@SuppressWarnings("unchecked")
	public List<Integer> getIds(){
		String sql="select userid from citymanager";
		return super.listBySQLQuery(sql);
	}
}
