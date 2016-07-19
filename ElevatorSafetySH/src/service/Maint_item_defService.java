package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.Maint_item_defDao;
import vo.Maint_item_def;

@Service
public class Maint_item_defService {
	@Resource
	public Maint_item_defDao midDao;
	/**
	 * 查询该维保记录下的检查项
	 * @param maintid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Maint_item_def> list(int maintid,int maint_type){
		String sql="select maint_item_id from maint_detail where maint_id="+maintid;
		List<Integer> idList=midDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Maint_item_def.class);
		dc.add(Restrictions.in("maint_item_id", idList));
		//后来的需求改动，不需要维保类型的条件
		/*
		 * dc.add(Restrictions.eq("mType.maint_type", maint_type));
		 */
		
		return midDao.getListByDc(dc);
	}
	/**
	 * 查询该记录下的层门明细编号
	 * @param maintid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Maint_item_def> listDoor(int maintid){
		String sql="select maint_item_id from maint_door where maint_id="+maintid;
		List<Integer> idList=midDao.getListBySQL(sql);
		DetachedCriteria dc=DetachedCriteria.forClass(Maint_item_def.class);
		dc.add(Restrictions.in("maint_item_id", idList));
		List<Maint_item_def> list=midDao.getListByDc(dc);
		for(Maint_item_def mid:list){
			Object obj=midDao.getObjectBySQL("select door_id from maint_door where maint_id="+maintid+" and maint_item_id="+mid.getMaint_item_id()+";");
			if(obj!=null){
				int doorid=Integer.parseInt(obj.toString());
				mid.setDoorid(doorid);
			}
			
		}
		return list;
	}
	/**
	 * 维保记录详细检查结果
	 * @param maint_id
	 * @param maint_item_id
	 * @return
	 */
	public Object getDetail(int maint_id,int maint_item_id){
		String sql="select maint_note,image_val,maint_result from maint_detail where maint_id="+maint_id+" and maint_item_id="+maint_item_id;
		return midDao.getObjectBySQL(sql);
	}
	/**
	 * 维保记录层门详细检查结果
	 * @param maint_id
	 * @param maint_item_id
	 * @return
	 */
	public Object getDoorDetail(int maint_id,int maint_item_id){
		String sql="select maint_note,image_val,maint_result from maint_door where maint_id="+maint_id+" and maint_item_id="+maint_item_id;
		return midDao.getObjectBySQL(sql);
	}
	public void getImage(int maint_id,int maint_item_id,int image_val,HttpServletResponse response){
		String sql="select image_data from maint_image where maint_id="+maint_id+" and maint_item_id="+maint_item_id+" and image_val="+image_val;
		System.out.println(sql);
		Connection con=midDao.getSessionFactory().openSession().connection();
		try {
			ResultSet res=con.createStatement().executeQuery(sql);
			
			if(res.next()){
				InputStream input= res.getBinaryStream(1);
				if(input!=null){
					response.setContentType("image/jpeg");
					
					try {
						response.setContentLength(input.available());
						OutputStream output=response.getOutputStream();
						byte[] bs=new byte[8192];
						while(true){
							int i=input.read(bs, 0, bs.length);
							if(i==-1){
								break;
							}
							output.write(bs,0,i);
						}
						output.write(65);
						output.flush();
						output.close();
						input.close();
						res.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}else{
				try {
					response.getWriter().println("error!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
