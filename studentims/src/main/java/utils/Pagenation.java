package utils;
/*
 * ��ҳ���
 */
public class Pagenation {
	//�ܼ�¼��
	private int total;
	//��ҳ��
	private int pages;
	//ÿҳ����
	private int rows=20;
	//��ǰҳ
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
