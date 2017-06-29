package vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "repair_image")
public class RepairImage {
	@Id
	private RepairImageKey key;
   private String path;
   private String mime;
   private String upload;
   private String camera_date;
   private String note;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	public String getCamera_date() {
		return camera_date;
	}
	public void setCamera_date(String camera_date) {
		this.camera_date = camera_date;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public RepairImageKey getKey() {
		return key;
	}
	public void setKey(RepairImageKey key) {
		this.key = key;
	}
	
	   
}
