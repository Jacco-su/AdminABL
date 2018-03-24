package com.dream.brick.equipment.bean;

import javax.persistence.*;

/**
 * 区域
 *
 * @author
 * @date 2017-12-05
 */
@Entity
@Table(name = "t_qgorg")
public class Qgorg  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4725135016331954412L;
	private String id;
    private String name;//区域名称
    private String address;//区域地址
    private String areacode;//区域所在地区编码
    private String areaname;//区域所在地区名称


    private int sortorder;//区域显示顺序


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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
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
	
	
	
}
