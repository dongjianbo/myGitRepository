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
import service.UserService;
import vo.Service1;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;
import vo.User;

@Controller
@RequestMapping("user")
public class UserController {
   @Resource
   public UserService userService;
   @Resource
   public HistoryService historyService;
   @Resource
   public History_listService history_listService;
   @RequestMapping("list")
   public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/userList");
		mav.addObject("userList",userService.list(key, 12, request));
		return mav;
	}
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<User> userList=userService.list();
   	JSONArray array=JSONArray.fromObject(userList);
   	return array.toString();
   }
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(User user,HttpServletRequest request){
		int iduser=Integer.parseInt(userService.insert(user).toString());
		request.getSession().setAttribute("iduser", iduser);
		History history=new History();
		history.setType(7);//类型
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
	    key.setKey(7);
        hilist.setKey(key);//key表示复合主键的类
        hilist.setValue(iduser+"");
		System.out.println( history_listService.insert(hilist).toString());
		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(User user){
		user=userService.findById(user.getIduser());
		JSONObject object=JSONObject.fromObject(user);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(User user){
		userService.update(user);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(User user){
		userService.delete(user);
		return "redirect:/user/list.do";
	}
	@ResponseBody
	@RequestMapping(value="selectId_user",produces="text/html;charset=utf-8")
	public String selectId_user(){
		List<User> list=userService.list();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
}
