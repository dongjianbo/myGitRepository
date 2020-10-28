package utils;
/*
 * 分页组件
 */
public class Pagenation {
	//总记录数
	private int total;
	//总页数
	private int pages;
	//每页行数
	private int rows=20;
	//当前页
	private int page;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
}
