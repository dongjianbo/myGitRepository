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
	 * ���ݲ���Ա��Ų�ѯ���Ų���
	 * @param operatorid
	 * @return
	 */
	
	public DeptGroup getDeptGroupById(int operatorid){
		return dgDao.get(DeptGroup.class, operatorid);
	}
	/**
	 * ��ѯ�ò����������ŵ�λ�������ű��
	 */
	public ArrayList<Integer> getUserIds(int dgid){
		return dgDao.getUserIds(dgid);
	}
	/**
	 * ��ѯ�ò�����������λ
	 */
	public ArrayList<User> getUsers(int dgid){
		return dgDao.getUsers(dgid);
	}
}

