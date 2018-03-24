package com.dream.brick.admin.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 模块实体类
 * 
 * @author maolei
 * 
 */
@Entity
@Table(name = "t_module")
public class Module {

	/**
	 * 模块ID
	 */
	private String id;
	/**
	 * 模块名称
	 */
	private String name;
	/**
	 * 模块名称(显示)
	 */
	private String menuname;
	/**
	 * 模块图标
	 */
	private String icon;
	/**
	 * 模块连接
	 */
	private String url;
	/**
	 * 父模块ID
	 */
	private String parentId;
	/**
	 * 排序ID
	 */
	private String orderId;
	/**
	 * 常显
	 */
	private int alwaysShow;

	/**
	 * 描述
	 */
	private String descript;

    /**
     * 树形层级
     */
    private int type;

    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getAlwaysShow() {
		return alwaysShow;
	}

	public void setAlwaysShow(int alwaysShow) {
		this.alwaysShow = alwaysShow;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}
