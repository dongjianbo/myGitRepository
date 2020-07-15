package vo;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="e_data_alarm")
public class E_data_alarm {
	@Id
	private int e_data_alarm_id;
	private String e_no;
	private String  created;
	private String received;
	private String finished;
	public int getE_data_alarm_id() {
		return e_data_alarm_id;
	}
	public void setE_data_alarm_id(int e_data_alarm_id) {
		this.e_data_alarm_id = e_data_alarm_id;
	}
	public String getE_no() {
		return e_no;
	}
	public void setE_no(String e_no) {
		this.e_no = e_no;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getReceived() {
		return received;
	}
	public void setReceived(String received) {
		this.received = received;
	}
	public String getFinished() {
		return finished;
	}
	public void setFinished(String finished) {
		this.finished = finished;
	}
	
	
}
