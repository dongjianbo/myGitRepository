package service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import dao.RepairImageDao;
import vo.RepairImage;
@Service
public class RepairImageService {
	@Resource
	public RepairImageDao repairImageDao;
	@SuppressWarnings("unchecked")
	public List<RepairImage> getImageList(int rid) {
		DetachedCriteria dc=DetachedCriteria.forClass(RepairImage.class);
		dc.add(Restrictions.eq("key.rid",rid));
		return repairImageDao.getListByDc(dc);
	}
}
