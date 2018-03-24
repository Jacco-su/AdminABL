package com.dream.framework.security;
/**
 * 检测请求传递的时间.是否超时.
 */

import com.dream.util.GeneralResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimeStampCheckHandler implements ISecurityHandler {
	
	@Override
	public boolean check(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//String date = request.getHeader("Date");
		do{
		String _dateString = request.getParameter("call_id");

		//不是数字的都不接受.
		if(_dateString == null || !_dateString.matches("\\d+")){
			break;
		}

		System.out.println(System.currentTimeMillis());
		//时间误差在30分钟内的都可接受.
		if(Math.abs(System.currentTimeMillis() - Long.parseLong(_dateString)) >1800000 ){
			break;
		}

		return true;

		}while(false);

		onCheckFailed(response);
		return false;
	}

	@Override
	public void onCheckFailed(HttpServletResponse response) {
		// TODO Auto-generated method stub
		//验证失败时响应.
				try{
					GeneralResponse response2 = new GeneralResponse();
					response2.setSuccess(false);
					response2.setMsg("请校准北京时间.");
					response.getOutputStream().write(response2.toString().getBytes());
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
	}

}
