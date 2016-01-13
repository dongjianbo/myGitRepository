package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.Servicer_type_defService;
import vo.Servicer_type_def;

@Controller
@RequestMapping("servicer_type_def")
public class Servicer_type_defController {
	@Resource
	public Servicer_type_defService  servicer_type_defService;
	//·µ»Øjson×Ö·û´®
	@ResponseBody
	@RequestMapping(value="list_json", produces = "text/html; charset=utf-8")
	public String list_json(){
		List<Servicer_type_def> servicer_type_deflist=servicer_type_defService.list();
		JSONArray array=JSONArray.fromObject(servicer_type_deflist);
		return array.toString();
	}

}
