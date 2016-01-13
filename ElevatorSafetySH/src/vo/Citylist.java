package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="citylist")
public class Citylist {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	private String id_city;
	private String name_city;
	
	public String getId_city() {
		return id_city;
	}
	public void setId_city(String id_city) {
		this.id_city = id_city;
	}
	public String getName_city() {
		return name_city;
	}
	public void setName_city(String name_city) {
		this.name_city = name_city;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
