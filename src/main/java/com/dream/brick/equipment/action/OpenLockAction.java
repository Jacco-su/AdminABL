package com.dream.brick.equipment.action;

import com.dream.brick.equipment.bean.AuthLog;
import com.dream.brick.equipment.bean.OpenLog;
import com.dream.brick.equipment.dao.IOpenLogDao;
import com.dream.framework.dao.Pager;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: OpenLockAction.java
 * @Description: 开锁日志
 * @date 2018-03-12 上午8:54
 */
@Controller
@Scope("prototype")
@RequestMapping("/openlog")
public class OpenLockAction {
	@Resource
	private IOpenLogDao openLogDao;
	@RequestMapping("/prList")
	public String prList()
			throws Exception {
		return "admin/openlog/list";
	}

	@RequestMapping("/list")
	@ResponseBody
	public String list(int page, int rows,String deptId,Pager pager)
			throws Exception {
		pager.setCurrentPage(page);
		pager.setPageSize(rows);
		JSONObject datas = new JSONObject();
		List<OpenLog> list = openLogDao.findLockLogList(deptId,pager);
		datas.put("total", pager.getTotalRow());
		datas.put("rows", list);
		return datas.toString();
	}
}
