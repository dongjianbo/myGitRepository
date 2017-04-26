package vo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "safer")
public class Safer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_safer")
	private int idsafer;
	private String name;
	private String idcard;
	private String tel;
	@Column(name = "id_user")
	private int iduser;
	@Column(name = "licence_code")
	private String licencecode;
	private String status;
	@Column(name = "id_mifare")
	private String idMifare;

	
	
	//配置一对一关系  用户表
	
	@OneToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_user",insertable=false,updatable=false)
    private User user;
	
	//配置一对一关系  状态表
	@OneToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="status",insertable=false,updatable=false)
    private Status_def status_def ;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status_def getStatus_def() {
		return status_def;
	}

	public void setStatus_def(Status_def status_def) {
		this.status_def = status_def;
	}

	public String getIdMifare() {
		return idMifare;
	}

	public void setIdMifare(String idMifare) {
		this.idMifare = idMifare;
	}

	public int getIdsafer() {
		return idsafer;
	}

	public void setIdsafer(int idsafer) {
		this.idsafer = idsafer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getLicencecode() {
		return licencecode;
	}

	public void setLicencecode(String licencecode) {
		this.licencecode = licencecode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
