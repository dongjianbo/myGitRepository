package util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class PageService
{
  public int page = 1;
  public int pageSize;
  public int pageCount;
  public int rowCount;
  public String httpFile;
  public StringBuffer pageParameter;
  public String basepath;

  @SuppressWarnings("rawtypes")
public int jlist(int pageSize, int rowCount, HttpServletRequest request)
  {
	this.basepath=request.getServletContext().getContextPath();
    this.rowCount = rowCount;
    this.pageSize = pageSize;
    this.pageParameter = new StringBuffer();
    try {
      if (pageSize <= 0)
        pageSize = 5;

      this.httpFile = request.getRequestURI();
      String nowPage = request.getParameter("page");
      Enumeration parames = request.getParameterNames();
      while (parames.hasMoreElements()) {
        String name = (String)parames.nextElement();
        String value = request.getParameter(name);
        if ((!(name.equals("page"))) && (!(value.equals("")))) {
          this.pageParameter.append("<input type=hidden name=");
          this.pageParameter.append(name);
          this.pageParameter.append(" value=");
          this.pageParameter.append(value);
          this.pageParameter.append(">");
        }
      }
      if ((nowPage != null) && (!(nowPage.equals("")))) {
        this.page = Integer.parseInt(nowPage);
        if (this.page < 1)
          this.page = 1;
      }
      this.pageCount = ((rowCount + pageSize - 1) / pageSize);
      if (this.page > this.pageCount){
    	  this.page = this.pageCount;
      }
     
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    request.setAttribute("pagination", PageFooter());
    return this.page;
  }

  public String PageFooter() {
    StringBuffer str = new StringBuffer();
    str.append("<script type=text/javascript>function pagego(p){var page = document.getElementById('page');page.value=p;document.formin.submit();}</script>");
    str.append("<form action=");
    str.append(getHttpFile());
    str.append(" name=formin method=post>");
    if (getPage() > 1) {
      str.append("<A href=javascript:pagego('1')><img src='"+basepath+"/images/report/firstPage.gif'/></A>&nbsp;&nbsp;");
      str.append(" <A href=javascript:pagego('");
      str.append(getPage() - 1);
      str.append("')><img src='"+basepath+"/images/report/prePage.gif'/></A>&nbsp;&nbsp;");
    } else {
      str.append("<img src='"+basepath+"/images/report/firstPage.gif'/>&nbsp;&nbsp;");
      str.append("<img src='"+basepath+"/images/report/prePage.gif'/>&nbsp;&nbsp;");
    }
    
    if (getPage() < getPageCount()) {
      str.append(" <A href=javascript:pagego('");
      str.append(getPage() + 1);
      str.append("')><img src='"+basepath+"/images/report/nextPage.gif'/></A>&nbsp;&nbsp;");
      str.append(" <A href=javascript:pagego('");
      str.append(getPageCount());
      str.append("')><img src='"+basepath+"/images/report/endPage.gif'/></A>&nbsp;&nbsp;");
    } else {
      str.append("<img src='"+basepath+"/images/report/nextPage.gif'/>&nbsp;&nbsp;");
      str.append("<img src='"+basepath+"/images/report/endPage.gif'/>&nbsp;&nbsp;");
    }
    str.append("&nbsp;&nbsp;页次：<font color=#000000>[");
    str.append(getPage());
    str.append("/");
    str.append(getPageCount());
    str.append("]</font>");
    str.append("&nbsp;&nbsp;&nbsp;&nbsp;转至<input name=page id=page type=text class=page value=");
    str.append(getPage());
    str.append(" style='width: 25px;height: 18px' maxlength=3 onkeyup=javascript:value=value.replace(/[^\\d]/g,'')>&nbsp;&nbsp;");
    str.append(getPageParameter());
    str.append("&nbsp;&nbsp;<a href='javascript:document.formin.submit();'>GO</a>&nbsp;&nbsp;");
    str.append("</form>");
    return str.toString();
  }

  public void notLog(HttpServletRequest request) {
    request.setAttribute
      ("pagination", 
      "<div align=center><font color=black size=2>暂无数据</font></div>");
  }

  public String getHttpFile() {
    return this.httpFile;
  }

  public void setHttpFile(String httpFile) {
    this.httpFile = httpFile;
  }

  public int getPage() {
    return this.page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPageCount() {
    return this.pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public StringBuffer getPageParameter() {
    return this.pageParameter;
  }

  public void setPageParameter(StringBuffer pageParameter) {
    this.pageParameter = pageParameter;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getRowCount() {
    return this.rowCount;
  }

  public void setRowCount(int rowCount) {
    this.rowCount = rowCount;
  }
}