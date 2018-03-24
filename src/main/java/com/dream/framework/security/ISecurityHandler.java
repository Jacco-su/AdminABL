package com.dream.framework.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISecurityHandler {
	
	//检测参数安全.安全返回true,否则返回 false.
	public boolean check(HttpServletRequest request, HttpServletResponse response);
	
	public void onCheckFailed(HttpServletResponse response);
}
