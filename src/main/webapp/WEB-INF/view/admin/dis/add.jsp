<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<div>
    <form name="addForm" id="addForm" action="${basePath}/disa/add" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>站点名称:</td>
                <td><input name="name" value="" class="easyui-validatebox" required="true"/></td>
            </tr>

            <tr>
                <td width="100">位置:</td>
                <td>
                    <input type="text" name="areaname" id="areaname" value="" readonly/><a class="easyui-linkbutton"
                                                                                           onclick="$('#selectArea').window('open');">选择</a>
                    <input type="hidden" name="areacode" id="areacode" value=""/>
                </td>
            </tr>
            <tr>
                <td>状态:</td>
                <td>
                    <input name="lock" value="" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>排序:</td>
                <td>
                    <input name="sortorder" class="easyui-validatebox" value="" required="true"/>
                </td>
            </tr>
        </table>
    </form>
</div>