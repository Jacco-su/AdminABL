<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div>
    <form name="editForm" id="editForm" action="${basePath}/orga/update" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>行政区域名称:</td>
                <td><input type="hidden" name="id" value="${orga.id}"/>
                    <input name="name" value="${orga.name }" class="easyui-validatebox" required="true"/></td>
            </tr>

            <%--<tr>--%>
            <%--<td width="100">管辖地区</td>--%>
            <%--<td>--%>
            <%--<input type="text" name="areaname" id="areaname" value="${areacode.areaname}" readonly/>--%>
            <%--<a class="easyui-linkbutton" onclick="$('#selectArea').window('open');">选择</a>--%>
            <%--<input type="hidden" name="areacode" id="areacode" value="${areacode.areacode}"/>--%>
            <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>级数:</td>
                <td><input name="grade" value="${areacode.grade}" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
        </table>
    </form>
</div>