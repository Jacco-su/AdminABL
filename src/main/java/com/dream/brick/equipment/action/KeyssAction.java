package com.dream.brick.equipment.action;

import com.alibaba.fastjson.JSON;
import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.dao.IDeptDao;
import com.dream.brick.admin.dao.impl.UserDao;
import com.dream.brick.equipment.bean.Keyss;
import com.dream.brick.equipment.dao.CollectorDao;
import com.dream.brick.equipment.dao.IKeyssDao;
import com.dream.framework.dao.Pager;
import com.dream.util.AppMsg;
import com.dream.util.FormatDate;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钥匙 操作类
 */


@Controller
@Scope("prototype")
@RequestMapping("/keyss")
public class KeyssAction {

    @Resource
    private IKeyssDao ikeyssDao;
    @Resource
    private CollectorDao collectorDao;
    @Resource
    private UserDao userDao;
    @Resource
    private IDeptDao deptDao;

    @RequestMapping("/prList")
    public String prList(String keyssId, HttpServletRequest request) throws Exception {
        return "admin/keyss/list";
    }


    @RequestMapping("/list")
    @ResponseBody
    public String List(int page, int rows, Pager pager,String deptId) throws Exception {
        pager.setCurrentPage(page);
        pager.setPageSize(rows);
        JSONObject datas = new JSONObject();
        if(StringUtils.isNotEmpty(deptId)){
            Department department =deptDao.find(Department.class,deptId);
            deptId=department.getAreacode();
        }
        List<Keyss> list = ikeyssDao.findKeyssList(deptId,pager);
        datas.put("total", pager.getTotalRow());
        datas.put("rows", list);

        return datas.toString();
    }


    @RequestMapping("/prAdd")
    public String prAdd(ModelMap modelMap) {
//        modelMap.addAttribute("collectorList", JSON.toJSONString(collectorDao.findAllCollector()));
        modelMap.addAttribute("usersList", JSON.toJSONString(userDao.findAllUser()));
        return "admin/keyss/add";
    }

    @RequestMapping("/prAddView")
    public String prAddView(ModelMap modelMap) {
//        modelMap.addAttribute("collectorList", JSON.toJSONString(collectorDao.findAllCollector()));
        modelMap.addAttribute("usersList", JSON.toJSONString(userDao.findAllUser()));
        return "admin/keyss/addList";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute Keyss keyss) {
        String message = "";
        try {
            Map<String,String> params=new HashMap<>();
            params.put("keyssMAC",keyss.getKeyssMAC());
            List<Keyss> keyssList=ikeyssDao.findkeys(params);
            if(keyssList.size()>0){
                return  StringUtil.jsonValue("0", "钥匙Mac地址存在重复，禁止添加！");
            }
            keyss.setKeyssDate(FormatDate.getYMdHHmmss());
            ikeyssDao.save(keyss);
            message = StringUtil.jsonValue("1", AppMsg.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            message = StringUtil.jsonValue("0", AppMsg.ADD_ERROR);
        }
        return message;
    }

    @RequestMapping("/prUpdate")
    public String Update(String id, ModelMap modelMap,String deptId, String dissName, Pager pager) throws Exception {
        Keyss keyss = ikeyssDao.find(Keyss.class, id);
        modelMap.addAttribute("keyss", keyss);
        //modelMap.addAttribute("userList", userDao.findAllUser());
        return "admin/keyss/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute Keyss keyss) {
        String message = "";
        try {
           keyss.setKeyssDate(FormatDate.getYMdHHmmss());
            ikeyssDao.update(keyss);
            message = StringUtil.jsonValue("1", AppMsg.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            message = StringUtil.jsonValue("0", AppMsg.UPDATE_ERROR);
        }
        return message;
    }

    @RequestMapping("/prView")
    public String View(String id, ModelMap modelMap) {
        Keyss keyss = ikeyssDao.find(Keyss.class, id);
        modelMap.addAttribute("keyss", keyss);
        return "admin/keyss/view";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String id) {
        String message = "";
        try {
            Keyss keyss = ikeyssDao.find(Keyss.class, id);
            ikeyssDao.delete(keyss);
            message = StringUtil.jsonValue("1", AppMsg.DEL_SUCCESS);
        } catch (Exception e) {

            e.printStackTrace();
            message = StringUtil.jsonValue("0", AppMsg.DEL_ERROR);
        }

        return message;
    }



}
