package com.dream.brick.admin.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.brick.admin.bean.Operation;

import java.util.List;

/**
 * 权限操作数据库访问接口
 * 
 * @author DC
 * 
 */
public interface IOperationDao extends BaseDao {
	/**
	 * 查出操作列表
	 * 
	 * @throws Exception
	 */
	public List<Operation> list() throws Exception;

	/**
	 * 查出操作列表(根据SQL条件)
	 * 
	 * @param sql
	 * @throws Exception
	 */
	public List<Operation> list(String sql) throws Exception;
}
