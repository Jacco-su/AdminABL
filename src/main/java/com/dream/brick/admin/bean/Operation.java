package com.dream.brick.admin.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 操作实体类 保存系统可以进行的所有操作
 * 
 * @author maolei
 * 
 */
@Entity
@Table(name = "t_operation")
public class Operation implements Serializable {	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -5273619522719465992L;
	/**
	 * 操作ID
	 */
	private String opId;
	/**
	 * 操作模块ID
	 */
	private String cls;
	/**
	 * 操作名
	 */
	private String name;
	/**
	 * 操作字符串
	 */
	private String opt;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

}
