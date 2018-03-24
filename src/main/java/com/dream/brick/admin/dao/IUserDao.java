package com.dream.brick.admin.dao;

import com.dream.framework.dao.BaseDao;
import com.dream.framework.dao.IPager;
import com.dream.brick.admin.bean.Role;
import com.dream.brick.admin.bean.User;
import com.dream.framework.dao.Pager;

import java.util.List;
import java.util.Map;

/**
 * 用户数据库访问接口
 * @author DC
 *
 */
public interface IUserDao extends BaseDao {

	/**
	 * 根据条件搜索用户
	 * @param pager
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<User> query(IPager pager, String username) throws Exception;
	/**
	 * 用户列表
	 * @return
	 * @throws Exception
	 */
	public List<User> list() throws Exception;
	/**
	 * 用户列表按部门查找(带分页)
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	public List<User> list(IPager pager, String deptId) throws Exception;
	/**
	 * 用户列表(带分页)
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	public List<User> listAll(IPager pager) throws Exception;
	/**
	 * 根据手机号搜索用户
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public User findByPhone(String phone)throws Exception;
	/**
	 * 根据角色ID查找
	 * @param roId
	 * @return
	 * @throws Exception
	 */
	public List<User> findByRoId(String roId)throws Exception;
	/**
	 * 根据角色ID和部门ID查找
	 * @param roId
	 * @return
	 * @throws Exception
	 */
	public List<User> findByRoId(String roId, String deptId)throws Exception;
	
	//根据部门id查找所以所有人员
	public List<User> findByDeptid(String deptId)throws Exception;
	
	//根据部门id查找部门一把手
	public User findDeptLeader(String deptid) throws Exception;
	/**根据账号查询用户**/
	public User findByName(String name);
	public List<User> findIdAndName(String deptId);
	/**
	 * 根据用户id查询对应的角色
	 * **/
	public List<Role> findRoles(String userid);
	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(User user);
	public User findUserById(String id);
	/**
	 * 查询所有人员的角色
	 * **/
	public Map<String,List<Role>> findAllUserRoles();
	/** 根据userId 查询用户的手机号 ***/
	public String getPhoneByUserId(String userId);
	public List<User> cacheAllUser();
	
	/**
	 * 修改考核大项的管理人员，把考核大项交给新人员管理
	 * **/
	public void changeInputUserId(String[] values);
	public int checkIP(String ip);
	public void updatePassword(User user);
	/**
	 * 缓存所有人员的部门ID
	 * ***/
	public Map<String,String> cacheUserDeptId();
	/***查询所有手机号**/
	public Map<String,String> findAllPhone();
	public void updateLoginKey(User user);
	public User findByUsername(String username);
	public List<User> listShowPass() throws Exception;


    public List<User> findByUsernames(Pager pager);
	List<User> ulist(IPager pager, String deptId) throws Exception;
	List<User> uquery(IPager pager, String username) throws Exception;
	void uupdateUser(User user);

	public List<User> keyssUser(String deptId,Pager pager) throws Exception;
}
