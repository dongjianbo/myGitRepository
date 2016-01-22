package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "owner")
public class Owner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_owner")
	private int idowner;
	private String name;
	@Column(name = "org_code")
	private String code;
	@Column(name = "legal_rep")
	private String legalrep;
	private String manager;
	private String postcode;
	@Column(name = "telephone")
	private String tel;
	@Column(name = "address")
	private String addr;
	@Column(name = "register_area")
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

	public int getIdowner() {
		return idowner;
	}

	public void setIdowner(int idowner) {
		this.idowner = idowner;
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

	public String getLegalrep() {
		return legalrep;
	}

	public void setLegalrep(String legalrep) {
		this.legalrep = legalrep;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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
