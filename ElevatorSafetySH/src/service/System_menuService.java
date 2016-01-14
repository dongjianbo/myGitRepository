package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import dao.System_menuDao;
import vo.System_menu;

@Service
public class System_menuService {
     @Resource
     public System_menuDao  system_menuDao;
     @SuppressWarnings("unchecked")
	public List<System_menu> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(System_menu.class);
		return system_menuDao.getListByDc(dc);
	}
}
