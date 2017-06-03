package controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import service.ComplainObjectService;
import vo.ComplianObject;


@Controller
@RequestMapping("complainObject")
public class ComplainObjectController {
   @Resource
   public ComplainObjectService complainObjectService;
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<ComplianObject> complainObjectList=complainObjectService.list();
   	JSONArray array=JSONArray.fromObject(complainObjectList);
   	return array.toString();
   }
}
