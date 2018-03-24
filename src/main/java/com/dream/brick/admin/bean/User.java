package com.dream.brick.admin.bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户实体类 保存注册的普通用户和管理员信息
 *
 * @author
 * 
 */
@Entity
@Table(name = "t_user")
public class User implements java.io.Serializable{
	
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 3718492115343215424L;

	private String id;
	
	/**
	 * 登陆名
	 */
	private String name;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 注册时间
	 */
	private String rdate;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 部门
	 */
	private Department dept;
	/**
	 * 角色
	 */
	private List<Role> roles=new ArrayList<Role>();
	
	private List<UserRole> roleList=new ArrayList<UserRole>();


	/**
	 * 排序ID
	 */
	private  int status;
	
	private int orderId;
	private String userDepts;
	private String haskh;//是否参与绩效考核
	
	private String loginTime;
	private String loginKey;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}




	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "deptId", nullable = false, updatable = true)
	public Department getDept() {
		return dept;
	}

	@Transient
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Transient
	public List<UserRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UserRole> roleList) {
		this.roleList = roleList;
	}
	
	@Column(name="user_depts")
	public String getUserDepts() {
		return userDepts;
	}

	public void setUserDepts(String userDepts) {
		this.userDepts = userDepts;
	}

	public String getHaskh() {
		return haskh;
	}

	public void setHaskh(String haskh) {
		this.haskh = haskh;
	}
	
	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

}
