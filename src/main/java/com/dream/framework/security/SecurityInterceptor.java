/**
 * 
 */
package com.dream.framework.security;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author taller
 * 实现对所有请求的拦截过滤,处理非法请求.
 * 
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {

		//设定返回内容的格式和编码.		
		response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
       return SecurityChecker.getInstance().check(request,response);
	
	};
}
