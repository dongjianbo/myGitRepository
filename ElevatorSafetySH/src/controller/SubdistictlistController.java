package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.SubdistictlistService;
import vo.Subdistictlist;

@Controller
@RequestMapping("subdistictlist")
public class SubdistictlistController {
	@Resource
	public SubdistictlistService subdistictlistService;
	@ResponseBody
	@RequestMapping(value="listById",produces="text/html;charset=utf-8")
	public String listById(String id_city,String id_distrct){
		System.out.println(id_city+"======"+id_distrct);
		List<Subdistictlist> list=subdistictlistService.getListById(id_city, id_distrct);
		JSONArray array=JSONArray.fromObject(list);
		return array.toString();
	}
}
