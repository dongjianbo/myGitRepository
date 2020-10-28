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
 * ����ѧ����Ϣ
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
		request.setCharacterEncoding("UTF-8");//����request�ı��뷽ʽ����ֹ��������

        String fileName ="ȫ��ѧ������.xls";//���õ������ļ�����
        //�������Dao,�����е�ѧ����Ϣ�����ݿ���ץȡ����,���ݸ���ʾҳ��
  		StudentDao sdao=new StudentDaoImp();
  		List<Student> slist=sdao.studentList();
        StringBuffer sb = new StringBuffer();//�������Ϣ�����ڴ�
        sb.append("<table>");
        sb.append("<tr><th>�༶</th><th>����</th><th>�Ա�</th><th>����</th><th>��ҵѧУ</th><th>��ϵ�绰</th><th>ʣ��ѧ��</th></tr>");
        for(Student st:slist){
        	sb.append("<tr><td>"+st.getCname()+"</td><td>"+st.getName()+"</td><td>"+st.getSex()+"</td><td>"+st.getJiguan()+"</td><td>"+st.getSchool()+"</td><td>"+st.getTel()+"</td><td>"+st.getScore()+"</td></tr>");
        }
        
        sb.append("</table");
        String contentType = "application/vnd.ms-excel";//���嵼���ļ��ĸ�ʽ���ַ���

        String recommendedName = new String(fileName.getBytes(),"iso_8859_1");//�����ļ����Ƶı����ʽ

        response.setContentType(contentType);//���õ����ļ���ʽ

        response.setHeader("Content-Disposition", "attachment; filename=" + recommendedName);//

        response.resetBuffer();

        //������������������ļ�

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
