package com.dream.brick.equipment.action;

/**
 * Created by taller on 17/11/29.
 */

import com.dream.brick.equipment.bean.SysArea;
import com.dream.brick.equipment.dao.SysAreaDao;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 区域controller
 */
@Controller
@Scope("prototype")
@RequestMapping("/area")
public class AreaAction {
    @Resource
    private SysAreaDao sysAreaDao;
    /**
     * 区域集合(JSON)
     */
    /**
     * 获取用户json
     */
    @RequestMapping(value="list")
    @ResponseBody
    public String getData(int grade, String pid, String areaCode) {
        Gson gson = new Gson();
        List<SysArea> areaList=sysAreaDao.findAreaList(grade,pid,areaCode);
        return gson.toJson(areaList);
    }
}
