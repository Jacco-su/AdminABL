package com.dream.brick.equipment.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.brick.equipment.bean.Area;

import java.util.List;

public interface AreaInfoDao extends BaseDao {
	public List<Area> findAreaByParentId(String parentcode);
	public List<Area> findAreaByCode(String areacode);
	public Area cacheAreaById(String id);
}
