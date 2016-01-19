package service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import dao.Maint_report_idDao;

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
}
