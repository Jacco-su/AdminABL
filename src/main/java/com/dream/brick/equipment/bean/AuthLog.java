package com.dream.brick.equipment.bean;

import com.dream.brick.admin.bean.User;

import javax.persistence.*;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: AuthLog.java
 * @Description: 授权记录表
 * @date 2018-03-02 上午10:57
 */
@Entity
@Table(name = "t_auth_log")
public class AuthLog {
	private String id;
	private String authName;//授权名称
	private String createTime;
	private int authType;//授权类型
	private User user;//授权人
	private String authKeys;//授权钥匙
	private String authLocks;//授权锁具
	private String authStartTime;//授权开始时间
	private String authEndTime;//授权介绍时间
	private String authStatus;//授权状态
	private String authKeysId;
	private String authLocksId;
	private String collectorId;//采集器ID
	private String disId;
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getAuthType() {
		return authType;
	}

	public void setAuthType(int authType) {
		this.authType = authType;
	}
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false, updatable = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAuthKeys() {
		return authKeys;
	}

	public void setAuthKeys(String authKeys) {
		this.authKeys = authKeys;
	}

	public String getAuthLocks() {
		return authLocks;
	}

	public void setAuthLocks(String authLocks) {
		this.authLocks = authLocks;
	}

	public String getAuthStartTime() {
		return authStartTime;
	}

	public void setAuthStartTime(String authStartTime) {
		this.authStartTime = authStartTime;
	}

	public String getAuthEndTime() {
		return authEndTime;
	}

	public void setAuthEndTime(String authEndTime) {
		this.authEndTime = authEndTime;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthKeysId() {
		return authKeysId;
	}

	public void setAuthKeysId(String authKeysId) {
		this.authKeysId = authKeysId;
	}

	public String getAuthLocksId() {
		return authLocksId;
	}

	public void setAuthLocksId(String authLocksId) {
		this.authLocksId = authLocksId;
	}



	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public String getDisId() {
		return disId;
	}

	public void setDisId(String disId) {
		this.disId = disId;
	}
}
