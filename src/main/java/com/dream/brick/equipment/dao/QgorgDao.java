package com.dream.brick.equipment.dao;

import com.dream.brick.equipment.bean.Area;
import com.dream.brick.equipment.bean.Qgorg;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;

/**
 * 数据库访问接口
 */

public interface QgorgDao  extends BaseDao {
    public List<Area> findAllArea();

    public List<Qgorg> findAllQgorg();

    public Area findAreaByAreacode(String areacode);

    public List<Qgorg> findQgorgByAreacode(String areacode, Pager pager);

    public List<Qgorg> findQgorgList(Pager pager);
}
