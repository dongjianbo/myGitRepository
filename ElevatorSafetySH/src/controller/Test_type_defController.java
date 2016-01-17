package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.Test_type_defService;
import vo.Test_type_def;

@Controller
@RequestMapping("test_type_def")
public class Test_type_defController {
	@Resource
	public Test_type_defService test_type_defService;
	@ResponseBody
   @RequestMapping(value="list",produces="text/html;charset=utf-8")
   public String list(){
	  List<Test_type_def> list=test_type_defService.list();
	  JSONArray array=JSONArray.fromObject(list);
	  return array.toString();
   }
}
