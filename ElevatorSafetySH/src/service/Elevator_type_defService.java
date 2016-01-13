package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;





import vo.Elevator_type_def;
import vo.Servicer_type_def;
import dao.Elevator_type_defDao;
import dao.Servicer_type_defDao;

@Service
public class Elevator_type_defService {
  @Resource
  public Elevator_type_defDao elevator_type_defDao;
  @SuppressWarnings("unchecked")
	public List<Elevator_type_def> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Elevator_type_def.class);
		return elevator_type_defDao.getListByDc(dc);
	} 
}
