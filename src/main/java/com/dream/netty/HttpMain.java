package com.dream.netty;

public class HttpMain {
    public static void main(String[] args) throws Exception{
        //发送 GET 请求
        /*String s=HttpRequest.sendGet("http://localhost:8888", "key=123&v=456");
        System.out.println(s);*/


        //发送 POST 请求
      /*  String sr=HttpRequest.sendPost("http://localhost:6144/Home/RequestPostString", "key=123&v=456");
        System.out.println(sr);*/
        String getUrl="http://localhost:8888";
        String param="";
        System.out.println("Get请求1:"+HttpsConnection.sendGet(getUrl, param,"utf-8"));
    }
}
