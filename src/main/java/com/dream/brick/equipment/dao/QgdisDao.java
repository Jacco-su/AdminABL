package com.dream.brick.equipment.dao;

import com.dream.brick.equipment.bean.Area;
import com.dream.brick.equipment.bean.Qgdis;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;

public interface QgdisDao extends BaseDao {
    public List<Area> findAllArea();

    public List<Qgdis> findAllQgdis();

    //public Qgorg findQgorgByAreacode(String areacode);
    public List<Qgdis> findQgdisByAreacode(String areacode);

    public List<Qgdis> findDisIdAndName();

    public List<Qgdis> findQgdisList(String deptId,String dissName,Pager pager);
    void addDis(Qgdis qgdis);
    List<Qgdis> findQgdisList(String deptId, String dissName);
}
