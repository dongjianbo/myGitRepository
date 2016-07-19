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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
import sun.misc.Compare;
import vo.Maint_item_def;

@Controller
@RequestMapping("maint_item_def")
public class Maint_item_defController {
	@Resource
	public Maint_item_defService midService;
	@RequestMapping(value="listById",produces="text/html;charset=utf-8")
	@ResponseBody
	public String list(int maint_id,int maint_type){
		List<Maint_item_def> midlist=midService.list(maint_id,maint_type);
		
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
			if(obj[2]!=null){
				//为mid设置检测结果
				if(Integer.parseInt(obj[2].toString())==0){
					mid.setMaint_result("不存在");
				}
				if(Integer.parseInt(obj[2].toString())==1){
					mid.setMaint_result("合格");
				}
				if(Integer.parseInt(obj[2].toString())==-1){
					mid.setMaint_result("不合格");
				}
			}
			mid.setInfo(info);
			
			
		}
		//查询层门的检查项
		List<Maint_item_def> midlist1=midService.listDoor(maint_id);
		for(Maint_item_def mid:midlist1){
			Object[] obj=(Object[])midService.getDoorDetail(maint_id, mid.getMaint_item_id());
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
			if(obj[2]!=null){
				//为mid设置检测结果
				if(Integer.parseInt(obj[2].toString())==0){
					mid.setMaint_result("不存在");
				}
				if(Integer.parseInt(obj[2].toString())==1){
					mid.setMaint_result("合格");
				}
				if(Integer.parseInt(obj[2].toString())==-1){
					mid.setMaint_result("不合格");
				}
			}
			mid.setInfo(info);
			
			
		}
		midlist.addAll(midlist1);
		//对集合进行排序
		Collections.sort(midlist,new Comparator<Maint_item_def>() {

			@Override
			public int compare(Maint_item_def o1, Maint_item_def o2) {
				if(o1.getMaint_item_id()>o2.getMaint_item_id()){
					return 1;
				}else if(o1.getMaint_item_id()<o2.getMaint_item_id()){
					return -1;
				}else{
					return 0;
				}
				
			}
			
		});
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
	@RequestMapping(value="getDoorDetail",produces="text/html;charset=utf-8")
	@ResponseBody
	public String getDoorDetail(int maint_id,int maint_item_id){
		Object[] obj=(Object[])midService.getDoorDetail(maint_id, maint_item_id);
		JSONArray array=JSONArray.fromObject(obj);
		return array.toString();
	}
	@RequestMapping("getImage")
	public void getImage(int maint_id,int maint_item_id,int image_val,HttpServletResponse response){
		midService.getImage(maint_id, maint_item_id, image_val, response);
		
	}
		
	
}
