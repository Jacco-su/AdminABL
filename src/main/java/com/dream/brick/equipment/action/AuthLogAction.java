package com.dream.brick.equipment.action;

import com.alibaba.fastjson.JSON;
import com.dream.brick.equipment.bean.*;
import com.dream.brick.equipment.dao.CollectorDao;
import com.dream.brick.equipment.dao.CollectoreDao;
import com.dream.brick.equipment.dao.IAuthLogDao;
import com.dream.brick.equipment.dao.IChartsDao;
import com.dream.brick.listener.SessionData;
import com.dream.framework.dao.Pager;
import com.dream.netty.HttpsConnection;
import com.dream.socket.entity.AuthModel;
import com.dream.socket.entity.DataProtocol;
import com.dream.socket.entity.JsonDataProtocol;
import com.dream.socket.utils.ByteUtil;
import com.dream.socket.utils.Constants;
import com.dream.util.AppMsg;
import com.dream.util.FormatDate;
import com.dream.util.RedisTemplateUtil;
import com.dream.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * 钥匙 操作类
 */


@Controller
@Scope("prototype")
@RequestMapping("/authlog")
public class AuthLogAction {
    @Resource
    private IAuthLogDao authLogDao;

    @Resource
    private CollectorDao collectorDao;

    @Resource
    private CollectoreDao collectoreDao;

    @Resource
    private RedisTemplate redisTemplate;
    private RedisTemplateUtil redisTemplateUtil = null;

    @RequestMapping("/prList")
    public String prList()
            throws Exception {
        System.out.println("-------------授权----------");
        return "admin/authlog/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(int page, int rows,String authName,String authStartTime,String authEndTime, String userId,Pager pager)
            throws Exception {
        pager.setCurrentPage(page);
        pager.setPageSize(rows);
        JSONObject datas = new JSONObject();
        List<AuthLog> list = authLogDao.findList(authName,authStartTime,authEndTime,userId,pager);
        datas.put("total", pager.getTotalRow());
        datas.put("rows", list);
        return datas.toString();
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute AuthLog authLog,HttpServletRequest request) {
        String message = "";
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            authLog.setId(uuid);
            authLog.setAuthStatus("0");
            authLog.setCreateTime(FormatDate.getYMdHHmmss());
            authLogDao.save(authLog);
//            message = StringUtil.jsonValue("1", "添加授权任务成功！");
        } catch (Exception e) {
            e.printStackTrace();
            message = StringUtil.jsonValue("0", AppMsg.ADD_ERROR);
        }
        String adminId= SessionData.getAdminId(request);
        message = auth(authLog,adminId,uuid,authLog.getCollectorId());
        /*if(StringUtils.isNotEmpty(authLog.getCollectorId())){
            message = auth(authLog,adminId,uuid,authLog.getCollectorId());
        }else{
            auth(authLog,adminId,uuid);
        }*/
        return message;
    }
    private String auth(AuthLog authLog,String adminId,String uuid,String collectorId){
        String authModel = new AuthModel(new byte[]{5},AuthModel.AuthorizationKey(ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(authLog.getUser().getId(),8)),authLog.getAuthLocksId(),"DD:17:16:65:FB:33",FormatDate.dateParse(authLog.getAuthStartTime()), FormatDate.dateParse(authLog.getAuthEndTime())),Constants.LOCK_KEY).toString();
        String macAddess="DD:17:16:65:FB:33".replace(":","");
        macAddess="00000000000000000000"+macAddess;

        DataProtocol dataProtocol=new DataProtocol(new byte[]{00,01}, ByteUtil.hexToBytes(macAddess),ByteUtil.hexToBytes(authModel));
        JsonDataProtocol jsonDataProtocol=new JsonDataProtocol();
//            jsonDataProtocol.setCollectorId(keys[0]);
//        jsonDataProtocol.setContent(dataProtocol.toString());
//        jsonDataProtocol.setDataType("client");

        String getUrl="http://localhost:8888";
        String param=dataProtocol.toString()+authLog.getCollectorId();
        String message = HttpsConnection.sendGet(getUrl, param,"utf-8");
//        System.out.println("Get请求:"+HttpsConnection.sendGet(getUrl, param,"utf-8"));
        System.out.println("返回内容------"+message);

        return  StringUtil.jsonValue("1", message.replace("*",""));
        //读取控制器
//        List<Collectore> collectoreList = collectoreDao.findCollectoreByCollector(collectorId);
        // System.out.println(collectoreList);
        /*if (collectoreList != null && collectoreList.size() > 0) {
            for (int z = 0; z < collectoreList.size(); z++) {
                Collectore collectore = collectoreList.get(z);
                if(StringUtils.isNotEmpty(collectore.getCeMAC())){
                    String macAddess=collectore.getCeMAC().replace(":","");
                    macAddess="00000000000000000000"+macAddess;
                    if(StringUtils.isNotEmpty(authLog.getAuthLocksId())) {
                        String [] locks=authLog.getAuthLocksId().split(",");
                        for (int i = 0; i <locks.length ; i++) {
                            if(StringUtils.isNotEmpty(locks[i])){
                                System.out.println(locks[i]);
                                System.out.println(ByteUtil.bytesToHex(locks[i].getBytes()));
                                String authModel = new AuthModel(new byte[]{5}, AuthModel.AuthorizationKey(ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(authLog.getUser().getId(),8)), locks[i], collectore.getCeMAC(), FormatDate.dateParse(authLog.getAuthStartTime()), FormatDate.dateParse(authLog.getAuthEndTime())), Constants.LOCK_KEY).toString();//
//                                System.out.println("开始授权！");
                                auth("5", macAddess,collectore.getCollector().getCcode(), adminId, authModel,uuid);//ByteUtil.hexStrToByteArray(ByteUtil.bytesToHex(keys[4].getBytes()))
                            }
                        }
                    }
                }
            }
        }*/
    }
   private void auth(AuthLog authLog,String adminId,String uuid){
       //读取采集器
       List<Collector> collectorList=collectorDao.findCollectorByQgdisid(authLog.getDisId());
       if(collectorList!=null&&collectorList.size()>0) {
           for (int j = 0; j < collectorList.size(); j++) {
               Collector collector = collectorList.get(j);
               auth(authLog,adminId,uuid,collector.getId());
           }
       }
   }
    private String  auth(String t,String macAddess,String collectorId,String adminId,String authModel,String uuid){
//        redisTemplateUtil = new RedisTemplateUtil(redisTemplate);
        DataProtocol dataProtocol=new DataProtocol(new byte[]{00,01}, ByteUtil.hexToBytes(macAddess),ByteUtil.hexToBytes(authModel));
        JsonDataProtocol jsonDataProtocol=new JsonDataProtocol();
        jsonDataProtocol.setCollectorId(collectorId);
        jsonDataProtocol.setContent(dataProtocol.toString());
        jsonDataProtocol.setDataType("client");
        System.out.println(dataProtocol.toString());
        String authKey= JSON.toJSONString(jsonDataProtocol)+";"+adminId;
        for (int i = 0; i < 3; i++) {
            redisTemplateUtil.setList("lanya-lite", authKey);
        }
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o = redisTemplateUtil.get(authKey);
        if (o == null) {
            return   "";
        } else {
            if("5".equals(t)){
                System.out.println("第一次授权开始");
                if(o.toString().indexOf("授权成功")>-1){
                    System.out.println("第二次授权开始");
                    AuthLog authLog=authLogDao.find(AuthLog.class,uuid);
                    authLog.setAuthStatus("1");
                    authLogDao.update(authLog);
                }
            }
            return  StringUtil.jsonValue("1", o.toString());
        }

    }
}