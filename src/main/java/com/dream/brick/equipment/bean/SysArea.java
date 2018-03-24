package com.dream.brick.equipment.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*
 * 区域实体
 * taller
 * 201505151055
 */
@Entity
@Table(name = "t_region")
public class SysArea implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String areaName;
	private Integer pid;
	private Integer areaSort;
	private Integer areaLevel;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	@Column(name="name")
	public String getAreaName() {
		return areaName;
	}
	@Column(name="pid")
	public Integer getPid() {
		return pid;
	}
	@Column(name="is_min")
	public Integer getAreaSort() {
		return areaSort;
	}
	@Column(name="grade")
	public Integer getAreaLevel() {
		return areaLevel;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public void setAreaSort(Integer areaSort) {
		this.areaSort = areaSort;
	}
	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}
	
}
