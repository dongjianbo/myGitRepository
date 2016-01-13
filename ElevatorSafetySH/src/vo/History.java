package vo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="history")
public class History {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_history")
	private int idhistory;
	private int type;
	private int operator;
	private String datetime;
	
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public int getIdhistory() {
		return idhistory;
	}
	public void setIdhistory(int idhistory) {
		this.idhistory = idhistory;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	
	
	

}
