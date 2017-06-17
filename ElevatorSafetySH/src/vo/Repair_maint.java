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
@Table(name = "repair_maint")
public class Repair_maint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rid")
	private int rid;
	private String note;
	private int user1;
	private int user2;
	private int user3;
	@Column(name = "repair_date")
	private String repairdate;
	private int image;
	@Column(name = "last_modified")
	private String lastmodified;
	
	
	
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="user1",insertable=false,updatable=false)
	private Servicer service1;//对应的维保人员对象
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="user2",insertable=false,updatable=false)
	private Servicer service2;//对应的维保人员对象
	
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="user3",insertable=false,updatable=false)
	private Safer safer;//对应的安全人员对象
	
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getRepairdate() {
		return repairdate;
	}
	public void setRepairdate(String repairdate) {
		this.repairdate = repairdate;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(String lastmodified) {
		this.lastmodified = lastmodified;
	}
	public int getUser3() {
		return user3;
	}
	public void setUser3(int user3) {
		this.user3 = user3;
	}
	public Servicer getService1() {
		return service1;
	}
	public void setService1(Servicer service1) {
		this.service1 = service1;
	}
	public Servicer getService2() {
		return service2;
	}
	public void setService2(Servicer service2) {
		this.service2 = service2;
	}
	public Safer getSafer() {
		return safer;
	}
	public void setSafer(Safer safer) {
		this.safer = safer;
	}
	
}
