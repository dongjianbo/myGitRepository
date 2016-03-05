package vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="maint_item_def")
public class Maint_item_def {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int maint_item_id;
	private int elevator_type;
	@ManyToOne
	@JoinColumn(name="elevator_type",insertable=false,updatable=false)
	private Elevator_type_def elType;
	private int maint_type;
	@ManyToOne
	@JoinColumn(name="maint_type",insertable=false,updatable=false)
	private Maint_type_def mType;
	private int maint_area;
	@ManyToOne
	@JoinColumn(name="maint_area",insertable=false,updatable=false)
	private Maint_area_def mArea;
	private int t5001_no;
	private String title;
	private String content;
	private String desc;
	private int act;
	private int enable;
	private Date last_modified;
	@Transient
	private String info;//¶àÓàµÄ×Ö¶Î
	@Transient
	private String maint_result;
	
	
	public String getMaint_result() {
		return maint_result;
	}
	public void setMaint_result(String maint_result) {
		this.maint_result = maint_result;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Elevator_type_def getElType() {
		return elType;
	}
	public void setElType(Elevator_type_def elType) {
		this.elType = elType;
	}
	public Maint_type_def getmType() {
		return mType;
	}
	public void setmType(Maint_type_def mType) {
		this.mType = mType;
	}
	public Maint_area_def getmArea() {
		return mArea;
	}
	public void setmArea(Maint_area_def mArea) {
		this.mArea = mArea;
	}
	public int getMaint_item_id() {
		return maint_item_id;
	}
	public void setMaint_item_id(int maint_item_id) {
		this.maint_item_id = maint_item_id;
	}
	public int getElevator_type() {
		return elevator_type;
	}
	public void setElevator_type(int elevator_type) {
		this.elevator_type = elevator_type;
	}
	public int getMaint_type() {
		return maint_type;
	}
	public void setMaint_type(int maint_type) {
		this.maint_type = maint_type;
	}
	public int getMaint_area() {
		return maint_area;
	}
	public void setMaint_area(int maint_area) {
		this.maint_area = maint_area;
	}
	public int getT5001_no() {
		return t5001_no;
	}
	public void setT5001_no(int t5001_no) {
		this.t5001_no = t5001_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getAct() {
		return act;
	}
	public void setAct(int act) {
		this.act = act;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public Date getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}
	
}
