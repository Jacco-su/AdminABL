<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>行政区域添加(自定义地区编号规则)</title>
</head>
<body>
<form name="addForm" id="addForm" action="add" method="post">
    <table class="mytable" align="center">
        <tr>
            <td>行政区域名称:</td>
            <td><input type="hidden" name="parentId" value="${parentId }"/>
                <input type="hidden" name="areacode" value="4101"/>
                <input name="name" class="easyui-validatebox" required="true"/></td>
        </tr>

        <tr>
            <td width="100">上级行政区</td>
            <td>
                <input type="text" name="areaname" id="areaname" value="" readonly/>
                <a class="easyui-linkbutton" onclick="$('#selectArea').window('open');">选择</a>
                <input type="hidden" name="areacode" id="areacode" value=""/>
            </td>
        </tr>
        <tr>
            <td>行政区编号</td>
            <td>
                <input name="areacode" class="easyui-validatebox" required="true"/></td>
        </tr>


        <tr>
            <td>级数:</td>
            <td>
                <input name="grade" class="easyui-validatebox" required="true"/>
                <input type="hidden" name="treeShow" value="1"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
