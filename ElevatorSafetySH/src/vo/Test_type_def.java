package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="test_type_def")
public class Test_type_def {
   @Id
   @Column(name="id_test_type")
   private String id_test_type;
   private String name;
   @Column(name="[desc]")
   private String desc;
public String getId_test_type() {
	return id_test_type;
}
public void setId_test_type(String id_test_type) {
	this.id_test_type = id_test_type;
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
