package vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="elevator_gis")
public class Elevator_gis {
	@Id
	private int elevator_id;
	private double latitude;
	private double longitude;
	private String got;
	private int type;
	private float acu;
	private String address;
	private String country;
	private String city;
	private String dist;
	private String street;
	private String street_num;
	private String last_modified;
	public int getElevator_id() {
		return elevator_id;
	}
	public void setElevator_id(int elevator_id) {
		this.elevator_id = elevator_id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getGot() {
		return got;
	}
	public void setGot(String got) {
		this.got = got;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getAcu() {
		return acu;
	}
	public void setAcu(float acu) {
		this.acu = acu;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet_num() {
		return street_num;
	}
	public void setStreet_num(String street_num) {
		this.street_num = street_num;
	}
	public String getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}
	
}
