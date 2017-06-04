package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "approve_ack")
public class Approve_ack {
	 @Id
	   @Column(name="id_approve")
	   @GeneratedValue(strategy=GenerationType.AUTO)
	   private int idapprove;
	   private String approve_name;
	   private String approve_desc;
	
	public int getIdapprove() {
		return idapprove;
	}
	public void setIdapprove(int idapprove) {
		this.idapprove = idapprove;
	}
	public String getApprove_name() {
		return approve_name;
	}
	public void setApprove_name(String approve_name) {
		this.approve_name = approve_name;
	}
	public String getApprove_desc() {
		return approve_desc;
	}
	public void setApprove_desc(String approve_desc) {
		this.approve_desc = approve_desc;
	}
	   
}
