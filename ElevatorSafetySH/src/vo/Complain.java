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
@Table(name = "complains")
public class Complain {
	 @Id
	   @Column(name="cid")
	   @GeneratedValue(strategy=GenerationType.AUTO)
	   private int cid;
	   private int type_object;
	   private int id_object;
	   private int level;
	   private int source;
	   private String contact;
	   private String content;
	   private int status;
	   private String result;
	   private String input1;
	   private String date1;
	   private String iput2;
	   private String date2;
	   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	   @JoinColumn(name="type_object",insertable=false,updatable=false)
	   private ComplianObject complainObject;//对应的投诉对象
	   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	   @JoinColumn(name="level",insertable=false,updatable=false)
	   private ComplianLevel complainLevel;//对应的投诉等级
	   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	   @JoinColumn(name="source",insertable=false,updatable=false)
	   private ComplianSource complainSource;//对应的投诉来源
	   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	   @JoinColumn(name="status",insertable=false,updatable=false)
	   private DealStatus dealStatus;//对应的处理状态
	   
	   
	   
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getType_object() {
		return type_object;
	}
	public void setType_object(int type_object) {
		this.type_object = type_object;
	}
	public int getId_object() {
		return id_object;
	}
	public void setId_object(int id_object) {
		this.id_object = id_object;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getInput1() {
		return input1;
	}
	public void setInput1(String input1) {
		this.input1 = input1;
	}
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	public String getIput2() {
		return iput2;
	}
	public void setIput2(String iput2) {
		this.iput2 = iput2;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	public ComplianObject getComplainObject() {
		return complainObject;
	}
	public void setComplainObject(ComplianObject complainObject) {
		this.complainObject = complainObject;
	}
	public ComplianLevel getComplainLevel() {
		return complainLevel;
	}
	public void setComplainLevel(ComplianLevel complainLevel) {
		this.complainLevel = complainLevel;
	}
	public ComplianSource getComplainSource() {
		return complainSource;
	}
	public void setComplainSource(ComplianSource complainSource) {
		this.complainSource = complainSource;
	}
	public DealStatus getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(DealStatus dealStatus) {
		this.dealStatus = dealStatus;
	}
	
	
}
