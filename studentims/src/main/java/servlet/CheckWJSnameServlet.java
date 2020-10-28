package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import dao.StudentDaoImp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import table.Student;

/**
 * Servlet implementation class CheckWJSnameServlet
 */
@WebServlet("/teacher/CheckWJSnameServlet")
public class CheckWJSnameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckWJSnameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StudentDao stDao=new StudentDaoImp();
		String cname=request.getParameter("cname");
		List<Student> stList=stDao.studentUpdate(cname);
		PrintWriter pw=response.getWriter();
		JSONObject jsonObject=new JSONObject();
		if(stList.size()==0){
			//查无此人
			jsonObject.put("state", 1);
		}else
		if(stList.size()>1){
			//不够准确
			jsonObject.put("state", 2);
		}else{
			jsonObject.put("state", 3);
			jsonObject.put("stlist", JSONArray.fromObject(stList));
		}
		String res=jsonObject.toString();
		System.out.println(res);
		pw.write(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
