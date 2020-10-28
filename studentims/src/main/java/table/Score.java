package table;

public class Score {
	private int id;
	private int sid;
	private int cid;
	private int score;
	/**
	 * 非表字段
	 * 
	 */
	private String sname;//学生姓名
	private String cname;//课程名
	private String bjname;//班级名
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
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getBjname() {
		return bjname;
	}
	public void setBjname(String bjname) {
		this.bjname = bjname;
	}
	
	
}
