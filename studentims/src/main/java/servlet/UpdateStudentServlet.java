package servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import dao.StudentDaoImp;
import table.Student;

/**
 * Servlet implementation class CreateStudentServlet
 */
@WebServlet("/teacher/UpdateStudentServlet")
public class UpdateStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateStudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置字符编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//获取页面上填写的表单数据
		String sid=request.getParameter("sid");
		String sname=request.getParameter("sname");
		String ssex=request.getParameter("ssex");
		String jiguan=request.getParameter("jiguan");
		String school=request.getParameter("school");
		String tel=request.getParameter("tel");
		String uname=request.getParameter("uname");
		//封装成Student对象
		Student st=new Student();
		st.setSid(Integer.parseInt(sid));
		st.setName(sname);
		st.setSex(ssex);
		st.setJiguan(jiguan);
		st.setSchool(school);
		st.setTel(tel);
		st.setUname(uname);
		//调用dao相关方法,将这个学生插入到数据表中
		StudentDao sdao=new StudentDaoImp();
		int i=sdao.updateStudent(st);
		if(i==1){
			//跳转到成功页面!
			response.sendRedirect(request.getContextPath()+File.separator+"updateStudentSuccess.jsp");
		}else{
			//跳转到成功页面!
			response.sendRedirect(request.getContextPath()+File.separator+"updateStudentSuccess.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
