package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name = "id_user")
	private int iduser;
	@Column(name = "licence_code")
	private String licencecode;
	private String status;
	@Column(name = "id_mifare")
	private String idMifare;

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

}
