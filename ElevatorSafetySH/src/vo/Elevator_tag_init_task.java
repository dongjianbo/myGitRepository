package vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="elevator_tag_init_task")
public class Elevator_tag_init_task {
   @Id
   @Column(name="task_id")
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private int task_id;
   private String title;
   private int elevator_id;
   private String elevator_type;
   private String elevator_code;
   private String elevator_address;
   private int elevator_layer_number;
   private Date download;
   private Date write_tag;
   private String imei;
   private Integer user_id;
public int getTask_id() {
	return task_id;
}
public void setTask_id(int task_id) {
	this.task_id = task_id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public int getElevator_id() {
	return elevator_id;
}
public void setElevator_id(int elevator_id) {
	this.elevator_id = elevator_id;
}
public String getElevator_type() {
	return elevator_type;
}
public void setElevator_type(String elevator_type) {
	this.elevator_type = elevator_type;
}
public String getElevator_code() {
	return elevator_code;
}
public void setElevator_code(String elevator_code) {
	this.elevator_code = elevator_code;
}
public String getElevator_address() {
	return elevator_address;
}
public void setElevator_address(String elevator_address) {
	this.elevator_address = elevator_address;
}
public int getElevator_layer_number() {
	return elevator_layer_number;
}
public void setElevator_layer_number(int elevator_layer_number) {
	this.elevator_layer_number = elevator_layer_number;
}

public Date getDownload() {
	return download;
}
public void setDownload(Date download) {
	this.download = download;
}
public Date getWrite_tag() {
	return write_tag;
}
public void setWrite_tag(Date write_tag) {
	this.write_tag = write_tag;
}
public String getImei() {
	return imei;
}
public void setImei(String imei) {
	this.imei = imei;
}
public Integer getUser_id() {
	return user_id;
}
public void setUser_id(Integer user_id) {
	this.user_id = user_id;
}

   
   
   
}
