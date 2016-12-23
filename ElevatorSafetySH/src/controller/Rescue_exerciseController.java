package controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import service.Rescue_exerciseService;
import vo.Rescue_exercise;

@Controller
@RequestMapping("rescue_exercise")
public class Rescue_exerciseController {
	@Resource
	public Rescue_exerciseService reService;
	@RequestMapping("rescue_exerciseList")
	public ModelAndView rescue_exerciseList(int eid,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("system/rescue_exerciseList");
		List<Rescue_exercise> reList=reService.getList(eid,10,request);
		mav.addObject("reList",reList);
		return mav;
	}
}
