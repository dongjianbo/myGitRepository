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
		//�����ַ�����
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//��ȡҳ������д�ı�����
		String sid=request.getParameter("sid");
		String sname=request.getParameter("sname");
		String ssex=request.getParameter("ssex");
		String jiguan=request.getParameter("jiguan");
		String school=request.getParameter("school");
		String tel=request.getParameter("tel");
		String uname=request.getParameter("uname");
		//��װ��Student����
		Student st=new Student();
		st.setSid(Integer.parseInt(sid));
		st.setName(sname);
		st.setSex(ssex);
		st.setJiguan(jiguan);
		st.setSchool(school);
		st.setTel(tel);
		st.setUname(uname);
		//����dao��ط���,�����ѧ�����뵽���ݱ���
		StudentDao sdao=new StudentDaoImp();
		int i=sdao.updateStudent(st);
		if(i==1){
			//��ת���ɹ�ҳ��!
			response.sendRedirect(request.getContextPath()+File.separator+"updateStudentSuccess.jsp");
		}else{
			//��ת���ɹ�ҳ��!
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
