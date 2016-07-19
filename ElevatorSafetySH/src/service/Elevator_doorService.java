package service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dao.BaseDao;
import vo.Elevator_door;

@Service
public class Elevator_doorService {
	@Resource
	public BaseDao baseDao;
	public Serializable save(Elevator_door door){
		return baseDao.save(door);
	}
}
