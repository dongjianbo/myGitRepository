package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDao;
import dao.AccountDaoImp;
import dao.StudentDao;
import dao.StudentDaoImp;
import table.Account;
import table.Student;

/**
 * Servlet implementation class CreateStudentServlet
 */
@WebServlet("/teacher/CreateStudentServlet")
public class CreateStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateStudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置字符编码
		response.setContentType("text/html;charset=utf-8");
		//获取页面上填写的表单数据
		int bjid=Integer.parseInt(request.getParameter("bjid"));
		String sname=request.getParameter("sname");
		String ssex=request.getParameter("ssex");
		String jiguan=request.getParameter("jiguan");
		String school=request.getParameter("school");
		String tel=request.getParameter("tel");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		//封装成Student对象
		Student st=new Student();
		st.setBid(bjid);
		st.setName(sname);
		st.setSex(ssex);
		st.setJiguan(jiguan);
		st.setSchool(school);
		st.setTel(tel);
		//调用dao相关方法,将这个学生插入到数据表中
		StudentDao sdao=new StudentDaoImp();
		int sid=sdao.createStudent(st);
		Account acc=new Account();
		acc.setPassword(password);
		acc.setUname(username);
		acc.setTruename(sname);
		acc.setRole(2);
		acc.setState(1);
		acc.setSid(sid);
		AccountDao accDao=new AccountDaoImp();
		int i=accDao.save(acc);
		if(i==1){
			//跳转到成功页面!
			response.sendRedirect(request.getContextPath()+"/createStudentSuccess.jsp");
		}else{
			//跳转到出错页面！
			response.sendRedirect(request.getContextPath()+"/500.html");
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
