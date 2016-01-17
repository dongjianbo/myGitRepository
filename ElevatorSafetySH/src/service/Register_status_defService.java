package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Register_status_def;

import dao.Register_status_defDao;

@Service
public class Register_status_defService {
	@Resource
	public Register_status_defDao register_status_defDao;
	@SuppressWarnings("unchecked")
	public List<Register_status_def> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Register_status_def.class);
		List<Register_status_def> list=register_status_defDao.getListByDc(dc);
		list.remove(0);
		return list;
	}
	public Serializable insert(Register_status_def register_status_def){
		return register_status_defDao.save(register_status_def);
	}
	public Register_status_def findById(int idRegister_status_def){
		return register_status_defDao.get(Register_status_def.class, idRegister_status_def);
	}
	public void update(Register_status_def register_status_def){
		register_status_defDao.update(register_status_def);
	}
	public void delete(Register_status_def register_status_def){
		register_status_defDao.delete(register_status_def);
	}

}
