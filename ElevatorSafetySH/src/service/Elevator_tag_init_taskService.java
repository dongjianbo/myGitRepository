package service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import vo.Elevator_tag_init_task;

import dao.Elevator_tag_init_taskDao;

@Service
public class Elevator_tag_init_taskService {
   @Resource
   public Elevator_tag_init_taskDao elevator_tag_init_taskDao;
   public Serializable insert(Elevator_tag_init_task e){
	  return elevator_tag_init_taskDao.save(e);
   }
}
