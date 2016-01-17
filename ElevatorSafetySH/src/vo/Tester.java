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
	
	//配置一对一     人员状态
	@OneToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_test",insertable=false,updatable=false)
    private Test test ;
	//配置一对一     人员状态
	@OneToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="status",insertable=false,updatable=false)
	private Status_def status_def ;
	
	
	
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

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Status_def getStatus_def() {
		return status_def;
	}

	public void setStatus_def(Status_def status_def) {
		this.status_def = status_def;
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
