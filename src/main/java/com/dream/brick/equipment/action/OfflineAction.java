package com.dream.brick.equipment.action;

import com.alibaba.fastjson.JSON;
import com.dream.brick.admin.bean.User;
import com.dream.brick.equipment.bean.AuthLog;
import com.dream.brick.equipment.bean.KeysAuth;
import com.dream.brick.equipment.dao.IAuthLogDao;
import com.dream.brick.equipment.dao.IKeysAuthDao;
import com.dream.socket.entity.AuthModel;
import com.dream.socket.utils.ByteUtil;
import com.dream.socket.utils.Constants;
import com.dream.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author 陶乐乐(wangyiqianyi@qq.com)
 * @ClassName: OfflineAction.java
 * @Description:
 * @date 2018-03-30 上午10:09
 */
@Controller
@Scope("prototype")
@RequestMapping("/offline")
public class OfflineAction {
    @Resource
    private RedisTemplate redisTemplate;
    private RedisTemplateUtil redisTemplateUtil = null;
    @Resource
    private IAuthLogDao authLogDao;
    @Resource
    private IKeysAuthDao keysAuthDao;
    //在线授权
    @RequestMapping("/prViewAuth")
    public String prViewAuth( ModelMap model) {
        return "admin/offline/authList";
    }
    /**
    * class_name:
    * param:
    * describe: 获取可用串口
    * creat_user: taller
    * creat_date: 2018/3/30
    * creat_time: 15:24
    **/
    @ResponseBody
    @RequestMapping("/serial")
    public String serial(HttpServletRequest request) {
        redisTemplateUtil = new RedisTemplateUtil(redisTemplate);
       /* ReadSerialPortData serialPortData=new ReadSerialPortData();
        List<String> list=serialPortData.findPort();
        List<JSONObject> jsonObjects=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JSONObject json=new JSONObject();
            json.put("id", list.get(i));
            json.put("name", list.get(i));
            jsonObjects.add(json);
        }*/
        //return JSON.toJSONString(list);
        redisTemplateUtil.setList("lanya-lite-client-server", "FAFB"+";"+request.getSession().getAttribute("userUUID")+";findPort");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return  "";
        }
        Object o = redisTemplateUtil.get("FAFB"+";"+request.getSession().getAttribute("userUUID")+";findPort");
        if(o==null||o.toString()==""){
            return  "";
        }else {
            return o.toString().replace("[","").replace("]","");
        }
    }
    @ResponseBody
    @RequestMapping("/read")
    public String readAuth(String serial,String T,String userId,String lockNum,String deptId,String startDate,String endDate,String keysId,HttpServletRequest request) {
        String  authModel=null;
        //ReadSerialPortData serialPortData=new ReadSerialPortData();
        redisTemplateUtil = new RedisTemplateUtil(redisTemplate);
        try {
            //钥匙绑定
            if("7".equals(T)){
                authModel=new AuthModel(new byte[]{7}, AuthModel.toBingKeyData(14, ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(userId,8))), Constants.KEY).toString();
            }else if("12".equals(T)){
                //钥匙校时
                authModel=new AuthModel(new byte[]{12},AuthModel.toData(12,14),Constants.LOCK_KEY).toString();//校时成功
            }else if("1".equals(T)) {
                //获取门锁信息  key=0000000002,1,DF:98,
                authModel = new AuthModel(new byte[]{1}, AuthModel.toData(1, 1), Constants.KEY).toString();
            }else if("2".equals(T)){
                //初始化锁      key=0000000002,2,DF:98:deptId,lockCode
                if(StringUtils.isEmpty(lockNum)){
                    Object value = redisTemplateUtil.get("lanya-lock-client"+deptId);
                    if (value == null) {
                        lockNum = StringUtil.addZeroForNum(deptId, 16);
                        redisTemplateUtil.set("lanya-lock-client"+deptId, lockNum);
                    } else {
                        lockNum = String.valueOf(Long.parseLong(value.toString()) + 1);
                        redisTemplateUtil.set("lanya-lock-client"+deptId, lockNum);
                    }
                }
                authModel=new AuthModel(new byte[]{2},AuthModel.toLockData(32,lockNum),Constants.KEY).toString();
            }else if("5".equals(T)){
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                AuthLog authLog = new AuthLog();
                authLog.setId(uuid);
                authLog.setCreateTime(FormatDate.getYMdHHmmss());
                authLog.setAuthStartTime(FormatDate.dateSdfHHmmssParse(startDate));
                authLog.setAuthName("离线授权!");
                User user=new User();
                user.setId(userId);
                authLog.setUser(user);
                authLog.setAuthType(2);
                authLog.setAuthKeysId("");
                authLog.setAuthKeys("");
                authLog.setAuthLocks(lockNum);
                authLog.setAuthLocksId(lockNum);
                authLog.setAuthEndTime(FormatDate.dateSdfHHmmssParse(endDate));
                authLogDao.save(authLog);
                authModel=new AuthModel(new byte[]{5},AuthModel.AuthorizationKey(ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(userId,8)),lockNum,keysId,startDate,endDate),Constants.LOCK_KEY).toString();
            }else if("13".equals(T)){
                //获取钥匙Mac地址
                authModel = new AuthModel(new byte[]{13}).toString();
            }
//            serialPortData.setResponseString("");
//            serialPortData.selectPort(serial);
//            serialPortData.write(authModel);
//            serialPortData.startRead(3);
        }catch (Exception e){
            e.printStackTrace();
            return  StringUtil.jsonValue("0","操作失败，请重新获取!");
        }
        redisTemplateUtil.setList("lanya-lite-client-server", authModel+";"+request.getSession().getAttribute("userUUID")+";"+serial);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return  StringUtil.jsonValue("0","操作失败，请重新获取!");
        }
        try{
            String responseStr="";
            Object o = redisTemplateUtil.get(authModel+";"+request.getSession().getAttribute("userUUID")+";"+serial);
            if(o==null||o.toString()==""){
                responseStr="暂未获取到信息，请重试！";
            }else{
                responseStr= ResponseSocketUtil.V(o.toString());
                responseStr=responseStr.replace("*","");
                if(responseStr.indexOf("授权成功")>-1){
                    KeysAuth keysAuth=new KeysAuth();
                    keysAuth.setKeysId(keysId);
                    keysAuth.setLockName("测试");
                    keysAuth.setLockNum(lockNum);
                    keysAuthDao.save(keysAuth);
                }
                if("2".equals(T)) {
                    if(responseStr.indexOf("初始化成功")==-1) {
                        lockNum = String.valueOf(Long.parseLong(lockNum) - 1);
                        redisTemplateUtil.set("lanya-lock-client" + deptId, lockNum);
                    }
                }
            }
            System.out.println(responseStr+"PPPP");

            return  StringUtil.jsonValue("1",responseStr);
        }catch (Exception e){
            e.printStackTrace();
            return  StringUtil.jsonValue("0","操作失败，请重新获取!");
        }
    }
    @RequestMapping("/prViewAreaOfflineAuth")
    public String prViewAreaOfflineAuth(){
        return "admin/offline/authAreaList";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute AuthLog authLog,String serial,HttpServletRequest request) {
        String message = "";
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            authLog.setId(uuid);
            authLog.setAuthStatus("0");
            authLog.setCreateTime(FormatDate.getYMdHHmmss());
            authLogDao.save(authLog);
            message = StringUtil.jsonValue("1", "添加授权任务成功！");
        } catch (Exception e) {
            e.printStackTrace();
            message = StringUtil.jsonValue("0", AppMsg.ADD_ERROR);
        }
        if(StringUtils.isNotEmpty(authLog.getDisId())){
            auth(authLog,serial,request);
        }else{
            auth(authLog,serial,request);
        }
        return message;
    }
    private void auth(AuthLog authLog,String serial,HttpServletRequest request) {
        if (StringUtils.isNotEmpty(authLog.getAuthLocksId())) {
            String[] locks = authLog.getAuthLocksId().split(",");
            for (int i = 0; i < locks.length; i++) {
                if (StringUtils.isNotEmpty(locks[i])) {
                    String authModel = new AuthModel(new byte[]{5}, AuthModel.AuthorizationKey(ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(authLog.getUser().getId(), 8)), locks[i], authLog.getAuthKeysId(), FormatDate.dateParse(authLog.getAuthStartTime()), FormatDate.dateParse(authLog.getAuthEndTime())), Constants.LOCK_KEY).toString();//
                    auth(authModel,serial,authLog,locks[i],authLog.getAuthKeysId(),request);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
    private String  auth(String authModel,String serial,AuthLog authLogFirst,String lockNum,String keysId,HttpServletRequest request){
        /*ReadSerialPortData serialPortData=new ReadSerialPortData();
        serialPortData.setResponseString("");
        serialPortData.selectPort(serial);
        serialPortData.write(authModel);
        serialPortData.startRead(2);*/
        redisTemplateUtil.setList("lanya-lite-client-server", authModel+";"+request.getSession().getAttribute("userUUID")+";"+serial);
        try{
            Thread.sleep(7000);
            String responseStr="";
            Object o = redisTemplateUtil.get(authModel+";"+request.getSession().getAttribute("userUUID")+";"+serial);
            if(o==null||o.toString()==""){
                responseStr="暂未获取到信息，请重试！";
            }else {
                responseStr = o.toString().replace("*", "");
                if (responseStr.indexOf("授权成功") > -1) {
                    System.out.println("第二次授权开始");
                    AuthLog authLog = authLogDao.find(AuthLog.class, authLogFirst.getId());
                    authLog.setAuthStatus("1");
                    authLogDao.update(authLog);
                    KeysAuth keysAuth = new KeysAuth();
                    keysAuth.setKeysId(keysId);
                    keysAuth.setLockName("测试");
                    keysAuth.setLockNum(lockNum);
                    keysAuthDao.save(keysAuth);
                }
            }
            return  StringUtil.jsonValue("1",responseStr);
        }catch (Exception e){
            return  StringUtil.jsonValue("0","操作失败，请重新获取!");
        }
    }
    //prViewOfflineAuth
    @RequestMapping("/prViewOfflineAuth")
    public String prViewOfflineAuth(){
        return "admin/offline/authOfflineList";
    }
    //串口离线添加钥匙
    @RequestMapping("/prAddKeys")
    public String prAddKeys() {
        return "admin/offline/addKeysList";
    }
    @RequestMapping("/prAddLock")
    public String prAddLock() {
        return "admin/offline/addLockList";
    }

    public static void main(String[] args) {
        String xx="xx,pp";
        System.out.println(JSON.toJSONString(xx));
    }
}
