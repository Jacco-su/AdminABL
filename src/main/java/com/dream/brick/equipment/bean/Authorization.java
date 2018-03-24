package com.dream.brick.equipment.bean;

import javax.persistence.*;
import java.util.Date;

/**
 * 授权记录实体类
 */

@Entity
@Table(name = "t_authorization")
public class Authorization implements java.io.Serializable {
    private static final long serialVersionUID = 4725135016331954412L;
    private String id;
    private Date starttime;   //起始时间
    private Date endtime;       //失效时间
    private String keyssid; //授权钥匙id
    private String locksid;   //开锁Id
    private String type;        //授权类型
    private String uid;    // 授权于
    private String aid;   //操作员
    private String adate;       //授权时间
    private String workticket; //工作票


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }


    public String getKeyssid() {
        return keyssid;
    }

    public void setKeyssid(String keyssid) {
        this.keyssid = keyssid;
    }

    public String getLocksid() {
        return locksid;
    }

    public void setLocksid(String locksid) {
        this.locksid = locksid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAdate() {
        return adate;
    }

    public void setAdate(String adate) {
        this.adate = adate;
    }

    public String getWorkticket() {
        return workticket;
    }

    public void setWorkticket(String workticket) {
        this.workticket = workticket;
    }
}