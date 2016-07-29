package vo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	//维保单位
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_service",insertable=false,updatable=false)
	private Service1 service1;

	
	//配置一对一  人员类型
	@OneToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="type",insertable=false,updatable=false)
    private Servicer_type_def servicer_type_def;
	
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
	public Servicer_type_def getServicer_type_def() {
		return servicer_type_def;
	}

	public void setServicer_type_def(Servicer_type_def servicer_type_def) {
		this.servicer_type_def = servicer_type_def;
	}

	public Status_def getStatus_def() {
		return status_def;
	}

	public void setStatus_def(Status_def status_def) {
		this.status_def = status_def;
	}

	public Service1 getService1() {
		return service1;
	}

	public void setService1(Service1 service1) {
		this.service1 = service1;
	}
	

}
