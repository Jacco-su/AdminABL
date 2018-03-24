<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div>
    <table class="mytable" align="center">
        <tr>
            <td>智能锁编号:</td>
            <td>${locks.lockNum}</td>
        </tr>
        <tr>
            <td>识别码:</td>
            <td>
                ${locks.lockCode}
            </td>
        </tr>
        <tr>
            <td width="100">所属站点:</td>
            <td>
                ${locks.qgdis.name}
            </td>
        </tr>
        <tr>
            <td width="100">安装时间:</td>
            <td>
                ${locks.lockDate}
            </td>
        </tr>
        <%--<tr>--%>
        <%--<td>是否可用:</td>--%>
        <%--<td>${locks.address}--%>
        <%--</td>--%>
        <%--</tr>--%>
    </table>
</div>