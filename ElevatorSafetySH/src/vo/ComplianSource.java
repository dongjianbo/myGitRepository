package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "complain_source")
public class ComplianSource {
	@Id
   @Column(name="id_source")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private int id_source;
   private String source_name;
   private String source_desc;
	public int getId_source() {
		return id_source;
	}
	public void setId_source(int id_source) {
		this.id_source = id_source;
	}
	public String getsource_name() {
		return source_name;
	}
	public void setsource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getsource_desc() {
		return source_desc;
	}
	public void setsource_desc(String source_desc) {
		this.source_desc = source_desc;
	}
	
	
	
	   
}
