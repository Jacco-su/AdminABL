package com.dream.framework.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class SecurityChecker  {

	public boolean check(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
		String urlString = request.getRequestURI().replace(request.getContextPath(), "");
		System.out.println(urlString);
		
		//首先进行时间规则检测.
		if(!timeStampCheckHandler.check(request, response)){
			return false;
		}
		
//		if(mHandlers.containsKey(urlString)){
//			boolean ret = mHandlers.get(urlString).check(request, response);
//			return ret;
//		}
		
		//通用的检测.
		if(!generalSecurityHandler.check(request, response)){
			return false;
		}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	private TimeStampCheckHandler timeStampCheckHandler = new TimeStampCheckHandler();
	private GeneralSecurityHandler generalSecurityHandler = new GeneralSecurityHandler();
	private static SecurityChecker _securityManager = new SecurityChecker();
	public static SecurityChecker getInstance(){
	
		return _securityManager;
	}
	
	public void putSecurityHandle(String action, ISecurityHandler handler){
		mHandlers.put(action, handler);
	}
	
	//保存一些独特的接口验证方法. 其余的接口将调用通用的验证.
	private final static HashMap<String, ISecurityHandler> mHandlers = new HashMap<String, ISecurityHandler>();
	static {
		
		mHandlers.put("/api/login", new LoginSecurityHandler());
		
		//注册,验证码,修改密码,都使用注册过滤器.
		RegistSecurityHandler registSecurityHandler = new RegistSecurityHandler();
		mHandlers.put("/api/user/register", registSecurityHandler);
		mHandlers.put("/api/user/updatePassword", registSecurityHandler);
		mHandlers.put("/api/user/getCheckCode", registSecurityHandler);
		
	}
}
