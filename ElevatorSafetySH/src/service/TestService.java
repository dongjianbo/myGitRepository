package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Test;
import vo.User;
import dao.TestDao;

@Service
public class TestService {
	@Resource
    public TestDao testDao;
	@SuppressWarnings("unchecked")
	public List<Test> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Test.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return testDao.findPageByDcQuery(dc, pageSize, request);
	}
	public List<Test> selectId_test(){
		DetachedCriteria dc=DetachedCriteria.forClass(Test.class);
		return testDao.getListByDc(dc);
	}
	@SuppressWarnings("unchecked")
	public List<Test> list(){
		DetachedCriteria dc=DetachedCriteria.forClass(Test.class);
		return testDao.getListByDc(dc);
	}
	public Serializable insert(Test Test){
		return testDao.save(Test);
	}
	public Test findById(int idTest){
		return testDao.get(Test.class, idTest);
	}
	public void update(Test Test){
		testDao.update(Test);
	}
	public void delete(Test Test){
		testDao.delete(Test);
	}
}
