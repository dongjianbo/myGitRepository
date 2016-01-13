package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
  @Column(name="model_name")
  private String modelname;
  @Column(name="type_elevator")
  private String typeElevator;
  private String suitplace;
  private String parameter11;
  private int parameter12;
  private Double parameter13;//小数类型封装
  private int parameter14;
  private int parameter21;
  private Double parameter22;
  private int parameter23;
  private int parameter24;
  private String parameter25;
  private String parameter31;
  private int parameter32;
  private Double parameter33;
  private int parameter34;
  private int parameter41;
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
public int getParameter12() {
	return parameter12;
}
public void setParameter12(int parameter12) {
	this.parameter12 = parameter12;
}
public Double getParameter13() {
	return parameter13;
}
public void setParameter13(Double parameter13) {
	this.parameter13 = parameter13;
}
public int getParameter14() {
	return parameter14;
}
public void setParameter14(int parameter14) {
	this.parameter14 = parameter14;
}
public int getParameter21() {
	return parameter21;
}
public void setParameter21(int parameter21) {
	this.parameter21 = parameter21;
}
public Double getParameter22() {
	return parameter22;
}
public void setParameter22(Double parameter22) {
	this.parameter22 = parameter22;
}
public int getParameter23() {
	return parameter23;
}
public void setParameter23(int parameter23) {
	this.parameter23 = parameter23;
}
public int getParameter24() {
	return parameter24;
}
public void setParameter24(int parameter24) {
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
public int getParameter32() {
	return parameter32;
}
public void setParameter32(int parameter32) {
	this.parameter32 = parameter32;
}
public Double getParameter33() {
	return parameter33;
}
public void setParameter33(Double parameter33) {
	this.parameter33 = parameter33;
}
public int getParameter34() {
	return parameter34;
}
public void setParameter34(int parameter34) {
	this.parameter34 = parameter34;
}
public int getParameter41() {
	return parameter41;
}
public void setParameter41(int parameter41) {
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
