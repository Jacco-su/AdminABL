<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/1/10
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>监控照片</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/icon.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/windowControl.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/toolbar.js"></script>
    <script type="text/javascript" src="${basePath}/js/calendar/WdatePicker.js"></script>
    <script type="text/javascript" src="${basePath}/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/uploadify/uploadify.css"/>
    <script type="text/javascript" src="${basePath}js/resources/js/jquery-1.8.2.min.js"></script>
</head>
<body>

<p>图片列表</p>

<table border="2" width="500" height="300">
    <tr>
        <td>
            <div>
                1<img alt="图片资源" src="${basePath}/uploads/workticket/00129.jpg">
            </div>
        </td>
        <td>
            2<img alt="image" src="/capture/seekExperts"><br>
        </td>
    </tr>
    <tr>
        <td>
            3<img border=0 src="downPhoto.do?fileId=${"fileList"}">
        </td>
    </tr>
    <tr>
        <td>
            4访问FTP <img src=""/>
        </td>
    </tr>
</table>
</body>
</html>

