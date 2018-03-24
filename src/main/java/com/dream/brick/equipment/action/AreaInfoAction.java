package com.dream.brick.equipment.action;

import com.dream.brick.equipment.bean.Area;
import com.dream.brick.equipment.dao.AreaInfoDao;
import com.dream.brick.listener.SessionData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("/areainfo")
public class AreaInfoAction {
	
	@Resource
	public AreaInfoDao areaDao;
	
	@RequestMapping("/findAreaByCode")
	@ResponseBody
	public String findAreaByCode(HttpServletRequest request) throws Exception {
		String areacode= SessionData.getAreacode(request);
		List<Area> areaList=areaDao.findAreaByCode(areacode);
		
		JSONArray jsonArray = new JSONArray();
		if(areaList.size()>1){
			for(Area area:areaList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", area.getAreacode());
				jsonObject.put("text",area.getAreaname());
				jsonObject.put("hasChildren","true");
				jsonObject.put("classes", "folder");
				jsonObject.put("state", "closed");
				JSONObject jsonOther = new JSONObject();
				jsonOther.put("grade", area.getGrade());
				jsonOther.put("parentcode", area.getParentcode());
				jsonObject.put("attributes",jsonOther);
				jsonArray.put(jsonObject);
			}
		}else{
			Area area=areaList.get(0);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", area.getAreacode());
			jsonObject.put("text",area.getAreaname());
			jsonObject.put("hasChildren","true");
			jsonObject.put("classes", "folder");
			jsonObject.put("state", "closed");
			JSONObject jsonOther = new JSONObject();
			jsonOther.put("grade", area.getGrade());
			jsonOther.put("parentcode", area.getParentcode());
			jsonOther.put("islast", area.getIslast());
			jsonObject.put("attributes",jsonOther);
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
	
	@RequestMapping("/findAreaByParentId")
	@ResponseBody
	public String findAreaByParentId(String parentId, HttpServletRequest request) throws Exception {
		    List<Area> areaList=areaDao.findAreaByParentId(parentId);
		    JSONArray jsonArray = new JSONArray();
			for(Area area:areaList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", area.getAreacode());
				jsonObject.put("text",area.getAreaname());
				JSONObject jsonOther = new JSONObject();
				jsonOther.put("grade", area.getGrade());
				jsonOther.put("islast", area.getIslast());
				jsonOther.put("areaname", area.getAreaname());
				jsonOther.put("parentcode", area.getParentcode());
				if(area.getGrade()<=3){
					jsonObject.put("hasChildren","true");
					jsonObject.put("state", "closed");
					jsonObject.put("classes", "folder");
				}else{
					jsonObject.put("hasChildren","false");
				}
				jsonObject.put("attributes",jsonOther);
				jsonArray.put(jsonObject);
			}
			return jsonArray.toString();
	}
}
