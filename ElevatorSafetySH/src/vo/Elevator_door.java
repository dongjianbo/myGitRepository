package vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="elevator_door")
public class Elevator_door {
	@Id
	private Elevator_doorKey key;
	private String note;
	private Date last_modified;
	
	public Elevator_doorKey getKey() {
		return key;
	}
	public void setKey(Elevator_doorKey key) {
		this.key = key;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}
	
}
