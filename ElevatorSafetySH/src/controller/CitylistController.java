package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.CitylistService;
import vo.Citylist;

@Controller
@RequestMapping("citylist")
public class CitylistController {
	@Resource
	public CitylistService citylistService;
	//·µ»Øjson×Ö·û´®
	@ResponseBody
	@RequestMapping(value="list", produces = "text/html; charset=utf-8")
	public String list(){
		List<Citylist> citylist=citylistService.list();
		JSONArray array=JSONArray.fromObject(citylist);
		return array.toString();
	}
	public int jiecheng(int n){
		if(n==0){
			return 1;
		}else{
			return n*jiecheng(n-1);
		}
	}
}
