package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "distinct_gis")
public class DistinctGis {
	   @Id
	   @Column(name="id")
	   private int id;
	   private String id_distinct;
	   private int level;
	   private double gis_x;
	   private double gis_y;
	
	public String getId_distinct() {
		return id_distinct;
	}
	public void setId_distinct(String id_distinct) {
		this.id_distinct = id_distinct;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public double getGis_x() {
		return gis_x;
	}
	public void setGis_x(double gis_x) {
		this.gis_x = gis_x;
	}
	public double getGis_y() {
		return gis_y;
	}
	public void setGis_y(double gis_y) {
		this.gis_y = gis_y;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	   
}
