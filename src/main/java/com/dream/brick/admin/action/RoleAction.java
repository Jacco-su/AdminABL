package com.dream.brick.admin.action;

import com.dream.framework.dao.Pager;
import com.dream.brick.admin.bean.Module;
import com.dream.brick.admin.bean.Operation;
import com.dream.brick.admin.bean.Role;
import com.dream.brick.admin.dao.IModuleDao;
import com.dream.brick.admin.dao.IOperationDao;
import com.dream.brick.admin.dao.IRoleDao;
import com.dream.util.AppData;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 角色操作实现类
 * 
 * @author maolei
 * 
 */
@Controller()
@Scope("prototype")
@RequestMapping("/role")
public class RoleAction {

	@Resource
	private IRoleDao roleDao;
	@Resource
	private IOperationDao operationDao;
	@Resource
	private IModuleDao moduleDao;

	

	@RequestMapping("/index")
	public String index() throws Exception {
		return "admin/role/list";
	}

	@RequestMapping("/list")
	@ResponseBody
	public String list(int page, int rows, Pager pager) throws Exception {
		pager.setCurrentPage(page);
		pager.setPageSize(rows);
		JSONObject datas = new JSONObject();
		List<Role> roleList = roleDao.findList(pager);
		datas.put("total", pager.getTotalRow());
		datas.put("rows", roleList);
		return datas.toString();
	}

	@RequestMapping("/prAdd")
	public String prAdd(ModelMap model){
		return "admin/role/add";
	}

	@RequestMapping("/prUpdate")
	public String prUpdate(String id, ModelMap model){
		Role role = roleDao.find(Role.class, id);
		StringBuffer opIds = new StringBuffer();
		for (Operation o : role.getOperations()) {
			opIds.append(o.getOpId()).append(",");
		}
		if (opIds.length() > 0) {
			opIds.deleteCharAt(opIds.length() - 1);
		}
		model.addAttribute("role", role);
		model.addAttribute("opIds", opIds.toString());
		return "admin/role/update";
	}

	@RequestMapping("/getOpTree")
	@ResponseBody
	public String getOpTree() throws Exception {
		List<Module> allModule=moduleDao.getWithoutRoot();
		Map<String,List<Operation>> opMap=findOperationMap();
		return getJson("null",allModule,opMap).toString();
	}
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String ids){
		if (StringUtils.isNotBlank(ids)) {
			for (String id : ids.split(",")) {
				if(AppData.getRoleName(id)!=null){
					continue;
				}
				Role role = new Role();
				role.setRoId(id);
				roleDao.delete(role);
			}
		}
		return "success";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@ModelAttribute Role role, String opIdList)
	{
		Set<Operation> s = new HashSet<Operation>();
		if (StringUtils.isNotBlank(opIdList)) {
			for (String id : opIdList.split(",")) {
				Operation o = operationDao.find(Operation.class, id);
				s.add(o);
			}
		}
		role.setOperations(s);
		roleDao.save(role);
		return "success";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute Role role, String opIdList)
	{
		Set<Operation> s = new HashSet<Operation>();
		if (StringUtils.isNotBlank(opIdList)) {
			for (String id : opIdList.split(",")) {
				Operation o = operationDao.find(Operation.class, id);
				s.add(o);
			}
		}
		role.setOperations(s);
		roleDao.update(role);
		return "success";
	}

	@SuppressWarnings("unused")
	private String createSQL(String[] list, String name) {
		StringBuffer sql = new StringBuffer();
		int i = 0;
		for (String l : list) {
			sql.append(name + "='");
			sql.append(l);
			sql.append("'");
			if (i < list.length - 1) {
				sql.append(" or ");
			}
			i++;
		}

		return sql.toString();
	}

	private JSONArray getJson(String parentId, List<Module> allModule, Map<String,List<Operation>> opMap) throws Exception {
		JSONArray jsonArray = new JSONArray();
		List<Module> ms = new ArrayList<Module>();
		if("null".equals(parentId)){
			ms=moduleDao.getChildren(parentId);
		}else{
			ms=findChildrenModule(parentId,allModule);
		}
		for (Module m : ms) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", m.getId());
			jsonObject.put("text", m.getMenuname());
			jsonObject.put("iconCls", "icon-folder");
			if (findChildrenModule(m.getId(),allModule).size() > 0) {
				jsonObject.put("state", "closed");
				jsonObject.put("children", getJson(m.getId(),allModule,opMap));
			} else {
				List<Operation> ops =opMap.get(m.getId());
				if (ops!=null&&ops.size()>0) {
					JSONArray jsonArray2 = new JSONArray();
					for (Operation op : ops) {
						JSONObject jsonObject2 = new JSONObject();
						jsonObject2.put("id", op.getOpId());
						jsonObject2.put("text", op.getName());
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
	
	public List<Module> findChildrenModule(String parentId, List<Module> allModule){
		List<Module> list=new ArrayList<Module>();
		for(Module module:allModule){
			if(parentId.equals(module.getParentId())){
				list.add(module);
			}
		}
		return list;
	}
	
	public Map<String,List<Operation>> findOperationMap(){
		Map<String,List<Operation>> opMap=new HashMap<String,List<Operation>>();
		List<Operation> opList = operationDao.query("from Operation", 0, 0);
		for(Operation op:opList){
			if(opMap.containsKey(op.getCls())){
				opMap.get(op.getCls()).add(op);
			}else{
				List<Operation> list=new ArrayList<Operation>();
				list.add(op);
				opMap.put(op.getCls(),list);
			}
		}
		return opMap;
	}
}
