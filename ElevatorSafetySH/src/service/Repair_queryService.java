package service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.Repair_queryDao;
import vo.Repair_query;

@Service
public class Repair_queryService {
  @Resource
  public Repair_queryDao  repair_queryDao;
  
  
//房管部门维修管理列表lz
  @SuppressWarnings("unchecked")
	public List<Repair_query> list(int approve_ark,int pageSize,HttpServletRequest request){
		String sql="SELECT  t1.rid "
				+" FROM repair_query t1 left join elevator t2 on  t1.eid = t2.id_elevator  left join repair_approve t3 on t1.rid = t3.rid "
				+" left join repair_maint t4 on t1.rid = t4.rid left join approve_ack t5 on t5.id_approve=t3.approver_ack"
				+" WHERE 1=1 ";
		if(approve_ark!=-1){
			sql+=" and ifnull(t3.approver_ack,0) ="+approve_ark;
		}
		sql+=" order by t1.upload desc";
		List<Long> list=repair_queryDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Repair_query.class);
		dc.add(Restrictions.in("rid", list));
		dc.addOrder(Order.desc("upload"));
		return repair_queryDao.findPageByDcQuery(dc, pageSize, request);
	} 
	//房管部门维修管理数量
	public int listCount(int approve_ark){
		String sql="SELECT  count(t1.rid)"
				+" FROM repair_query t1,elevator t2,repair_approve t3,repair_maint t4,approve_ack t5"
				+" WHERE t1.eid = t2.id_elevator AND t1.rid = t3.rid AND t1.rid = t4.rid and t5.id_approve=t3.approver_ack";
		if(approve_ark!=-1){
			sql+=" and t3.approver_ack ="+approve_ark;
		}
		Object obj=repair_queryDao.getObjectBySQL(sql);
		if(obj!=null){
			return Integer.parseInt(obj.toString());
		}else{
			return 0;
		}
	}
	//房管部门信息id查询
	public Repair_query findById(int rid){
 		return repair_queryDao.get(Repair_query.class, rid);
 	}
	//房管部门维修使用单位列表lz
	@SuppressWarnings("unchecked")
	public List<Repair_query> list(int approve_ark, int pageSize, HttpServletRequest request, List<Integer> idList) {
		String sql="SELECT  t1.rid "
				+" FROM repair_query t1 left join elevator t2 on  t1.eid = t2.id_elevator  left join repair_approve t3 on t1.rid = t3.rid "
				+" left join repair_maint t4 on t1.rid = t4.rid left join approve_ack t5 on t5.id_approve=t3.approver_ack"
				+" WHERE 1=1 ";
		if(approve_ark!=-1){
			sql+=" and ifnull(t3.approver_ack,0) ="+approve_ark;
		}
		sql+=" order by t1.upload desc";
		List<Long> list=repair_queryDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Repair_query.class);
		if (!list.isEmpty()) {
			dc.add(Restrictions.in("rid", list));
		}
		if (!idList.isEmpty()) {
			dc.add(Restrictions.in("eid", idList));
		}else{
			dc.add(Restrictions.isNull("eid"));
		}
		dc.addOrder(Order.desc("upload"));
		return repair_queryDao.findPageByDcQuery(dc, pageSize, request);
	}
	public int listCount(int approve_ark, List<Integer> idList) {
		String sql="SELECT  count(t1.rid)"
				+" FROM repair_query t1 left join elevator t2 on  t1.eid = t2.id_elevator  left join repair_approve t3 on t1.rid = t3.rid "
				+" left join repair_maint t4 on t1.rid = t4.rid left join approve_ack t5 on t5.id_approve=t3.approver_ack"
				+" WHERE 1=1 ";
		if(approve_ark!=-1){
			sql+=" and t3.approver_ack ="+approve_ark;
		}
		if(!idList.isEmpty()){
			sql+=" and t2.id_elevator in (";
			for(int i=0;i<idList.size();i++){
				sql+=idList.get(i);
				if(i+1!=idList.size()){
					sql+=",";
				}
			}
			sql+=")";
		}
		Object obj=repair_queryDao.getObjectBySQL(sql);
		if(obj!=null){
			return Integer.parseInt(obj.toString());
		}else{
			return 0;
		}
	}
	
	
	
}

