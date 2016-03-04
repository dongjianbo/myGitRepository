package service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.Maint_report_idDao;
import util.DateUtils;
import vo.Maint_report_id;

@Service
public class Maint_report_idService {
	@Resource
	public Maint_report_idDao mriDao;
	public String getTypeNameById(int type){
		String sql="select mtd.desc from maint_type_def mtd where mtd.maint_type="+type;
		Object obj=mriDao.getObjectBySQL(sql);
		if(obj!=null){
			return obj.toString();
		}
		return "";
	}
	@SuppressWarnings({ "unchecked"})
	public List<Maint_report_id> listByTaskType(int maint_type,int elevator_id,HttpServletRequest request){
		
		DetachedCriteria dc=DetachedCriteria.forClass(Maint_report_id.class);
		if(maint_type!=-1){
			if(maint_type==123){
				dc.add(Restrictions.in("maint_type", new Object[]{1,2,3}));
			}else{
				dc.add(Restrictions.eq("maint_type", maint_type));
			}
			
		}
		if(elevator_id!=0){
			dc.add(Restrictions.eq("elevator_id", elevator_id));
		}
		return mriDao.findPageByDcQuery(dc, 10, request);
	}
}
