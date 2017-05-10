package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.CitylistService;
import service.DesignerService;
import service.HistoryService;
import service.History_listService;
import vo.Citylist;
import vo.Designer;
import vo.History;
import vo.History_list;
import vo.History_listKey;
import vo.Operator;

@Controller
@RequestMapping("designer")
public class DesignerController {
	@Resource
	public DesignerService designerService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	@Resource
	public CitylistService cityService;
	/**
	 * ��������Ա�еĵ�λ����
	 * @param key
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/designerList");
		List<Designer> dlist=designerService.list(key,12,request);
		for(Designer d:dlist){
			String code=d.getRegister_area();
			Citylist city=cityService.listBy_Idcity(code);
			d.setRegistCity(city);
		}
		mav.addObject("designerList", dlist);
		return mav;
	}
	/**
	 * �����ල��Ա�����쵥λ��ѯ
	 * @param key
	 * @param request
	 * @return
	 */
	@RequestMapping("list1")
	public ModelAndView list1(String key,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/designerList1");
		List<Designer> dlist=designerService.list(key,12,request);
		for(Designer d:dlist){
			String code=d.getRegister_area();
			Citylist city=cityService.listBy_Idcity(code);
			d.setRegistCity(city);
		}
		mav.addObject("designerList", dlist);
		return mav;
	}
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Designer designer,HttpServletRequest request,HttpServletResponse response){
		//������Ƶ�λ��Ϣ
		int iddesigner=Integer.parseInt(designerService.insert(designer).toString());
		System.out.println(iddesigner);
		//����history��Ϣ
		History history=new History();
		history.setType(1);//����
		Operator op=(Operator)request.getSession().getAttribute("login");
		history.setOperator(op.getIdoperator());//��¼��id
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=format.format(date);
		history.setDatetime(s);//��ǰʱ��
		int idhistory=Integer.parseInt(historyService.insert(history).toString());
		//������Ϣ��history_list����
	    History_list hilist=new History_list();
	    History_listKey key=new History_listKey();
	    key.setIdhistory(idhistory);
	    key.setKey(1);
        hilist.setKey(key);//key��ʾ������������
        hilist.setValue(iddesigner+"");
        System.out.println( history_listService.insert(hilist).toString());
		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Designer designer){
		designer=designerService.findById(designer.getIddesigner());
		JSONObject object=JSONObject.fromObject(designer);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Designer designer){
		designerService.update(designer);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Designer designer){
		designerService.delete(designer);
		return "redirect:/designer/list.do";
	}
	@RequestMapping(value="selectId_designer",produces="text/html;charset=utf-8")
	@ResponseBody
	public String selectId_designer(){
		List<Designer> list=designerService.getId_designer();
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
}
