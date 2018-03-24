package com.dream.brick.equipment.action;

import com.dream.brick.equipment.bean.Log;
import com.dream.brick.equipment.dao.LogDao;
import com.dream.framework.dao.Pager;
import com.dream.util.AppMsg;
import com.dream.util.MsgResponse;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taller on 17/11/29.
 */
@Controller
@Scope("prototype")
@RequestMapping("/log")
public class LogAction {
    @Resource
    private LogDao logDao;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public String list(int page, int rows, Pager pager) throws Exception {
        pager.setCurrentPage(page);
        pager.setPageSize(rows);
        JSONObject datas = new JSONObject();
        List<Log> logList=logDao.findLogList(pager);
        datas.put("total", pager.getTotalRow());
        datas.put("rows", logList);
        return datas.toString();
    }
    @RequestMapping("/index")
    public String index(){
        return "log/list";
    }
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(ModelMap model){
        return "log/add";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteJnjs(int id){
        MsgResponse response=new MsgResponse();
        try{
            Log log=logDao.find(Log.class,id);
            logDao.delete(log);
            response.setMessage(AppMsg.DEL_SUCCESS);
            response.setResult("1");
        }catch(Exception e){
            e.printStackTrace();
            response.setMessage(AppMsg.DEL_ERROR);
            response.setResult("0");
        }
        return response.toString();
    }

}
