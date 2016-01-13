package controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.HistoryService;
import service.History_listService;
import service.SaferService;
import service.SystemstateService;
import vo.Designer;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;
import vo.Safer;

@Controller
@RequestMapping("safer")
public class SaferController {
  @Resource
  public SaferService saferService;
  @Resource
  public HistoryService  historyService;
  @Resource
  public History_listService  history_listService; 
  @Resource
  public SystemstateService systemstateService; 
  @RequestMapping("list")
  public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/saferList");
		mav.addObject("saferList",saferService.list(key, 10, request));
		return mav;
	}

	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Safer safer,HttpServletRequest request){
		
		//���밲ȫ��Ա��Ϣ
		int idsafer=Integer.parseInt(saferService.insert(safer).toString());//���ر������
		//�޸�systemstate��Ϣ
		systemstateService.update();
		//����history��Ϣ
		History history=new History();
		history.setType(13);//����
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//��½��id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//��ǰʱ��
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		System.out.println("idhistory:"+idhistory);
		
		//����histroy_list��Ϣ
		History_list  history_list=new History_list();
		History_listKey  history_listKey=new History_listKey();  
		history_listKey.setIdhistory(idhistory);
		history_listKey.setKey(13);
		history_list.setKey(history_listKey);;
		history_list.setValue(idsafer+"");
		history_listService.insert(history_list);
		
		//����IC����Ϣ
		return "ok";
	}
	
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Safer safer){
		safer=saferService.findById(safer.getIdsafer());
		JSONObject object=JSONObject.fromObject(safer);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Safer safer){
		saferService.update(safer);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Safer safer){
		saferService.delete(safer);
		return "redirect:/safer/list.do";
	}
}
