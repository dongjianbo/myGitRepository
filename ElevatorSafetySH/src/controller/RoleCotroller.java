package controller;



import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import service.HistoryService;
import service.History_listService;
import service.RoleService;
import service.ServiceService;
import vo.Role;


@Controller
@RequestMapping("role")
public class RoleCotroller {
	@Resource
	public RoleService roleService;
	@Resource
	public ServiceService serviceService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	 @RequestMapping("list")
	    public ModelAndView list(String key,HttpServletRequest request){
			ModelAndView mav=new ModelAndView("system/roleList");
			System.out.println(key);
			mav.addObject("roleList",roleService.list(key, 12, request));
			return mav;
		}
	 
    @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
    @ResponseBody
    public String list_json(){
    	List<Role> roleList=roleService.list();
    	JSONArray array=JSONArray.fromObject(roleList);
    	return array.toString();
    }
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Role role,HttpServletRequest request){
		roleService.insert(role);
		return "ok";
	}
	@RequestMapping(value="toUpdate",produces="text/html;charset=utf-8")
	@ResponseBody
	public String toUpdate(Role role){
		role=roleService.findById(role.getIdrole());
		JSONObject object=JSONObject.fromObject(role);
		return object.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Role role){
		roleService.update(role);
		return "ok";
	}
	@RequestMapping("delete")
	public String delete(Role role){
		roleService.delete(role);
		return "redirect:/role/list.do";
	}
}
