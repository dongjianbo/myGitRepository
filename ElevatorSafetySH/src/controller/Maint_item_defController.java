package controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import service.Maint_item_defService;
import vo.Maint_item_def;

@Controller
@RequestMapping("maint_item_def")
public class Maint_item_defController {
	@Resource
	public Maint_item_defService midService;
	@RequestMapping(value="listById",produces="text/html;charset=utf-8")
	@ResponseBody
	public String list(int maint_id){
		List<Maint_item_def> midlist=midService.list(maint_id);
		JSONArray array=JSONArray.fromObject(midlist);
		return array.toString();
	}
}
