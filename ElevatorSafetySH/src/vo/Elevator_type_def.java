package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="elevator_type_def")
public class Elevator_type_def {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="elevator_type")
	private String elevatortype;
	private String name;
	@Column(name="[desc]")
	private String desc;

	public String getElevatortype() {
		return elevatortype;
	}
	public void setElevatortype(String elevatortype) {
		this.elevatortype = elevatortype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
