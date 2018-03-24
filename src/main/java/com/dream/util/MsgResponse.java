package com.dream.util;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 
 * @author taller 网络请求返回的实体类.包含请求成功失败, 请求响应数据等内容 .
 */

public class MsgResponse implements Serializable {

	public String toString() {
		return new Gson().toJson(this);
	}

	private String message;
	private String result;

	public MsgResponse() {

	}

	public MsgResponse(String result, String message) {
		this.result = result;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
