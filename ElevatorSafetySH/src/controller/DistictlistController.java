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
	@ResponseBody
	@RequestMapping(value="listByIdCity", produces = "text/html; charset=utf-8")
	public String getListByIdCity(String id_city){
		System.out.println(id_city);
		List<Distictlist> list=distictlistService.getListByCityId(id_city);
		JSONArray jsonarray=JSONArray.fromObject(list);
		return jsonarray.toString();
	}
}
