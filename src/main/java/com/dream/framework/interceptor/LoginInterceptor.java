package com.dream.framework.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dream.framework.security.SecurityChecker;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登陆验证拦截器
 * 
 * @author maolei
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		String action = request.getRequestURI();
		if(action.contains(".css")
				||action.contains(".js")
				||action.contains(".jpg")
				||action.contains(".png")
				||action.contains(".gif")
				||action.contains("zhzjLogin")
				||action.contains("globalmsg")
				||action.contains("zhzjLogin")
				||action.contains("userModel")
				){
			return true;
		}
		/*if(action.contains("api")){
			return SecurityChecker.getInstance().check(request,response);
		}*/
		boolean flag = true;
		List<String> ignoreList = new ArrayList<String>();
		ignoreList.add("api");
		ignoreList.add("login");
		ignoreList.add("logout");
		ignoreList.add("zhzjLogin");
		ignoreList.add("userModel");
		for (String i : ignoreList) {
			if (action.contains(i)) {
				flag = false;
				break;
			}
		}

		if ((flag) && (request.getSession().getAttribute("admin") == null)) {
			request.getRequestDispatcher("/WEB-INF/view/commons/noLogin.jsp").forward(
					request, response);
			return false;
		}
		return true;
	}

}

