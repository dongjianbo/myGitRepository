package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user")
    private int iduser;
	private String name;
	@Column(name="org_code")
	private String code;
	@Column(name="safe_department")
	private String safedept;
	private String postcode;
	@Column(name="telephone")
	private String tel;
	private String manager;
	@Column(name="address")
	private String addr;
	@Column(name="register_area")
	private String registerArea;
	public String getRegisterArea() {
		return registerArea;
	}
	public void setRegisterArea(String registerArea) {
		this.registerArea = registerArea;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
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
	public String getSafedept() {
		return safedept;
	}
	public void setSafedept(String safedept) {
		this.safedept = safedept;
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
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	
	
}
