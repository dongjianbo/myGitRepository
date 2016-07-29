package vo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="notice")
public class Notice {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_notice;
	private int id_elevator;
	private int maint_type;
	private String last_service;
	private String date_notice;
	private int id_service;
	private int id_user;
	private String this_service;
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_elevator",insertable=false,updatable=false)
	private Elevator elevator;//对应的电梯对象
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="maint_type",insertable=false,updatable=false)
	private Maint_type_def maintType;//对应的维保类型
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_service",insertable=false,updatable=false)
	private Service1 service;//对应的维保单位
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_user",insertable=false,updatable=false)
	private User user;//对应的使用单位
	
	public int getId_notice() {
		return id_notice;
	}
	public void setId_notice(int id_notice) {
		this.id_notice = id_notice;
	}
	public int getId_elevator() {
		return id_elevator;
	}
	public void setId_elevator(int id_elevator) {
		this.id_elevator = id_elevator;
	}
	public int getMaint_type() {
		return maint_type;
	}
	public void setMaint_type(int maint_type) {
		this.maint_type = maint_type;
	}
	
	public int getId_service() {
		return id_service;
	}
	public void setId_service(int id_service) {
		this.id_service = id_service;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	
	public Elevator getElevator() {
		return elevator;
	}
	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}
	public Maint_type_def getMaintType() {
		return maintType;
	}
	public void setMaintType(Maint_type_def maintType) {
		this.maintType = maintType;
	}
	public Service1 getService() {
		return service;
	}
	public void setService(Service1 service) {
		this.service = service;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLast_service() {
		return last_service;
	}
	public void setLast_service(String last_service) {
		this.last_service = last_service;
	}
	public String getDate_notice() {
		return date_notice;
	}
	public void setDate_notice(String date_notice) {
		this.date_notice = date_notice;
	}
	public String getThis_service() {
		return this_service;
	}
	public void setThis_service(String this_service) {
		this.this_service = this_service;
	}
	
}
