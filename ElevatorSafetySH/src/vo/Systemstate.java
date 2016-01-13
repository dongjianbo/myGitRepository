package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "systemstate")
public class Systemstate {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "version_person")
	private int versionperson;
	@Column(name = "version_elevator")
	private int versionelevator;
	
	public int getVersionperson() {
		return versionperson;
	}
	public void setVersionperson(int versionperson) {
		this.versionperson = versionperson;
	}
	public int getVersionelevator() {
		return versionelevator;
	}
	public void setVersionelevator(int versionelevator) {
		this.versionelevator = versionelevator;
	}
}
