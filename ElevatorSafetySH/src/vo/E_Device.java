package vo;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="e_device")
public class E_Device {
	@Id
	private String eno;
	private int id_elevator;
	private String  ondutycall;
	public String getEno() {
		return eno;
	}
	public void setEno(String eno) {
		this.eno = eno;
	}
	public int getId_elevator() {
		return id_elevator;
	}
	public void setId_elevator(int id_elevator) {
		this.id_elevator = id_elevator;
	}
	public String getOndutycall() {
		return ondutycall;
	}
	public void setOndutycall(String ondutycall) {
		this.ondutycall = ondutycall;
	}
	
	
}
