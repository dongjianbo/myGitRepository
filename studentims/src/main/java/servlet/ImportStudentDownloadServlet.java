package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.AccountDao;
import dao.AccountDaoImp;
import dao.BanJiDao;
import dao.BanJiDaoImp;
import dao.StudentDao;
import dao.StudentDaoImp;
import table.Account;
import table.Student;

/**
 * 下载学生模板
 */
@WebServlet("/teacher/ImportStudentDownloadServlet")
public class ImportStudentDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportStudentDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/octet-stream");
        //解决 以文件形式下载 而不会被浏览器打开    以及中文文件名需要编码
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("学员信息批量导入模板", "utf-8")+".xlsx");
        OutputStream os = response.getOutputStream();
        String path = this.getServletContext().getRealPath("/download");
        FileInputStream input=new FileInputStream(new File(path,"学员信息批量导入模板.xlsx"));
        int len=0;
        byte[] buffer = new byte[8192];
        while((len=input.read(buffer))!=-1){
            os.write(buffer, 0, len);
        }
        input.close();
        os.flush();
        os.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
