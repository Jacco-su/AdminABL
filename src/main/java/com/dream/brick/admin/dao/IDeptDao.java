package com.dream.brick.admin.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.brick.admin.bean.Department;

import java.util.List;

/**
 * 部门数据库访问接口
 * @author maolei
 *
 */
public interface IDeptDao extends BaseDao {
	/**
	 * 根据父部门ID得到子部门
	 * @param parentId
	 * @return
	 */
	List<Department> getChildren(String parentId, String areacode);
	public List<Department> findDeptIdAndName();
	public Department getDeptByName(String deptName, String areacode);
	
}
