package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Role;
import vo.Service1;
import dao.RoleDao;

@Service
public class RoleService {
  @Resource
  public RoleDao roleDao;
	@SuppressWarnings("unchecked")
	public List<Role> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Role.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return roleDao.findPageByDcQuery(dc, pageSize, request);
	}
	@SuppressWarnings("unchecked")
	public List<Role> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Role.class);
		return roleDao.getListByDc(dc);
	}
	public Serializable insert(Role role){
		return roleDao.save(role);
	}
	public Role findById(int idrole){
		return roleDao.get(Role.class, idrole);
	}
	public void update(Role role){
		roleDao.update(role);
	}
	public void delete(Role role){
		roleDao.delete(role);
	}
}
