package service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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
				dc.add(Restrictions.in("maint_type", new Object[]{1,2,3,4}));
			}else{
				dc.add(Restrictions.eq("maint_type", maint_type));
			}
			
		}
		if(elevator_id!=0){
			dc.add(Restrictions.eq("elevator_id", elevator_id));
		}
		dc.addOrder(Order.desc("maint_date"));
		List<Maint_report_id> list= mriDao.findPageByDcQuery(dc, 10, request);
		for(Maint_report_id mri:list){
			//判断是否逾期
			if(mri.getMaint_type()==1){
				//如果是半月维保，最后一次（含半月维保，季度维保，半年维保，全年维保）与本次  时间超过15天
				//查询上一次维保时间
				String maint_upload=DateUtils.format(mri.getMaint_upload());
				String sql="select to_days('"+maint_upload+"')-to_days(max(maint_upload)) from maint_report_id "
						+ "where maint_upload<'"+maint_upload+"' "
								+ "and elevator_id="+mri.getElevator_id()+" "
										+ "and maint_type in (1,2,3,4)";
				Object obj=mriDao.getObjectBySQL(sql);
				if(obj!=null){
					Integer days=Integer.parseInt(obj.toString());
					if(days>15){
						mri.setOverdue(1);
					}else{
						mri.setOverdue(0);
					}
				}
			}
			if(mri.getMaint_type()==2){
				//判断季度维保逾期，最后一次（含季度维保，半年维保，全年维保）与本次时间超过90天
				String maint_upload=DateUtils.format(mri.getMaint_upload());
				String sql="select to_days('"+maint_upload+"')-to_days(max(maint_upload)) from maint_report_id "
						+ "where maint_upload<'"+maint_upload+"' "
								+ "and elevator_id="+mri.getElevator_id()+" "
										+ "and maint_type in (2,3,4)";
				Object obj=mriDao.getObjectBySQL(sql);
				if(obj!=null){
					Integer days=Integer.parseInt(obj.toString());
					if(days>90){
						mri.setOverdue(1);
					}else{
						mri.setOverdue(0);
					}
				}
			}
			if(mri.getMaint_type()==3){
				//判断半年维保逾期，最后一次（半年维保，全年维保）与本次时间超过180天
				String maint_upload=DateUtils.format(mri.getMaint_upload());
				String sql="select to_days('"+maint_upload+"')-to_days(max(maint_upload)) from maint_report_id "
						+ "where maint_upload<'"+maint_upload+"' "
								+ "and elevator_id="+mri.getElevator_id()+" "
										+ "and maint_type in (3,4)";
				Object obj=mriDao.getObjectBySQL(sql);
				if(obj!=null){
					Integer days=Integer.parseInt(obj.toString());
					if(days>180){
						mri.setOverdue(1);
					}else{
						mri.setOverdue(0);
					}
				}
			}
			if(mri.getMaint_type()==4){
				//判断全年维保逾期，上次全年维保与本次时间超过365天
				String maint_upload=DateUtils.format(mri.getMaint_upload());
				String sql="select to_days('"+maint_upload+"')-to_days(max(maint_upload)) from maint_report_id "
						+ "where maint_upload<'"+maint_upload+"' "
								+ "and elevator_id="+mri.getElevator_id()+" "
										+ "and maint_type=4";
				Object obj=mriDao.getObjectBySQL(sql);
				if(obj!=null){
					Integer days=Integer.parseInt(obj.toString());
					if(days>365){
						mri.setOverdue(1);
					}else{
						mri.setOverdue(0);
					}
				}
			}
		}
		return list;
	}
}
