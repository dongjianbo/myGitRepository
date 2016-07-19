package vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Embeddable
@SuppressWarnings("serial")
public class Elevator_doorKey implements Serializable{
	private int elevator_id;
	private String door_id;
	
	
	public int getElevator_id() {
		return elevator_id;
	}
	public void setElevator_id(int elevator_id) {
		this.elevator_id = elevator_id;
	}
	public String getDoor_id() {
		return door_id;
	}
	public void setDoor_id(String door_id) {
		this.door_id = door_id;
	}
	
}
