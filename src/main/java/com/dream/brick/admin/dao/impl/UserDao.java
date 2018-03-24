package com.dream.brick.admin.dao.impl;

import com.dream.framework.dao.BaseDaoImpl;
import com.dream.framework.dao.IPager;
import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.bean.Role;
import com.dream.brick.admin.bean.User;
import com.dream.brick.admin.bean.UserRole;
import com.dream.brick.admin.dao.IUserDao;
import com.dream.framework.dao.Pager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户数据库访问实现类
 * 
 * @author DC
 * 
 */
@Transactional
@Repository()
public class UserDao extends BaseDaoImpl implements IUserDao {

	@Override
	public List<User> list(IPager pager, String deptId) throws Exception {
		String hql = "from User where dept.areacode like '"+deptId+"%' and id!='"+deptId+"' and name != 'admin' and name != 'alluser' and status!=0 order by orderId , rdate asc";
		return query(hql, pager);
	}

	@Override
	public List<User> listAll(IPager pager) throws Exception {
		String hql = "from User where name != 'admin' and name != 'alluser' and status!=0 order by orderId asc";
		return query(hql, pager);
	}
	@Override
	public List<User> listShowPass() throws Exception {
		return findList("from User  where name != 'admin' and status!=0 and haskh !='0'");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> list() throws Exception {
		return findList("from User  where name != 'admin' and status!=0 order by orderId, rdate asc");
	}

	@Override
	public List<User> query(IPager pager, String username) throws Exception {
		String hql = "from User where username like ? and name != 'admin' and name != 'alluser' and status!=0 order by orderId , rdate asc";
		return query(hql, pager, "%" + username + "%");
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findByPhone(String phone) throws Exception {
		Session session = getSession();
		String sql = "from User where phone=? and name != 'admin' and status!=0 order by orderId , rdate asc";
		try {
			Query query = session.createQuery(sql);
			query.setString(0, phone);
			List<User> list = query.list();
			if (list.size() > 0) {
				return list.get(0);
			} else
				return null;
		} finally {
			session.close();
		}
	}

	@Override
	public List<User> findByRoId(String roId) throws Exception {
		// query("select u from User u,Role r where r.roId in elements (u.roles) and r.roId=?",
		// 0, 0, roId);
		List<User> query = query(
				"select u from User u join u.roles r where r.roId=? and  u.status!=0", 0, 0,
				roId);
		return query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByRoId(final String roId, final String deptId) throws Exception {
		List query = query("select u from User u join u.roles r where r.roId=? and u.dept.id=? and  u.status!=0", 0, 0, roId, deptId);
	    return query;
	}

	@Override
	public List<User> findByDeptid(String deptId) throws Exception {
		// TODO Auto-generated method stub
		List query=query(" from User u  where  u.dept.id=? and u.id!='"+deptId+"' and u.name != 'alluser' and  u.status!=0 order by orderId", 0, 0,  deptId);
		return query;
	}
	public User findDeptLeader(String deptid) throws Exception {
		List<User> query =query("from User u where u.dept.id=? and u.status!=0 order by orderId",0,0,deptid);
		if(query!=null&&query.size()>0){
			return query.get(0);
		}else{
			query=query("from User u where u.dept.parentId=? and u.dept.name='局领导' and u.status!=0 order by orderId",0,0,deptid);
			return query.get(0);
		}
	}
	public User findByName(String name){
		String hql="from User u where u.name=? or u.username=?";
		List<User> results=query(hql,0,0,name,name);
		if(results.size()>0){
			User user=results.get(0);
			return user;
		}
		return null;
	}
	public List<User> findIdAndName(String deptId){
		String hql="select u.id,u.username,u.haskh,u.dept.id,u.dept.name from User u where status!=0 order by u.orderId";
		if(deptId!=null&&!"".equals(deptId)){
			hql="select u.id,u.username,u.haskh,u.dept.id,u.dept.name from User u where u.dept.id='"+deptId+"' and status!=0 order by u.orderId";
		}
		List<Object[]> results=query(hql, 0,0);
		List<User> list=new ArrayList<User>();
		for(int i=0;i<results.size();i++){
			Object[] objs=results.get(i);
			User user=new User();
			user.setId(String.valueOf(objs[0]));
			user.setUsername(String.valueOf(objs[1]));
			user.setHaskh(String.valueOf(objs[2]));
			Department dept=new Department();
			dept.setId(String.valueOf(objs[3]));
			if((user.getId()).equals(dept.getId())){
				continue;
			}
			dept.setName(String.valueOf(objs[4]));
			user.setDept(dept);
			list.add(user);
		}
		return list;
	}
	public List<Role> findRoles(String id){
		String hql="select ur.role from UserRole ur where ur.user.id=?";
		List<Role> roles=query(hql,0,0,id);
		return roles;
	}
	public Map<String,List<Role>> findAllUserRoles(){
		Map<String,List<Role>> urmap=new HashMap<String,List<Role>>();
		String hql="select ur.user.id,ur.role.roId,ur.role.name from UserRole ur";
		List<Object[]> results=query(hql,0,0);
	    for(Object[] objs:results){
	    	String userid= String.valueOf(objs[0]);
	    	Role role=new Role();
			role.setRoId(String.valueOf(objs[1]));
			role.setName(String.valueOf(objs[2]));
			if(urmap.containsKey(userid)){
				urmap.get(userid).add(role);
			}else{
				List<Role> list=new ArrayList<Role>();
				list.add(role);
				urmap.put(userid, list);
			}
	    }
		return urmap;
	}
	public void addUser(User user){
		save(user);
		List<UserRole> roleList=user.getRoleList();
		for(UserRole ur:roleList){
			ur.setUser(user);
			save(ur);
		}
	}
	public void updateUser(User user){
		String hql="delete from UserRole ur where ur.user.id=?";
		executeUpdate(hql,user.getId());
		update(user);
		List<UserRole> roleList=user.getRoleList();
		for(UserRole ur:roleList){
			ur.setUser(user);
			save(ur);
		}
	}	
	public void deleteUser(User user){
		String hql="delete from UserRole ur where ur.user.id=?";
		executeUpdate(hql,user.getId());
		
		hql="update User set status=0 where id=?";
		executeUpdate(hql,user.getId());
	}
	public User findUserById(String id){
		User user=(User)find(User.class,id);
		List<Role> roles=findRoles(id);
		user.setRoles(roles);
		return user;
	}
	public String getPhoneByUserId(String userId){
		String phone=null;
		String hql="select t.phone from User t where t.id=? and t.phone is not null";
		List<String> results=query(hql,0,0,userId);
		if(results.size()>0){
			phone=results.get(0);
			if(!phone.matches("\\d{11}")){
				phone=null;
			}
		}
		return phone;
	}
	public List<User> cacheAllUser(){
		String hql="select u.id,u.username,u.dept.id,u.dept.name from User u";
		List<Object[]> results=query(hql, 0,0);
		List<User> list=new ArrayList<User>();
		for(int i=0;i<results.size();i++){
			Object[] objs=results.get(i);
			User user=new User();
			user.setId(String.valueOf(objs[0]));
			user.setUsername(String.valueOf(objs[1]));
			Department dept=new Department();
			dept.setId(String.valueOf(objs[2]));
			dept.setName(String.valueOf(objs[3]));
			user.setDept(dept);
			list.add(user);
		}
		return list;
	}
	
	public void changeInputUserId(String[] values){
		String deptId=values[0];
		String userId=values[1];
		String update="update CItemInput t set t.userId=?,t.username=? where t.deptId=?";
		//把考核大项原有的管理人员修改为新人员
		executeUpdate(update,userId,values[2],deptId);
	}
	public int checkIP(String ip){
		String hql="select count(*) from Ipctrl t where t.ip='"+ip+"'";
		int count=getResultNumber(hql);
		return count;
	}
	public void updatePassword(User user){
		String hql="update User set password=? where id=?";
		executeUpdate(hql,user.getPassword(),user.getId());
	}
	public Map<String,String> cacheUserDeptId(){
		Map<String,String> deptIdMap=new HashMap<String,String>();
		String hql="select t.id,t.dept.id from User t";
		List<Object[]> results=query(hql,0,0);
		for(Object[] objs:results){
			deptIdMap.put((String)objs[0], (String)objs[1]);
		}
		return deptIdMap;
	}
	public Map<String,String> findAllPhone(){
		String hql="select t.id,t.phone from User t where t.phone is not null and t.phone!=''";
		List<Object[]> results=query(hql,0,0);
		Map<String,String> phoneMap=new HashMap<String,String>();
		for(Object[] objs:results){
			phoneMap.put((String)objs[0], (String)objs[1]);
		}
		return phoneMap;
	}
	public void updateLoginKey(User user){
		String hql="update User set loginTime=?,loginKey=? where id=?";
		this.executeUpdate(hql, new Object[]{user.getLoginTime(),user.getLoginKey(),user.getId()});
	}	
	public User findByUsername(String username){
		String hql="select u from User u where u.username=?";
		List<User> results=query(hql,0,0,username);
		if(results.size()>0){
			User user=results.get(0);
			return user;
		}
		return null;
    }

    public List<User> findByUsernames(Pager pager) {
        String hql = "select t from User t ";
        return findList(hql);
    }

    public List<User> findAllUser() {
        String hql = "select t from User t ";
        return findList(hql);
    }

	@Override
	public List<User> ulist(IPager pager, String deptId) throws Exception {
		String hql = "from User where deptId=? and id!='"+deptId+"'  and name is null and status!=0 order by orderId , rdate asc";
		return query(hql, pager, deptId);
	}
	@Override
	public List<User> uquery(IPager pager, String username) throws Exception {
		String hql = "from User where username like ?  and name is null and status!=0 order by orderId , rdate asc";
		return query(hql, pager, "%" + username + "%");
	}
	public void uupdateUser(User user){
		update(user);
	}


	public List<User> keyssUser(String deptId,Pager pager) throws Exception {
		String hql=null;
		if(StringUtils.isNotEmpty(deptId)){
			hql = "from User where dept.id= "+deptId;
		}else{
			hql = "from User";
		}
		return query(hql, pager);
	}

}
