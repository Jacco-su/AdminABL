package com.dream.brick.equipment.dao.impl;

import com.dream.brick.equipment.bean.Keyss;
import com.dream.brick.equipment.dao.IKeyssDao;
import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 数据库访问 接口实现类
 */

@Transactional
@Repository
public class KeyssDaoImpl extends BaseDaoImpl implements IKeyssDao {

    public List<Keyss> findAllKeyss() {
        String hql = "from Keyss t order by t.keyssDate desc";
        return findList(hql);
    }

    public List<Keyss> findKeyssList(Pager pager) {
        String hql = "from Keyss t order by t.keyssDate desc";
        return query(hql, pager);
    }

    @Override
    public List<Keyss> findKeyssList(String deptId,Pager pager) {
        String hql=null;
        if(StringUtils.isNotEmpty(deptId)){
            hql = "from Keyss where user.dept.areacode like '"+deptId+"%' order by keyssDate desc";
        }else{
            hql = "from Keyss order by keyssDate desc";
        }

        return query(hql, pager);
    }
    @Override
    public List<Keyss> findKeyssUserList(String userId) {
        String     hql = "from Keyss where userId="+userId;
        return findList(hql);
    }
    @Override
    public List<Keyss> findkeys(Map<String,String> params) {
        StringBuilder hql=new StringBuilder();
        hql.append("from Keyss t where 1=1");
        if(StringUtils.isNotEmpty(params.get("keyssMAC"))){
            hql.append(" and t.keyssMAC ='").append(params.get("keyssMAC")).append("'");
        }
        if(StringUtils.isNotEmpty(params.get("userId"))){
            hql.append(" and t.user.id ='").append(params.get("userId")).append("'");
        }
        return findList(hql.toString());
    }
}
