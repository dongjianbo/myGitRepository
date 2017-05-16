package vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(Maint_detail_key.class)
public class Maint_detail {
	@Id
	private int maint_id;
	@Id
	private int maint_item_id;
	private Integer maint_result;
	private String maint_note;
	private String maint_date;
	
	private Integer image_val;
	private Integer maint_workflow;
	
	/**
	 * 检查项
	 */
	@ManyToOne
	@JoinColumn(name="maint_item_id",insertable=false,updatable=false)
	private Maint_item_def mid;
	/**
	 * 维保记录
	 */
	@ManyToOne
	@JoinColumn(name="maint_id",insertable=false,updatable=false)
	private Maint_report_id mri;
	public int getMaint_id() {
		return maint_id;
	}
	public void setMaint_id(int maint_id) {
		this.maint_id = maint_id;
	}
	public int getMaint_item_id() {
		return maint_item_id;
	}
	public void setMaint_item_id(int maint_item_id) {
		this.maint_item_id = maint_item_id;
	}
	public Integer getMaint_result() {
		return maint_result;
	}
	public void setMaint_result(Integer maint_result) {
		this.maint_result = maint_result;
	}
	public String getMaint_note() {
		return maint_note;
	}
	public void setMaint_note(String maint_note) {
		this.maint_note = maint_note;
	}
	public String getMaint_date() {
		return maint_date;
	}
	public void setMaint_date(String maint_date) {
		this.maint_date = maint_date;
	}
	public Integer getImage_val() {
		return image_val;
	}
	public void setImage_val(Integer image_val) {
		this.image_val = image_val;
	}
	public Integer getMaint_workflow() {
		return maint_workflow;
	}
	public void setMaint_workflow(Integer maint_workflow) {
		this.maint_workflow = maint_workflow;
	}
	public Maint_item_def getMid() {
		return mid;
	}
	public void setMid(Maint_item_def mid) {
		this.mid = mid;
	}
	public Maint_report_id getMri() {
		return mri;
	}
	public void setMri(Maint_report_id mri) {
		this.mri = mri;
	}
	
}
