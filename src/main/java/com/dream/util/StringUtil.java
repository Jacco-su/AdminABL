package com.dream.util;

import com.dream.brick.admin.bean.User;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class StringUtil {
	public static String jsonValue(String result, String message){
		 JSONObject json=new JSONObject();
		 json.put("result", result);
		 json.put("message", message);
		 return json.toString();
	}
	public static String dealNull(String value){
		if(value==null){
			return "";
		}
		value=value.trim();
		return value;
	}
	public static String dealParam(String value){
		if(value==null){
			return "";
		}
		value=value.trim();
		value=value.replaceAll("'", "").replaceAll("\"", "").trim();	
		return value;
	}	
	/**
	 * 判断前台审核人员是否需要自动选中
	 * **/
	public static void jobUserCheck(List<Object> list, HttpServletRequest request){
		if(list.size()==1){
			Map<String,Object> data=(Map<String,Object>)list.get(0);
			request.setAttribute("autojob",data.get("job"));
			request.setAttribute("autobttext",data.get("bttext"));
			request.setAttribute("autoaction",data.get("action"));
			request.setAttribute("nodeId",data.get("nodeId"));
			request.setAttribute("nextNodeId",data.get("nextNodeId"));
			//只有一步操作,把任务名称自动填写
			List<User> users=(List<User>)data.get("userList");
			if(users.size()==1){
				//只有一个人,自动选中
				request.setAttribute("oneuser","checked");
			}else{
				request.setAttribute("oneuser","");
			}
		}else{
			request.setAttribute("autojob","");
			request.setAttribute("autobttext","");
			request.setAttribute("autoaction","");
			request.setAttribute("oneuser","");
			request.setAttribute("nodeId","");
			request.setAttribute("nextNodeId","");
		}
	}	
	
	public static String patternPrint(String pattern, double number){
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(number);
	}
	
	public static String showScore(double number){
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(number);
	}
	
	public static String findCItemOrder(String sortOrder){
		if(sortOrder==null||"".equals(sortOrder)){
			return "10";
		}
		String[] orders=sortOrder.split("_");
		if(orders.length==0){
			return "10";
		}
		if(orders.length==1){
			return orders[0];
		}else{
			return orders[1];
		}
	}
}
