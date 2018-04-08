//package com.dream.api;
//
//import com.alibaba.fastjson.JSON;
//import com.dream.brick.admin.bean.User;
//import com.dream.brick.equipment.bean.AuthLog;
//import com.dream.brick.equipment.bean.Authorization;
//import com.dream.brick.equipment.bean.Qgdis;
//import com.dream.brick.equipment.dao.IAuthLogDao;
//import com.dream.brick.equipment.dao.IAuthorizationDao;
//import com.dream.brick.listener.SessionData;
//import com.dream.socket.entity.AuthModel;
//import com.dream.socket.entity.DataProtocol;
//import com.dream.socket.entity.JsonDataProtocol;
//import com.dream.socket.utils.ByteUtil;
//import com.dream.socket.utils.Constants;
//import com.dream.util.AppMsg;
//import com.dream.util.FormatDate;
//import com.dream.util.RedisTemplateUtil;
//import com.dream.util.StringUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.UUID;
//
//@Controller
//@RequestMapping("/redis")
//public class RedisAction {
//    @Resource
//    private RedisTemplate redisTemplate;
//    private RedisTemplateUtil redisTemplateUtil = null;
//    @Resource
//    private IAuthorizationDao authorizationDao;
//
//    @Resource
//    private IAuthLogDao authLogDao;
//    @RequestMapping(value = "/get", method = {RequestMethod.POST})
//    @ResponseBody
//    public String get(String key,HttpServletRequest request) {
//        redisTemplateUtil = new RedisTemplateUtil(redisTemplate);
//        String [] keys=key.split(",");
//        String  authModel=null;
//        //钥匙绑定
//        if("7".equals(keys[1])){
//            ////采集器id:指令字:mac地址:钥匙mac地址:用户id
//            authModel=new AuthModel(new byte[]{7}, AuthModel.toBingKeyData(14,ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(keys[4],8))), Constants.KEY).toString();
//        }else if("12".equals(keys[1])){
//            //给钥匙校时
//            ////采集器id:指令字:控制器mac地址:钥匙mac地址
//           authModel=new AuthModel(new byte[]{12},AuthModel.toData(12,14),Constants.LOCK_KEY).toString();//校时成功
//        }else if("5".equals(keys[1])){
//            //开始授权
//            //采集器id:指令字:控制器mac地址:钥匙mac地址:锁识别号:开始日期:结束日期:用户id:站点ID
//            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//
//            AuthLog authLog = new AuthLog();
//            authLog.setId(uuid);
//            authLog.setCreateTime(FormatDate.getYMdHHmmss());
//            authLog.setAuthStartTime(keys[6]);
//            authLog.setAuthName("在线授权!");
//            User user=new User();
//            user.setId(keys[7]);
//            authLog.setUser(user);
//            authLog.setAuthType(2);
//            authLog.setAuthKeysId(keys[3]);
//            authLog.setAuthKeys(keys[3]);
//            authLog.setAuthLocks(keys[4]);
//            authLog.setAuthLocksId(keys[4]);
//            if(!"".equals(keys[6])) {
//                authLog.setAuthEndTime(FormatDate.dateSdfHHmmssParse(keys[6]));
//            }
//            Qgdis qgdis=new Qgdis();
//            qgdis.setId(keys[8]);
//            authLog.setQgdis(qgdis);
//            authLogDao.save(authLog);
//            authModel=new AuthModel(new byte[]{5},AuthModel.AuthorizationKey(ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(keys[7],8)),keys[4],keys[2],keys[5],keys[6]),Constants.LOCK_KEY).toString();//
//        }else if("1".equals(keys[1])){
//            //获取门锁信息  key=0000000002,1,DF:98,
//            //采集器id:指令字:控制器mac地址
//            authModel=new AuthModel(new byte[]{1},AuthModel.toData(1,1),Constants.KEY).toString();
//        }else if("2".equals(keys[1])){
//            //初始化锁      key=0000000002,2,DF:98:deptId,lockCode
//            String lockNum = "";
//            if(keys.length==5){
//                lockNum=keys[4];
//            }else {
//                Object value = redisTemplateUtil.get("lanya-lock-client");
//                if (value == null) {
//                    lockNum = "00" + keys[3];
//                    lockNum = addZeroForNum(lockNum, 16);
//                    redisTemplateUtil.set("lanya-lock-client", lockNum);
//                } else {
//                    lockNum = String.valueOf(Long.parseLong(value.toString()) + 1);
//                    redisTemplateUtil.set("lanya-lock-client", lockNum);
//                }
//            }
//            authModel=new AuthModel(new byte[]{2},AuthModel.toLockData(32,lockNum),Constants.KEY).toString();
//
//        }else if("13".equals(keys[1])){
//            //获取钥匙Mac地址
//            authModel = new AuthModel(new byte[]{13}).toString();
//        }
//
//        String macAddess=keys[2].replace(":","");
//        macAddess="00000000000000000000"+macAddess;
//        DataProtocol dataProtocol=new DataProtocol(new byte[]{00,01}, ByteUtil.hexToBytes(macAddess),ByteUtil.hexToBytes(authModel));
//        JsonDataProtocol jsonDataProtocol=new JsonDataProtocol();
//        jsonDataProtocol.setCollectorId(keys[0]);
//        jsonDataProtocol.setContent(dataProtocol.toString());
//        jsonDataProtocol.setDataType("client");
//        System.out.println(dataProtocol.toString());
//        String authKey=JSON.toJSONString(jsonDataProtocol)+";"+SessionData.getAdminId(request);
//        /*if("2".equals(keys[1])){
//            redisTemplateUtil.setList("lanya-lite", authKey);
//        }else {
//            for (int i = 0; i < 3; i++) {
//                redisTemplateUtil.setList("lanya-lite", authKey);
//            }
//        }*/
//        redisTemplateUtil.setList("lanya-lite", authKey);
//        int time=10000;
//        try {
//            Thread.sleep(time);
//            Object o = redisTemplateUtil.get(authKey);
//            /*while (time>0){
//                o = redisTemplateUtil.get(authKey);
//                if(o==null){
//                    redisTemplateUtil.setList("lanya-lite", authKey);
//                    time-=3000;
//                    Thread.sleep(3000);
//                }else{
//                    int t=o.toString().lastIndexOf("*")+1;
//                    if(keys[1].equals(t+"")){
//                        break;
//                    }else{
//                        time-=3000;
//                        o=null;
//                        redisTemplateUtil.setList("lanya-lite", authKey);
//                        Thread.sleep(3000);
//                    }
//                }
//            }*/
//            if (o == null) {
//                return   StringUtil.jsonValue("0", AppMsg.ADD_ERROR);
//            } else {
//               /* if("5".equals(keys[1])){
//                    if("授权成功!".equals(o.toString())){
//                        Authorization authorization=new Authorization();
//                        authorization.setType("client");
//                        authorization.setLocksid(keys[4]);
//                        authorization.setKeyssid(keys[3]);
//                        authorization.setAid(SessionData.getAdminId(request));
//                        authorization.setUid(keys[7]);
//                        authorization.setStarttime(FormatDate.getYMdHHmmss(keys[5]));
//                        authorization.setEndtime(FormatDate.getYMdHHmmss(keys[6]));
//                        authorization.setAdate(FormatDate.getYMdHHmmss());
//                        authorizationDao.save(authorization);
//                    }
//                }*/
//                return  StringUtil.jsonValue("1", o.toString().replace("*",""));
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return  StringUtil.jsonValue("1", "连接失败!");
//        }
//
//    }
//
//    @RequestMapping("/set")
//    @ResponseBody
//    public String set(String key, String value) {
//        redisTemplateUtil = new RedisTemplateUtil(redisTemplate);
//        redisTemplateUtil.set(key, value);
//        return StringUtil.jsonValue("1", AppMsg.ADD_SUCCESS);
//    }
//    public static String addZeroForNum(String str, int strLength) {
//        int strLen = str.length();
//        StringBuffer sb = null;
//        while (strLen < strLength) {
//            sb = new StringBuffer();
//            //sb.append("0").append(str);// 左补0
//            sb.append(str).append("0");//右补0
//            str = sb.toString();
//            strLen = str.length();
//        }
//        return str;
//    }
//    public static void main(String[] args) {
//
//        System.out.println("*********塑封袋".replace("*",""));
//        System.out.println(addZeroForNum("4110003",16));
//        //34313130303033303030303030303031
//        System.out.println(ByteUtil.bytesToHex("".getBytes()));
//        System.out.println(ByteUtil.hexStr2Str("30343031303230323030303030303030"));
//        System.out.println("0000000002,2,DF:98:deptId,2".split(",").length);
//    }
//}
