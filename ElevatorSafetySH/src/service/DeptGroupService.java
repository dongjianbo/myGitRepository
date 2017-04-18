package service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.DeptGroupDao;
import vo.DeptGroup;

@Service
public class DeptGroupService {
	@Resource
	public DeptGroupDao dgDao;
	public DeptGroup getDeptGroupByOperatorId(int operatorid){
		return dgDao.getDGByOperatorid(operatorid);
	}
}
