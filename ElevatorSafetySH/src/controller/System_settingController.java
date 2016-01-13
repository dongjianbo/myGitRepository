package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.System_settingService;
import vo.Citylist;
import vo.System_setting;

@Controller
@RequestMapping("system_setting")
public class System_settingController {
	@Resource
	public System_settingService  system_settingService;
	//·µ»Øjson×Ö·û´®
	@ResponseBody
	@RequestMapping(value="list", produces = "text/html; charset=utf-8")
	public String list(){
		List<System_setting> system_settinglist=system_settingService.list();
		JSONArray array=JSONArray.fromObject(system_settinglist);
		return array.toString();
	}

}
