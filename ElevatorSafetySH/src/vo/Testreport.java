package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="testreport")
public class Testreport {
   @Id
   @Column(name="id_testreport")
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private int id_testreport;
   private int id_elevator;//外键
   private int id_test;//外键
   private String type_test;//外键
   private String number_report;
   private String date_test;
   private int result;//外键
   private int id_tester;//外键
   private String signer;
   private String date_sign;
   @Column(name="[desc]")
   private String desc;
   private String date_accident;
   private String type_accident;//外键
   private String process_accident;
public int getId_testreport() {
	return id_testreport;
}
public void setId_testreport(int id_testreport) {
	this.id_testreport = id_testreport;
}
public int getId_elevator() {
	return id_elevator;
}
public void setId_elevator(int id_elevator) {
	this.id_elevator = id_elevator;
}
public int getId_test() {
	return id_test;
}
public void setId_test(int id_test) {
	this.id_test = id_test;
}
public String getType_test() {
	return type_test;
}
public void setType_test(String type_test) {
	this.type_test = type_test;
}
public String getNumber_report() {
	return number_report;
}
public void setNumber_report(String number_report) {
	this.number_report = number_report;
}
public String getDate_test() {
	return date_test;
}
public void setDate_test(String date_test) {
	this.date_test = date_test;
}
public int getResult() {
	return result;
}
public void setResult(int result) {
	this.result = result;
}
public int getId_tester() {
	return id_tester;
}
public void setId_tester(int id_tester) {
	this.id_tester = id_tester;
}
public String getSigner() {
	return signer;
}
public void setSigner(String signer) {
	this.signer = signer;
}
public String getDate_sign() {
	return date_sign;
}
public void setDate_sign(String date_sign) {
	this.date_sign = date_sign;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public String getDate_accident() {
	return date_accident;
}
public void setDate_accident(String date_accident) {
	this.date_accident = date_accident;
}
public String getType_accident() {
	return type_accident;
}
public void setType_accident(String type_accident) {
	this.type_accident = type_accident;
}
public String getProcess_accident() {
	return process_accident;
}
public void setProcess_accident(String process_accident) {
	this.process_accident = process_accident;
}
   
   
}
