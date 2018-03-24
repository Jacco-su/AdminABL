package com.dream.brick.admin.dao;

import com.dream.brick.admin.bean.Role;
import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.Pager;

import java.util.List;

/**
 * 角色数据库访问接口
 * @author maolei
 *
 */
public interface IRoleDao extends BaseDao {
	/**
	 * 角色条件搜索列表
	 * @param sql
	 * @throws Exception
	 */
	public List<Role> list(String sql);
	public List<Role> findList(Pager pager);
	public List<Role> findAll();
	/**
	 * 考核小项适用人员，需要查询所有角色
	 * **/
	public List<Role> findRoleName(boolean isAll);

//	public List<UserRole> findUserRoleName(boolean isAll);

}
