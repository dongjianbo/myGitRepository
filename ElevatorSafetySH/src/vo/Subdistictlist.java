package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subdistictlist")
public class Subdistictlist {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	private String id_subdistrict;
	private String id_district;
	private String id_city;
	private String name_subdistrict;
	public String getId_subdistrict() {
		return id_subdistrict;
	}
	public void setId_subdistrict(String id_subdistrict) {
		this.id_subdistrict = id_subdistrict;
	}
	public String getId_district() {
		return id_district;
	}
	public void setId_district(String id_district) {
		this.id_district = id_district;
	}
	public String getId_city() {
		return id_city;
	}
	public void setId_city(String id_city) {
		this.id_city = id_city;
	}
	public String getName_subdistrict() {
		return name_subdistrict;
	}
	public void setName_subdistrict(String name_subdistrict) {
		this.name_subdistrict = name_subdistrict;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
