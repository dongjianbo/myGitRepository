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
		//得到生成的验证码字符
		String code=VerifyCodeUtils.generateVerifyCode(4);
		System.out.println("验证码为:"+code);
		//得到session
		HttpSession session=request.getSession();
		//将正确的验证码存入session
		session.setAttribute("yzm", code);
		//将验证码画在图片上
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
