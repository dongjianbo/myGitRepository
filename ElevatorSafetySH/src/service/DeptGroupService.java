package service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.DeptGroupDao;
import vo.DeptGroup;
import vo.User;

@Service
public class DeptGroupService {
	@Resource
	public DeptGroupDao dgDao;
	/**
	 * 根据操作员编号查询集团部门
	 * @param operatorid
	 * @return
	 */
	
	public DeptGroup getDeptGroupById(int operatorid){
		return dgDao.get(DeptGroup.class, operatorid);
	}
	/**
	 * 查询该部门所属集团单位下属部门编号
	 */
	public ArrayList<Integer> getUserIds(int dgid){
		return dgDao.getUserIds(dgid);
	}
	/**
	 * 查询该部门下所属单位
	 */
	public ArrayList<User> getUsers(int dgid){
		return dgDao.getUsers(dgid);
	}
}

