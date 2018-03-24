package com.dream.api;

import com.dream.brick.admin.bean.Syslog;
import com.dream.brick.admin.bean.User;
import com.dream.brick.admin.dao.IUserDao;
import com.dream.brick.equipment.bean.Keyss;
import com.dream.brick.equipment.bean.KeyssList;
import com.dream.brick.equipment.bean.Locks;
import com.dream.brick.equipment.bean.OpenLog;
import com.dream.brick.equipment.dao.IKeyssDao;
import com.dream.brick.equipment.dao.IKeysListDao;
import com.dream.brick.equipment.dao.ILocksDao;
import com.dream.brick.equipment.dao.IOpenLogDao;
import com.dream.brick.listener.BasicData;
import com.dream.util.FormatDate;
import com.dream.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: ApiAction.java
 * @Description: 对外接口
 * @date 2018-02-12 下午4:55
 */
@Controller
@Scope("prototype")

@RequestMapping("/api")
public class ApiAction {
	@Resource
	private IKeyssDao ikeyssDao;
	@Resource
	private ILocksDao ilocksDao;
	@Resource
	private IKeysListDao IKeysListDao;

	@Resource
	private IUserDao userDao;
	@Resource
	private IOpenLogDao openLogDao;
	@RequestMapping("keys")
	@ResponseBody
	public String addKeyss(@ModelAttribute Keyss keyss){
		try {
			keyss.setKeyssDate(FormatDate.getYMdHHmmss());
			ikeyssDao.save(keyss);
			return  StringUtil.jsonValue("1", "添加成功！");
		}catch (Exception e){
			return  StringUtil.jsonValue("0", "添加失败！");

		}
	}
	/**
	 * @author       陶乐乐(wangyiqianyi@qq.com)
	 * @Description:  插入统计
	 * @date         2018-02-22 18:00:56
	 * @params
	 * @return
	 * @throws
	 */
	@RequestMapping("keys/list")
	@ResponseBody
	public String keyssList(@ModelAttribute KeyssList keyssList){
		try {
			keyssList.setCreateTime(FormatDate.getYMdHHmmss());
			IKeysListDao.save(keyssList);
			return  StringUtil.jsonValue("1", "添加成功！");
		}catch (Exception e){
			e.printStackTrace();
			return  StringUtil.jsonValue("0", "添加失败！");

		}
	}
	/**
	 * @author       陶乐乐(wangyiqianyi@qq.com)
	 * @Description: 添加设备日志
	 * @date         2018-03-01 09:58:25
	 * @params
	 * @return
	 * @throws
	 */
	@RequestMapping("/logs")
	@ResponseBody
	public void apiLog(@ModelAttribute Syslog syslog){
		String[] arr = syslog.getUsername().split("");
		String userId=arr[1]+arr[3]+arr[5]+arr[7];
		User user=userDao.findUserById(userId);
		if(user!=null){
			syslog.setUsername(userDao.findUserById(userId).getUsername());
		}
		syslog.setCreateTime(FormatDate.getYMdHHmmss());
		BasicData.saveSyslog(syslog);
	}
	/**
	 * @author       陶乐乐(wangyiqianyi@qq.com)
	 * @Description: 添加开锁日志
	 * @date         2018-03-01 09:58:25
	 * @params
	 * @return
	 * @throws
	 */
	@RequestMapping("open/log")
	@ResponseBody
	public void apiLog(@ModelAttribute OpenLog openlog){
		String[] arr = openlog.getUser().getId().split("");
		String userId=arr[1]+arr[3]+arr[5]+arr[7];
		openlog.setCreateTime(FormatDate.getYMdHHmmss());
		User user=userDao.findUserById(userId);
		openlog.setUser(user);

		if(StringUtils.isNotEmpty(openlog.getLockNum())){
			Map<String,String> map=new HashMap<>();
			map.put("lockCode",openlog.getLockNum());
          List<Locks> list= ilocksDao.findLocks(map);
          if(list.size()>0){
	          openlog.setLockName(list.get(0).getLockNum());
          }
		}

		openLogDao.save(openlog);
		/*String[] arr = syslog.getUsername().split("");
		String userId=arr[1]+arr[3]+arr[5]+arr[7];
		User user=userDao.findUserById(userId);
		if(user!=null){
			syslog.setUsername(userDao.findUserById(userId).getUsername());
		}
		syslog.setCreateTime(FormatDate.getYMdHHmmss());
		BasicData.saveSyslog(syslog);*/
	}
}
