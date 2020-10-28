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
		//�����ַ�����
		response.setContentType("text/html;charset=utf-8");
		//��ȡҳ������д�ı�����
		int bjid=Integer.parseInt(request.getParameter("bjid"));
		String sname=request.getParameter("sname");
		String ssex=request.getParameter("ssex");
		String jiguan=request.getParameter("jiguan");
		String school=request.getParameter("school");
		String tel=request.getParameter("tel");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		//��װ��Student����
		Student st=new Student();
		st.setBid(bjid);
		st.setName(sname);
		st.setSex(ssex);
		st.setJiguan(jiguan);
		st.setSchool(school);
		st.setTel(tel);
		//����dao��ط���,�����ѧ�����뵽���ݱ���
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
			//��ת���ɹ�ҳ��!
			response.sendRedirect(request.getContextPath()+"/createStudentSuccess.jsp");
		}else{
			//��ת������ҳ�棡
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
