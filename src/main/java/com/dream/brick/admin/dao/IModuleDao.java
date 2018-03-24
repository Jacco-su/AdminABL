package com.dream.brick.admin.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.brick.admin.bean.Module;

import java.util.List;

/**
 * 模块数据库访问接口
 * @author DC
 *
 */
public interface IModuleDao extends BaseDao {
	/**
	 * 根据父模块ID得到子模块
	 * @param parentId
	 * @return
	 */
	List<Module> getChildren(String parentId);

	Module findByName(String name);
	public List<Module> getWithoutRoot();
	public List<Module> getWithoutRootByType(int type);
}
