package com.dream.brick.admin.dao.impl;

import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.dao.IDeptDao;
import com.dream.framework.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门数据库访问实现类
 * 
 * @author DC
 * 
 */
@Transactional
@Repository()
public class DeptDao extends BaseDaoImpl implements IDeptDao {

	@Override
	public List<Department> getChildren(String parentId, String areacode) {
		if (parentId == null){
			return findList("from Department where  areacode like '" + areacode + "%' order by Id asc");
		}else{
			return query("from Department where parentId = ? and areacode like '" + areacode + "%' order by Id asc",
					0, 0, parentId);
		}

    }

    public List<Department> findDeptIdAndName(){
		String hql="select id,name,parentId,haskh from Department order by parentId asc";
		List<Object[]> results=query(hql,0,0);
		List<Department> list=new ArrayList<Department>();
		for(Object[] objs:results){
			Department dept=new Department();
			dept.setId(String.valueOf(objs[0]));
			dept.setName(String.valueOf(objs[1]));
			dept.setParentId(String.valueOf(objs[2]));
			dept.setHaskh((Integer)objs[3]);
			list.add(dept);
		}
		return list;
	}

	@Override
	public Department getDeptByName(String deptName, String areacode) {
		List<Object> query = query("from Department where name=? and areacode like '"+areacode+"%'", 0, 0,
				deptName);
		if (query != null && query.size() > 0)
			return (Department) query.get(0);
		return null;
	}
}
