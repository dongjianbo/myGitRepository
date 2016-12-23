package service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.Rescue_exerciseDao;
import vo.Rescue_exercise;

@Service
public class Rescue_exerciseService {
	@Resource
	public Rescue_exerciseDao reDao;
	@SuppressWarnings("unchecked")
	public List<Rescue_exercise> getList(int eid,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Rescue_exercise.class);
		dc.add(Restrictions.eq("eid", eid));
		List<Rescue_exercise> reList=reDao.findPageByDcQuery(dc, 10, request);
		return reList;
	}
}
