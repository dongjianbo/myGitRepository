package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="system_setting")
public class System_setting {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="alarm_15_service")
	private int alarm_15_service;
	public int getAlarm_15_service() {
		return alarm_15_service;
	}
	public void setAlarm_15_service(int alarm_15_service) {
		this.alarm_15_service = alarm_15_service;
	}
	public int getAlarm_90_service() {
		return alarm_90_service;
	}
	public void setAlarm_90_service(int alarm_90_service) {
		this.alarm_90_service = alarm_90_service;
	}
	public int getAlarm_180_service() {
		return alarm_180_service;
	}
	public void setAlarm_180_service(int alarm_180_service) {
		this.alarm_180_service = alarm_180_service;
	}
	public int getAlarm_360_service() {
		return alarm_360_service;
	}
	public void setAlarm_360_service(int alarm_360_service) {
		this.alarm_360_service = alarm_360_service;
	}
	public int getAlarm_rounds() {
		return alarm_rounds;
	}
	public void setAlarm_rounds(int alarm_rounds) {
		this.alarm_rounds = alarm_rounds;
	}
	public int getAlarm_test() {
		return alarm_test;
	}
	public void setAlarm_test(int alarm_test) {
		this.alarm_test = alarm_test;
	}
	private int alarm_90_service;
	private int alarm_180_service;
	private int alarm_360_service;
	private int alarm_rounds;
	private int alarm_test;
}
