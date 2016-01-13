package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "servicer")
public class Servicer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_servicer")
	private int idservicer;
	private String name;
	private String idcard;
	@Column(name = "id_service")
	private int idservice;
	@Column(name = "licence_code")
	private String licencecode;
	private String type;
	private String status;
	@Column(name = "id_mifare")
	private String idMifare;

	public String getIdMifare() {
		return idMifare;
	}

	public void setIdMifare(String idMifare) {
		this.idMifare = idMifare;
	}

	public int getIdservicer() {
		return idservicer;
	}

	public void setIdservicer(int idservicer) {
		this.idservicer = idservicer;
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

	public int getIdservice() {
		return idservice;
	}

	public void setIdservice(int idservice) {
		this.idservice = idservice;
	}

	public String getLicencecode() {
		return licencecode;
	}

	public void setLicencecode(String licencecode) {
		this.licencecode = licencecode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
