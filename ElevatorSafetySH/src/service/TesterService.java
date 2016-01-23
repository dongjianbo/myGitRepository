package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Safer;
import vo.Tester;

import dao.TesterDao;

@Service
public class TesterService {
  @Resource
  public TesterDao testerDao;
  @SuppressWarnings("unchecked")
	public List<Tester> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Tester.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("licencecode", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return testerDao.findPageByDcQuery(dc, pageSize, request);
	}
  	@SuppressWarnings("unchecked")
	public List<Tester> listByTestId(int testid){
		DetachedCriteria dc=DetachedCriteria.forClass(Tester.class);
		dc.add(Restrictions.eq("idtest", testid));
		return testerDao.getListByDc(dc);
	}
	public Serializable insert(Tester tester){
		return testerDao.save(tester);
	}
	public Tester findById(int idtester){
		return testerDao.get(Tester.class, idtester);
	}
	public void update(Tester tester){
		testerDao.update(tester);
	}
	public void delete(Tester tester){
		testerDao.delete(tester);
	}
	@SuppressWarnings("unchecked")
	public List<Tester> getid_tester(){
		DetachedCriteria dc=DetachedCriteria.forClass(Tester.class);
		return testerDao.getListByDc(dc);
	}
	public void updateCard(String idMifare,int idservicer){
		Tester s=testerDao.get(Tester.class, idservicer);
		s.setIdMifare(idMifare);
		testerDao.update(s);
	}
}
