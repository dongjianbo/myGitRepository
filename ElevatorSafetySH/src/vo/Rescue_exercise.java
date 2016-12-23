package vo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rescue_exercise")
public class Rescue_exercise {
	@Id
	@Column(name="rid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int rid;
	private int type;//0=应急维修 1=救援演习
	private int eid;
	//电梯
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="eid",insertable=false,updatable=false)
	private Elevator elevator;
	private int user1;
	//维保人员1
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="user1",insertable=false,updatable=false)
	private Servicer servicer1;
	private int user2;
	//维保人员2
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="user2",insertable=false,updatable=false)
	private Servicer servicer2;
	private int user3;
	//安全人员
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="user3",insertable=false,updatable=false)
	private Safer safer;
	private String arrived;
	private String work_begin;
	private String work_end;
	private String uploaded;
	private String note;
	private int image;
	private int image2;
	private String last_modified;
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public int getUser1() {
		return user1;
	}
	public void setUser1(int user1) {
		this.user1 = user1;
	}
	public int getUser2() {
		return user2;
	}
	public void setUser2(int user2) {
		this.user2 = user2;
	}
	public int getUser3() {
		return user3;
	}
	public void setUser3(int user3) {
		this.user3 = user3;
	}
	public String getArrived() {
		return arrived;
	}
	public void setArrived(String arrived) {
		this.arrived = arrived;
	}
	public String getWork_begin() {
		return work_begin;
	}
	public void setWork_begin(String work_begin) {
		this.work_begin = work_begin;
	}
	
	public String getWork_end() {
		return work_end;
	}
	public void setWork_end(String work_end) {
		this.work_end = work_end;
	}
	public String getUploaded() {
		return uploaded;
	}
	public void setUploaded(String uploaded) {
		this.uploaded = uploaded;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public int getImage2() {
		return image2;
	}
	public void setImage2(int image2) {
		this.image2 = image2;
	}
	public String getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}
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
	public Safer getSafer() {
		return safer;
	}
	public void setSafer(Safer safer) {
		this.safer = safer;
	}
	
}
