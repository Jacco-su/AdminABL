package com.dream.brick.equipment.dao.impl;

import com.dream.brick.equipment.bean.Charts;
import com.dream.brick.equipment.dao.IChartsDao;
import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据库 接口 实现类
 */

@Transactional
@Repository
public class ChartsDaoImpl extends BaseDaoImpl implements IChartsDao {

    public List<Charts> findAllCharts() {
        String hql = "from Charta t order by t.sortorder";
        return findList(hql);
    }

    public List<Charts> findChartsList(Pager pager) {
        String hql = "from Charts t order by t.sortorder";
        return query(hql, pager);
    }

}
