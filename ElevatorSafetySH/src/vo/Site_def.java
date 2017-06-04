package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="site_def")
public class Site_def {
   @Id
   @Column(name="idsite")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private String idsite;
   private String site_name;
   private String site_desc;
public String getIdsite() {
	return idsite;
}
public void setIdsite(String idsite) {
	this.idsite = idsite;
}
public String getSite_name() {
	return site_name;
}
public void setSite_name(String site_name) {
	this.site_name = site_name;
}
public String getSite_desc() {
	return site_desc;
}
public void setSite_desc(String site_desc) {
	this.site_desc = site_desc;
}

   
}
