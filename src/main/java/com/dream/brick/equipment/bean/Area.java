package com.dream.brick.equipment.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 地区
 * 
 * @author maolei
 * @date 2017-12-13 16:50
 */
@Entity
@Table(name = "t_areacode")
public class Area implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3807066447407536714L;
	private String areacode;//地区编码
	private String areaname;//地区名称
	private int sortorder;//地区显示顺序
	private int grade;
    private String parentcode;    //上级
    private int islast;

    @Id
    public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public int getSortorder() {
		return sortorder;
	}
	public void setSortorder(int sortorder) {
		this.sortorder = sortorder;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	@Column(name="parent_code")
	public String getParentcode() {
		return parentcode;
	}
	
	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}
	@Column(name="is_last")
	public int getIslast() {
		return islast;
	}
	public void setIslast(int islast) {
		this.islast = islast;
	}
	
	
}
