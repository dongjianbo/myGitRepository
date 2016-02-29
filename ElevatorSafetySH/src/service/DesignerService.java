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

import vo.Designer;

@Service
public class DesignerService {
	@Resource
	public DesignerDao designerDao;
	@SuppressWarnings("unchecked")
	public List<Designer> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Designer.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return designerDao.findPageByDcQuery(dc, pageSize, request);
	}
    @SuppressWarnings("unchecked")
	public List<Designer> getId_designer(){
    	DetachedCriteria dc=DetachedCriteria.forClass(Designer.class);
    	return designerDao.getListByDc(dc);
		
	}
	public Serializable insert(Designer designer){
		return designerDao.save(designer);
	}
	public Designer findById(int iddesigner){
		return designerDao.get(Designer.class, iddesigner);
	}
	public void update(Designer designer){
		designerDao.update(designer);
	}
	public void delete(Designer designer){
		designerDao.delete(designer);
	}
	@SuppressWarnings("unchecked")
	public List<Object[]> listDesigner(){
		String sql="select id_designer,name from designer";
		return designerDao.listBySQLQuery(sql);
	}
}
