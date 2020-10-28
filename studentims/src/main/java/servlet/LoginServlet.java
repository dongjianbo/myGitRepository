package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.jws.soap.InitParam;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDao;
import dao.AccountDaoImp;
import table.Account;

/**
 * Servlet implementation class LoginServlet
 */
/**
 * 
 * @WebServlet��ע��
 * ���ע���������÷������servlet��path
 *
 */
@WebServlet(value="/LoginServlet",initParams={@WebInitParam(name="e",value="utf-8")})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     * web�������ڼ��ز�����servlet��ʱ����Զ�����init()
     * init()�������serlvet�ĵ���
     */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}
	


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�����ַ�����ΪUTF-8
		response.setContentType("text/html; charset=UTF-8");
		// TODO Auto-generated method stub
		//response������Ӧ
		//response.getWriter();�����Ӧ�����,����ͨ�������������ַ��������Ӧҳ����.
		//request.getContextPath();���webӦ����
		//��ȡҳ���ύ������
		String uname=request.getParameter("username");
		String pwd=request.getParameter("password");
		String yzm=request.getParameter("yzm");
		//��֤��֤���Ƿ���ȷ
		HttpSession session=request.getSession();
		String trueyzm=session.getAttribute("yzm").toString();
		if(trueyzm!=null&&trueyzm.equalsIgnoreCase(yzm)){
			AccountDao adao=new AccountDaoImp();
			Account acc=new Account();
			acc.setUname(uname);
			acc.setPassword(pwd);
			int i=adao.login(acc);
			String errorMessage="";
			switch(i){
				case 1:errorMessage="�û���������";break;
				case 2:errorMessage="�������";break;
				case 3:errorMessage="�˺��쳣,�������Ա��ϵ";break;
			}
			if(i==1||i==2||i==3){
				//��em����request
				request.setAttribute("em", errorMessage);
				//request����תĿ��
				RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);//����¼���ɹ�ʱ,���ص���¼ҳ�����µ�¼
			}
			if(i==4){
				//���û������������cookie,�´��ٵ�¼��ʱ��,�����Զ���ʾ
				Cookie cname=new Cookie("n", URLEncoder.encode(uname, "UTF-8"));
				Cookie pname=new Cookie("p", URLEncoder.encode(pwd, "UTF-8"));
				cname.setMaxAge(60*60);
				pname.setMaxAge(60*60);
				response.addCookie(cname);
				response.addCookie(pname);
				System.out.println("cookie����ɹ�!");
				//����¼�ɹ���,�鵽��¼�˵���Ϣ,�����ݵ�Ŀ��ҳ��ȥ��ʹ��
				Account acc1=adao.getAccount(uname, pwd);
				//�����������뵽session��,�Ự�е�����ҳ��Ҳ����ʹ��
				session.setAttribute("user", acc1);
				//request����תĿ��
				//RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				//rd.forward(request, response);//����¼�ɹ���,��ת����ҳ
				//ʹ���ض�����Ա����ظ��ĵ�¼
				response.sendRedirect("index.jsp");
			}
		}else{
			//��em����request
			request.setAttribute("em", "��֤����д����!");
			//request����תĿ��
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);//����¼���ɹ�ʱ,���ص���¼ҳ�����µ�¼
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
