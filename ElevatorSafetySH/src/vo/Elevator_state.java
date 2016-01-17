package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="elevator_state")
public class Elevator_state {
	@Id
	@Column(name="id_elevator")
	private int idelevator;
	private String last_15_service;
	private String last_90_service;
	private String last_180_service;
	private String last_360_service;
	@Column(name="last_rounds")
	private String lastrounds;
	@Column(name="last_test")
	private String lasttest;
	@Column(name="label_write")
	private String labelwrite;
	@Column(name="label_demo")
	private String labeldemo;
	@Column(name="last_modified")
	private String lastmodified;
	
	
	public int getIdelevator() {
		return idelevator;
	}
	public void setIdelevator(int idelevator) {
		this.idelevator = idelevator;
	}
	public String getLast_15_service() {
		return last_15_service;
	}
	public void setLast_15_service(String last_15_service) {
		this.last_15_service = last_15_service;
	}
	public String getLast_90_service() {
		return last_90_service;
	}
	public void setLast_90_service(String last_90_service) {
		this.last_90_service = last_90_service;
	}
	public String getLast_180_service() {
		return last_180_service;
	}
	public void setLast_180_service(String last_180_service) {
		this.last_180_service = last_180_service;
	}
	public String getLast_360_service() {
		return last_360_service;
	}
	public void setLast_360_service(String last_360_service) {
		this.last_360_service = last_360_service;
	}
	public String getLastrounds() {
		return lastrounds;
	}
	public void setLastrounds(String lastrounds) {
		this.lastrounds = lastrounds;
	}
	public String getLasttest() {
		return lasttest;
	}
	public void setLasttest(String lasttest) {
		this.lasttest = lasttest;
	}
	public String getLabelwrite() {
		return labelwrite;
	}
	public void setLabelwrite(String labelwrite) {
		this.labelwrite = labelwrite;
	}
	public String getLabeldemo() {
		return labeldemo;
	}
	public void setLabeldemo(String labeldemo) {
		this.labeldemo = labeldemo;
	}
	public String getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(String lastmodified) {
		this.lastmodified = lastmodified;
	}
	
	
}
