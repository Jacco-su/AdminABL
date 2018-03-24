package com.dream.brick.equipment.action;

import com.dream.brick.admin.bean.Department;
import com.dream.brick.equipment.dao.AreaInfoDao;
import com.dream.brick.listener.SessionData;
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

/**
 * 部门操作实现类
 *
 * @author DC
 */
@Controller()
@Scope("prototype")
@RequestMapping("/orga")
public class OrgAction {

    @Resource
    private AreaInfoDao areaInfoDao;

    private AreaInfoAction areaInfoAction;

    @RequestMapping("/index")
    public String index(String orgId, HttpServletRequest request)
            throws Exception {
        return "admin/org/list";
    }

//	@RequestMapping("/getChildren")
//	@ResponseBody
//	public String getChildren(String parentId, HttpServletRequest request)
//			throws Exception {
//		JSONArray datas = new JSONArray();
//		String areacode= SessionData.getAreacode(request);
//		List<Department> children = areaInfoDao.getChildren(parentId,areacode);
//		List<Department> deptAll=areaInfoDao.findDeptIdAndName();
//		for (Department dept : children) {
//			Map<String, Object> tempMap = new HashMap<String, Object>();
//			tempMap.put("id", dept.getId());
//			tempMap.put("text", dept.getName());
//			tempMap.put("iconCls", "icon-ok");
//			for(Department d:deptAll){
//				if((dept.getId()).equals(d.getParentId())){
//					tempMap.put("state", "closed");
//					break;
//				}
//			}
//			datas.put(tempMap);
//		}
//		return datas.toString();
//	}

    @RequestMapping("/prAdd")
    public String prAdd(String parentId, ModelMap model, HttpServletRequest request) {
//		String areacode= SessionData.getAreacode(request);
//		List<Qgorg> qgorgList=BasicData.findQgorgByAreacode(areacode);
//		model.addAttribute("qgorgList", qgorgList);
        model.addAttribute("parentId", parentId);
        return "admin/org/add";
    }

    @RequestMapping("/prUpdate")
    public String prUpdate(String id, ModelMap model, HttpServletRequest request) {
        Department dept = areaInfoDao.find(Department.class, id);
//		String areacode= SessionData.getAreacode(request);
//		List<Qgorg> qgorgList=BasicData.findQgorgByAreacode(areacode);
//		model.addAttribute("qgorgList", qgorgList);
        model.addAttribute("dept", dept);
        return "admin/org/update";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute Department dept) {

        String message = "";

        try {
            if (StringUtils.isBlank(dept.getParentId())) {
                dept.setParentId("null");
            }
            dept.setQgorgId("1001");
            dept.setAreacode("4101");
            areaInfoDao.save(dept);
            message = StringUtil.jsonValue("1", AppMsg.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            message = StringUtil.jsonValue("0", AppMsg.ADD_ERROR);
        }
        return message;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute Department dept) {
//		Qgorg qgorg=BasicData.findQgorgById(dept.getQgorgId());
//		dept.setAreacode(qgorg.getAreacode());
        areaInfoDao.update(dept);
        return "success";
    }


    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String ids, HttpServletRequest request) {
        String message = "";
//		String areacode= SessionData.getAreacode(request);
//		List<Department> children = deptDao.getChildren(ids,areacode);
//		if(children.size()>0){
//            message = StringUtil.jsonValue("0", AppMsg.getMessage("dept"));
////			message="该地区拥有区域，不允许删除";
//            //100该部门拥有子部门，不允许删除
//			return message;
//		}
        String parentId = SessionData.getAreacode(request);
//        List<Department> children = areaInfoDao.getChildren(ids, parentId);
//        if(children.size()>0){
//            message = StringUtil.jsonValue("0", AppMsg.getMessage("dept"));
////			message="该地区拥有区域，不允许删除";
//            //100该部门拥有子部门，不允许删除
//            return message;
//		}
        String hql = "select count(*) from User t where t.dept.id=?";

        int count = areaInfoDao.getResultNumber(hql, ids);
        if (count > 0) {
            message = StringUtil.jsonValue("0", AppMsg.getMessage("deptUser"));
//			message="该区域拥有人员，不允许删除";
            //101该区域拥有人员，不允许删除
            return message;
        }
        String hqldis = "select count(*) from Qgdis t where t.dept.id=?";

        int count2 = areaInfoDao.getResultNumber(hqldis, ids);
        if (count2 > 0) {
            message = StringUtil.jsonValue("0", AppMsg.getMessage("dis"));

            //101该区域拥有站点，不允许删除
            return message;
        }
        try {
            Department dept = new Department();
            dept.setId(ids);
            areaInfoDao.delete(dept);
            message = StringUtil.jsonValue("1", AppMsg.DEL_SUCCESS);
        } catch (Exception e) {
            message = StringUtil.jsonValue("0", AppMsg.DEL_ERROR);
        }
        return message;
    }


    @RequestMapping("/getDeptTree")
    @ResponseBody
    public String getDeptTree() throws Exception {
        return getJson("null").toString();
    }

    private JSONArray getJson(String parentcode) throws Exception {
//		JSONArray jsonArray = new JSONArray();
//		List<Department> areas = areaInfoDao.query(
//				"from Area where parentcode=? and treeShow=1", 0, 0,
//				parentcode);
//		for (Area area : areas) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("id", area.getAreacode());
//			jsonObject.put("text", area.getAreaname());
//			jsonArray.put(jsonObject);
//		}
        JSONArray jsonArray = new JSONArray();

//		JSONObject jsonObject = areaInfoAction.findAreaByParentId(parentcode);
//		jsonArray.put(jsonObject);
        return jsonArray;
    }
}
