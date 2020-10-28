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
		//创建主题样式  
	    StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
	    //设置标题字体  
	    standardChartTheme.setExtraLargeFont(new Font("楷体",Font.BOLD,20));  
	    //设置图例的字体  
	    standardChartTheme.setRegularFont(new Font("楷体",Font.PLAIN,15));  
	    //设置轴向的字体  
	    standardChartTheme.setLargeFont(new Font("楷体",Font.PLAIN,15));  
	    //应用主题样式  
	    ChartFactory.setChartTheme(standardChartTheme); 
	    //查询每班每门科的及格率
	    ChartDaoImp chartDao=new ChartDaoImp();
		DefaultCategoryDataset dataset=chartDao.getDataSet_JGL();
		JFreeChart jfc=ChartFactory.createBarChart("各班各科目及格率一览", "班级", "及格率", dataset);
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
