package servlet;

import java.awt.Font;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.category.DefaultCategoryDataset;
import dao.ChartDaoImp;

/**
 * Servlet implementation class CreateChart1Servlet
 */
@WebServlet("/teacher/CreateChart1Servlet")
public class CreateChart1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateChart1Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		//����������ʽ  
	    StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
	    //���ñ�������  
	    standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));  
	    //����ͼ��������  
	    standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15));  
	    //�������������  
	    standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15));  
	    //Ӧ��������ʽ  
	    ChartFactory.setChartTheme(standardChartTheme); 
	    //��ѯÿ��ÿ�ſƵļ�����
	    ChartDaoImp chartDao=new ChartDaoImp();
		DefaultCategoryDataset dataset=chartDao.getDataSet_JGL();
		JFreeChart jfc=ChartFactory.createBarChart("�������Ŀ������һ��", "�༶", "������", dataset);
		ChartUtils.writeChartAsJPEG(response.getOutputStream(), jfc, 800, 500);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
