package controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import service.ComplainLevelService;
import vo.ComplianLevel;


@Controller
@RequestMapping("complainLevel")
public class ComplainLevelController {
   @Resource
   public ComplainLevelService complainLevelService;
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<ComplianLevel> complainLevelList=complainLevelService.list();
   	JSONArray array=JSONArray.fromObject(complainLevelList);
   	return array.toString();
   }
}
