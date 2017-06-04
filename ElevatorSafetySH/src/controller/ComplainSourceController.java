package controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import service.ComplainSourceService;
import vo.ComplianSource;


@Controller
@RequestMapping("complainSource")
public class ComplainSourceController {
   @Resource
   public ComplainSourceService complainSourceService;
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<ComplianSource> complainSourceList=complainSourceService.list();
   	JSONArray array=JSONArray.fromObject(complainSourceList);
   	return array.toString();
   }
}
