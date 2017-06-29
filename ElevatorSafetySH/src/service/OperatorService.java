package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import util.MD5;
import vo.Approve_ack;
import vo.Elevator;
import vo.Operator;
import vo.RepairImage;
import dao.CitylistDao;
import dao.OperatorDao;

@Service
public class OperatorService {
   @Resource
   public OperatorDao operatorDao;
   @Resource
   public CitylistService cityService;
   @Resource
   public DistictlistService distictService;
   @Resource
   public SubdistictlistService subdistictService;
   @SuppressWarnings("unchecked")
 	public List<Operator> list(String key,int pageSize,HttpServletRequest request){
 		DetachedCriteria dc=DetachedCriteria.forClass(Operator.class);
 		if(key!=null&&!"".equals(key.trim())){
 			dc.add(Restrictions.or(Restrictions.like("idcard", key,MatchMode.ANYWHERE),
 					Restrictions.like("name", key,MatchMode.ANYWHERE)));
 			
 		}
 		List<Operator> olist=operatorDao.findPageByDcQuery(dc, pageSize, request);
 		//��ѯ����Ա������
 		for(Operator o:olist){
 			o.setCity(cityService.listBy_Idcity(o.getIdcity()));
 			if(o.getIddistrict()!=null&&!"".equals(o.getIddistrict())){
 				o.setDistict(distictService.getDistictById(o.getIdcity(), o.getIddistrict()));
 			}
 			if(o.getIdsubdistrict()!=null&&!"".equals(o.getIdsubdistrict())){
 				o.setSubdistict(subdistictService.getSubdistictById(o.getIdcity(), o.getIddistrict(), o.getIdsubdistrict()));
 			}
 		}
 		return olist;
 	}
 	public Serializable insert(Operator operator){
 		return operatorDao.save(operator);
 	}
 	public Operator findById(int idoperator){
 		return operatorDao.get(Operator.class, idoperator);
 	}
 	public void update(Operator operator){
 		operatorDao.update(operator);
 	}
 	public void updateStatus(int idoperator){
 		String sql="update operator set status='0' where id_operator="+idoperator;
 		Session session=operatorDao.getSessionFactory().openSession();
 		Transaction  tran=session.beginTransaction();
 		SQLQuery sqlquery=session.createSQLQuery(sql);
 		sqlquery.executeUpdate();
 		tran.commit();
 		session.close();
 		}
 	public void updateStatus1(int idoperator){
 		String sql="update operator set status='1' where id_operator="+idoperator;
 		Session session=operatorDao.getSessionFactory().openSession();
 		Transaction  tran=session.beginTransaction();
 		SQLQuery sqlquery=session.createSQLQuery(sql);
 		sqlquery.executeUpdate();
 		tran.commit();
 		session.close();
 	}
 	public int updatePassword(int idoperator,String password){
 		String sql="update operator set password='"+password+"' where id_operator="+idoperator;
 		Session session=operatorDao.getSessionFactory().openSession();
 		Transaction  tran=session.beginTransaction();
 		SQLQuery sqlquery=session.createSQLQuery(sql);
 		int i=sqlquery.executeUpdate();
 		tran.commit();
 		session.close();
 		return i;
 	}
 	//�޸Ľ�ɫ
 	public void updateRole(Operator operator){
 		String sql="update operator set id_role='"+operator.getIdprivilege()+"' where id_operator="+operator.getIdoperator();
 		Session session=operatorDao.getSessionFactory().openSession();
 		Transaction  tran=session.beginTransaction();
 		SQLQuery sqlquery=session.createSQLQuery(sql);
 		sqlquery.executeUpdate();
 		tran.commit();
 		session.close();
 	}
 	public void delete(Operator operator){
 		operatorDao.delete(operator);
 	}
 	public void delete(String sql){
 		operatorDao.delete(sql);
 	}
 	@SuppressWarnings("unchecked")
	public int check(Operator operator){
 		//�Ƚ���½����������ٺ����ݿ��еıȶ�
 		MD5 md5=new MD5();
 		String code=md5.getMD5ofStr(operator.getPassword());//���������
 		DetachedCriteria dc=DetachedCriteria.forClass(Operator.class);
 		dc.add(Restrictions.eq("loginname", operator.getLoginname().trim()));
 		List<Operator> operlist=operatorDao.getListByDc(dc);//���ݿ��е�����
 		if(operlist==null||operlist.size()==0){
 			return 1;//��ʾ�û���������
 		}else{
 			System.out.println(operator.getLoginname());
 			if(!code.equals(operlist.get(0).getPassword())){
 				System.out.println(code);
 					return 2;//��ʾ���벻��ȷ
 			}else{
 				if(operlist.get(0).getStatus().equals("0")){
 					//����
 					return 4;
 				}else{
 					return 3;//�û�״̬����
 				}
 				
 			}
 		}
 	}
 	@SuppressWarnings("unchecked")
	public Operator getOper(Operator operator){
 		DetachedCriteria dc=DetachedCriteria.forClass(Operator.class);
 		dc.add(Restrictions.eq("loginname", operator.getLoginname().trim()));
 		List<Operator> operlist=operatorDao.getListByDc(dc);
 		Operator o=operlist.get(0);
 		o.setCity(cityService.listBy_Idcity(o.getIdcity()));
 		o.setDistict(distictService.getDistictById(o.getIdcity(), o.getIddistrict()));
 		o.setSubdistict(subdistictService.getSubdistictById(o.getIdcity(), o.getIddistrict(), o.getIdsubdistrict()));
 		return o;
 	}
 	@SuppressWarnings("rawtypes")
	public List listOperatorByDc(DetachedCriteria dc){
 		return operatorDao.getListByDc(dc);
 	}
	public Operator selectById(int approver) {
		//DetachedCriteria dc=DetachedCriteria.forClass(Operator.class);
		//dc.add(Restrictions.eq("idoperator", approver));
		System.out.println(approver);
		return operatorDao.get(Operator.class, approver);
		//return (Operator)operatorDao.getListByDc(dc).get(0);
	}
	
}
