package com.dream.brick.equipment.bean;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: keyssList.java
 * @Description:
 * @date 2018-02-23 上午9:59
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_keys_list")
public class KeyssList implements Serializable {
	private Integer id;
	private Qgdis qgdis;
	private String createTime;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "pdf_id", nullable = false, updatable = true)
	public Qgdis getQgdis() {
		return qgdis;
	}

	public void setQgdis(Qgdis qgdis) {
		this.qgdis = qgdis;
	}

	@Column(name="create_time")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
