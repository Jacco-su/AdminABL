package com.dream.brick.admin.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户角色实体类
 * 
 * @author ml
 * 
 */
@Entity
@Table(name = "t_user_role")
public class UserRole {
	private String id;
	private User user;
	private Role role;
	
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false, updatable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId", nullable = false, updatable = false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
