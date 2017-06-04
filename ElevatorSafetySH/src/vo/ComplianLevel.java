package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "complain_level")
public class ComplianLevel {
	@Id
   @Column(name="id_level")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private int id_level;
   private String level_name;
   private String level_desc;
	public int getId_level() {
		return id_level;
	}
	public void setId_level(int id_level) {
		this.id_level = id_level;
	}
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}
	public String getLevel_desc() {
		return level_desc;
	}
	public void setLevel_desc(String level_desc) {
		this.level_desc = level_desc;
	}
	
	
	
	   
}
