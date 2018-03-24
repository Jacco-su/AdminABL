package com.dream.brick.admin.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.brick.admin.bean.Syslog;
import com.dream.brick.admin.dao.SyslogDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * 
 * @author maolei
 * 
 */
@Transactional
@Repository()
public class SyslogDaoImpl  extends BaseDaoImpl implements SyslogDao{
	public void saveSyslog(Syslog syslog){
		save(syslog);
	}
}
