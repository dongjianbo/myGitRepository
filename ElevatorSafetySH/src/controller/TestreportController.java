package controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import service.HistoryService;
import service.History_listService;
import service.TestreportService;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;
import vo.Testreport;

@Controller
@RequestMapping("testreport")
public class TestreportController {
  @Resource
  public TestreportService testreportService;
  @Resource
  public HistoryService historyService;
  @Resource
  public History_listService history_listService;
  @RequestMapping("toInsert")
  public ModelAndView toInsert(HttpServletRequest request){
	  Operator op=(Operator)request.getSession().getAttribute("login");
		if(!op.getTypeOperator().equals("30")&&!op.getTypeOperator().equals("31")){
			ModelAndView mav=new ModelAndView("error");
			mav.addObject("error","当前登录人非检测单位人员!");
			return mav;
		}else{
			ModelAndView mav=new ModelAndView("system/insertTestReport");
			return mav;
		}
	  
  }
  @RequestMapping("insert")
  public String insert(Testreport testreport,HttpServletRequest request){
	  int id_testreport=Integer.parseInt(testreportService.insert(testreport).toString());
	  //给history插入记录
	  History history=new History();
	  history.setType(31);//类型
	  Operator op=(Operator)request.getSession().getAttribute("login");
	  history.setOperator(op.getIdoperator());//登录人id
	  Date date=new Date();
	  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String s=format.format(date);
	  history.setDatetime(s);//当前时间
	  int idhistory=Integer.parseInt(historyService.insert(history).toString());
      //给histroy_list插入记录
	  History_list hilist=new History_list();
	  History_listKey key=new History_listKey();
	  key.setIdhistory(idhistory);
	  key.setKey(31);
      hilist.setKey(key);//key表示复合主键的类
      hilist.setValue(id_testreport+"");
	  history_listService.insert(hilist);
      return "redirect:/testreport/toInsert.do";
  }
}
