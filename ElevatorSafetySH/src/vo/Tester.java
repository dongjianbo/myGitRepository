package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tester")
public class Tester {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tester")
	private int idtester;
	private String name;
	private String idcard;
	@Column(name = "id_test")
	private int idtest;
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

	public int getIdtester() {
		return idtester;
	}

	public void setIdtester(int idtester) {
		this.idtester = idtester;
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

	public int getIdtest() {
		return idtest;
	}

	public void setIdtest(int idtest) {
		this.idtest = idtest;
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
