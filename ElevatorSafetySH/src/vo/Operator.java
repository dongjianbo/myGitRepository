package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name = "id_district")
	private String iddistrict;
	@Column(name = "id_subdistrict")
	private String idsubdistrict;
	private String loginname;
	private String password;
	@Column(name="id_organization")
	private int idOrganization;
	@Column(name = "id_role")
	private int idprivilege;
	private String status;

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
