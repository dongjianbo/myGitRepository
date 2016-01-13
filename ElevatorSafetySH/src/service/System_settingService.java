package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import vo.Citylist;
import vo.System_setting;
import dao.System_settingDao;

@Service
public class System_settingService {
     @Resource
     public System_settingDao   system_settingDao;
     public List<System_setting> list(){
 		DetachedCriteria dc=DetachedCriteria.forClass(System_setting.class);
 		return system_settingDao.getListByDc(dc);
 	}
}
