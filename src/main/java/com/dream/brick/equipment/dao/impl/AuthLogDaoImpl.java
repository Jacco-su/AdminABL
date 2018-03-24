package com.dream.brick.equipment.dao.impl;

import com.dream.brick.equipment.bean.AuthLog;
import com.dream.brick.equipment.bean.Charts;
import com.dream.brick.equipment.dao.IAuthLogDao;
import com.dream.brick.equipment.dao.IChartsDao;
import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据库 接口 实现类
 */

@Transactional
@Repository
public class AuthLogDaoImpl extends BaseDaoImpl implements IAuthLogDao {
    @Override
    public List<AuthLog> findAll() {
        String hql = "from AuthLog";
        return findList(hql);
    }

    @Override
    public List<AuthLog> findList(String authName,
                                  String authStartTime,
                                  String authEndTime,
                                  String userId,
                                  Pager pager) {
        StringBuilder hql=new StringBuilder();
        hql.append("from AuthLog t where 1=1 ");
        if(StringUtils.isNotEmpty(authName)){
            hql.append("and t.authName like '%").append(authName).append("%'");
        }
        if(StringUtils.isNotEmpty(authStartTime)){
            hql.append(" and t.authStartTime  >='").append(authStartTime).append("'");

        }
        if(StringUtils.isNotEmpty(authEndTime)){
            hql.append(" and t.authEndTime  <= '").append(authEndTime).append("'");
        }
        if(StringUtils.isNotEmpty(userId)){
            hql.append(" and t.user.username  like '%").append(authEndTime).append("%'");
        }
        hql.append(" order by t.createTime desc");
        return query(hql.toString(),pager);
    }
}
