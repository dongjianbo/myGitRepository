package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="service")
public class Service1 {
	   @Id
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	   @Column(name="id_service")
	   private int idservice;
	   private String name;
	   @Column(name="org_code")
	   private String code;
	   private String licename;
	   @Column(name="licence_code")
	   private String licence;
	   private String manager;
	   @Column(name="telephone")
	   private String tel;
	   @Column(name="address")
	   private String addr;
	   @Column(name="register_area")
	   private String registerArea;
	   @Transient
	   private Citylist registCity;
	   
	   
	public Citylist getRegistCity() {
		return registCity;
	}
	public void setRegistCity(Citylist registCity) {
		this.registCity = registCity;
	}
	public String getRegisterArea() {
		return registerArea;
	}
	public void setRegisterArea(String registerArea) {
		this.registerArea = registerArea;
	}
	public int getIdservice() {
		return idservice;
	}
	public void setIdservice(int idservice) {
		this.idservice = idservice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLicename() {
		return licename;
	}
	public void setLicename(String licename) {
		this.licename = licename;
	}
	public String getLicence() {
		return licence;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	   
	   
	   
}
