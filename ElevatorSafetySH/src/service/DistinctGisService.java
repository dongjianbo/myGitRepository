package service;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.DistinctGisDao;
import vo.DistinctGis;
import vo.Elevator;

@Service
public class DistinctGisService {
  @Resource
  public DistinctGisDao  distinctGisDao;
	

	public DistinctGis selectByDisId(String id_subdistrict) {
		String sql="select id,id_distinct,level,gis_x,gis_y from distinct_gis where id_distinct='"+id_subdistrict+"'";
		Object obj=distinctGisDao.getObjectBySQL(sql);
		//如果该区域还没有录入地图数据，默认漯河市区
		if(obj==null){
			sql="select id,id_distinct,level,gis_x,gis_y from distinct_gis where id_distinct='00'";
			obj=distinctGisDao.getObjectBySQL(sql);
		}
		 Object[] objClass = (Object[])obj;
			  DistinctGis dis = new DistinctGis();//需要放入的实体类
		     dis.setId((Integer)objClass[0]);
		     dis.setId_distinct(objClass[1].toString());
		     dis.setLevel((Integer)objClass[2]);
		     dis.setGis_x((Double)objClass[3]);
		     dis.setGis_y((Double)objClass[4]);
		return dis;
	}

/**
 * 根据id_distinct获取坐标信息
 * @param id_distinct
 * @return
 */
	public DistinctGis getByDId(String id_distinct) {
		DetachedCriteria dc=DetachedCriteria.forClass(DistinctGis.class);
		dc.add(Restrictions.eq("id_distinct", id_distinct));
		return (DistinctGis)distinctGisDao.getListByDc(dc).get(0);
	}

	public void update(DistinctGis e) {
		distinctGisDao.update(e);
	}
}

