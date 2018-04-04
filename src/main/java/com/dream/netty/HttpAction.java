package com.dream.netty;

import com.dream.brick.admin.bean.User;
import com.dream.brick.equipment.bean.AuthLog;
import com.dream.brick.equipment.bean.Qgdis;
import com.dream.brick.equipment.dao.IAuthLogDao;
import com.dream.socket.entity.AuthModel;
import com.dream.socket.entity.DataProtocol;
import com.dream.socket.entity.JsonDataProtocol;
import com.dream.socket.utils.ByteUtil;
import com.dream.socket.utils.Constants;
import com.dream.util.FormatDate;
import com.dream.util.RedisTemplateUtil;
import com.dream.util.StringUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/redis")
public class HttpAction {

    @Resource
    private RedisTemplate redisTemplate;
    private RedisTemplateUtil redisTemplateUtil = null;
    @Resource
    private IAuthLogDao authLogDao;

    @RequestMapping(value = "/get", method = {RequestMethod.POST})
    @ResponseBody
    public String get(String key,HttpServletRequest request) {
        redisTemplateUtil = new RedisTemplateUtil(redisTemplate);
        String [] keys=key.split(",");
        String  authModel=null;

        /*绑定蓝牙钥匙*/
        if("7".equals(keys[1])){

            authModel=new AuthModel(new byte[]{7}, AuthModel.toBingKeyData(14, ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(keys[4],8))), Constants.KEY).toString();
        }
        /*钥匙校时*/
        else if("12".equals(keys[1])){
            //采集器id:指令字:控制器mac地址:钥匙mac地址
            authModel=new AuthModel(new byte[]{12},AuthModel.toData(12,14),Constants.LOCK_KEY).toString();//校时成功
        }
        else if("5".equals(keys[1])){
            //开始授权
            //采集器id:指令字:控制器mac地址:钥匙mac地址:锁识别号:开始日期:结束日期:用户id:站点ID
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            AuthLog authLog = new AuthLog();
            authLog.setId(uuid);
            authLog.setCreateTime(FormatDate.getYMdHHmmss());
            authLog.setAuthStartTime(keys[6]);
            authLog.setAuthName("在线授权!");
            authLog.setAuthStatus("1");
            User user=new User();
            user.setId(keys[7]);
            authLog.setUser(user);
            authLog.setAuthType(2);
            authLog.setAuthKeysId(keys[3]);
            authLog.setAuthKeys(keys[3]);
            authLog.setAuthLocks(keys[4]);
            authLog.setAuthLocksId(keys[4]);
            if(!"".equals(keys[6])) {
                authLog.setAuthEndTime(FormatDate.dateSdfHHmmssParse(keys[6]));
            }
            Qgdis qgdis=new Qgdis();
            qgdis.setId(keys[8]);
            authLog.setQgdis(qgdis);
            authLogDao.save(authLog);
            authModel=new AuthModel(new byte[]{5},AuthModel.AuthorizationKey(ByteUtil.hexStrToByteArray(ByteUtil.addZeroForNum(keys[7],8)),keys[4],"DD:17:16:65:FB:33",keys[5],keys[6]),Constants.LOCK_KEY).toString();//
        }
        else if("1".equals(keys[1])){
            //获取门锁信息  key=0000000002,1,DF:98,
            //采集器id:指令字:控制器mac地址
            authModel=new AuthModel(new byte[]{1},AuthModel.toData(1,1),Constants.KEY).toString();
        }
        else if("2".equals(keys[1])){
           /* //初始化锁      key=0000000002,2,DF:98:deptId,lockCode

            if(keys.length==5){
                lockNum=keys[4];
            }else {
                lockNum = "00" + keys[3];
                lockNum = addZeroForNum(lockNum, 16);
            }*/
            //初始化锁      key=0000000002,2,DF:98:deptId,lockCode
            String lockNum = "";
            if(keys.length==5){
                lockNum=keys[4];
            }else {
                Object value = redisTemplateUtil.get("lanya-lock-client"+keys[3]);
                if (value == null) {
                    lockNum = StringUtil.addZeroForNum(keys[3], 16);
                    redisTemplateUtil.set("lanya-lock-client"+keys[3], lockNum);
                } else {
                    lockNum = String.valueOf(Long.parseLong(value.toString()) + 1);
                    redisTemplateUtil.set("lanya-lock-client"+keys[3], lockNum);
                }
            }
            authModel=new AuthModel(new byte[]{2},AuthModel.toLockData(32,lockNum),Constants.KEY).toString();

        }
        else if("13".equals(keys[1])){
            //获取钥匙Mac地址
            authModel = new AuthModel(new byte[]{13}).toString();
        }

        String macAddess="DD:17:16:65:FB:33".replace(":","");
        macAddess="00000000000000000000"+macAddess;
//        String macAddess="00000000000000000000";
        //控制器ID==》页面获取 , 格式==》12345
        String collectoreID=keys[0];
        DataProtocol dataProtocol=new DataProtocol(new byte[]{00,01}, ByteUtil.hexToBytes(macAddess),ByteUtil.hexToBytes(authModel));
        JsonDataProtocol jsonDataProtocol=new JsonDataProtocol();
//            jsonDataProtocol.setCollectorId(keys[0]);
//        jsonDataProtocol.setContent(dataProtocol.toString());
//        jsonDataProtocol.setDataType("client");

        String getUrl="http://localhost:8888";
        String param=dataProtocol.toString()+collectoreID;
        String message = HttpsConnection.sendGet(getUrl, param,"utf-8");
//        System.out.println("Get请求:"+HttpsConnection.sendGet(getUrl, param,"utf-8"));
        System.out.println("返回内容------"+message);

        return  StringUtil.jsonValue("1", message.replace("*",""));
    }
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            //sb.append("0").append(str);// 左补0
            sb.append(str).append("0");//右补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }
}

