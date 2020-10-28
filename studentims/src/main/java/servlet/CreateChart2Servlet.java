package servlet;

import java.awt.Font;
import java.io.IOException;
import java.util.Locale;

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
import org.jfree.data.general.DefaultPieDataset;

import dao.ChartDaoImp;

/**
 * Servlet implementation class CreateChart1Servlet
 */
@WebServlet("/teacher/CreateChart2Servlet")
public class CreateChart2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateChart2Servlet() {
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
	    ChartDaoImp chartDao=new ChartDaoImp();
		DefaultCategoryDataset dataset=chartDao.getDataSet_PJF();
		JFreeChart jfc=ChartFactory.createBarChart("�������Ŀƽ����һ��", "�༶", "ƽ����", dataset);
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