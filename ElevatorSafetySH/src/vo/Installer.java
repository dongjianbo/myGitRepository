package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="installer")
public class Installer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_installer")
	private int idinstaller;
	private String name;
	@Column(name="org_code")
	private String code;
	private String licename;
	private String licence;
	private String manager;
	@Column(name="telphone")
	private String tel;
	@Column(name="address")
	private String addr;
	private String register_area;
	@Transient
	private Citylist registCity;
	
	public Citylist getRegistCity() {
		return registCity;
	}
	public void setRegistCity(Citylist registCity) {
		this.registCity = registCity;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getRegister_area() {
		return register_area;
	}
	public void setRegister_area(String register_area) {
		this.register_area = register_area;
	}
	public int getIdinstaller() {
		return idinstaller;
	}
	public void setIdinstaller(int idinstaller) {
		this.idinstaller = idinstaller;
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
