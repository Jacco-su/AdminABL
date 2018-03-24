package com.dream.brick.admin.action;

import com.dream.framework.dao.Pager;
import com.dream.brick.admin.bean.Operation;
import com.dream.brick.admin.dao.IOperationDao;
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
import java.util.List;

/**
 * 权限操作实现类
 * 
 * @author maolei
 * 
 */
@Controller()
@Scope("prototype")
@RequestMapping("/operation")
public class OperationAction {

	@Resource
	private IOperationDao operationDao;

	@RequestMapping("/index")
	public String index() throws Exception {
		return "admin/operation/list";
	}

	@RequestMapping("/list")
	@ResponseBody
	public String list(int page, int rows, Pager pager, String moduleId)
			throws Exception {
		pager.setCurrentPage(page);
		pager.setPageSize(rows);
		JSONObject datas = new JSONObject();
		List<Operation> opList = operationDao.query(
				"from Operation where cls = ?", pager, moduleId);
		datas.put("total", pager.getTotalRow());
		datas.put("rows", opList);
		return datas.toString();
	}

	@RequestMapping("/prAdd")
	public String prAdd(String cls, ModelMap model){
		model.addAttribute("cls", cls);
		return "admin/operation/add";
	}

	@RequestMapping("/prUpdate")
	public String prUpdate(String id, ModelMap model){
		Operation operation = operationDao.find(Operation.class, id);
		model.addAttribute("operation", operation);
		return "admin/operation/update";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String ids){
		if (StringUtils.isNotBlank(ids)) {
			for (String id : ids.split(",")) {
				Operation operation = operationDao.find(Operation.class, id);
				operationDao.delete(operation);
			}
		}
		return "success";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@ModelAttribute Operation operation){
		operationDao.save(operation);
		return "success";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute Operation operation){
		operationDao.update(operation);
		return "success";
	}

}
