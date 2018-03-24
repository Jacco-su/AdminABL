package com.dream.brick.msg.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;
import com.dream.brick.msg.bean.Affair;

import java.util.List;

public interface AffairDao  extends BaseDao {
	public List<Affair> findArrair(Pager pager);
	public List<Affair> findByYearMonth(Pager pager);
}
