package com.dream.brick.equipment.dao;

import com.dream.brick.equipment.bean.Collector;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;

/**
 * 数据库访问接口
 */

public interface CollectorDao extends BaseDao {
    //    public List<Qgdis> findAllQgdis();
//
    public List<Collector> findAllCollector();
//
//    public Qgdis findQgdisByAreacode(String qgdisid);
//
public List<Collector> findCollectorByQgdisid(String deptid);

    public List<Collector> findCollectorList(Pager pager);
    List<Collector> findCollectorList(String deptId, Pager pager);
    /*List<Collector>*/

//    public void addCollector(Collector collector);
List<Collector> findCollectorList(String deptId);
}
