package com.dream.brick.equipment.action;


import com.dream.brick.admin.bean.Department;
import com.dream.brick.admin.dao.IDeptDao;
import com.dream.brick.equipment.bean.Collector;
import com.dream.brick.equipment.bean.Locks;
import com.dream.brick.equipment.bean.Qgdis;
import com.dream.brick.equipment.dao.QgdisDao;
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
import java.util.List;

import static com.dream.brick.listener.BasicData.qgdisDao;

//import com.dream.brick.equipment.bean.Qgorg;

/**
 * 站点 操作实现类
 */
@Controller
@Scope("prototype")
@RequestMapping("/disa")

public class DistributionAction {

    @Resource
    private QgdisDao disDao;

    @Resource
    private IDeptDao deptDao;

    @RequestMapping("/prList")
    public String prList(String disId, HttpServletRequest request)
            throws Exception {
        return "admin/diss/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(int page, int rows, String deptId, String dissName, Pager pager)
            throws Exception {
        pager.setCurrentPage(page);
        pager.setPageSize(rows);
        JSONObject datas = new JSONObject();
        if(StringUtils.isNotEmpty(deptId)){
            Department department =deptDao.find(Department.class,deptId);
            deptId=department.getAreacode();
        }
        List<Qgdis> list = disDao.findQgdisList(deptId, dissName, pager);
        datas.put("total", pager.getTotalRow());
        datas.put("rows", list);
        return datas.toString();
    }

    @RequestMapping("/prAdd")
    public String prAdd(String deptId, ModelMap model) {
        model.addAttribute("deptId", deptId);
        return "admin/diss/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute Qgdis disa) {
        String message = "";
        try {
            disa.setCreateTime(FormatDate.getYMdHHmmss());
            disDao.addDis(disa);
            message = StringUtil.jsonValue("1", AppMsg.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            message = StringUtil.jsonValue("0", AppMsg.ADD_ERROR);
        }
        return message;
    }

    @RequestMapping("/prUpdate")
    public String prUpdate(String id, ModelMap model) {
        Qgdis qgdis = qgdisDao.find(Qgdis.class, id);
        model.addAttribute("qgdis", qgdis);
        return "admin/diss/update";
    }

    @RequestMapping("/prView")
    public String prView(String id, ModelMap model) {
        Qgdis disa = disDao.find(Qgdis.class, id);
        model.addAttribute("disa", disa);
        return "admin/diss/view";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@ModelAttribute Qgdis disa) {
        String message = "";
//        disa.setCreateTime(FormatDate.getYMdHHmmss().trim());
        try {
            disa.setAddress(disa.getAddress().trim());
            disa.setName(disa.getName().trim());
//            disa.setCreateTime(disa.getCreateTime());
            disDao.update(disa);
            message = StringUtil.jsonValue("1", AppMsg.UPDATE_SUCCESS);
        } catch (Exception e) {
            message = StringUtil.jsonValue("0", AppMsg.UPDATE_ERROR);
        }
        return message;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String id, HttpServletRequest request) {
        String message = "";
        if(1==1){

            //有智能锁不能删除
            String hql = "from Locks where qgdis.id="+id;
            List<Locks> lockList= disDao.findList(hql);
            if (lockList.size()>0) {
                message = StringUtil.jsonValue("0", AppMsg.getMessage("dislock"));
                //101该站点拥有智能锁，不允许删除
                return message;
            }

            //有采集器不能删除
            String hql2 = "from Collector where dis.id="+id;
           /* String hql2 = "SELECT * from tCollector  WHERE disId = "+id;
            Collector collectorList= (Collector)disDao.queryBySql(hql2);
            System.out.println(collectorList+".......count2");
            if (collectorList!=null) {
                message = StringUtil.jsonValue("1", AppMsg.getMessage("collector"));
                //101该有采集器不能删除，不允许删除
                return message;
            }*/
            List<Collector> collectorList = disDao.findList(hql2);
            if (collectorList.size()>0) {
                message = StringUtil.jsonValue("1", AppMsg.getMessage("collector"));
                //101该有采集器不能删除，不允许删除
                return message;
            }
        }


        /*//有控制器不能删除
        String hql3 = "select count(1) from Colloctore t where t.qgdis.id=?";
        int count3 = disDao.getResultNumber(hql, id);
        System.out.println(count3+".......count3");
        if (count3 > 0) {
            message = StringUtil.jsonValue("0", AppMsg.getMessage("dislock"));
            //101该站点拥有智能锁，不允许删除
            return message;
        }*/

        try {
            Qgdis disa = disDao.find(Qgdis.class, id);
            System.out.println(disa);
            disDao.delete(disa);
            message = StringUtil.jsonValue("3", AppMsg.DEL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            message = StringUtil.jsonValue("4", AppMsg.DEL_ERROR);
        }
        return message;
    }

}
