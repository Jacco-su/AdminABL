package com.dream.socket.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: JsonDataProtocol.java
 * @Description:
 * @date 2018-01-22 下午2:37
 */
public class JsonDataProtocol {
	@JSONField(name = "CollectorId")
	private String CollectorId;//采集器ID
	@JSONField(name = "DataType")
	private String DataType;//命令类型
	@JSONField(name = "Content")
	private String Content;//协议字符串16进制

	public String getCollectorId() {
		return CollectorId;
	}

	public void setCollectorId(String collectorId) {
		CollectorId = collectorId;
	}

	public String getDataType() {
		return DataType;
	}

	public void setDataType(String dataType) {
		DataType = dataType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "{\"CollectorId\": \""+getCollectorId()+"\",\"Content\": \""+getContent()+"\",\"DataType\": \""+getDataType()+"\"}";
	}
}
