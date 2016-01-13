package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.Elevator_type_defService;
import service.Servicer_type_defService;
import vo.Elevator_type_def;
import vo.Servicer_type_def;

@Controller
@RequestMapping("elevator_type_def")
public class Elevator_type_defController {
	@Resource
	public Elevator_type_defService elevator_type_defService;
	//·µ»Øjson×Ö·û´®
	@ResponseBody
	@RequestMapping(value="list_json", produces = "text/html; charset=utf-8")
	public String list_json(){
		List<Elevator_type_def> elevator_type_deflist=elevator_type_defService.list();
		JSONArray array=JSONArray.fromObject(elevator_type_deflist);
		return array.toString();
	}

}
