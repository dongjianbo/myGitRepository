package vo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "repair_query")
public class Repair_query {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rid")
	private int rid;
	private int eid;
	@Column(name = "user_safer")
	private String usersafer;
	private String note;
	private int image;
	@Column(name = "last_modified")
	private String lastmodified;
	private String upload;
	
	
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="eid",insertable=false,updatable=false)
	private Elevator elevator;//对应的电梯对象
	
	/*@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_approve",insertable=false,updatable=false)
	private Approve_ack approveack;//
*/	//配置一对一关系  
	@OneToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="rid",insertable=false,updatable=false)
    private Repair_approve repairapprove ;

	//配置一对一关系  
	@OneToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="rid",insertable=false,updatable=false)
    private Repair_maint repairmaint ;

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getUsersafer() {
		return usersafer;
	}

	public void setUsersafer(String usersafer) {
		this.usersafer = usersafer;
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

	public String getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(String lastmodified) {
		this.lastmodified = lastmodified;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}

	public Repair_approve getRepairapprove() {
		return repairapprove;
	}

	public void setRepairapprove(Repair_approve repairapprove) {
		this.repairapprove = repairapprove;
	}

	public Repair_maint getRepairmaint() {
		return repairmaint;
	}

	public void setRepairmaint(Repair_maint repairmaint) {
		this.repairmaint = repairmaint;
	}

	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}

	/*public Approve_ack getApproveack() {
		return approveack;
	}

	public void setApproveack(Approve_ack approveack) {
		this.approveack = approveack;
	}
*/
	
}
