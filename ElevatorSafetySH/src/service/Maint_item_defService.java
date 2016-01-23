package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.Maint_item_defDao;
import vo.Maint_item_def;

@Service
public class Maint_item_defService {
	@Resource
	public Maint_item_defDao midDao;
	@SuppressWarnings("unchecked")
	public List<Maint_item_def> list(int maintid){
		String sql="select maint_item_id from maint_detail where maint_id="+maintid;
		List<Integer> idList=midDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Maint_item_def.class);
		dc.add(Restrictions.in("maint_item_id", idList));
		return midDao.getListByDc(dc);
	}
}
