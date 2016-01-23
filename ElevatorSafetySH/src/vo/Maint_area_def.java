package vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="maint_area_def")
public class Maint_area_def {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int maint_area;
	private String name;
	private String desc;
	public int getMaint_area() {
		return maint_area;
	}
	public void setMaint_area(int maint_area) {
		this.maint_area = maint_area;
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
