package vo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="system_menu")
public class System_menu {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_system_menu;
	private String name_item;
	private int id_class;
	private int id_item;
	private String item_desc;
	private String url;
	public int getId_system_menu() {
		return id_system_menu;
	}
	public void setId_system_menu(int id_system_menu) {
		this.id_system_menu = id_system_menu;
	}
	public String getName_item() {
		return name_item;
	}
	public void setName_item(String name_item) {
		this.name_item = name_item;
	}
	public int getId_class() {
		return id_class;
	}
	public void setId_class(int id_class) {
		this.id_class = id_class;
	}
	public int getId_item() {
		return id_item;
	}
	public void setId_item(int id_item) {
		this.id_item = id_item;
	}
	public String getItem_desc() {
		return item_desc;
	}
	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
