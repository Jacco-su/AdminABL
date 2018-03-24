package com.dream.brick.admin.bean;

import javax.persistence.*;

/**
 * 部门实体类
 * 
 * @author maolei
 * 
 */
@Entity
@Table(name = "t_dept")
public class Department implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2956187501390550627L;
	/**
	 * 部门ID
	 */
	private String id;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 父部门ID
	 */
	private String parentId;
	/**
     * 区域ID
     */
	private String orgId;
	/**
	 * 排序ID
	 */
	private String orderId;
	/**
	 * 树显示
	 */
	private int treeShow;
	/**
	 * 描述
	 */
	private String descript;
	/**
	 * 所属板块
	 */
	private String bk;
	private int haskh;//是否参与绩效考核
	
	private String qgorgId;
	private String areacode;

	//	@Id
//	@GenericGenerator(name = "systemUUID", strategy = "uuid")
//	@GeneratedValue(generator = "systemUUID")
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getTreeShow() {
		return treeShow;
	}

	public void setTreeShow(int treeShow) {
		this.treeShow = treeShow;
	}

	public String getBk() {
		return bk;
	}

	public void setBk(String bk) {
		this.bk = bk;
	}

	public int getHaskh() {
		return haskh;
	}

	public void setHaskh(int haskh) {
		this.haskh = haskh;
	}

	public String getQgorgId() {
		return qgorgId;
	}

	public void setQgorgId(String qgorgId) {
		this.qgorgId = qgorgId;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	
	
}
