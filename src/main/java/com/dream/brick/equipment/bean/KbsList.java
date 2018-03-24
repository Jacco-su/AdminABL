package com.dream.brick.equipment.bean;

import javax.persistence.*;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: KbsList.java
 * @Description:
 * @date 2018-03-06 下午2:03
 */
@Entity
@Table(name = "t_kbs_list")
public class KbsList {
	private String id;
    private Qgdis qgdis;
    private String createTime;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "disId", nullable = false, updatable = true)
	public Qgdis getQgdis() {
		return qgdis;
	}

	public void setQgdis(Qgdis qgdis) {
		this.qgdis = qgdis;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
