package com.dream.brick.admin.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.brick.admin.bean.Module;
import com.dream.brick.admin.dao.IModuleDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模块数据库访问实现类
 * 
 * @author DC
 * 
 */
@Transactional
@Repository()
public class ModuleDao extends BaseDaoImpl implements IModuleDao {

	@Override
	public List<Module> getChildren(String parentId) {
		if (parentId == null)
			parentId = "null";
		return query("select t from Module t where parentId = ? order by orderId asc",
				0, 0, parentId);
	}
	
	@Override
	public Module findByName(String name) {
		List<Object> query = query("from Module where menuname = ?",0, 0, name);
		if(query!=null&&query.size()>0)return (Module) query.get(0);
		return null;
	}
	
	public List<Module> getWithoutRoot(){
		return query("select t from Module t where parentId !='null' order by orderId asc",
				0, 0);
	}
	
	public List<Module> getWithoutRootByType(int type){
		return query("select t from Module t where parentId !='null' and t.type="+type+" order by orderId asc",
				0, 0);
	}	
	
}
