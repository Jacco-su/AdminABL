package com.dream.brick.equipment.action;

import com.dream.brick.equipment.bean.Charts;
import com.dream.brick.equipment.dao.IChartsDao;
import com.dream.framework.dao.Pager;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 钥匙 操作类
 */


@Controller
@Scope("prototype")
@RequestMapping("/chart")
public class ChartsAction {

    private IChartsDao chartsDao;

    @RequestMapping("/prList")
    public String prList(String orgId, HttpServletRequest request)
            throws Exception {
        return "admin/charts/list";
    }

    @RequestMapping("/keysList")
    public String keysList(String orgId, HttpServletRequest request)
            throws Exception {
        return "admin/charts/view";
    }

    @RequestMapping("/List")
    @ResponseBody
    public String list(int page, int rows, String deptId, String username, Pager pager)
            throws Exception {
        pager.setCurrentPage(page);
        pager.setPageSize(rows);
        JSONObject datas = new JSONObject();
        List<Charts> list = chartsDao.findChartsList(pager);
        datas.put("total", pager.getTotalRow());
        datas.put("rows", list);
        return datas.toString();
    }
    @RequestMapping("/kbsReport")
    @ResponseBody
    public String kbsList(int page, int rows, Pager pager){
        pager.setCurrentPage(page);
        pager.setPageSize(rows);
        JSONObject datas = new JSONObject();
        List<Charts> list = chartsDao.findChartsList(pager);
        datas.put("total", pager.getTotalRow());
        datas.put("rows", list);
        return datas.toString();
    }
    @RequestMapping("/kbsList")
    public String kbsList()
            throws Exception {
        return "admin/charts/kbsList";
    }

}