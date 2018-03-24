package com.dream.brick.msg.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 消息关键字  写信或者写日志的时候方便快还输入
 * @author maolei
 * @ time 2015-07-13 13:50
 */
@Entity
@Table(name = "t_msgword")
public class Msgword {
	private String id;
	private String userId;//所有者
	private String title;//标题
	private String content;//内容
	private String createTime;//添加时间
	private int type;//1 代表写邮件的关键字 2 代表日志关键字
	
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="user_id")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="create_time")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
