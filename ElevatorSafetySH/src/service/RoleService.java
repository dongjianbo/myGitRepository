package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.FetchMode;
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
		DetachedCriteria dc=DetachedCriteria.forClass(Role.class);
		dc.setFetchMode("menus", FetchMode.JOIN);
		dc.add(Restrictions.eq("idrole", idrole));
		return (Role)roleDao.getListByDc(dc).get(0);
	}
	public void update(Role role){
		roleDao.update(role);
	}
	public void delete(Role role){
		roleDao.delete(role);
	}
	@SuppressWarnings("rawtypes")
	public List getMenus(int roleid){
		String sql="select m.name_item,m.url from system_menu m left join role_menu rm "
				+ "on m.id_system_menu=rm.id_menu left join role r on r.id_role=rm.id_role "
				+ "where r.id_role="+roleid+" order by m.id_system_menu asc";
		return roleDao.listBySQLQuery(sql);
	}
}
