package com.dream.brick.admin.dao;
import com.dream.framework.dao.BaseDao;

public interface DBBarkDao extends BaseDao {
	/**
	 * 备份数据库
	 * **/
	public void jixiaoBark();
	/**
	 * 删除临时分数
	 * **/
	public void clearTempScore();
}
