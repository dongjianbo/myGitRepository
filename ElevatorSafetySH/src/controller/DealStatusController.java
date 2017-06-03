package controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import service.DealStatusService;
import vo.DealStatus;


@Controller
@RequestMapping("dealStatus")
public class DealStatusController {
   @Resource
   public DealStatusService dealStatusService;
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<DealStatus> dealStatusList=dealStatusService.list();
   	JSONArray array=JSONArray.fromObject(dealStatusList);
   	return array.toString();
   }
}
