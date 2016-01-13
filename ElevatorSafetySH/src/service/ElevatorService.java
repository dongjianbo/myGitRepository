package service;
import java.io.Serializable;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import dao.ElevatorDao;
import vo.Elevator;



@Service
public class ElevatorService {
	@Resource
	public ElevatorDao elevatorDao;
	public Serializable insert(Elevator elevator){
		return elevatorDao.save(elevator);
	}

}
