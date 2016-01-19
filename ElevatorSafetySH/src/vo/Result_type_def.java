package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="result_type_def")
public class Result_type_def {
   @Id
   @Column(name="id_result_type")
   private String id_result_type;
   private String name;
   @Column(name="[desc]")
   private String desc;

public String getId_result_type() {
	return id_result_type;
}
public void setId_result_type(String id_result_type) {
	this.id_result_type = id_result_type;
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
