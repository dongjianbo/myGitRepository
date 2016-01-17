package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.Accident_type_defService;
import vo.Accident_type_def;

@Controller
@RequestMapping("accident_type_def")
public class Accident_type_defController {
  @Resource
  public Accident_type_defService accident_type_defService;
  
  @ResponseBody
  @RequestMapping(value="list",produces="text/html;charset=utf-8")
  public String list(){
	 List<Accident_type_def> list=accident_type_defService.list();
	 JSONArray array=JSONArray.fromObject(list);
	 return array.toString();
  }
}
