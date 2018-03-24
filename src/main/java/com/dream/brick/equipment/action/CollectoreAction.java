package com.dream.brick.equipment.action;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.dao.IDeptDao;
import com.dream.brick.equipment.bean.Collector;
import com.dream.brick.equipment.bean.Collectore;
import com.dream.brick.equipment.dao.CollectorDao;
import com.dream.brick.equipment.dao.CollectoreDao;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 采集器 操作实现类
 */
@Controller
@Scope("prototype")
@RequestMapping("/collectore")

public class CollectoreAction {

    @Resource
    private IDeptDao deptDao;
    @Resource
    private CollectoreDao collectoreDao;
    @Resource
    private CollectorDao collectorDao;

    @RequestMapping("/prList")
    public String prList()
            throws Exception {
        return "admin/collectore/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(int page, int rows, Pager pager,String deptId)
            throws Exception {
        pager.setCurrentPage(page);
        pager.setPageSize(rows);
        JSONObject datas = new JSONObject();
        if(StringUtils.isNotEmpty(deptId)){
            Department department =deptDao.find(Department.class,deptId);
            deptId=department.getAreacode();
        }
        List<Collectore> list = collectoreDao.findCollectoreList(deptId,pager);
        datas.put("total", pager.getTotalRow());
        datas.put("rows", list);
        return datas.toString();
    }


    @RequestMapping("/prAdd")
    public String prAdd(ModelMap modelMap,String deptId) {
        if(StringUtils.isNotEmpty(deptId)){
            Department department =deptDao.find(Department.class,deptId);
            deptId=department.getAreacode();
        }
        modelMap.addAttribute("collectorList",JSON.toJSONString(collectorDao.findCollectorList(deptId), SerializerFeature.DisableCircularReferenceDetect));
        return "admin/collectore/add";
    }
/*
    @RequestMapping("/all")
    @ResponseBody
    public String all(String deptId) {
        if(StringUtils.isNotEmpty(deptId)){
            Department department =deptDao.find(Department.class,deptId);
            deptId=department.getAreacode();
        }
        return JSON.toJSONString(collectorDao.findCollectorList(deptId),SerializerFeature.DisableCircularReferenceDetect);
    }*/

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute Collectore collectorea) {
        String message = "";
        try {
            collectorea.setCeDate(FormatDate.getYMdHHmmss());
//                collectorea.setCeCode(collectorea.getCeCode());
            collectoreDao.addCollectore(collectorea);
            message = StringUtil.jsonValue("1", AppMsg.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            message = StringUtil.jsonValue("0", AppMsg.ADD_ERROR);
        }
        return message;
    }

    @RequestMapping("/prUpdate")
    public String prUpdate(String id, ModelMap model,String deptId) {
        Collectore collectore = collectoreDao.find(Collectore.class, id);
        model.addAttribute("collectore", collectore);
        if(StringUtils.isNotEmpty(deptId)){
            Department department =deptDao.find(Department.class,deptId);
            deptId=department.getAreacode();
        }
        model.addAttribute("collectorList", JSON.toJSONString(collectorDao.findCollectorList(deptId),SerializerFeature.DisableCircularReferenceDetect));
        return "admin/collectore/update";
    }

    @RequestMapping("/prView")
    public String prView(String id, ModelMap model) {
        Collectore collectore = collectoreDao.find(Collectore.class, id);
        model.addAttribute("collectorea", collectore);
        return "admin/collectore/view";
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute Collectore collectore) {
        String message = "";

        try{

            collectoreDao.update(collectore);
                message = StringUtil.jsonValue("1", AppMsg.UPDATE_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                message = StringUtil.jsonValue("0", AppMsg.UPDATE_ERROR);
            }
        return message;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(String id) {
        String message = "";
        try {
            Collectore collectore = collectoreDao.find(Collectore.class, id);
            collectoreDao.delete(collectore);
            message = StringUtil.jsonValue("1", AppMsg.DEL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            message = StringUtil.jsonValue("0", AppMsg.DEL_ERROR);
        }
        return message;
    }
}
