package servlet;

import java.io.IOException;
import java.io.InputStream;
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
 * 批量导入学生信息
 */
@WebServlet("/teacher/ImportStudentServlet")
public class ImportStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    StudentDao stDao=new StudentDaoImp();
    BanJiDao bjDao=new BanJiDaoImp();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportStudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//添加失败名单
		ArrayList<String> errorNames=new ArrayList<>();
		
		//设置字符编码
		response.setContentType("text/html;charset=utf-8");
		//创建一个解析器工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 判断enctype属性是否为multipart/form-data 
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            try {
                //解析请求，将表单中每个输入项封装成一个FileItem对象
                List<FileItem> fileItems = upload.parseRequest(request);
                // 迭代表单数据
                for (FileItem fileItem : fileItems) {
                    //判断输入的类型是 普通输入项 还是文件
                    if (fileItem.isFormField()) {
                        //普通输入项 ,得到input中的name属性的值,fileItem.getFieldName()
                        ////得到输入项中的值,fileItem.getString("UTF-8"),"UTF-8"防止中文乱码
                        System.out.println(fileItem.getFieldName()+"\t"+fileItem.getString("UTF-8"));
                    } else {
                    	InputStream in=fileItem.getInputStream();
                        //上传的是文件，获得文件上传字段中的文件名
                    	XSSFWorkbook book=new XSSFWorkbook(in);
                    	int sheets=book.getNumberOfSheets();
                    	System.out.println(sheets);
                    	//循环所有的工作表
                    	for (int i = 0; i < sheets; i++) {
                    		Sheet sheet=book.getSheetAt(i);
                    		//获取工作表的名称
                    		String classname=sheet.getSheetName();
                    		System.out.println(classname);
                    		//获取最后一行的行号
                    		int lastrow=sheet.getLastRowNum();
                    		for (int j = 1; j <= lastrow; j++) {
								Row r=sheet.getRow(j);
								Cell cell1=r.getCell(0);
								if(cell1!=null){
									String xm=cell1.getStringCellValue();
									System.out.println(xm);
									String xb=r.getCell(1).getStringCellValue();
									System.out.println(xb);
									String jg=r.getCell(2).getStringCellValue();
									System.out.println(jg);
									String byxx=r.getCell(3).getStringCellValue();
									System.out.println(byxx);
									r.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
									String lxfs=r.getCell(4).getStringCellValue();
									System.out.println(lxfs);
									//查询该工作表的班级ID
									int bjid=bjDao.getIdByName(classname);
									if(bjid!=-1){
										Student st=new Student();
										st.setBid(bjid);
										st.setName(xm);
										st.setSex(xb);
										st.setJiguan(jg);
										st.setSchool(byxx);
										st.setTel(lxfs);
										int res=stDao.createStudent(st);
										if(res<=0){
											System.err.println("添加学员失败");
											errorNames.add(xm);
										}else{
											Account acc=new Account();
											acc.setPassword("123456");
											acc.setUname(xm);
											acc.setTruename(xm);
											acc.setRole(2);
											acc.setState(1);
											acc.setSid(res);
											AccountDao accDao=new AccountDaoImp();
											int res1=accDao.save(acc);
											if(res1<=0){
												System.err.println("添加账号失败");
												errorNames.add(xm);
											}
										}
									}else{
										request.setAttribute("error", "班级名称错误！");
									}
									
								}
							}
						}
                    	in.close();
                    }
                    fileItem.delete();
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }else{
            System.out.println("普通表单");
        }
        if(errorNames.size()>0){
        	request.setAttribute("msg", "导入完毕，有"+errorNames.size()+"个学生数据导入失败，可能姓名重复或数据格式不对！");
        	request.setAttribute("errornames", errorNames);
        }else{
        	request.setAttribute("msg", "数据导入成功！");
        }
        request.getRequestDispatcher("/importStudent.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
