package com.dream.brick.equipment.dao.impl;

import com.dream.brick.equipment.bean.Authorization;
import com.dream.brick.equipment.dao.IAuthorizationDao;
import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 数据库访问 接口 实现类
 */

@Transactional
@Repository
public class AuthorizationDaoImpl extends BaseDaoImpl implements IAuthorizationDao {


    public List<Authorization> findAllAuthorization() {
//        String hql = "from Authorization a order by a.adate";
        String hql = "from Authorization,User ";
        return findList(hql);
    }


    public List<Authorization> findAuthorizationList(Pager pager) {
        String hql = "from Authorization a order by a.adate desc";
//        String hql = "from Authorization where aId=?  and name != 'admin' and name != 'alluser' and status!=0 order by orderId , rdate asc";
        return query(hql, pager);

    }

    public List<Authorization> findListDisa(String disa){
        return findList("from Qgdis where dept.areacode like '"+disa+"%'");
    }
}
