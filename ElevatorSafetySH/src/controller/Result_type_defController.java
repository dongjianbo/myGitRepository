package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.Result_type_defService;
import vo.Result_type_def;
@Controller
@RequestMapping("result_type_def")
public class Result_type_defController {
	@Resource
	public Result_type_defService result_type_defService;
	@ResponseBody//JSON字符串返回要用到这个属性 界面上才可以接受
   @RequestMapping(value="list",produces="text/html;charset=utf-8")
   public String list(){
	  List<Result_type_def> list=result_type_defService.list();
	  JSONArray array=JSONArray.fromObject(list);
	  return array.toString();
   }
}
