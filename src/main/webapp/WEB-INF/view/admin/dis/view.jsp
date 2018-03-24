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
            <td>站点名称:</td>
            u
            <td>${disa.name}</td>
        </tr>
        <tr>
            <td>位置:</td>
            <td>
                ${disa.address}
            </td>
        </tr>
        <tr>
            <td width="100">编号:</td>
            <td>
                ${disa.areaname}
            </td>
        </tr>
        <tr>
            <td>排序:</td>
            <td>${disa.sortorder}
            </td>
        </tr>
    </table>
</div>