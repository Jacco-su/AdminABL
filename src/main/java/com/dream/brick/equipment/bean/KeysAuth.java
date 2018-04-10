package com.dream.brick.equipment.bean;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 * 钥匙授权信息
 * @author Admin
 * @create 2018-04-02 15:14
 **/
@Entity
@Table(name = "t_keys_auth")
public class KeysAuth {
    private String id;
    private String keysId;
    private String lockNum;
    private String lockName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeysId() {
        return keysId;
    }

    public void setKeysId(String keysId) {
        this.keysId = keysId;
    }

    public String getLockNum() {
        return lockNum;
    }

    public void setLockNum(String lockNum) {
        this.lockNum = lockNum;
    }


    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }
}
