package com.dream.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 绝对路径拦截器
 * 
 * @author maolei
 * 
 */
public class BasePathInterceptor implements HandlerInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
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
		request.setAttribute("basePath", request.getContextPath());
		/*response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		if(request.getRequestURI().indexOf("delete")>0){
			MsgResponse response2 = new MsgResponse();
			response2.setResult("0");
			response2.setMessage("禁止删除!");
			try {
				response.getOutputStream().write(response2.toString().getBytes("utf-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
*/
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		return true;
	}

}

