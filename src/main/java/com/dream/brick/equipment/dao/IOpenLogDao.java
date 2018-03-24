package com.dream.brick.equipment.dao;

import com.dream.brick.equipment.bean.Locks;
import com.dream.brick.equipment.bean.OpenLog;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: IOpenLogDao.java
 * @Description:
 * @date 2018-03-10 下午2:15
 */
public interface IOpenLogDao extends BaseDao {
	 List<OpenLog> findLockLogList(String deptId, Pager pager);
}
