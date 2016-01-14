package vo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	@Id
	@Column(name="id_role")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idrole;
	
	private String name_role;
	@Column(name="[desc]")
	private String desc;
	private String role_status;
	@ManyToMany
	@JoinTable(name="role_menu",joinColumns={@JoinColumn(name="id_role",referencedColumnName="id_role")},
	inverseJoinColumns={@JoinColumn(name="id_menu",referencedColumnName="id_system_menu")})
	private Set<System_menu> menus=new HashSet<System_menu>();
	
	
	public Set<System_menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<System_menu> menus) {
		this.menus = menus;
	}
	public int getIdrole() {
		return idrole;
	}
	public void setIdrole(int idrole) {
		this.idrole = idrole;
	}
	public String getName_role() {
		return name_role;
	}
	public void setName_role(String name_role) {
		this.name_role = name_role;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getRole_status() {
		return role_status;
	}
	public void setRole_status(String role_status) {
		this.role_status = role_status;
	}
	
	
	

}
