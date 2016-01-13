package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import vo.Installer;

import dao.InstallerDao;

@Service
public class InstallerService {
	@Resource
   public InstallerDao nstallerdao;
	@SuppressWarnings("unchecked")
	public List<Installer> list(String key,int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Installer.class);
		if(key!=null&&!"".equals(key.trim())){
			dc.add(Restrictions.or(Restrictions.like("code", key,MatchMode.ANYWHERE),
					Restrictions.like("name", key,MatchMode.ANYWHERE)));
			
		}
		return nstallerdao.findPageByDcQuery(dc, pageSize, request);
	}
	public List<Installer> selectId_installer(){
		DetachedCriteria dc=DetachedCriteria.forClass(Installer.class);
		return nstallerdao.getListByDc(dc);
	}
	public Serializable insert(Installer Installer){
		return nstallerdao.save(Installer);
	}
	public Installer findById(int idInstaller){
		return nstallerdao.get(Installer.class, idInstaller);
	}
	public void update(Installer Installer){
		nstallerdao.update(Installer);
	}
	public void delete(Installer Installer){
		nstallerdao.delete(Installer);
	}
}
