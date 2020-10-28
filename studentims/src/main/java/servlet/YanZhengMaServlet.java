package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.VerifyCodeUtils;

/**
 * Servlet implementation class YanZhengMaServlet
 */
@WebServlet("/YanZhengMaServlet")
public class YanZhengMaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YanZhengMaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		//�õ����ɵ���֤���ַ�
		String code=VerifyCodeUtils.generateVerifyCode(4);
		System.out.println("��֤��Ϊ:"+code);
		//�õ�session
		HttpSession session=request.getSession();
		//����ȷ����֤�����session
		session.setAttribute("yzm", code);
		//����֤�뻭��ͼƬ��
		VerifyCodeUtils.outputImage(120, 40, response.getOutputStream(), code);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
