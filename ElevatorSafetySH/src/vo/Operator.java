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
@Table(name = "operator")
public class Operator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_operator")
	private int idoperator;
	@Column(name = "type_operator")
	private String typeOperator;
	private String name;
	private String idcard;	
	@Column(name = "id_city")
	private String idcity;
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_city",insertable=false,updatable=false)
	//��¼�����ڳ���
	private Citylist city;
	@Column(name = "id_district")
	private String iddistrict;
	//��¼����������
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_district",insertable=false,updatable=false)
	private Distictlist distict;
	@Column(name = "id_subdistrict")
	private String idsubdistrict;
	//��¼�����ڽֵ�
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="id_subdistrict",insertable=false,updatable=false)
	private Subdistictlist subdistict;
	private String loginname;
	private String password;
	@Column(name="id_organization")
	private int idOrganization;
	@Column(name = "id_role")
	private int idprivilege;
	private String status;

	public Citylist getCity() {
		return city;
	}

	public void setCity(Citylist city) {
		this.city = city;
	}

	public Distictlist getDistict() {
		return distict;
	}

	public void setDistict(Distictlist distict) {
		this.distict = distict;
	}

	public Subdistictlist getSubdistict() {
		return subdistict;
	}

	public void setSubdistict(Subdistictlist subdistict) {
		this.subdistict = subdistict;
	}

	public String getTypeOperator() {
		return typeOperator;
	}

	public void setTypeOperator(String typeOperator) {
		this.typeOperator = typeOperator;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIdOrganization() {
		return idOrganization;
	}

	public void setIdOrganization(int idOrganization) {
		this.idOrganization = idOrganization;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIdoperator() {
		return idoperator;
	}

	public void setIdoperator(int idoperator) {
		this.idoperator = idoperator;
	}

	public int getIdprivilege() {
		return idprivilege;
	}

	public void setIdprivilege(int idprivilege) {
		this.idprivilege = idprivilege;
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

	public String getIdsubdistrict() {
		return idsubdistrict;
	}

	public void setIdsubdistrict(String idsubdistrict) {
		this.idsubdistrict = idsubdistrict;
	}

	public String getIddistrict() {
		return iddistrict;
	}

	public void setIddistrict(String iddistrict) {
		this.iddistrict = iddistrict;
	}

	public String getIdcity() {
		return idcity;
	}

	public void setIdcity(String idcity) {
		this.idcity = idcity;
	}

}
