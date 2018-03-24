package com.dream.brick.equipment.dao;

import com.dream.brick.equipment.bean.Locks;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;
import java.util.Map;

/**
 * 访问数据库接口
 */

public interface ILocksDao extends BaseDao {

    public List<Locks> findAllLocks();

    List<Locks> findLocksByDis(String disId);
    public List<Locks> findLocksList(Pager pager);
    public List<Locks> findLocksList(String deptId,String dissName,Pager pager);
    List<Locks> findLocks(Map<String,String> params);
    List<Locks> findLocks(Locks locks);
}
