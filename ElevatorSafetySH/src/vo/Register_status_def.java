package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="register_status_def")
public class Register_status_def {
   @Id
   @Column(name="id_register_status")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private String id_register_status;
   private String name;
   private String desc;
public String getId_register_status() {
	return id_register_status;
}
public void setId_register_status(String id_register_status) {
	this.id_register_status = id_register_status;
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
