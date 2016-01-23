package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.DistictlistService;
import vo.Distictlist;

@Controller
@RequestMapping("distictlist")
public class DistictlistController {
	@Resource
	public DistictlistService distictlistService;
	
	@RequestMapping(value="listByIdCity", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getListByIdCity(String id_city){
		List<Distictlist> list=distictlistService.getListByCityId(id_city);
		JSONArray jsonarray=JSONArray.fromObject(list);
		System.out.println(jsonarray.toString());
		return jsonarray.toString();
	}
}
