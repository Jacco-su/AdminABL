<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<form name="editForm" id="editForm" action="${basePath}/module/update" method="post">
<table class="mytable" align="center">
	<tr>
		<td>模块名称:</td>
		<td>
			<input type="hidden" name="parentId"  value="${module.parentId }"/>
			<input type="hidden" name="id"  value="${module.id }"/>
			<input name="name"  class="easyui-validatebox" required="true" value="${module.name }"/></td>
	</tr>
	<tr>
		<td>显示名称:</td>
		<td><input name="menuname"  class="easyui-validatebox" required="true" value="${module.menuname }"/></td>
	</tr>
	<tr>
		<td>图标:</td>
		<td><input name="icon"  class="easyui-validatebox" required="true" value="${module.icon }"/></td>
	</tr>
	<tr>
		<td>链接地址:</td>
		<td><input name="url"  class="easyui-validatebox" required="true" value="${module.url }"/></td>
	</tr>
	<tr>
		<td>模块类型:</td>
		<td>
		            <select name="type" id="type">
		                       <option value="1" <c:if test="${module.type==1}">selected</c:if>>导航菜单</option>
		                       <option value="2" <c:if test="${module.type==2}">selected</c:if>>操作权限</option>
		            </select>
		</td>
	</tr>
	<tr>
		<td>排序:</td>
		<td><input name="orderId"  class="easyui-validatebox" required="true" value="${module.orderId }"/></td>
	</tr>
	<tr>
		<td>所有人可见:</td>
		<td><input type="checkbox" name="alwaysShow" value="1" <c:if test="${module.alwaysShow==1}">checked="checked"</c:if> /></td>
	</tr>
</table>
</form>