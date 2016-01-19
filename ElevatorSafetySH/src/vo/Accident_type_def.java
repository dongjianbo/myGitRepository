package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accident_type_def")
public class Accident_type_def {
  @Id
  @Column(name="id_accident_type")
  private String id_accident_type;
  private String name;
  private String desc;
public String getId_accident_type() {
	return id_accident_type;
}
public void setId_accident_type(String id_accident_type) {
	this.id_accident_type = id_accident_type;
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
