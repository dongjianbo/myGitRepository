package controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.Operator_typeService;
import vo.Operator_type;


@Controller
@RequestMapping("operator_type_def")
public class Operator_typeController {
   @Resource
   public Operator_typeService operator_typeService;
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<Operator_type> operator_type_defList=operator_typeService.list();
   	JSONArray array=JSONArray.fromObject(operator_type_defList);
   	return array.toString();
   }
}
