package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Service1;
import vo.User;
import dao.UserDao;

@Service
public class UserService {
	@Resource
    public UserDao userDao;
	@SuppressWarnings("unchecked")
	public List<User> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("username", key,MatchMode.ANYWHERE)));
			
		}
		return userDao.findPageByDcQuery(dc, pageSize, request);
	}
	@SuppressWarnings("unchecked")
	public List<User> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		return userDao.getListByDc(dc);
	}
	public Serializable insert(User user){
		return userDao.save(user);
	}
	public User findById(int iduser){
		return userDao.get(User.class, iduser);
	}
	public void update(User user){
		userDao.update(user);
	}
	public void delete(User user){
		userDao.delete(user);
	} 
}
