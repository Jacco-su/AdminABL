package com.dream.brick.admin.action;

import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.bean.User;
import com.dream.brick.admin.dao.IDeptDao;
import com.dream.brick.admin.dao.IUserDao;
import com.dream.brick.listener.SessionData;
import com.dream.util.AppData;
import com.dream.util.AppMsg;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("/userselect")
public class UserSelect {
	@Resource
	private IDeptDao deptDao;
	@Resource
	private IUserDao userDao;
	

	@RequestMapping("/getDeptUserTree")
	@ResponseBody
	public String getDeptUserTree() throws Exception {
		List<Department> allDept=deptDao.findDeptIdAndName();
		List<User> allUser=userDao.findIdAndName(null);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", "null");
		jsonObject.put("text",AppMsg.getMessage("userselect100"));//100  全院人员
		jsonObject.put("iconCls", "icon-ok");
		jsonObject.put("state", "closed");
		jsonObject.put("children", getJson("null",allDept,allUser));
		jsonArray.put(jsonObject);
		
		return jsonArray.toString();
	}

	private JSONArray getJson(String parentId, List<Department> allDept, List<User> allUser) throws Exception {
		JSONArray jsonArray = new JSONArray();
		List<Department> depts =getDeptChildren(parentId,allDept);
		for (Department dept : depts) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", dept.getId());
			jsonObject.put("text", dept.getName());
			jsonObject.put("iconCls", "icon-ok");
			if (getDeptChildren(dept.getId(),allDept).size() > 0) {
				jsonObject.put("state", "closed");
				jsonObject.put("children", getJson(dept.getId(),allDept,allUser));
			} else {
				List<User> users =getUserByDeptid(dept.getId(),allUser);
				if (users.size() > 0) {
					JSONArray jsonArray2 = new JSONArray();
					for (User user : users) {
						if((AppData.ALLUSERID).equals(user.getId())){
							continue;
						}
						JSONObject jsonObject2 = new JSONObject();
						jsonObject2.put("id", user.getId());
						jsonObject2.put("text", user.getUsername());
						jsonArray2.put(jsonObject2);
					}
					jsonObject.put("state", "closed");
					jsonObject.put("children", jsonArray2);
				}
			}
			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}
	@RequestMapping("/prSelect")
	public String prSelect(){
		return "admin/user_select";
	}
	
	public List<Department> getDeptChildren(String parentId, List<Department> allDept){
		List<Department> children=new ArrayList<Department>();
		for(Department dept:allDept){
			if(dept.getParentId().equals(parentId)){
				children.add(dept);
			}
		}
		return children;
	}
	public List<User> getUserByDeptid(String deptId, List<User> allUser){
		List<User> users=new ArrayList<User>();
		for(User u:allUser){
			 if(deptId.equals(u.getDept().getId())){
				 if(deptId.equals(u.getId())){
					 //代表部门全员人员的过滤掉
					 continue;
				 }
				 users.add(u);
			 }
		}
		return users;
	}
	/**
	 * 查询当前登录人员所在部门的所有人员
	 * **/
	@RequestMapping("/myDeptUserTree")
	@ResponseBody
	public String myDeptUserTree(HttpServletRequest request) throws Exception {
		User admin=SessionData.getLoginAdmin(request);
		JSONArray jsonArray = new JSONArray();
		List<User> users =userDao.findByDeptid(admin.getDept().getId());
		String allUserId=request.getParameter("allUserId");
		if(allUserId==null){
			allUserId="";
		}
		if("allUserId".equals(allUserId)){
			JSONObject jsonObject1 = new JSONObject();
			jsonObject1.put("id", admin.getDept().getId());
			jsonObject1.put("text",admin.getDept().getName()+AppMsg.getMessage("userselect101"));//101 全部人员
			jsonArray.put(jsonObject1);
		}
		for (User user : users) {
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("id", user.getId());
			jsonObject2.put("text", user.getUsername());
			jsonArray.put(jsonObject2);
		}
		if("allUserId".equals(allUserId)){
			JSONObject jsonObject3 = new JSONObject();
			jsonObject3.put("id", "402883394d88de64014d88de644b0920");
			jsonObject3.put("text",AppMsg.getMessage("userselect100"));//100 全院人员
			jsonArray.put(jsonObject3);
		}
		return jsonArray.toString();
	}
	
	/**
	 * 查询考核标准录入人员
	 * **/
	@RequestMapping("/itemInputUserTree")
	@ResponseBody
	public String itemInputUserTree(HttpServletRequest request) throws Exception {
		JSONArray jsonArray = new JSONArray();
		String hql="select t.user.id,t.user.dept.name,t.user.username from " +
				" UserRole t where t.role.roId=? order by t.user.dept.orderId";
		List<Object[]> results=userDao.query(hql,0,0,"402883044dfef6ac014dff1ebce90006");
		//查询所有的绩效联络员
		for (Object[] objs : results) {
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("id", String.valueOf(objs[0]));
			jsonObject2.put("text","["+objs[1]+"]"+objs[2]);
			jsonArray.put(jsonObject2);
		}
		return jsonArray.toString();
	}
}
