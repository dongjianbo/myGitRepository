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
@Table(name="modellist")
public class Modellist {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id_model")
  private int idmodel;
  @Column(name="id_manufer")
  private int idmanufer;
  //配置 多对一 生产厂家
  @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
  @JoinColumn(name="id_manufer",insertable=false,updatable=false)
  private Manufer manufer;
  
  
  @Column(name="model_name")
  private String modelname;
  @Column(name="type_elevator")
  private String typeElevator;
//配置 多对一电梯类型
  @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
  @JoinColumn(name="type_elevator",insertable=false,updatable=false)
  private Elevator_type_def elevator_type_def ;
  
  public Manufer getManufer() {
	return manufer;
}
public void setManufer(Manufer manufer) {
	this.manufer = manufer;
}
public Elevator_type_def getElevator_type_def() {
	return elevator_type_def;
}
public void setElevator_type_def(Elevator_type_def elevator_type_def) {
	this.elevator_type_def = elevator_type_def;
}
private String suitplace;
  private String parameter11;
  private Integer parameter12;
  private Double parameter13;//小数类型封装
  private Integer parameter14;
  private Integer parameter21;
  private Double parameter22;
  private Integer parameter23;
  private Integer parameter24;
  private String parameter25;
  private String parameter31;
  private Integer parameter32;
  private Double parameter33;
  private Integer parameter34;
  private Integer parameter41;
  private Double parameter42;
  private Double parameter43;
  private Double parameter44;
  private Double parameter45;
  private Double parameter46;

public int getIdmodel() {
	return idmodel;
}
public void setIdmodel(int idmodel) {
	this.idmodel = idmodel;
}
public int getIdmanufer() {
	return idmanufer;
}
public void setIdmanufer(int idmanufer) {
	this.idmanufer = idmanufer;
}
public String getModelname() {
	return modelname;
}
public void setModelname(String modelname) {
	this.modelname = modelname;
}
public String getTypeElevator() {
	return typeElevator;
}
public void setTypeElevator(String typeElevator) {
	this.typeElevator = typeElevator;
}
public String getSuitplace() {
	return suitplace;
}
public void setSuitplace(String suitplace) {
	this.suitplace = suitplace;
}
public String getParameter11() {
	return parameter11;
}
public void setParameter11(String parameter11) {
	this.parameter11 = parameter11;
}
public Integer getParameter12() {
	return parameter12;
}
public void setParameter12(Integer parameter12) {
	this.parameter12 = parameter12;
}
public Double getParameter13() {
	return parameter13;
}
public void setParameter13(Double parameter13) {
	this.parameter13 = parameter13;
}
public Integer getParameter14() {
	return parameter14;
}
public void setParameter14(Integer parameter14) {
	this.parameter14 = parameter14;
}
public Integer getParameter21() {
	return parameter21;
}
public void setParameter21(Integer parameter21) {
	this.parameter21 = parameter21;
}
public Double getParameter22() {
	return parameter22;
}
public void setParameter22(Double parameter22) {
	this.parameter22 = parameter22;
}
public Integer getParameter23() {
	return parameter23;
}
public void setParameter23(Integer parameter23) {
	this.parameter23 = parameter23;
}
public Integer getParameter24() {
	return parameter24;
}
public void setParameter24(Integer parameter24) {
	this.parameter24 = parameter24;
}
public String getParameter25() {
	return parameter25;
}
public void setParameter25(String parameter25) {
	this.parameter25 = parameter25;
}
public String getParameter31() {
	return parameter31;
}
public void setParameter31(String parameter31) {
	this.parameter31 = parameter31;
}
public Integer getParameter32() {
	return parameter32;
}
public void setParameter32(Integer parameter32) {
	this.parameter32 = parameter32;
}
public Double getParameter33() {
	return parameter33;
}
public void setParameter33(Double parameter33) {
	this.parameter33 = parameter33;
}
public Integer getParameter34() {
	return parameter34;
}
public void setParameter34(Integer parameter34) {
	this.parameter34 = parameter34;
}
public Integer getParameter41() {
	return parameter41;
}
public void setParameter41(Integer parameter41) {
	this.parameter41 = parameter41;
}
public Double getParameter42() {
	return parameter42;
}
public void setParameter42(Double parameter42) {
	this.parameter42 = parameter42;
}
public Double getParameter43() {
	return parameter43;
}
public void setParameter43(Double parameter43) {
	this.parameter43 = parameter43;
}
public Double getParameter44() {
	return parameter44;
}
public void setParameter44(Double parameter44) {
	this.parameter44 = parameter44;
}
public Double getParameter45() {
	return parameter45;
}
public void setParameter45(Double parameter45) {
	this.parameter45 = parameter45;
}
public Double getParameter46() {
	return parameter46;
}
public void setParameter46(Double parameter46) {
	this.parameter46 = parameter46;
}

  
   
  
  
}
