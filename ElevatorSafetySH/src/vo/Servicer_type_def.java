package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="servicer_type_def")
public class Servicer_type_def {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_servicer_type")
	private String idservicertype;
	private String name;
	@Column(name="[desc]")
	private String desc;

	public String getIdservicertype() {
		return idservicertype;
	}
	public void setIdservicertype(String idservicertype) {
		this.idservicertype = idservicertype;
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
