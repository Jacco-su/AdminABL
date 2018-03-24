package com.dream.brick.equipment.dao.impl;

import com.dream.brick.equipment.bean.Collector;
import com.dream.brick.equipment.dao.CollectorDao;
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
public class CollectorDaoImpl extends BaseDaoImpl implements CollectorDao {
    //    public List<Qgdis> findAllQgdis() {
//        String hql = "from Qgdis t order by t.sortorder";
//
//        return findList(hql);
//    }
//
    public List<Collector> findAllCollector() {
        String hql = "from Collector";
        return findList(hql);
    }
//
//    public Qgdis findQgdisByAreacode(String disId) {
//        return (Qgdis) find(Qgdis.class, disId);
//    }
//
public List<Collector> findCollectorByQgdisid(String disId) {
      String hql = "from Collector t where disId="+disId;
    return findList(hql);
}

    public List<Collector> findCollectorList(Pager pager) {
        String hql = "from Collector";
        return query(hql, pager);
    }


    public List<Collector> findCollectorList(String deptId, Pager pager) {
        String hql=null;
        if(StringUtils.isNotEmpty(deptId)){
            hql = "from Collector where dis.dept.areacode  like '"+deptId+"%'  order by cdate desc";
        }else{
            hql = "from Collector order by cdate desc";
        }
        return query(hql, pager);
    }

    public void addCollector(Collector collector) {
        save(collector);
//        List<UserRole> roleList=user.getRoleList();
//        for(UserRole ur:roleList){
//            ur.setCollector(collector);
//            save(ur);
        //       }
    }
    public List<Collector> findCollectorList(String deptId) {
        String hql=null;
        if(StringUtils.isNotEmpty(deptId)){
            hql = "from Collector where dis.dept.areacode  like '"+deptId+"%' order by cdate desc";
        }else{
            hql = "from Collector order by cdate desc";
        }
        return findList(hql);
    }
}
