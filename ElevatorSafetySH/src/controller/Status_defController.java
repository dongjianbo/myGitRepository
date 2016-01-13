package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.Servicer_type_defService;
import service.Status_defService;
import vo.Servicer_type_def;
import vo.Status_def;

@Controller
@RequestMapping("status_def")
public class Status_defController {
	@Resource
	public Status_defService  status_defService;
	//·µ»Øjson×Ö·û´®
	@ResponseBody
	@RequestMapping(value="list_json", produces = "text/html; charset=utf-8")
	public String list_json(){
		List<Status_def> servicer_type_deflist=status_defService.list();
		JSONArray array=JSONArray.fromObject(servicer_type_deflist);
		return array.toString();
	}
}
