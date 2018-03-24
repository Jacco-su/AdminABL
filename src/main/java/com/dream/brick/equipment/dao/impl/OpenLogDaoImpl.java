package com.dream.brick.equipment.dao.impl;

import com.dream.brick.equipment.bean.OpenLog;
import com.dream.brick.equipment.dao.IOpenLogDao;
import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class OpenLogDaoImpl extends BaseDaoImpl implements IOpenLogDao {
	public List<OpenLog> findLockLogList(String deptId, Pager pager) {
		StringBuilder hql=new StringBuilder();
		hql.append(" from OpenLog t where 1=1 order by t.createTime desc");
		return query(hql.toString(), pager);
	}
}
