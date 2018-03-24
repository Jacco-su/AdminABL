<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div>
    <form name="editForm" id="editForm" action="${basePath}/disa/update" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>站点名称:</td>
                <td>
                    <input  name="id" type="hidden" value="${qgdis.id}"/>
                    <input  name="dept.id" type="hidden" value="${qgdis.dept.id}"/>
                    <input name="name" class="easyui-validatebox" width="280px" value="${qgdis.name}" required="true"/>
                    </td>
            </tr>
            <tr>
                <td>所在地址:</td>
                <td>
                    <textarea name="address" class="easyui-validatebox" id="address" cols="25" rows="3"
                              required="true">${qgdis.address}</textarea>
                    <%--<input name="address" class="easyui-validatebox" id="addres" cols="30" rows="10" value="${qgdis.address}" required="true">></input>--%>
                    <input type="hidden" name="createTime" class="easyui-validatebox" id="time" cols="30" rows="10"
                           value="${qgdis.createTime}" required="true"/>
                </td>
            </tr>
            <%--<tr>--%>
            <%--<td>修改时间:</td>--%>
            <%--<td>--%>

            <%--<input name="time" class="easyui-validatebox" id="time" cols="30" rows="10"--%>
            <%--value="${qgdis.createTime}" required="true"/>--%>
            <%--</td>--%>
            <%--</tr>--%>
        </table>
    </form>
</div>