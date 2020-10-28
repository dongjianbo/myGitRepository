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
 * ��������ѧ����Ϣ
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
		//���ʧ������
		ArrayList<String> errorNames=new ArrayList<>();
		
		//�����ַ�����
		response.setContentType("text/html;charset=utf-8");
		//����һ������������
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //�ļ��ϴ�������
        ServletFileUpload upload = new ServletFileUpload(factory);
        // �ж�enctype�����Ƿ�Ϊmultipart/form-data 
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            try {
                //�������󣬽�����ÿ���������װ��һ��FileItem����
                List<FileItem> fileItems = upload.parseRequest(request);
                // ����������
                for (FileItem fileItem : fileItems) {
                    //�ж������������ ��ͨ������ �����ļ�
                    if (fileItem.isFormField()) {
                        //��ͨ������ ,�õ�input�е�name���Ե�ֵ,fileItem.getFieldName()
                        ////�õ��������е�ֵ,fileItem.getString("UTF-8"),"UTF-8"��ֹ��������
                        System.out.println(fileItem.getFieldName()+"\t"+fileItem.getString("UTF-8"));
                    } else {
                    	InputStream in=fileItem.getInputStream();
                        //�ϴ������ļ�������ļ��ϴ��ֶ��е��ļ���
                    	XSSFWorkbook book=new XSSFWorkbook(in);
                    	int sheets=book.getNumberOfSheets();
                    	System.out.println(sheets);
                    	//ѭ�����еĹ�����
                    	for (int i = 0; i < sheets; i++) {
                    		Sheet sheet=book.getSheetAt(i);
                    		//��ȡ�����������
                    		String classname=sheet.getSheetName();
                    		System.out.println(classname);
                    		//��ȡ���һ�е��к�
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
									//��ѯ�ù�����İ༶ID
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
											System.err.println("���ѧԱʧ��");
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
												System.err.println("����˺�ʧ��");
												errorNames.add(xm);
											}
										}
									}else{
										request.setAttribute("error", "�༶���ƴ���");
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
            System.out.println("��ͨ��");
        }
        if(errorNames.size()>0){
        	request.setAttribute("msg", "������ϣ���"+errorNames.size()+"��ѧ�����ݵ���ʧ�ܣ����������ظ������ݸ�ʽ���ԣ�");
        	request.setAttribute("errornames", errorNames);
        }else{
        	request.setAttribute("msg", "���ݵ���ɹ���");
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
