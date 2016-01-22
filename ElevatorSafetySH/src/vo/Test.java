package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "test")
public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_test")
	private int idtest;
	private String name;
	@Column(name = "org_code")
	private String code;
	private String licename;
	@Column(name = "licence_code")
	private String licencecode;
	private String manager;
	private String telephone;
	@Column(name = "address")
	private String addr;
	@Column(name = "register_area")
	private String registerArea;
	@Transient
	public Citylist registCity;
	
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

	public String getRegisterArea() {
		return registerArea;
	}

	public void setRegisterArea(String registerArea) {
		this.registerArea = registerArea;
	}

	public int getIdtest() {
		return idtest;
	}

	public void setIdtest(int idtest) {
		this.idtest = idtest;
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

	public String getLicencecode() {
		return licencecode;
	}

	public void setLicencecode(String licencecode) {
		this.licencecode = licencecode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
