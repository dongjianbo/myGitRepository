package vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class History_listKey implements Serializable{
 @Column(name="id_history")
 private int idhistory;
 @Column(name="[key]")
 private int key;
public int getIdhistory() {
	return idhistory;
}
public void setIdhistory(int idhistory) {
	this.idhistory = idhistory;
}
public int getKey() {
	return key;
}
public void setKey(int key) {
	this.key = key;
}
@Override
public int hashCode() {
	// TODO Auto-generated method stub
	return super.hashCode();
}
@Override
public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	return super.equals(obj);
}
 
 
 
}
