package com.dream.brick.listener;

import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.bean.Role;
import com.dream.brick.admin.bean.Syslog;
import com.dream.brick.admin.bean.User;
import com.dream.brick.admin.dao.IDeptDao;
import com.dream.brick.admin.dao.IRoleDao;
import com.dream.brick.admin.dao.IUserDao;
import com.dream.brick.admin.dao.SyslogDao;
import com.dream.brick.equipment.bean.*;
import com.dream.brick.equipment.dao.*;
import com.dream.framework.dao.Pager;
import com.dream.util.AppMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取基础数据 有些公用的查询，像文件，部门等放在这里，提供通用的方法
 * @author maolei
 * @date 2017-11-11 16:35
 **/
public class BasicData {
	public static SpringUtil sprintUtil;
	public static IRoleDao roleDao;
	public static IUserDao userDao;
	public static IDeptDao deptDao;
	public static QgorgDao qgorgDao;
	public static QgdisDao qgdisDao;
	public static CollectorDao collectorDao;
	public static SyslogDao logDao;

	public static ILocksDao ilocksDao;
	public static IKeyssDao ikeyssDao;
	//    public static IAuthorizationDao iauthorizationDao;
	//private static RedisDao redisDao;
	public static CollectoreDao collectoreDao;

    public static IKeysListDao IKeysListDao;

    public static IAuthLogDao authLogDao;
    public static IOpenLogDao openLogDao;

	public static SpringUtil getSprintUtil()
	{
		return sprintUtil;
	}
	public static void setSprintUtil(SpringUtil su) {
		sprintUtil = su;
		roleDao = (IRoleDao) SpringUtil.getObject("roleDao");
		userDao = (IUserDao) SpringUtil.getObject("userDao");
		deptDao = (IDeptDao) SpringUtil.getObject("deptDao");
		qgorgDao = (QgorgDao) SpringUtil.getObject("qgorgDaoImpl");
		qgdisDao = (QgdisDao) SpringUtil.getObject("qgdisDaoImpl");
		logDao=(SyslogDao) SpringUtil.getObject("syslogDaoImpl");
		ilocksDao = (ILocksDao) SpringUtil.getObject("locksDaoImpl");
		ikeyssDao = (IKeyssDao) SpringUtil.getObject("keyssDaoImpl");
		collectorDao = (CollectorDao) SpringUtil.getObject("collectorDaoImpl");
		collectoreDao = (CollectoreDao) SpringUtil.getObject("collectoreDaoImpl");
//               iauthorizationDao = (IAuthorizationDao) SpringUtil.getObject("authorizationDapImpl");
		IKeysListDao = (IKeysListDao) SpringUtil.getObject("keysListDaoImpl");
		authLogDao=(IAuthLogDao) SpringUtil.getObject("authLogDaoImpl");
		openLogDao=(IOpenLogDao) SpringUtil.getObject("openLogDaoImpl");

	}
	/**
	 * files 指fileid字符串,格式如'1111.txt;1112.doc;1113.png'
	 *
	 * **/
	public static List<Role> findAll(){
		return roleDao.findAll();
	}
	public static List<Department> findDeptIdAndName(){
		return deptDao.findDeptIdAndName();
	}
	public static Map<String,Department> cacheDepartment(){
		Map<String,Department> deptMap=new HashMap<String,Department>();
		List<Department> list=deptDao.findDeptIdAndName();
		for(Department dept:list){
			deptMap.put(dept.getId(), dept);
		}
		return deptMap;
	}
	public static Map<String,User> cacheAllUser(){
		Map<String,User> userMap=new HashMap<String,User>();
		List<User> allUser=userDao.cacheAllUser();
		for(User user:allUser){
			userMap.put(user.getId(), user);
		}
		return userMap;
	}

	public static String findUserDeptId(String userId){
		return (userDao.cacheUserDeptId()).get(userId);
	}
	public static String getAppMsg(String key){
		return AppMsg.getMessage(key);
	}
	public static User findByUsername(String uname){
		return userDao.findByUsername(uname);
	}
	public static List<Area> findAllArea(){
		return qgorgDao.findAllArea();
	}
	public static List<Qgorg> findAllQgorg(){
		return qgorgDao.findAllQgorg();
	}
	public static Area findAreaByAreacode(String areacode){
		return qgorgDao.findAreaByAreacode(areacode);
	}

	//**********
	public static List<Qgdis> findAllQgdis() {
		return qgdisDao.findAllQgdis();
	}

	public static List<Collector> findAllCollector() {
		return collectorDao.findAllCollector();
	}

	public static List<Collectore> findAllCollectore() {
		return collectoreDao.findAllCollectore();
	}

	public static List<Locks> findAllLocks() {
		return ilocksDao.findAllLocks();
	}

	public static List<Keyss> findAllKeyss() {
		return ikeyssDao.findAllKeyss();
	}
//    public static List<Authorization> findAllAuthorization() {
//    	return iauthorizationDao.findAllAuthorization();}
	//*******

    public static List<Qgorg> findQgorgByAreacode(String areacode, Pager pager) {
        return qgorgDao.findQgorgByAreacode(areacode, pager);
    }
	public static Qgorg findQgorgById(String id){
		return (Qgorg)qgorgDao.find(Qgorg.class,id);
	}


	//***************
	public static List<Qgdis> findQgdisByAreacode(String areacode) {
		return qgdisDao.findQgdisByAreacode(areacode);
	}

	public static Qgdis findQgdsiById(String id) {
		return (Qgdis) qgdisDao.find(Qgdis.class, id);
	}

	/*public static List<Collector> findCollectorByQgdisId(String disId) {
		return collectorDao.findCollectorByQgdisid(disId);
	}*/
//	public static Collector findCollectorByCollectordisId(String areacode){
//		return qgorgDao.findAreaByAreacode(areacode);
//	}
//	public static Collector findCollectorById(String id) {
//		return (Collector) collectoreDao.find(Collector.class, id);
//	}

//    public static Collector findCollectorById(String id) {
//        return (Collector) collectorDao.find(Collector.class, id);
//    }

	public static void saveSyslog(Syslog syslog){
		logDao.saveSyslog(syslog);
	}


	public static Locks finLocksById(String id) {
		return (Locks) ilocksDao.find(Locks.class, id);
	}

	public static Keyss finKeyssById(String id) {
		return (Keyss) ikeyssDao.find(Keyss.class, id);
	}


}
