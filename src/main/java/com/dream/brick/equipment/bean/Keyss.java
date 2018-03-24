package com.dream.brick.equipment.bean;


import com.dream.brick.admin.bean.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 蓝牙钥匙实体类
 */

@Entity
@Table(name = "t_keyss")
public class Keyss implements Serializable {

    private String id;             //蓝牙钥匙id 编号
    private String keyssCode;
    private String keyssName;       //钥匙自定义名称
    private String keyssMAC;       //蓝牙钥匙MAC地址

    private String keyssDate;         //领用时间
    private String sortorder;            //排序
    /**
     * 常显
     */
    private int alwaysShow;


    private User user;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyssCode() {
        return keyssCode;
    }

    public void setKeyssCode(String keyssCode) {
        this.keyssCode = keyssCode;
    }

    public String getKeyssName() {
        return keyssName;
    }

    public void setKeyssName(String keyssName) {
        this.keyssName = keyssName;
    }

    public String getKeyssMAC() {
        return keyssMAC;
    }

    public void setKeyssMAC(String kryssMAC) {
        this.keyssMAC = kryssMAC;
    }


    public String getKeyssDate() {
        return keyssDate;
    }

    public void setKeyssDate(String keyssDate) {
        this.keyssDate = keyssDate;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public int getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(int alwaysShow) {
        this.alwaysShow = alwaysShow;
    }


//    @ManyToOne(optional = false, fetch = FetchType.EAGER)
//    @JoinColumn(name = "userName", nullable = false,insertable=false, updatable = false)
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false, updatable = true)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
