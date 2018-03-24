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
public class GeneralInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
		
		System.out.println(request.getRequestURI());
		
		return true;
	
	};
}
