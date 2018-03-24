package com.dream.framework.security;

/**
 * @author taller
 * 能用的验证,
 */
import com.dream.util.GeneralResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GeneralSecurityHandler implements ISecurityHandler {

	@Override
	public boolean check(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

		do {
			String token = request.getParameter("token");
			String dateString = request.getParameter("call_id");
			String signString = request.getParameter("app_sign");
			String security = PropertiesUtils
					.getSecurityProperty("SecurityKey");

			// 其中有一个为空,则为非法请求.
			if (token == null || dateString == null || signString == null
					|| security == null) {
				break;
			}


			return true;

		} while (false);
		onCheckFailed(response);
		return false;
	}

	@Override
	public void onCheckFailed(HttpServletResponse response) {
		// TODO Auto-generated method stub
		GeneralResponse response2 = new GeneralResponse();
		response2.setSuccess(false);
		response2.setMsg("非法请求!");

		try {
			response.getOutputStream().write(response2.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
