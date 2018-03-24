package com.dream.brick.msg.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;
import com.dream.brick.msg.bean.Msgword;

import java.util.List;

public interface MsgwordDao extends BaseDao {
	public List<Msgword> findWordList(Pager pager);
}
