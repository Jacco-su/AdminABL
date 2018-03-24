package com.dream.framework.interceptor;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dream.brick.admin.bean.User;

/**
 * 日志拦截器
 * 
 * @author DC
 * 
 */
public class LogInterceptor implements HandlerInterceptor {

	private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss.sss";
	private static String MESSAGE = "\n[User:{0} , action:{1} , args:{2} ,result:{3}, time:{4} , take:{5}ms]\n=======================================";
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private User user = new User();
	private String result;;
	private long startTime;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception e)
			throws Exception {
		if (log.isInfoEnabled()) {
			String username = user.getName();
			if (username != null) {
				String errormsg = (String) request.getAttribute("errormsg");
				if (errormsg != null) {
					result = errormsg;
				} else if (e != null) {
					result = e.getMessage();
				} else {
					result = "success";
				}
				String url = request.getRequestURI();
				String param = request.getQueryString();
				String now = new SimpleDateFormat(DATA_FORMAT)
						.format(new Date());
				log.info(MessageFormat.format(MESSAGE, new Object[] { username,
						url, param, result, now,
						System.currentTimeMillis() - startTime }));
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		Object o = request.getSession().getAttribute("admin");
		if (o == null) {
			String username = request.getParameter("name");
			if (username != null)
				user.setName(username);
		} else {
			user = (User) o;
		}
		startTime = System.currentTimeMillis();
		return true;
	}
}
