package controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import service.ApproveAckService;
import vo.Approve_ack;


@Controller
@RequestMapping("approve_ack")
public class ApproveAckController {
   @Resource
   public ApproveAckService approveAckService;
   @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
   @ResponseBody
   public String list_json(){
   	List<Approve_ack> approve_ackList=approveAckService.list();
   	JSONArray array=JSONArray.fromObject(approve_ackList);
   	return array.toString();
   }
}
