package vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="history_list")
public class History_list {
 @Id
 private History_listKey key;
 private String value;
 
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public History_listKey getKey() {
	return key;
}
public void setKey(History_listKey key) {
	this.key = key;
}
 
}
