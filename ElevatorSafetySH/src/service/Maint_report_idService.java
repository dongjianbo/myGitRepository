package service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.HistoryDao;
import dao.History_listDao;
import dao.Maint_report_idDao;
import util.DateUtils;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Maint_detail;
import vo.Maint_report_id;

@Service
public class Maint_report_idService {
	@Resource
	public Maint_report_idDao mriDao;
	@Resource
	public HistoryDao hDao;
	@Resource
	public History_listDao hlDao;
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
			//�ж��Ƿ�����
			if(mri.getMaint_type()==1){
				//����ǰ���ά�������һ�Σ�������ά��������ά��������ά����ȫ��ά�����뱾��  ʱ�䳬��15��
				//��ѯ��һ��ά��ʱ��
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
				//�жϼ���ά�����ڣ����һ�Σ�������ά��������ά����ȫ��ά�����뱾��ʱ�䳬��90��
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
				//�жϰ���ά�����ڣ����һ�Σ�����ά����ȫ��ά�����뱾��ʱ�䳬��180��
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
				//�ж�ȫ��ά�����ڣ��ϴ�ȫ��ά���뱾��ʱ�䳬��365��
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
	/**
	 * ��ѯ���ϸ�ά����¼��ϸ
	 */
	@SuppressWarnings("unchecked")
	public List<Maint_detail> getListMaintDefailNO(int pageSize,HttpServletRequest request){
		DetachedCriteria dc=DetachedCriteria.forClass(Maint_detail.class);
		dc.setFetchMode("mid", FetchMode.JOIN);
		dc.setFetchMode("mri", FetchMode.JOIN);
		dc.createAlias("mri", "mri");
		dc.setFetchMode("mri.elevator", FetchMode.JOIN);
		dc.add(Restrictions.eq("maint_result", -1));
		return mriDao.findPageByDcQuery(dc, pageSize,request);
		
	}
	/**
	 * �����ϸ�ά����¼
	 */
	@SuppressWarnings("deprecation")
	public void updateMaint_Note(Maint_detail md,int id_operator){
		Session session=mriDao.getSessionFactory().openSession();
		Transaction tran=session.beginTransaction();
		try {
			String sql="update maint_detail set maint_result=-2,maint_note=concat(maint_note,'�����˵����"+md.getMaint_note()+"')"
					+ " where maint_id="+md.getMaint_id()+" and maint_item_id="+md.getMaint_item_id();
			session.createSQLQuery(sql).executeUpdate();
			//����history
			History h=new History();
			h.setOperator(id_operator);
			h.setType(41);
			h.setDatetime(new Date().toLocaleString());
			Integer hid=(Integer)session.save(h);
			//����history_list
			History_listKey key1=new History_listKey();
			History_listKey key2=new History_listKey();
			key1.setIdhistory(hid);
			key1.setKey(25);
			key2.setIdhistory(hid);
			key2.setKey(26);
			History_list hl1=new History_list();
			History_list hl2=new History_list();
			hl1.setKey(key1);
			hl1.setValue(md.getMaint_id()+"");
			hl2.setKey(key2);
			hl2.setValue(md.getMaint_item_id()+"");
			session.save(hl1);
			session.save(hl2);
			tran.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
	}
}
