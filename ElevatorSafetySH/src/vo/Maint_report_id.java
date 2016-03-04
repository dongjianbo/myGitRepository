package vo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="maint_report_id")
public class Maint_report_id {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer maint_id;

	private Integer elevator_id;
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="elevator_id",insertable=false,updatable=false)
	private Elevator elevator;
	
	private Integer user1_id;
	@ManyToOne(cascade=CascadeType.REFRESH)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="user1_id",insertable=false,updatable=false)
	private Servicer servicer1;
	private Integer user2_id;
	@ManyToOne(cascade=CascadeType.REFRESH)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="user2_id",insertable=false,updatable=false)
	private Servicer servicer2;
	private Integer user3_id;
	@ManyToOne(cascade=CascadeType.REFRESH)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="user3_id",insertable=false,updatable=false)
	private Safer servicer3;
	private int maint_type;
	private Date maint_date;
	private Date maint_upload;
	@Column(name="[desc]")
	private String desc;
	
	public Elevator getElevator() {
		return elevator;
	}
	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}
	public Servicer getServicer1() {
		return servicer1;
	}
	public void setServicer1(Servicer servicer1) {
		this.servicer1 = servicer1;
	}
	public Servicer getServicer2() {
		return servicer2;
	}
	public void setServicer2(Servicer servicer2) {
		this.servicer2 = servicer2;
	}
	
	
	public Safer getServicer3() {
		return servicer3;
	}
	public void setServicer3(Safer servicer3) {
		this.servicer3 = servicer3;
	}
	public Integer getMaint_id() {
		return maint_id;
	}
	public void setMaint_id(Integer maint_id) {
		this.maint_id = maint_id;
	}
	public Integer getElevator_id() {
		return elevator_id;
	}
	public void setElevator_id(Integer elevator_id) {
		this.elevator_id = elevator_id;
	}
	public Integer getUser1_id() {
		return user1_id;
	}
	public void setUser1_id(Integer user1_id) {
		this.user1_id = user1_id;
	}
	public Integer getUser2_id() {
		return user2_id;
	}
	public void setUser2_id(Integer user2_id) {
		this.user2_id = user2_id;
	}
	public Integer getUser3_id() {
		return user3_id;
	}
	public void setUser3_id(Integer user3_id) {
		this.user3_id = user3_id;
	}
	public int getMaint_type() {
		return maint_type;
	}
	public void setMaint_type(int maint_type) {
		this.maint_type = maint_type;
	}
	public Date getMaint_date() {
		return maint_date;
	}
	public void setMaint_date(Date maint_date) {
		this.maint_date = maint_date;
	}
	public Date getMaint_upload() {
		return maint_upload;
	}
	public void setMaint_upload(Date maint_upload) {
		this.maint_upload = maint_upload;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
