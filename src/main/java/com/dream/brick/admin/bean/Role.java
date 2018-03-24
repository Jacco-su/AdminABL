package com.dream.brick.admin.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * 角色实体类 保存自定的系统角色
 * 
 * @author maolei
 * 
 */
@Entity
@Table(name = "t_role")
public class Role implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 971441913717105247L;
	/**
	 * 角色ID
	 */
	private String roId;
	/**
	 * 角色名
	 */
	private String name;
	/**
	 * 可见模块
	 */
	private String modules;
	/**
	 * 可见表单
	 */
	private String forms;
	/**
	 * 排序Id
	 */
	private int orderId;
	/**
	 * 允许操作
	 */
	private Set<Operation> operations;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(targetEntity = Operation.class, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinTable(name = "r_ro_op", joinColumns = { @JoinColumn(name = "roId") }, inverseJoinColumns = { @JoinColumn(name = "opId") })
	public Set<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	public String getRoId() {
		return roId;
	}

	public void setRoId(String roId) {
		this.roId = roId;
	}

	@Column(length = 2000)
	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public String getForms() {
		return forms;
	}

	public void setForms(String forms) {
		this.forms = forms;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
