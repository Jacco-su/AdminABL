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
            <td>授权时间:</td>
            <td>
                ${authorizationa.adate}
            </td>
        </tr>
        <tr>
            <td>钥匙:</td>
            <td>
                ${authorizationa.keyssid}
            </td>
        </tr>
        <tr>
            <td width="100">智能锁:</td>
            <td>
                ${authorizationa.locksid}
            </td>
        </tr>
        <tr>
            <td>工作票:</td>
            <td>
                ${authorizationa.workticket}
            </td>
        </tr>
    </table>
</div>