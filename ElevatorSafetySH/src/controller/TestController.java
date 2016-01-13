package controller;

import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.HistoryService;
import service.History_listService;
import service.TestService;
import vo.Service1;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;
import vo.Test;

@Controller
@RequestMapping("test")
public class TestController {
	@Resource
	public TestService testService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@RequestMapping("list")
	public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/testList");
		mav.addObject("testList", testService.list(key,12,request));
		return mav;
	}
	 @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
	    @ResponseBody
	    public String list_json(){
	    	List<Test> testList=testService.list();
	    	JSONArray array=JSONArray.fromObject(testList);
	    	return array.toString();
	    }
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Test test,HttpServletRequest request){
		int idtest=Integer.parseInt(testService.insert(test).toString());
		request.getSession().setAttribute("idtest",idtest);
		History history=new History();
		history.setType(4);//类型
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//登录人id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//当前时间
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//插入信息到systemstate表中（待写）
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(4);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(idtest+"");
        System.out.println( history_listService.insert(hilist).toString());
		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Test test){
		test=testService.findById(test.getIdtest());
		JSONObject object=JSONObject.fromObject(test);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Test test){
		testService.update(test);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Test test){
		testService.delete(test);
		return "redirect:/test/list.do";
	}
	@RequestMapping(value="selectId_test",produces="text/html;charset=utf-8")
	@ResponseBody
	public String selectId_test(){
		List<Test> list=testService.selectId_test();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
}
