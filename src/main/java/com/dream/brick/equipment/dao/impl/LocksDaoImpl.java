package com.dream.brick.equipment.dao.impl;

import com.dream.brick.equipment.bean.Locks;
import com.dream.brick.equipment.dao.ILocksDao;
import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 数据库 接口实现类
 */


@Transactional
@Repository
public class LocksDaoImpl extends BaseDaoImpl implements ILocksDao {

    public List<Locks> findAllLocks() {
        String hql = "from Locks";
        return findList(hql);
    }

    @Override
    public List<Locks> findLocksByDis(String disId) {
        String hql="from Locks t where dissId="+disId;
       return findList(hql);
    }

//    public List<Locks> findLocksByAreacode(String areacode){
//        String hql="from Locks t where order by t.sortorder";
//        return findList(hql);
//    }

    @Override
    public List<Locks> findLocksList(Pager pager) {
        String hql = "from Locks";
        return query(hql, pager);
    }

    public List<Locks> findLocksList(String deptId,String dissName,Pager pager) {
        StringBuilder hql=new StringBuilder();
        hql.append(" from Locks t where 1=1 ");
        if(StringUtils.isNotEmpty(deptId)){
            hql.append(" and qgdis.dept.areacode like '").append(deptId).append("%'");
        }
        if(StringUtils.isNotEmpty(dissName)){
            hql.append(" and qgdis.id=").append(dissName);
        }
        hql.append(" order by t.lockDate desc");
        return query(hql.toString(), pager);
    }

    @Override
    public List<Locks> findLocks(Map<String,String> params) {
        StringBuilder hql=new StringBuilder();
        hql.append("from Locks t where 1=1");
        if(StringUtils.isNotEmpty(params.get("lockCode"))){
            hql.append(" and t.lockCode ='").append(params.get("lockCode")).append("'");
        }
        if(StringUtils.isNotEmpty(params.get("lockNum"))&&StringUtils.isNotEmpty(params.get("dissId"))){
            hql.append(" and t.lockNum ='").append(params.get("lockNum")).append("'");
            hql.append(" and t.qgdis.id =").append(params.get("dissId"));
        }
        return findList(hql.toString());
    }

    @Override
    public List<Locks> findLocks(Locks locks) {
        StringBuilder hql=new StringBuilder();
        hql.append("from Locks t where 1=1");
        if(StringUtils.isNotEmpty(locks.getLockCode())){
            hql.append(" and t.lockCode ='").append(locks.getLockCode()).append("'");
        }
        if(StringUtils.isNotEmpty(locks.getLockNum())&&StringUtils.isNotEmpty(locks.getQgdis().getId())){
            hql.append(" and t.lockNum ='").append(locks.getLockNum()).append("'");
            hql.append(" and t.qgdis.id=").append(locks.getQgdis().getId());
        }
        return findList(hql.toString());
    }

}
