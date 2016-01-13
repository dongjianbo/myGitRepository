package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.Register_status_defService;
import vo.Register_status_def;


@Controller
@RequestMapping("register_status_def")
public class Register_status_defController {
   @Resource
   public Register_status_defService Register_status_defService;
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<Register_status_def> register_status_defList=Register_status_defService.list();
   	JSONArray array=JSONArray.fromObject(register_status_defList);
   	return array.toString();
   }
}
