package com.dream.brick.admin.action;

import com.dream.brick.admin.bean.Module;
import com.dream.brick.admin.dao.IModuleDao;
import com.dream.util.AppMsg;
import com.dream.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模块操作实现类
 * 
 * @author maolei
 * 
 */
@Controller()
@Scope("prototype")
@RequestMapping("/module")
public class ModuleAction {

	@Resource
	private IModuleDao moduleDao;

	@RequestMapping("/index")
	public String index(String orgId, HttpServletRequest request)
	{
		return "admin/module/list";
	}

	@RequestMapping("/getChildren")
	@ResponseBody
	public String getChildren(String parentId, HttpServletRequest request)
			throws Exception {
		JSONArray datas = new JSONArray();
		List<Module> children = moduleDao.getChildren(parentId);
		for (Module module : children) {
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("id", module.getId());
			tempMap.put("text", module.getMenuname());
			tempMap.put("iconCls", module.getIcon());
			if (moduleDao.getChildren(module.getId()).size() > 0) {
				tempMap.put("state", "closed");
			}
			datas.put(tempMap);
		}
		return datas.toString();
	}

	

	@RequestMapping("/prAdd")
	public String prAdd(String parentId, ModelMap model){
		model.addAttribute("parentId", parentId);
		return "admin/module/add";
	}

	@RequestMapping("/prUpdate")
	public String prUpdate(String id, ModelMap model){
		Module module = moduleDao.find(Module.class, id);
		model.addAttribute("module", module);
		return "admin/module/update";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String ids){
		String message="";
		String hql="select count(*) from Module t where t.parentId=?";
		int count=moduleDao.getResultNumber(hql,ids);
		if(count>0){
			message=StringUtil.jsonValue("0",AppMsg.getMessage("module100"));
			//100该模块有子模块，不允许删除
			return message;
		}
        try{
			Module module = new Module();
			module.setId(ids);
			moduleDao.delete(module);
			hql="delete from Operation t where t.cls=?";
			//删除操作权限
			moduleDao.executeUpdate(hql,ids);
			message=StringUtil.jsonValue("1",AppMsg.DEL_SUCCESS);
        }catch(Exception e){
        	message=StringUtil.jsonValue("0",AppMsg.DEL_ERROR);
        }
		return message;
	}

	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public String add(@ModelAttribute Module module){
		if (StringUtils.isBlank(module.getParentId()))
			module.setParentId("null");
		moduleDao.save(module);
		return "success";
	}

	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute Module module){
		moduleDao.update(module);
		return "success";
	}

}
