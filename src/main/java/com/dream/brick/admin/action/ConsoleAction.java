package com.dream.brick.admin.action;

import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.bean.Module;
import com.dream.brick.admin.bean.Role;
import com.dream.brick.admin.bean.User;
import com.dream.brick.admin.dao.IDeptDao;
import com.dream.brick.admin.dao.IModuleDao;
import com.dream.brick.admin.dao.IUserDao;
import com.dream.brick.equipment.bean.Qgorg;
import com.dream.brick.listener.SessionData;
import com.dream.util.AppData;
import com.dream.util.AppMsg;
import com.dream.util.MD5;
import com.dream.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 控制台操作实现类
 * 
 * @author maolei
 * 
 */
@Controller()
@Scope("prototype")
@RequestMapping("/log")
public class ConsoleAction {
	@Resource
	private IUserDao userDao;
	@Resource
	private IDeptDao deptDao;
	@Resource
	private IModuleDao moduleDao;
	private Set<String> moduleSet = new HashSet<String>();

	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	public String userlogin(HttpServletRequest request, String loginName, String loginPwd, ModelMap model)
	{
		String errormsg = "";
		try {
			moduleSet = new HashSet<String>();
			User admin=userDao.findByName(loginName);
			if (admin!=null) {
				String ecpwd=MD5.encrytion(loginPwd);
				if(!ecpwd.equals(admin.getPassword())){
					errormsg =findLogMsg(101);
					//101 登陆失败!请核对您的用户名和密码是否正确!
					request.setAttribute("errormsg", errormsg);
					return "/console/login";
				}
				if(admin.getStatus()!=1){
					errormsg =findLogMsg(102);
					//102 用户名无效!
					request.setAttribute("errormsg", errormsg);
					return "/console/login";
				}
				String username = admin.getName();
				Department dept = admin.getDept();
				/*if(!"null".equals(dept.getParentId())){
					dept = deptDao.find(Department.class, dept.getParentId());
				}*/
				//String qgorgId=dept.getQgorgId();
				//Qgorg qgorg=deptDao.find(Qgorg.class, qgorgId);
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");

				request.getSession().setAttribute("userUUID",uuid);

				request.getSession().setAttribute("admin", admin);
				request.getSession().setAttribute("dept", dept);
				//request.getSession().setAttribute("seqgorg",qgorg);
				request.getSession().setAttribute("seareacode", dept.getAreacode());    //**
                admin.setRoles(userDao.findRoles(admin.getId()));
				List<Role> roles = admin.getRoles();
				if(roles.size()==0){
					Role ptRole=userDao.find(Role.class, AppData.NORMALROLEID);
					//默认为普通人员角色
					if(ptRole!=null){
						roles.add(ptRole);
					}
				}
				String tip="";
				for (Role role : roles) {
					String modules = role.getModules();
					if (modules != null) {
						for (String m : modules.split(",")) {
							if("".equals(m)){
								continue;
							}
							moduleSet.add(m);
						}
					}
				}
				JSONObject jsonObject = new JSONObject();
				List<Module> root = new ArrayList<Module>();
				List<Module> temp = moduleDao.getChildren("null");
				List<Module> children=moduleDao.getWithoutRootByType(1);//所有的操作菜单
				List<Module> operateChildren=moduleDao.getWithoutRootByType(2);//所有的操作权限
				Map<String,String> operateMap=new HashMap<String,String>();
				for (Module m : temp) {
					if ("admin".equals(username) || m.getAlwaysShow() == 1
							|| moduleSet.contains(m.getId())) {
						jsonObject.put(m.getName(), getJson(m.getId(),username,children));
						root.add(m);
					}
				}

				/**  以下获取操作权限 **/
				for(Module opeatem:operateChildren){
					   if("admin".equals(username)||moduleSet.contains(opeatem.getId())){
						   operateMap.put(opeatem.getUrl(),"1");
					   }
				}
				Map<String,String> showPassMap=new HashMap();
				if("1".equals(operateMap.get("check_detail_show"))){
					showPassMap.put(admin.getHaskh(),"check_detail_show");
				}else{
					List<User> userList=userDao.listShowPass();
					for (int i = 0; i <userList.size() ; i++) {
						User u =userList.get(i);
						showPassMap.put(u.getHaskh(),"check_detail_show");
					}
				}
				request.getSession().setAttribute("showPassMap",showPassMap);
				request.getSession().setAttribute("operateMap",operateMap);
				model.addAttribute("module", jsonObject.toString());
				model.addAttribute("root", root);
				model.addAttribute("tip", tip);
				SessionData.createSyslog(request,0, "登录系统");
				return "/console/index";
			} else {
				errormsg =findLogMsg(101);
				//登陆失败!请核对您的用户名和密码是否正确!
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/console/login";
		}
		request.setAttribute("errormsg", errormsg);
		return "/console/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		String ip=request.getRemoteHost();
		boolean right=checkIP(ip);
		if(right==false){
			request.setAttribute("message",findLogMsg(100));
			//该IP地址没有登录权限
			return "/commons/globalmsg";
		}
		return "/console/login";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String loginout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "/console/login";
	}
	
	@RequestMapping(value = "mainPanle", method = RequestMethod.GET)
	public String mainPanle(HttpServletRequest request) {
		return "/console/mainPanle";
	}
	
	
	

	@RequestMapping(value = "isRegister", method = RequestMethod.GET)
	@ResponseBody
	public String isRegister(String name) {
		User user = userDao.findByName( name);
		if (user != null) {
			return "false";
		} else {
			return "true";
		}
	}
	
	
	@RequestMapping(value = "changeYear")
	@ResponseBody
	public String changeYear(String year, HttpServletRequest request) {
		String message="";
		try{
			HttpSession session=request.getSession();
			session.setAttribute("operateYear",year);
			message=StringUtil.jsonValue("1",findLogMsg(103));
			//103切换成功！
		}catch(Exception e){
			message=StringUtil.jsonValue("0",findLogMsg(104));
			//104切换失败！
		}
		return message;
	}

	@RequestMapping(value = "checkDept", method = RequestMethod.GET)
	@ResponseBody
	public String checkDept(String name, HttpServletRequest request){
		try{
			name = new String(name.getBytes("ISO-8859-1"),"utf-8");
		}catch(Exception e){
			
		}
		String areacode= SessionData.getAreacode(request);
		Department dept = deptDao.getDeptByName(name,areacode);
		if (dept != null) {
			return "true";
		} else {
			return "false";
		}
	}

	@RequestMapping(value = "alterPass")
	@ResponseBody
	public String alterPass(HttpServletRequest request, String oldpwd, String password){
		String message="";
		User user = (User) request.getSession().getAttribute("admin");
		if (user != null) {
			String ecpwd=MD5.encrytion(oldpwd);
			if(ecpwd.equals(user.getPassword())){
				user.setPassword(MD5.encrytion(password));
				userDao.updatePassword(user);
				message=StringUtil.jsonValue("1",findLogMsg(105));
				//105修改成功
			}else{
				message=StringUtil.jsonValue("0",findLogMsg(106));
				//106原密码错误
			}
		}else{
			message=StringUtil.jsonValue("0",findLogMsg(106));
			
		}
		return message;
	}
	private JSONArray getJson(String parentId, String username, List<Module> children) throws Exception {
		JSONArray jsonArray = new JSONArray();
		List<Module> ms = findModuleChildren(parentId,children);
		for (Module m : ms) {
			if ("admin".equals(username) || m.getAlwaysShow() == 1
					|| moduleSet.contains(m.getId())) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("menuid", m.getId());
				jsonObject.put("menuname", m.getMenuname());
				jsonObject.put("icon", m.getIcon());
				String url = m.getUrl();
				if (url.contains("?"))
					url += "&cls=" + m.getId();
				else
					url += "?cls=" + m.getId();
				jsonObject.put("url", url);
				if (findModuleChildren(m.getId(),children).size() > 0) {
					jsonObject.put("menus", getJson(m.getId(),username,children));
				}
				jsonArray.put(jsonObject);
			}
		}
		return jsonArray;
	}
	
	public List<Module> findModuleChildren(String parentId, List<Module> children){
		List<Module> list=new ArrayList<Module>();
		for(Module module:children){
			if(parentId.equals(module.getParentId())){
				list.add(module);
			}
		}
		return list;
	}
	/**
	 * 判断IP是否可以登录系统
	 * **/
	public boolean checkIP(String hostIP){
		if(1==1){
			return true;
		}
		if("127.0.0.1".equals(hostIP)){
			return true;
		}
		String defIP=AppData.DEFAULTIP;
		String[] ipArray=defIP.split(",");
		boolean right=false;
		for(String ip:ipArray){
			String ipPrefix=ip.substring(0,ip.indexOf("x"));
			//获取前缀
			if(hostIP.contains(ipPrefix)){
				right=true;
				break;
			}
		}
		if(right==true){
			return true;
		}
		return true;
	}
	@RequestMapping("/userCenter")
	public String userCenter(String id, ModelMap model, HttpServletRequest request){
		model.addAttribute("user", SessionData.getLoginAdmin(request));
		return "admin/user_center";
	}
	public String findLogMsg(int key){
		return AppMsg.getMessage("login"+key);
	}

	@RequestMapping(value = "/userlogin", method = RequestMethod.GET)
	public String userlogin() {

		return "/console/login";
	}


}
