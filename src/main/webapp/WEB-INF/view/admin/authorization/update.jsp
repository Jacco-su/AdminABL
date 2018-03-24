<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>


<meta charset="UTF-8">
<title>Basic DateTimeBox - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui-1.5.3/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui-1.5.3/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui-1.5.3/demo/demo.css">

<script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
<%--<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>--%>
<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
<%--<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/jquery.min.js"></script>--%>
<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/jquery.easyui.mobile.js"></script>

<script>

    $('#dt').datetimebox({
        value: '3/4/2010 2:3',
        required: true,
        showSeconds: false
    });

    //    function ww3(date) {
    //        var y = date.getFullYear();
    //        var m = date.getMonth() + 1;
    //        var d = date.getDate();
    //        var h = date.getHours();
    //        var str = y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d) + ':' + (h < 10 ? ('0' + h) : h);
    //        return str;
    //    }
    //
    //    function w3(s) {
    //        if (!s) return new Date();
    //        var y = s.substring(0, 4);
    //        var m = s.substring(5, 7);
    //        var d = s.substring(8, 10);
    //        var h = s.substring(11, 14);
    //        var min = s.substring(15, 17);
    //        var sec = s.substring(18, 20);
    //        if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(sec)) {
    //            return new Date(y, m - 1, d, h, min, sec);
    //        } else {
    //            return new Date();
    //        }
    //    }

    //修改日历框的显示格式
    function formatter(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        month = month < 10 ? '0' + month : month;
        day = day < 10 ? '0' + day : day;
        hour = hour < 10 ? '0' + hour : hour;
        return year + "-" + month + "-" + day + "    " + hour;
    }

    function parser(s) {
        var t = Date.parse(s);
        if (!isNaN(t)) {
            return new Date(t);
        } else {
            return new Date(s + ":00:00");
        }
    }

</script>

<%--/**   修改*/--%>
<div>
    <form name="addForm" id="addForm" action="${basePath}/authorization/onLine" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>
                    获取钥匙MAC:
                </td>
                <td>
                    <input type="" onclick="keyssMAC"> <br/>
                </td>
            </tr>
            <tr>
                <td>
                    授权期限:
                </td>
                <td>
                    <input type="text" id="datetime1" class="easyui-datetimebox">
                    到
                    <input id="dt" type="text" name="birthday">

                </td>
            </tr>


</div>
</tr>
<tr>
    <td>
        授权使用人:
    </td>
    <td>
        <input type="text" name="name" id="username" value="" readonly/> <a class="easyui-linkbutton"
                                                                            onclick="$('#selectUser').window('open');">选择</a>
        <input type="hidden" name="userid" id="usercode" value=""/>

        <input name="startTime" id="startTime${rand}" data-options="formatter:formatter,parser:parser"
               class="easyui-datetimebox"/>
    </td>
</tr>
<tr>
    <td>
        锁具范围:
    </td>
    <td>
        <input type="text"> <br/>
    </td>
</tr>
<tr>
    <td>
        操作人:
    </td>
    <td>
        <input type="text"> <br/>
    </td>
</tr>
<tr>
    <td>
        工作票:
    </td>
    <td>
        <input type="file" value="ss"> <br/>
    </td>
</tr>
<tr>
    <td>
        <input type="button" value="确认授权">
    </td>
    <td>
        <input type="reset" value="重置"><br/>
    </td>
</tr>


</table>
</form>
</div>

<%--<div id="selectUser">--%>
<%--<div class="easyui-layout" fit="true">--%>
<%--<a class="easyui-linkbutton" icon="icon-ok" onclick="search();">查询</a>--%>
<%--<div region="center" border="false" style="padding: 10px;">--%>
<%--<ul id="tree" style="margin-top: 10px;"></ul>--%>
<%--</div>--%>
<%--<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">--%>
<%--<a class="easyui-linkbutton" icon="icon-ok" onclick="setToarea();">确定</a>--%>
<%--<a class="easyui-linkbutton" onclick="$('#selectUser').window('close');">关闭</a>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>




