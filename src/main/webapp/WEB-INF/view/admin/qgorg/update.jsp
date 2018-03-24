<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div>
    <form name="editForm" id="editForm" action="${basePath}/qgorga/update" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>区域名称:</td>
                <td><input type="hidden" name="id" value="${qgorga.id}"/>
                    <input name="name" value="${qgorga.name }" class="easyui-validatebox" required="true"/></td>
            </tr>
            <tr>
                <td>区域地址:</td>
                <td>
                    <input name="address" value="${qgorga.address}" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td width="100">管辖地区</td>
                <td>
                    <input type="text" name="areaname" id="areaname" value="${qgorga.areaname}" readonly/><a
                        class="easyui-linkbutton" onclick="$('#selectArea').window('open');">选择</a>
                    <input type="hidden" name="areacode" id="areacode" value="${qgorga.areacode}"/>
                </td>
            </tr>
            <tr>
                <td>排序:</td>
                <td><input name="sortorder" value="${qgorga.sortorder}" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
        </table>
    </form>
</div>