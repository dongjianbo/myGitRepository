package table;


public class WeiJi {

	private int id;//
	private int sid;//违纪学生id--外键	
	private String wjtime;//违纪时间
	private String wjyuanyin;//违纪原因
	private int koufen;//扣分
	private int state;//确认状态
	private String info;//补充说明
	private String chufaren;//处罚人
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getWjtime() {
		return wjtime;
	}
	public void setWjtime(String wjtime) {
		this.wjtime = wjtime;
	}
	public String getWjyuanyin() {
		return wjyuanyin;
	}
	public void setWjyuanyin(String wjyuanyin) {
		this.wjyuanyin = wjyuanyin;
	}
	public int getKoufen() {
		return koufen;
	}
	public void setKoufen(int koufen) {
		this.koufen = koufen;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getChufaren() {
		return chufaren;
	}
	public void setChufaren(String chufaren) {
		this.chufaren = chufaren;
	}
	
}
