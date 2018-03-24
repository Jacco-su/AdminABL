package com.dream.framework.security;

import com.dream.util.GeneralResponse;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistSecurityHandler implements ISecurityHandler {

	@Override
	public boolean check(HttpServletRequest request,
			HttpServletResponse response) {

		do {
			// 是登录请求,则查找其中的参数:
			String account = request.getParameter("username");
			String dateString = request.getParameter("call_id");
			String signString = request.getParameter("app_sign");
			String security = PropertiesUtils
					.getSecurityProperty("SecurityKey");

			// 其中有一个为空,则为非法请求.
			if (account == null || dateString == null || signString == null
					|| security == null) {
				break;
			}

			String sing = DigestUtils.md5Hex(account + dateString + security);
			// System.out.println(sing.hashCode());
			if (!sing.equalsIgnoreCase(signString)) {
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
		response2.setMsg("登录失败!");

		try {
			response.getOutputStream().write(response2.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
