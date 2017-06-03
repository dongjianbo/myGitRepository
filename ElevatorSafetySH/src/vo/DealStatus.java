package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deal_status")
public class DealStatus {
	@Id
   @Column(name="id_deal")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private int id_deal;
   private String deal_name;
   private String deal_desc;
	public int getId_deal() {
		return id_deal;
	}
	public void setId_deal(int id_deal) {
		this.id_deal = id_deal;
	}
	public String getdeal_name() {
		return deal_name;
	}
	public void setdeal_name(String deal_name) {
		this.deal_name = deal_name;
	}
	public String getdeal_desc() {
		return deal_desc;
	}
	public void setdeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}
	
	
	
	   
}
