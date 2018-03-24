package com.dream.brick.equipment.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.brick.equipment.bean.SysArea;

import java.util.List;


public interface SysAreaDao extends BaseDao {
	public List<SysArea> findAreaList(int grade, String pid, String areaCode);
}
