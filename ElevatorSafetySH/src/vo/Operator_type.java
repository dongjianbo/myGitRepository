package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="operator_type_def")
public class Operator_type {
   @Id
   @Column(name="id_operator_type")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private String id_operator_type;
   private String name;
   @Column(name="desc")
   private String descript;
   
public String getId_operator_type() {
	return id_operator_type;
}
public void setId_operator_type(String id_operator_type) {
	this.id_operator_type = id_operator_type;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescript() {
	return descript;
}
public void setDescript(String descript) {
	this.descript = descript;
}
   
   
}
