package com.dream.brick.admin.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.Pager;
import com.dream.brick.admin.bean.Role;
import com.dream.brick.admin.dao.IRoleDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色数据库访问实现类
 * 
 * @author maolei
 * id='useforall' 代表全部角色，在考核小项需要用到
 */
@Transactional
@Repository()
public class RoleDao extends BaseDaoImpl implements IRoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> list(String hql){
		if (!"".equals(hql)){
			return query("from Role where id!='useforall' and " + hql,0,0);
			//
		}
		return (new ArrayList());
	}
	
	
	
	public List<Role> findList(Pager pager){
		String hql="select r.roId,r.name from Role r where id!='useforall' order by orderId asc";
		List<Object[]> results=query(hql,pager);
		List<Role> list=new ArrayList<Role>();
		for(Object[] eles:results){
			Role role=new Role();
			role.setRoId((String)eles[0]);
			role.setName((String)eles[1]);
			list.add(role);
		}
		return list;
	}
	
	public List<Role> findAll(){
		return query("from Role  where id!='useforall' order by orderId asc",0,0);
	}
	
	public List<Role> findRoleName(boolean isAll){
		String hql="select r.roId,r.name from Role r order by r.orderId asc";
		if(isAll==false){
			hql="select r.roId,r.name from Role r  where id!='useforall' order by r.orderId asc";
		}
		List<Object[]> results=query(hql,0,0);
		List<Role> list=new ArrayList<Role>();
		for(Object[] eles:results){
			Role role=new Role();
			role.setRoId((String)eles[0]);
			role.setName((String)eles[1]);
			list.add(role);
		}
		return list;
	}
}
