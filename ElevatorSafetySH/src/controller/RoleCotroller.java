package controller;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import service.System_menuService;
import vo.Role;
import vo.System_menu;


@Controller
@RequestMapping("role")
public class RoleCotroller {
	@Resource
	public RoleService roleService;
	@Resource
	public System_menuService systemmenuService;
	@Resource
	public ServiceService serviceService;
	@Resource
	public HistoryService historyService;
	@Resource
	public History_listService history_listService;
	 @RequestMapping("list")
	    public ModelAndView list(String key,HttpServletRequest request){
			ModelAndView mav=new ModelAndView("system/roleList");
			mav.addObject("roleList",roleService.list(key, 12, request));
			mav.addObject("menuList",systemmenuService.list());
			return mav;
		}
	 
    @RequestMapping(value="list_json",produces="text/html;charset=utf-8")
    @ResponseBody
    public String list_json(){
    	List<Role> roleList=roleService.list();
    	for(Role role:roleList){
    		//因为role类下配置多对多关系 下的menus没有级联查询，导致转json的时候需要获取menus（就会一直报懒加载错误） ，但是我们并不需要这个menus附属对象，所以直接给menus赋予一个控制就可以了
			role.setMenus(null);
    	}
    	JSONArray array=JSONArray.fromObject(roleList);
    	return array.toString();
    }
    
	@RequestMapping(value="insert",produces="text/html;charset=utf-8")
	@ResponseBody
	public String insert(Role role,int menus_id[],HttpServletRequest request){
		Set<System_menu> menuSet=new HashSet<System_menu>();
		for(int menu_id:menus_id){
			System_menu smenu=new System_menu();
			smenu.setId_system_menu(menu_id);
			menuSet.add(smenu);
		}
		role.setMenus(menuSet);
		role.setRole_status("1");
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
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="getMenus",produces="text/html;charset=utf-8")
	@ResponseBody
	public String getMenus(int roleid){
		List menus=roleService.getMenus(roleid);
		JSONArray array=JSONArray.fromObject(menus);
		return array.toString();
	}
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(Role role,int menus_id[]){
		Set<System_menu> menuSet=new HashSet<System_menu>();
		for(int menu_id:menus_id){
			System_menu smenu=new System_menu();
			smenu.setId_system_menu(menu_id);
			menuSet.add(smenu);
		}
		role.setMenus(menuSet);
		role.setRole_status("1");
		roleService.update(role);
		return "ok";
	}
	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delete(Role role){
		try {
			roleService.delete(role);
			return "yes";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "no";
	}
	@RequestMapping(value="changeStatus",produces="text/html;charset=utf-8")
	@ResponseBody
	public String changeStatus(int idrole){
		roleService.changeStatus(idrole);
		return "ok";
	}
}
