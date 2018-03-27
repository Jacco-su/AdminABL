//package com.dream.netty;
//
//import com.dream.util.StringUtil;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//@RequestMapping("/redis")
//public class HttpAction {
//
//    @RequestMapping(value = "/get", method = {RequestMethod.POST})
//    @ResponseBody
//    public String get(String key,HttpServletRequest request) {
//        String [] keys=key.split(",");
//        String message = "";
//        /*绑定蓝牙钥匙*/
//        if("7".equals(keys[1])){
//            String getUrl="http://localhost:8888";
//            String param="";
//            message = HttpsConnection.sendGet(getUrl, param,"utf-8");
//            System.out.println("Get请求1:"+HttpsConnection.sendGet(getUrl, param,"utf-8"));
//        }
//        return  StringUtil.jsonValue("1", message.replace("*",""));
//    }
//}
