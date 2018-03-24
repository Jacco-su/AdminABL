package com.dream.brick.equipment.bean;

import com.dream.brick.admin.bean.Department;

import javax.persistence.*;

/**
 * 站点 数据库防问实体 类
 *
 * @author
 * @date 2017-12-05
 */
@Entity
@Table(name = "t_qgdis")
public class Qgdis implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4725135016331954412L;
    private String id;
    private String name;// 站点名称
    private String address;// 站点地址
    /**
     * 部门
     */
    private Department dept;//所属

    private String createTime;//创建时间

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
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "deptId", nullable = false, updatable = true)
    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
