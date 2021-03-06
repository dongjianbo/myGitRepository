package vo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "repair_approve")
public class Repair_approve {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rid")
	private int rid;
	@Column(name = "approver_ack")
	private Integer approveack;
	private Integer approver;
	@Transient
	private String approver_name;
	@Column(name = "approve_date")
	private String  approve_date;
	private String note;
	private String note2;
	
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="approver_ack",insertable=false,updatable=false)
	private Approve_ack approveType;//
	
	/*@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="approver",insertable=false,updatable=false)
	private Operator operator;//
*/
	
	
	
	public int getRid() {
		return rid;
	}
	public String getApprover_name() {
		return approver_name;
	}
	public void setApprover_name(String approver_name) {
		this.approver_name = approver_name;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	
	public Integer getApprover() {
		return approver;
	}
	public void setApprover(Integer approver) {
		this.approver = approver;
	}
	
	
	public Integer getApproveack() {
		return approveack;
	}
	public void setApproveack(Integer approveack) {
		this.approveack = approveack;
	}
	public String getApprove_date() {
		return approve_date;
	}
	public void setApprove_date(String approve_date) {
		this.approve_date = approve_date;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	public Approve_ack getApproveType() {
		return approveType;
	}
	public void setApproveType(Approve_ack approveType) {
		this.approveType = approveType;
	}
	/*public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	*/
	
}
