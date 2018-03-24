package com.dream.brick.equipment.dao;

import com.dream.brick.equipment.bean.AuthLog;
import com.dream.brick.equipment.bean.Charts;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;

/**
 * 数据库访问接口
 */

public interface IAuthLogDao extends BaseDao {

    public List<AuthLog> findAll();

    public List<AuthLog> findList(String authName,String authStartTime,String authEndTime,String userId,Pager pager);

}
