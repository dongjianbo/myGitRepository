package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.DesignerDao;
import dao.Elevator_stateDao;
import vo.Designer;
import vo.Elevator_state;

@Service
public class Elevator_stateService {
	@Resource
	public Elevator_stateDao elevator_stateDao;
	@SuppressWarnings("unchecked")
	public List<Elevator_state> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator_state.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return elevator_stateDao.findPageByDcQuery(dc, pageSize, request);
	}
	public Serializable insert(Elevator_state elevator_state){
		return elevator_stateDao.save(elevator_state);
	}
	public Elevator_state findById(int idelevator){
		return elevator_stateDao.get(Elevator_state.class, idelevator);
	}
	public void update(Elevator_state elevator_state){
		elevator_stateDao.update(elevator_state);
	}
	public void delete(Elevator_state elevator_state){
		elevator_stateDao.delete(elevator_state);
	}
}
