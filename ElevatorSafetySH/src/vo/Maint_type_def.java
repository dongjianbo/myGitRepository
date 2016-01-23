package vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="maint_type_def")
public class Maint_type_def {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int maint_type;
	private String name;
	private String desc;
	public int getMaint_type() {
		return maint_type;
	}
	public void setMaint_type(int maint_type) {
		this.maint_type = maint_type;
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
