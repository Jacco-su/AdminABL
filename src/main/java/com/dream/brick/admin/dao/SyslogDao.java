package com.dream.brick.admin.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.brick.admin.bean.Syslog;

/**
 * 角色数据库访问接口
 * @author maolei
 *
 */
public interface SyslogDao extends BaseDao {
	public void saveSyslog(Syslog syslog);
}
