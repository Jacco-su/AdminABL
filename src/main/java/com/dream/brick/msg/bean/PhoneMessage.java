package com.dream.brick.msg.bean;

import com.dream.brick.admin.bean.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 手机短信
 * 
 * @author maolei
 * @date 2015-06-14 10:10
 */
@Entity
@Table(name = "t_phone_msg")
public class PhoneMessage {
	private String id;
	private String toNames;//收信人
	private String toUserIds;//收信人id
	private String content;//短信内容
	private String phones;//收信人收机号
	private String inputPhones;//直接输入的手机号码发送
	private String createTime;//创建时间
	private User user;//发信人
	
	
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="to_names")
	public String getToNames() {
		return toNames;
	}
	public void setToNames(String toNames) {
		this.toNames = toNames;
	}
	@Column(name="to_user_ids")
	public String getToUserIds() {
		return toUserIds;
	}
	public void setToUserIds(String toUserIds) {
		this.toUserIds = toUserIds;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="phones")
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	@Column(name="create_time")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name="input_phones")
	public String getInputPhones() {
		return inputPhones;
	}
	public void setInputPhones(String inputPhones) {
		this.inputPhones = inputPhones;
	}
	
	
}
