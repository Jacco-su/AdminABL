package com.dream.brick.equipment.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;
import com.dream.brick.equipment.bean.Log;

import java.util.List;


public interface LogDao extends BaseDao {
	public List<Log> findLogList(Pager pager);
}
