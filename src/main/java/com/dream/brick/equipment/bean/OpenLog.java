package com.dream.brick.equipment.bean;

import com.dream.brick.admin.bean.User;

import javax.persistence.*;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: OpenLog.java
 * @Description: 开锁日志
 * @date 2018-03-10 下午1:59
 */
@Entity
@Table(name = "t_open_log")
public class OpenLog {
	private String id;
	private User user;
	private String openTime;
	private String lockNum;
	private String logNum;
	private String createTime;
	private String lockName;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false, updatable = true)
	public User getUser() {
		return user;
	}

	public String getLockName() {
		return lockName;
	}

	public void setLockName(String lockName) {
		this.lockName = lockName;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getLockNum() {
		return lockNum;
	}

	public void setLockNum(String lockNum) {
		this.lockNum = lockNum;
	}

	public String getLogNum() {
		return logNum;
	}

	public void setLogNum(String logNum) {
		this.logNum = logNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
