package vo;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="e_data_status")
public class E_data_status {
	@Id
	private String eno;
	private int e_data_history_id;
	private int e_data_alarm_id;
	public String getEno() {
		return eno;
	}
	public void setEno(String eno) {
		this.eno = eno;
	}
	public int getE_data_history_id() {
		return e_data_history_id;
	}
	public void setE_data_history_id(int e_data_history_id) {
		this.e_data_history_id = e_data_history_id;
	}
	public int getE_data_alarm_id() {
		return e_data_alarm_id;
	}
	public void setE_data_alarm_id(int e_data_alarm_id) {
		this.e_data_alarm_id = e_data_alarm_id;
	}
	
}
