package com.dream.util;

import java.util.HashMap;
import java.util.Map;

public class AppData {
	public static Map<String,String> roleMap=new HashMap<String,String>();
	public static String ALLUSERID="402883394d88de64014d88de644b0920";//全院人员id
	public static String JIXAOROLEID="402883044dfef6ac014dff1ebce90006";//绩效联络员角色id
	public static String NORMALROLEID="402883044dfef6ac014dff395815003d";
	public static String btclassid="4028815652c8e91d0152c8e91d8f0000";
	public static String lzclassid="4028815652c8e91d0152c8e91d900002";
	public static String prodclassid="";
	public static String DEFAULTIP=	"10.141.5.x,192.168.3.x,127.0.0.x";
	static{
		roleMap.put("4028824a36de97b50136de9acdcb0001","系统管理员");
		roleMap.put("4028824a37539392013753b7ea1f0004","主管领导");
		roleMap.put("402883044dfef6ac014dff1ebce90006","绩效联络员");
		roleMap.put("402883044dfef6ac014dff395815003d","普通人员");
		roleMap.put("8a8e83833dfc2cb1013dfc33b6a70002","部门领导");
		roleMap.put("8a8e83833dfc2cb1013dfc33b6a70003","绩考办人员");
		roleMap.put("402883044e436635014e43bafa5c002b","绩效主管领导");
		roleMap.put("8a8e83833dfc2cb1013dfc33b6a70004","院领导");
	}
	
	public static String getRoleName(String key){
		return roleMap.get(key);
	}
}
