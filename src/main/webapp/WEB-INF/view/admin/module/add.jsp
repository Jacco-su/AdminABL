<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<form name="addForm" id="addForm" action="${basePath}/module/add" method="post">
<table class="mytable" align="center">
	<tr>
		<td>模块名称:</td>
		<td><input type="hidden" name="parentId"  value="${parentId }"/>
			<input name="name"  class="easyui-validatebox" required="true"/></td>
	</tr>
	<tr>
		<td>显示名称:</td>
		<td><input name="menuname"  class="easyui-validatebox" required="true"/></td>
	</tr>
	<tr>
		<td>图标:</td>
		<td><input name="icon"  class="easyui-validatebox" required="true" value="icon-sys"/></td>
	</tr>
	<tr>
		<td>链接地址:</td>
		<td><input name="url"  class="easyui-validatebox" required="true" value="#"/></td>
	</tr>
	
	<tr>
		<td>模块类型:</td>
		<td>
		            <select name="type" id="type">
		                       <option value="1">导航菜单</option>
		                       <option value="2">操作权限</option>
		            </select>
		</td>
	</tr>	
	
	
	<tr>
		<td>排序:</td>
		<td><input name="orderId"  class="easyui-validatebox" required="true" value="000"/></td>
	</tr>
	<tr>
		<td>所有人可见:</td>
		<td><input type="checkbox" name="alwaysShow" value="1"/></td>
	</tr>
</table>
</form>