<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%--/**   修改*/--%>
<div>
    <form name="editForm" id="editForm" action="${basePath}/disa/update" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>站点名称:</td>
                <td><input type="hidden" name="id" value="${disa.id}"/>
                    <input name="name" value="${disa.name }" class="easyui-validatebox" required="true"/></td>
            </tr>

            <tr>
                <td width="100">位置</td>
                <td>
                    <input type="text" name="areaname" id="areaname" value="${disa.areaname}" readonly/><a
                        class="easyui-linkbutton" onclick="$('#selectArea').window('open');">选择</a>
                    <input type="hidden" name="areacode" id="areacode" value="${disa.areacode}"/>
                </td>
            </tr>
            <tr>
                <td>编号:</td>
                <td>
                    <input name="address" value="${disa.id}" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>排序:</td>
                <td><input name="sortorder" value="${disa.sortorder}" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
        </table>
    </form>
</div>