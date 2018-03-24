package com.dream.framework.security;

import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtils
 *
 * @author taller 2017-12-16
 *
 */
public class PropertiesUtils {

	//使用单例方式,避免重复加载解析资源.
	private static Properties securityProperties = null;
	
	public static Properties getSecurityProperties(){
		if(securityProperties != null){
			return securityProperties;
		}
		Properties p = new Properties();
		try{
			InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("SecurityConfig.properties");
			p.load(inputStream);
		}catch(Exception e ){
			e.printStackTrace();
		}
		securityProperties = p;
		return p;
	}
	
	public static String getSecurityProperty(String key){
		Properties p = getSecurityProperties();
		return p.getProperty(key, "");
	}
}
