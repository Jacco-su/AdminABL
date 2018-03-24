package com.dream.brick.admin.action;

import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.bean.Role;
import com.dream.brick.admin.bean.User;
import com.dream.brick.admin.bean.UserRole;
import com.dream.brick.admin.dao.DBBarkDao;
import com.dream.brick.admin.dao.IDeptDao;
import com.dream.brick.admin.dao.IRoleDao;
import com.dream.brick.admin.dao.IUserDao;
import com.dream.brick.equipment.bean.Keyss;
import com.dream.brick.equipment.dao.IKeyssDao;
import com.dream.brick.listener.SessionData;
import com.dream.framework.dao.Pager;
import com.dream.util.AppData;
import com.dream.util.AppMsg;
import com.dream.util.MD5;
import com.dream.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户操作实现类
 */


@Controller()
@Scope("prototype")
@RequestMapping("/user")
public class UserAction {

	@Resource
	private IUserDao userDao;
	@Resource
	private IRoleDao roleDao;
	@Resource
	private IDeptDao deptDao;
	@Resource
	private DBBarkDao dbBarkDao;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource
	private IKeyssDao ikeyssDao;
	@RequestMapping("/list")
	@ResponseBody
	public String list(int page, int rows, String deptId, String username, Pager pager)
			throws Exception {
		pager.setCurrentPage(page);
		pager.setPageSize(rows);
		JSONObject datas = new JSONObject();
		List<User> userList = new ArrayList<User>();
		if (StringUtils.isNotBlank(deptId)) {
		Department department =deptDao.find(Department.class,deptId);
			userList = userDao.list(pager, department.getAreacode());
		}else if(StringUtils.isNotBlank(username)){
			userList = userDao.query(pager, username);
		}
		Map<String,List<Role>> urmap=userDao.findAllUserRoles();
		for(User user:userList){
			if(urmap.containsKey(user.getId())){
				user.setRoles(urmap.get(user.getId()));
			}
		}
		datas.put("total", pager.getTotalRow());
		datas.put("rows", userList);
		return datas.toString();
	}


	@RequestMapping("/ulist")
	@ResponseBody
	public String ulist(int page, int rows, String deptId, String username, Pager pager)
			throws Exception {
		pager.setCurrentPage(page);
		pager.setPageSize(rows);
		JSONObject datas = new JSONObject();
		List<User> userList = new ArrayList<User>();
		if (StringUtils.isNotBlank(deptId)) {
			userList = userDao.ulist(pager, deptId);
		}else if(StringUtils.isNotBlank(username)){
			userList = userDao.uquery(pager, username);
		}
		/*Map<String,List<Role>> urmaps=userDao.findAllUserRoles();
		for(User user:userList){
			if(urmaps.containsKey(user.getId())){
				user.setRoles(urmaps.get(user.getId()));
			}
		}*/
		datas.put("total", pager.getTotalRow());
		datas.put("rows", userList);
		return datas.toString();
	}



	//	管理员
	@RequestMapping("/index")
	public String index(){
		return "admin/list";
	}

	//	人员管理
	@RequestMapping("/uindex")
	public String uindex() {
		return "admin/ulist";
	}

	@RequestMapping("/prAdd")
    public String prAdd(String deptId, ModelMap model) {
		List<Role> roles = roleDao.findRoleName(false);
		List<Department> deptList = deptDao.findDeptIdAndName();
        model.addAttribute("deptId", deptId);
		model.addAttribute("roles", roles);
		model.addAttribute("deptList", deptList);
        return "admin/add";
    }

    @RequestMapping("/uprAdd")
    public String uprAdd(String deptId, ModelMap model) {
		List<Role> roles = roleDao.findRoleName(false);
		List<Department> deptList = deptDao.findDeptIdAndName();
		model.addAttribute("deptId", deptId);
		model.addAttribute("roles", roles);
		model.addAttribute("deptList", deptList);
		return "admin/uadd";
    }

    @RequestMapping(value = "/uadd", method = RequestMethod.POST)
    @ResponseBody
	public String uadd(@ModelAttribute User users, String[] roIdList, String[] deptIdList)
			throws Exception {
		users.setStatus(1);
		users.setRdate(sdf.format(new Date().getTime()));
//		users.setPassword("2");
//		users.setName("看了");
		initRole(users, roIdList, deptIdList);
		String message = "";
        try {
			userDao.addUser(users);
			message = StringUtil.jsonValue("1", AppMsg.ADD_SUCCESS);
        } catch (Exception e) {
			e.printStackTrace();
			message = StringUtil.jsonValue("0", AppMsg.ADD_ERROR);
        }
        return message;
    }

	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public String add(@ModelAttribute User user, String[] roIdList, String[] deptIdList)
			throws Exception {
		user.setStatus(1);
		user.setPassword(MD5.encrytion(user.getPassword()));
		user.setRdate(sdf.format(new Date().getTime()));
		initRole(user,roIdList,deptIdList);
		String message="";
		try{
			userDao.addUser(user);
			message=StringUtil.jsonValue("1",AppMsg.ADD_SUCCESS);
		}catch(Exception e){
			message=StringUtil.jsonValue("0",AppMsg.ADD_ERROR);
		}
		return message;
	}

	@RequestMapping("/prUpdate")
	public String prUpdate(String id, ModelMap model){
		List<Role> roles = roleDao.findRoleName(false);
		//List<Department> deptList=deptDao.findDeptIdAndName();
		User user = userDao.findUserById(id);
		StringBuffer roIds = new StringBuffer();
		for (Role o : user.getRoles()) {
			roIds.append(o.getRoId() + ",");
		}
		if (roIds.length() > 0) {
			roIds.deleteCharAt(roIds.length() - 1);
		}
		model.addAttribute("user", user);
		model.addAttribute("roIds", roIds);
		//model.addAttribute("deptIds",user.getUserDepts());
		model.addAttribute("roles",roles);
		//model.addAttribute("deptList",deptList);
		return "admin/update";
	}

	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute User user, String[] roIdList, String[] deptIdList) {

		String message="";
		try{
			initRole(user,roIdList,deptIdList);
			user.setStatus(1);
			User oldUser=userDao.find(User.class,user.getId());
			user.setPassword(oldUser.getPassword());//密码不能修改
			userDao.updateUser(user);
			message=StringUtil.jsonValue("1",AppMsg.UPDATE_SUCCESS);
			for(String roleId:roIdList){
				if((AppData.JIXAOROLEID).equals(roleId)){
					//如果是绩效人员
					String[] values=new String[3];
					values[0]=user.getDept().getId();
					values[1]=user.getId();
					values[2]=user.getUsername();
					userDao.changeInputUserId(values);
					//考核大项由该人员负责
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message=StringUtil.jsonValue("0",AppMsg.UPDATE_ERROR);
		}
		return message;
	}

	@RequestMapping("/uprUpdate")
	public String uprUpdate(String id, ModelMap model) {
		List<Role> roles = roleDao.findRoleName(false);
		//List<Department> deptList=deptDao.findDeptIdAndName();
		User user = userDao.findUserById(id);
		StringBuffer roIds = new StringBuffer();
		for (Role o : user.getRoles()) {
			roIds.append(o.getRoId() + ",");
		}
		if (roIds.length() > 0) {
			roIds.deleteCharAt(roIds.length() - 1);
		}
		model.addAttribute("user", user);
		model.addAttribute("roIds", roIds);
		//model.addAttribute("deptIds",user.getUserDepts());
		model.addAttribute("roles", roles);
		//model.addAttribute("deptList",deptList);
		return "admin/uupdate";
	}

	@RequestMapping(value = "/uupdate", method = RequestMethod.POST)
	@ResponseBody
	public String uupdate(@ModelAttribute User user, String[] roIdList, String[] deptIdList) {
		String message = "";
		try {
			user.setStatus(1);
			userDao.uupdateUser(user);
			message = StringUtil.jsonValue("1", AppMsg.UPDATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			message = StringUtil.jsonValue("0", AppMsg.UPDATE_ERROR);
		}
		return message;
	}


	public void initRole(User user, String[] list, String[] deptIdList){
		if(list==null){
			list=new String[0];
		}
		if(deptIdList==null){
			deptIdList=new String[0];
		}
		List<UserRole> roleList=new ArrayList<UserRole>();
		for(String roleId:list){
			if("".equals(roleId)){
				continue;
			}
			UserRole ur=new UserRole();
			Role role=new Role();
			role.setRoId(roleId);
			ur.setRole(role);
			ur.setUser(user);
			roleList.add(ur);
		}
		user.setRoleList(roleList);
		StringBuilder sb=new StringBuilder("");
		for(String deptId:deptIdList){
			sb.append(deptId).append(",");
		}
		String deptIds=sb.toString();
		if(deptIds.length()>0){
			deptIds=deptIds.substring(0,deptIds.length()-1);
		}
		user.setUserDepts(deptIds);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String ids){
		String msg = "success";
		if (StringUtils.isNotBlank(ids)) {
			for (String id : ids.split(",")) {
				if (!id.equals("admin")) {
					Map<String,String> params=new HashMap<>();
					params.put("userId",id);
					List<Keyss>	list = ikeyssDao.findkeys(params);
					if(list.size()>0){
						return AppMsg.getMessage("user103");
					}
					User user =userDao.find(User.class, id);
					user.setStatus(0);
					userDao.deleteUser(user);
				} else {
					msg = AppMsg.getMessage("user100");
				}
			}
		}
		return msg;
	}

	@RequestMapping("/initPassword")
	@ResponseBody
	public String initPassword(String id){
		User user=new User();
		user.setId(id);
		user.setPassword(MD5.encrytion("111aaa"));
		String message="";
		try{
			userDao.updatePassword(user);
			message=StringUtil.jsonValue("1",AppMsg.getMessage("user101")+"111aaa");
			//密码重置成功，新密码为
		}catch(Exception e){
			message=StringUtil.jsonValue("0",AppMsg.getMessage("user102"));
			//102密码重置失败
		}
		return message;
	}

	@RequestMapping("/prDbbark")
	public String prDbbark(){
		return "admin/dbbark";
	}

	@RequestMapping("/dbbark")
	@ResponseBody
	public String dbbark(){
		String batpath=AppMsg.getMessage("batpath");
		File file=new File(batpath);
		if(!file.exists()){
			return AppMsg.getMessage("batno");
			//文件丢失，无法备份，请联系技术员
		}
		dbBarkDao.jixiaoBark();
		String message="\u5907\u4EFD\u6210\u529F";
		//备份成功
		return message;
	}

	/**
	 * 用户更新自己的信息
	 * ***/
	@RequestMapping("/updateInfo")
	@ResponseBody
	public String updateInfo(User user, HttpServletRequest request){
		String message="";
		User admin= SessionData.getLoginAdmin(request);
		admin.setPhone(user.getPhone());
		admin.setEmail(user.getEmail());
		if (StringUtils.isNotEmpty(user.getHaskh())) {
			admin.setHaskh(user.getHaskh());
		}
		try{
			userDao.update(admin);
			request.getSession().setAttribute("admin", admin);
			message=StringUtil.jsonValue("1",AppMsg.UPDATE_SUCCESS);
		}catch(Exception e){
			message=StringUtil.jsonValue("0",AppMsg.UPDATE_ERROR);
		}
		return message;
	}


    //钥匙领用人
    @RequestMapping("/kList")
    @ResponseBody
    public String findByUsernames(int page, int rows, Pager pager)
            throws Exception {
        pager.setCurrentPage(page);
        pager.setPageSize(rows);
        JSONObject datas = new JSONObject();
        List<User> list = userDao.findByUsernames(pager);
        datas.put("total", pager.getTotalRow());
        datas.put("rows", list);
        return datas.toString();
    }

	@RequestMapping("/udelete")
	@ResponseBody
	public String udelete(String ids){
		String msg = "success";
		if (StringUtils.isNotBlank(ids)) {
			for (String id : ids.split(",")) {
				if (!id.equals("admin")) {
					User user =userDao.find(User.class, id);
					user.setStatus(0);
					userDao.deleteUser(user);
				} else {
					msg = AppMsg.getMessage("user100");
					//admin用户不能被删除! AppMsg.UPDATE_ERROR
				}
			}
		}
		return msg;
	}
}
