package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.Maint_item_defService;
import vo.Maint_item_def;

@Controller
@RequestMapping("maint_item_def")
public class Maint_item_defController {
	@Resource
	public Maint_item_defService midService;
	@RequestMapping(value="listById",produces="text/html;charset=utf-8")
	@ResponseBody
	public String list(int maint_id){
		List<Maint_item_def> midlist=midService.list(maint_id);
		for(Maint_item_def mid:midlist){
			Object[] obj=(Object[])midService.getDetail(maint_id, mid.getMaint_item_id());
			String info="";
			if(obj[0]!=null&&!obj[0].equals("")){
				try {
					info+=URLDecoder.decode(obj[0].toString(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(obj[1]!=null&&Integer.parseInt(obj[1].toString())>0){
				info+="("+obj[1]+"p)";
			}
			
			mid.setInfo(info);
		}
		JSONArray array=JSONArray.fromObject(midlist);
		return array.toString();
	}
	@RequestMapping(value="getDetail",produces="text/html;charset=utf-8")
	@ResponseBody
	public String getDetail(int maint_id,int maint_item_id){
		Object[] obj=(Object[])midService.getDetail(maint_id, maint_item_id);
		JSONArray array=JSONArray.fromObject(obj);
		return array.toString();
	}
	@RequestMapping("getImage")
	public void getImage(int maint_id,int maint_item_id,int image_val,HttpServletResponse response){
		midService.getImage(maint_id, maint_item_id, image_val, response);
		
	}
		
	
}
