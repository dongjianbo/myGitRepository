package service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.DistinctGisDao;
import vo.DistinctGis;

@Service
public class DistinctGisService {
  @Resource
  public DistinctGisDao  distinctGisDao;
	

	public DistinctGis selectByDisId(String id_subdistrict) {
		String sql="select id,id_distinct,level,gis_x,gis_y from distinct_gis where id_distinct='"+id_subdistrict+"'";
		Object obj=distinctGisDao.getObjectBySQL(sql);
		//���������û��¼���ͼ���ݣ�Ĭ���������
		if(obj==null){
			sql="select id,id_distinct,level,gis_x,gis_y from distinct_gis where id_distinct='00'";
			obj=distinctGisDao.getObjectBySQL(sql);
		}
		 Object[] objClass = (Object[])obj;
			  DistinctGis dis = new DistinctGis();//��Ҫ�����ʵ����
		     dis.setId((Integer)objClass[0]);
		     dis.setId_distinct(objClass[1].toString());
		     dis.setLevel((Integer)objClass[2]);
		     dis.setGis_x((Double)objClass[3]);
		     dis.setGis_y((Double)objClass[4]);
		return dis;
	}
}

