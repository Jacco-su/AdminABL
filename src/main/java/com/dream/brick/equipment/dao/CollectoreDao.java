package com.dream.brick.equipment.dao;

import com.dream.brick.equipment.bean.Collectore;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;

/**
 * 数据库访问接口
 */

public interface CollectoreDao extends BaseDao {
    //    public List<Qgdis> findAllQgdis();
//
    public List<Collectore> findAllCollectore();

    //
//    public Qgdis findQgdisByAreacode(String qgdisid);
//
    public List<Collectore> findCollectoreByQgdisid(String disId);

    public List<Collectore> findCollectoreList(Pager pager);
    List<Collectore> findCollectoreList(String depyId,Pager pager);

    void addCollectore(Collectore collectore);
    List<Collectore> findCollectoreByCollector(String collectorId);
}
