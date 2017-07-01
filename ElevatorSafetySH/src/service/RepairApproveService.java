package service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.RepairApproveDao;
import vo.Repair_approve;

@Service
public class RepairApproveService {
  @Resource
  public RepairApproveDao  repairApproveDao;

  public Serializable insert(Repair_approve repair_approve){
		return repairApproveDao.save(repair_approve);
	}
  public void update(Repair_approve repair_approve){
		repairApproveDao.update(repair_approve);
	}
}

