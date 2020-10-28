package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import dao.StudentDaoImp;
import table.Student;

/**
 * 导出学生信息
 * Servlet implementation class ExceportStudentServlet
 */
@WebServlet("/teacher/ExceportStudentServlet")
public class ExceportStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExceportStudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");//设置request的编码方式，防止中文乱码

        String fileName ="全部学生数据.xls";//设置导出的文件名称
        //调用相关Dao,将所有的学生信息从数据库中抓取出来,传递给显示页面
  		StudentDao sdao=new StudentDaoImp();
  		List<Student> slist=sdao.studentList();
        StringBuffer sb = new StringBuffer();//将表格信息放入内存
        sb.append("<table>");
        sb.append("<tr><th>班级</th><th>姓名</th><th>性别</th><th>籍贯</th><th>毕业学校</th><th>联系电话</th><th>剩余学分</th></tr>");
        for(Student st:slist){
        	sb.append("<tr><td>"+st.getCname()+"</td><td>"+st.getName()+"</td><td>"+st.getSex()+"</td><td>"+st.getJiguan()+"</td><td>"+st.getSchool()+"</td><td>"+st.getTel()+"</td><td>"+st.getScore()+"</td></tr>");
        }
        
        sb.append("</table");
        String contentType = "application/vnd.ms-excel";//定义导出文件的格式的字符串

        String recommendedName = new String(fileName.getBytes(),"iso_8859_1");//设置文件名称的编码格式

        response.setContentType(contentType);//设置导出文件格式

        response.setHeader("Content-Disposition", "attachment; filename=" + recommendedName);//

        response.resetBuffer();

        //利用输出输入流导出文件

        ServletOutputStream sos = response.getOutputStream();

        sos.write(sb.toString().getBytes());

        sos.flush();

        sos.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
