package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="status_def")
public class Status_def {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_status")
	private String idstatus;
	private String name;
	private String desc;

	
	public String getIdstatus() {
		return idstatus;
	}
	public void setIdstatus(String idstatus) {
		this.idstatus = idstatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
