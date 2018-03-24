package com.dream.brick.equipment.action;

import com.dream.brick.equipment.bean.Area;
import com.dream.brick.equipment.bean.Qgorg;
import com.dream.brick.equipment.dao.QgorgDao;
import com.dream.brick.listener.BasicData;
import com.dream.framework.dao.Pager;
import com.dream.util.AppMsg;
import com.dream.util.StringUtil;
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
import java.util.List;

/**
 * 区域操作实现类
 * 
 * @author ml
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/qgorga")
public class QgorgAction {
		@Resource
		private QgorgDao qgorgDao;
		
		@RequestMapping("/prList")
		public String prList(String orgId, HttpServletRequest request)
				throws Exception {
			return "admin/qgorg/list";
		}
		
		@RequestMapping("/list")
		@ResponseBody
		public String list(int page, int rows, String areacode, Pager pager)
				throws Exception {
			pager.setCurrentPage(page);
			pager.setPageSize(rows);
			JSONObject datas = new JSONObject();
			List<Qgorg> list = qgorgDao.findQgorgByAreacode(areacode, pager);
			datas.put("total", pager.getTotalRow());
			datas.put("rows", list);
			return datas.toString();
		}

		
		@RequestMapping("/prAdd")
		public String prAdd(ModelMap model){
			return "admin/qgorg/add";
		}
		
		@RequestMapping(value = "/add", method = RequestMethod.POST)
		@ResponseBody
		public String add(@ModelAttribute Qgorg qgorga) {
			String message="";
			try{
				Area area = BasicData.findAreaByAreacode(qgorga.getAreacode());
				qgorga.setAreaname(area.getAreaname());
				qgorga.setAddress(qgorga.getAddress().trim());
				qgorgDao.save(qgorga);
				message=StringUtil.jsonValue("1",AppMsg.ADD_SUCCESS);
			}catch(Exception e){
				message=StringUtil.jsonValue("0",AppMsg.ADD_ERROR);
			}
			return message;
		}
		
		@RequestMapping("/prUpdate")
		public String prUpdate(String id, ModelMap model){
			Qgorg qgorga = qgorgDao.find(Qgorg.class, id);
			model.addAttribute("qgorga", qgorga);
			return "admin/qgorg/update";
		}
		
		@RequestMapping("/prView")
		public String prView(String id, ModelMap model){
			Qgorg qgorga = qgorgDao.find(Qgorg.class, id);
			model.addAttribute("qgorga", qgorga);
			return "admin/qgorg/view";
		}		
		
		
		@RequestMapping(value = "/update", method = RequestMethod.POST)
		@ResponseBody
		public String update(@ModelAttribute Qgorg qgorga) {
			String message="";
			try{
				Area area = BasicData.findAreaByAreacode(qgorga.getAreacode());
				qgorga.setAreaname(area.getAreaname());
				qgorga.setAddress(qgorga.getAddress().trim());
				qgorga.setName(qgorga.getName().trim());
				qgorgDao.update(qgorga);
				message=StringUtil.jsonValue("1",AppMsg.UPDATE_SUCCESS);
			}catch(Exception e){
				message=StringUtil.jsonValue("0",AppMsg.UPDATE_ERROR);
			}
			return message;
		}
		@RequestMapping("/delete")
		@ResponseBody
		public String delete(String id){
			String message="";
			String hql="select count(*) from Department t where t.qgorgId=?";
			int count = qgorgDao.getResultNumber(hql, id);
			if(count>0){
				message=StringUtil.jsonValue("0",AppMsg.getMessage("orga101"));
                //101该区域拥有部门，不允许删除
                return message;
			}
			try{
				Qgorg qgorga = qgorgDao.find(Qgorg.class, id);
				qgorgDao.delete(qgorga);
				message=StringUtil.jsonValue("1",AppMsg.DEL_SUCCESS);
			}catch(Exception e){
				message=StringUtil.jsonValue("0",AppMsg.DEL_ERROR);
			}
			return message;
		}		
		
}
