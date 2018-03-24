package com.dream.brick.equipment.dao;

import com.dream.brick.equipment.bean.Authorization;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;

/**
 * 数据库访问接口
 */

public interface IAuthorizationDao extends BaseDao {

    public List<Authorization> findAllAuthorization();

    public List<Authorization> findAuthorizationList(Pager pager);
    List<Authorization> findListDisa(String disa);


}
