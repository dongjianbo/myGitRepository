package vo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="designer")
public class Designer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_designer")
	private int iddesigner;
	private String name;
	@Column(name="org_code")
	private String code;
	private String licename;
	@Column(name="licence_code")
	private String licence;
	private String manager;
	@Column(name="telphone")
	private String tel;
	@Column(name="address")
	private String addr;
	private String register_area;
	public String getRegister_area() {
		return register_area;
	}
	public void setRegister_area(String register_area) {
		this.register_area = register_area;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public int getIddesigner() {
		return iddesigner;
	}
	public void setIddesigner(int iddesigner) {
		this.iddesigner = iddesigner;
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
