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
            <td>控制器编号:</td>
            <td>${collectorea.cename}</td>
        </tr>
        <tr>
            <td>控制器ID</td>
            <td>${collectorea.ceMAC}</td>
        </tr>
        <%--<tr>--%>
        <%--<td>所属站点:</td>--%>
        <%--<td>--%>
        <%--${collectorea.}--%>
        <%--</td>--%>
        <%--</tr>--%>

        <%--<tr>--%>
        <%--<td>排序:</td>--%>
        <%--<td>${collectorea.}--%>
        <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td>添加时间</td>
            <td>${collectorea.ceDate}</td>
        </tr>
    </table>
</div>