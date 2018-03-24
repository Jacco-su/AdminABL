package com.dream.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppMsg {
	public static Map<String,String> msgMap=new HashMap<String,String>();
	public static final String CMT_SUCCESS="\u63D0\u4EA4\u6210\u529F";
	//提交成功
	public static final String CMT_ERROR="\u63D0\u4EA4\u5931\u8D25";
	//提交失败
	public static final String PUB_SUCCESS="\u53D1\u5E03\u6210\u529F";//发布成功
	public static final String PUB_ERROR="\u53D1\u5E03\u5931\u8D25";//发布失败
	
	public static final String AGREE="\u540C\u610F";//同意
	public static final String NOT_AGREE="\u4E0D\u540C\u610F";//不同意
	public static final String NEXT_STEP="\u4E0B\u4E00\u6B65";//下一步
	public static final String UPDATE_SUCCESS="\u4FEE\u6539\u6210\u529F";
	//修改成功
	public static final String UPDATE_ERROR="\u4FEE\u6539\u5931\u8D25";//修改失败
	public static final String ADD_SUCCESS="\u6DFB\u52A0\u6210\u529F";//添加成功
	public static final String ADD_ERROR="\u6DFB\u52A0\u5931\u8D25";//添加失败
	public static final String DEL_SUCCESS="\u5220\u9664\u6210\u529F";//删除成功
	public static final String DEL_ERROR="\u5220\u9664\u5931\u8D25";//删除成功
	
	public static final String YW_DEPT="\u4E1A\u52A1\u90E8\u95E8";//业务部门
	public static final String ZH_DEPT="\u7EFC\u5408\u90E8\u95E8";//综合部门

    public static final String REQUEST_SUCCESS = "\u6DFB\u52A0\u6210\u529F";//添加成功

	public static Properties msgprops;
	public static void loadMessage(){
		   InputStream is = AppMsg.class.getResourceAsStream("/appmsg.properties");
		   msgprops= new Properties();
	      try {
	    	 msgprops.load(is);
	      }catch (Exception e) {
	        System.err.println("不能读取属性文件. " +
	       "请确保db.properties在CLASSPATH指定的路径中");
	      } 
	}
	public static String getMessage(String key){
		return msgprops.getProperty(key);
	}
}
