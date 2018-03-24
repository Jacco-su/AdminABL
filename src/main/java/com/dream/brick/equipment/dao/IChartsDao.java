package com.dream.brick.equipment.dao;

import com.dream.brick.equipment.bean.Charts;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;

/**
 * 数据库访问接口
 */

public interface IChartsDao extends BaseDao {

    public List<Charts> findAllCharts();

    public List<Charts> findChartsList(Pager pager);

}
