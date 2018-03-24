<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--<style>--%>
<%--#area {--%>
<%--/*background-image: ;*/--%>
<%--background-color: #8DB2E3;--%>
<%--border-radius: 2em;--%>
<%--}--%>
<%--</style>--%>
<link rel="stylesheet" type="text/css"
      href="${basePath}/js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css"
      href="${basePath}/js/easyui/themes/icon.css"/>
<%--<script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>--%>
<%--<script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>--%>
<%--<script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>--%>
<script>
    function addArea() {
        $('#tempDeptId').val('${dept.id}');
        $('#selectArea').window('open');
    }
</script>
<div>
    <form name="editForm" id="editForm" action="${basePath}/dept/update" method="post">
        <table class="mytable" align="center">
            <tr>
                <td>区域名称:</td>
                <td><input type="hidden" name="id" value="${dept.id}"/>
                    <input type="hidden" name="parentId" value="${dept.parentId}"/>
                    <input type="hidden" id="tempDeptId" value="${dept.id}"/>
                    <input name="name" value="${dept.name }" class="easyui-validatebox" required="true"/></td>
            </tr>
            <%--<tr>
                <td>排序:</td>
                <td><input name="orderId" value="${dept.orderId}" class="easyui-validatebox" required="true"/>
                <input type="hidden" name="treeShow" value="1"/>
                </td>
            </tr>--%>

            <tr>
                <td>所属行政区:</td>
                <td>
                    <%--<input name="areac" coords="" href="" alt="" value="${dept.areacode}"/>--%>

                    <input type="text" name="areaname" id="areaname" value="${area.areaname}" readonly/>
                    <%--readonly/>--%>
                    <a class="easyui-linkbutton" id="area" onclick="addArea()">选择</a>
                    <input type="hidden" name="areacode" id="areacode" value="${dept.areacode}"/>
                </td>
            </tr>


            <tr>
                <td>
                    区域编号:
                </td>
                <td>
                    <input value="${dept.areacode}" disabled="true "/>
                </td>
            </tr>
        </table>
    </form>
</div>
