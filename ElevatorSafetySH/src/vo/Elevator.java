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
@Table(name="elevator")
public class Elevator {
   @Id
   @Column(name="id_elevator")
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private int id_elevator;
   private String register_org;
   private String register_code;
   private String device_code;
   private int id_designer;
   //设计单位
   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
   @JoinColumn(name="id_designer",insertable=false,updatable=false)
   private Designer designer;
   private int id_manufer;
   //生产单位
   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
   @JoinColumn(name="id_manufer",insertable=false,updatable=false)
   private Manufer manufer;
   private String date_manufer;//日期
   private String code_manufer;
   private String constucter;
   private String startdate_construct;//日期
   private String enddate_construct;//日期
   private String accepter_construct;
   private String check_construct;
   private String check_construct_code;
   private int id_installer;
   //安装单位
   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
   @JoinColumn(name="id_installer",insertable=false,updatable=false)
   private Installer installer;
   private int id_owner;
   //产权单位
   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
   @JoinColumn(name="id_owner",insertable=false,updatable=false)
   private Owner owner;
   private Integer id_user;
   private String id_city;
   private String id_district;
   private String id_subdistrict;
   private String address;
   private String date_declare;//日期
   private String date_register;//日期
   private String date_enable;//日期
   private String project_duty;
   private Integer id_service;
   //维保单位
   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
   @JoinColumn(name="id_service",insertable=false,updatable=false)
   private Service1 service;
   private Integer id_test;
   //检测单位
   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
   @JoinColumn(name="id_test",insertable=false,updatable=false)
   private Test test;
   private int num_floor_elevator;
   private int id_elevator_model;
   //电梯型号
   @ManyToOne(cascade=CascadeType.REFRESH,optional=true)
   @JoinColumn(name="id_elevator_model",insertable=false,updatable=false)
   private Elevator_type_def elevatorType;
   private String register_status;
   @Column(name="[desc]")
   private String desc;
   private Float gis_x;
   private Float gis_y;
   private String gis_type;
public int getId_elevator() {
	return id_elevator;
}

public String getRegister_status() {
	return register_status;
}

public void setRegister_status(String register_status) {
	this.register_status = register_status;
}

public void setId_elevator(int id_elevator) {
	this.id_elevator = id_elevator;
}
public String getRegister_org() {
	return register_org;
}
public void setRegister_org(String register_org) {
	this.register_org = register_org;
}
public String getRegister_code() {
	return register_code;
}
public void setRegister_code(String register_code) {
	this.register_code = register_code;
}
public String getDevice_code() {
	return device_code;
}
public void setDevice_code(String device_code) {
	this.device_code = device_code;
}
public int getId_designer() {
	return id_designer;
}
public void setId_designer(int id_designer) {
	this.id_designer = id_designer;
}
public Designer getDesigner() {
	return designer;
}
public void setDesigner(Designer designer) {
	this.designer = designer;
}
public int getId_manufer() {
	return id_manufer;
}
public void setId_manufer(int id_manufer) {
	this.id_manufer = id_manufer;
}
public Manufer getManufer() {
	return manufer;
}
public void setManufer(Manufer manufer) {
	this.manufer = manufer;
}
public String getDate_manufer() {
	return date_manufer;
}
public void setDate_manufer(String date_manufer) {
	this.date_manufer = date_manufer;
}
public String getCode_manufer() {
	return code_manufer;
}
public void setCode_manufer(String code_manufer) {
	this.code_manufer = code_manufer;
}
public String getConstucter() {
	return constucter;
}
public void setConstucter(String constucter) {
	this.constucter = constucter;
}
public String getStartdate_construct() {
	return startdate_construct;
}
public void setStartdate_construct(String startdate_construct) {
	this.startdate_construct = startdate_construct;
}
public String getEnddate_construct() {
	return enddate_construct;
}
public void setEnddate_construct(String enddate_construct) {
	this.enddate_construct = enddate_construct;
}
public String getAccepter_construct() {
	return accepter_construct;
}
public void setAccepter_construct(String accepter_construct) {
	this.accepter_construct = accepter_construct;
}
public String getCheck_construct() {
	return check_construct;
}
public void setCheck_construct(String check_construct) {
	this.check_construct = check_construct;
}
public String getCheck_construct_code() {
	return check_construct_code;
}
public void setCheck_construct_code(String check_construct_code) {
	this.check_construct_code = check_construct_code;
}
public int getId_installer() {
	return id_installer;
}
public void setId_installer(int id_installer) {
	this.id_installer = id_installer;
}
public Installer getInstaller() {
	return installer;
}
public void setInstaller(Installer installer) {
	this.installer = installer;
}
public int getId_owner() {
	return id_owner;
}
public void setId_owner(int id_owner) {
	this.id_owner = id_owner;
}
public Owner getOwner() {
	return owner;
}
public void setOwner(Owner owner) {
	this.owner = owner;
}
public Integer getId_user() {
	return id_user;
}
public void setId_user(Integer id_user) {
	this.id_user = id_user;
}
public String getId_city() {
	return id_city;
}
public void setId_city(String id_city) {
	this.id_city = id_city;
}
public String getId_district() {
	return id_district;
}
public void setId_district(String id_district) {
	this.id_district = id_district;
}
public String getId_subdistrict() {
	return id_subdistrict;
}
public void setId_subdistrict(String id_subdistrict) {
	this.id_subdistrict = id_subdistrict;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getDate_declare() {
	return date_declare;
}
public void setDate_declare(String date_declare) {
	this.date_declare = date_declare;
}
public String getDate_register() {
	return date_register;
}
public void setDate_register(String date_register) {
	this.date_register = date_register;
}
public String getDate_enable() {
	return date_enable;
}
public void setDate_enable(String date_enable) {
	this.date_enable = date_enable;
}
public String getProject_duty() {
	return project_duty;
}
public void setProject_duty(String project_duty) {
	this.project_duty = project_duty;
}
public Integer getId_service() {
	return id_service;
}
public void setId_service(Integer id_service) {
	this.id_service = id_service;
}
public Service1 getService() {
	return service;
}
public void setService(Service1 service) {
	this.service = service;
}
public Integer getId_test() {
	return id_test;
}
public void setId_test(Integer id_test) {
	this.id_test = id_test;
}
public Test getTest() {
	return test;
}
public void setTest(Test test) {
	this.test = test;
}
public int getNum_floor_elevator() {
	return num_floor_elevator;
}
public void setNum_floor_elevator(int num_floor_elevator) {
	this.num_floor_elevator = num_floor_elevator;
}
public int getId_elevator_model() {
	return id_elevator_model;
}
public void setId_elevator_model(int id_elevator_model) {
	this.id_elevator_model = id_elevator_model;
}
public Elevator_type_def getElevatorType() {
	return elevatorType;
}
public void setElevatorType(Elevator_type_def elevatorType) {
	this.elevatorType = elevatorType;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public Float getGis_x() {
	return gis_x;
}
public void setGis_x(Float gis_x) {
	this.gis_x = gis_x;
}
public Float getGis_y() {
	return gis_y;
}
public void setGis_y(Float gis_y) {
	this.gis_y = gis_y;
}
public String getGis_type() {
	return gis_type;
}
public void setGis_type(String gis_type) {
	this.gis_type = gis_type;
}


 
   
   
}
