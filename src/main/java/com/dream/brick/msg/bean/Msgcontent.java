package com.dream.brick.msg.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 信息内容类
 * 
 * @author maolei
 * 
 */
@Entity
@Table(name = "t_msg_content")
public class Msgcontent {
	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 使用人
	 */
	private String belong;
	/**
	 * 发信人
	 */
	private String fromUser;
	/**
	 * 收信人
	 */
	private String toUser;
	
	/**
	 * 发信人
	 */
	private String fromName;
	/**
	 * 收信人
	 */
	private String toUserShow;
	
	/**
	 * 信件类型
	 */
	private String mailType;
	/**
	 * 信件主题
	 */
	private String title;
	/**
	 * 信件内容
	 */
	private String content;
	
	private String files;
	
	private String createTime;
	
	/**
	 * 是否全局
	 */
	private char whole='0';
	
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToUserShow() {
		return toUserShow;
	}
	public void setToUserShow(String toUserShow) {
		this.toUserShow = toUserShow;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
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
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public char getWhole() {
		return whole;
	}
	public void setWhole(char whole) {
		this.whole = whole;
	}	
	
	
	
	
}
