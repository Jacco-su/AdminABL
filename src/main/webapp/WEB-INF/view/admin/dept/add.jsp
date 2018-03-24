<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>


<div>

	<form name="addForm" id="addForm" action="${basePath}/dept/add" method="post">
<table class="mytable" align="center">
	<tr>
        <td>区域名称:</td>
        <td><input type="hidden" name="parentId" value="${parentId }"/>
			<%--<input type="hidden" name="areacode" value="4101"/>--%>
            <input name="name" class="easyui-validatebox" required="true"/></td>
	</tr>

	<tr>
		<td width="100">所属行政区:</td>
		<td>
            <input type="text" name="areaname" id="addareaname" value="" required="true"/>
			<a class="easyui-linkbutton" onclick="$('#selectArea').window('open');">选择</a>
            <input type="hidden" name="areacode" id="addareacode" value="" required="true"/>
		</td>
	</tr>

	<%--<tr>--%>
	<%--<td>所属行政区:</td>--%>
	<%--<td>--%>
	<%--&lt;%&ndash;<input name="areac" coords="" href="" alt="" value="${dept.areacode}"/>&ndash;%&gt;--%>

	<%--<input type="text" name="areaname" id="areaname" value="${area.areaname}"/>--%>
	<%--&lt;%&ndash;readonly/>&ndash;%&gt;--%>
	<%--<a class="easyui-linkbutton" id="area" onclick="$('#selectArea').window('open');">选择</a>--%>
	<%--<input type="" name="areacode" id="areacode" value="${dept.areacode}"/>--%>
	<%--</td>--%>
	<%--</tr>--%>



	<%--<tr>
            <td>排序:</td>
            <td>
            <input name="orderId"  class="easyui-validatebox" required="true"/>
            <input type="hidden" name="treeShow" value="1"/>
            </td>
        </tr>--%>
</table>
</form>

</div>

