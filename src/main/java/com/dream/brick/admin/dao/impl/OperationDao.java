package com.dream.brick.admin.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.brick.admin.bean.Operation;
import com.dream.brick.admin.dao.IOperationDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限操作数据库访问实现类
 * @author maolei
 *
 */
@Transactional
@Repository()
public class OperationDao extends BaseDaoImpl implements IOperationDao {


	@SuppressWarnings("unchecked")
	public List<Operation> list() throws Exception {
		return findList("from Operation order by name asc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> list(String hql) throws Exception {
		return findList("from Operation where "+hql);
	}

}
