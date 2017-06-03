package controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import service.Site_defService;
import vo.Site_def;


@Controller
@RequestMapping("sit_def")
public class Site_defController {
   @Resource
   public Site_defService site_defService;
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<Site_def> site_defList=site_defService.list();
   	JSONArray array=JSONArray.fromObject(site_defList);
   	return array.toString();
   }
}
